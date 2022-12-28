package TH3;

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;
import jgl.GLUT;

public class bt1_b extends GLCanvas {
	
    private static int a = 0;
    private static float b = 0;
    private static boolean l = true;

    private void myinit () {
    	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
	myGL.glShadeModel (GL.GL_FLAT);
    }

    public void display () {
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
	
	myGL.glPushMatrix();
		myGL.glPushMatrix ();
		myGL.glRotatef ((float)a, 0.0f, 0.0f, 1.0f);
		myGL.glTranslatef(0.0f, 2.0f, 0.0f);
	    myUT.glutWireSphere(0.25, 10, 8);
	    myGL.glPopMatrix ();
	
	    myGL.glPushMatrix ();
	    myGL.glTranslatef(0.0f + b, 0.0f, 0.0f);
		myGL.glScalef (1.5f, 1.0f, 1.0f);
		myUT.glutWireCube (1.0);
	    myGL.glPopMatrix ();
    myGL.glPopMatrix();	
	myGL.glFlush ();
    }
    
    public void bounceDisplay () {
    	
    	if (l) {
    		
    		if (b < -(0.14 * 12)) {
    			
    			l = false;
    		}
    		a = (a + 5) % 360;
			b -= 0.14;
			myUT.glutPostRedisplay ();
    	} else if (!l) {
    		
    		if (b > (0.14 * 12)){
    			
    			l = true;
    		}
    		a = (a - 5) % 360;
			b += 0.14;
			myUT.glutPostRedisplay ();
    	}
    }

    public void myReshape (int w, int h) {
	myGL.glViewport (0, 0, w, h);
	myGL.glMatrixMode (GL.GL_PROJECTION);
	myGL.glLoadIdentity ();
	myGLU.gluPerspective (60.0, (double)w/(double)h, 1.0, 20.0);
	myGL.glMatrixMode (GL.GL_MODELVIEW);
	myGL.glLoadIdentity ();
	myGL.glTranslatef (0.0f, 0.0f, -5.0f);
    }

    public void mouse (int button, int state, int x, int y) {
    	
    	if (button == GLUT.GLUT_LEFT_BUTTON) {
    	    if (state == GLUT.GLUT_DOWN) {
    		myUT.glutIdleFunc ("bounceDisplay");
    	    }
    	} else if (button == GLUT.GLUT_MIDDLE_BUTTON) {
    	    if (state == GLUT.GLUT_DOWN) {
    		myUT.glutIdleFunc (null);
    	    }
    	}
    }
    
    /* ARGSUSED1 */
    public void keyboard (char key, int x, int y) {
	switch (key) {
	    case 's':
	    if (b < -(0.14 * 13)) {
	    	
	    } else {
	    	
	    	a = (a + 5) % 360;
			b -= 0.14;
			myUT.glutPostRedisplay ();
	    }
		break;
	    case 'S':
	    if (b > (0.14 * 13)) {
	    	
	    } else {
	    	
	    	a = (a - 5) % 360;
			b += 0.14;
			myUT.glutPostRedisplay ();
	    }
		break;
	    case 27:
		System.exit(0);
	    default:
		break;
	}
    }

    public void init () {
	myUT.glutInitWindowSize (400, 400);
	myUT.glutInitWindowPosition (0, 0);
	myUT.glutCreateWindow (this);
	myinit ();
	myUT.glutDisplayFunc ("display");
	myUT.glutReshapeFunc ("myReshape");
	myUT.glutMouseFunc ("mouse");
	myUT.glutKeyboardFunc ("keyboard");
	myUT.glutMainLoop ();
    }

    static public void main (String args[]) throws IOException {
	Frame mainFrame = new Frame ();
	mainFrame.setSize (700, 500);
	bt1_b mainCanvas = new bt1_b ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
