package TH3;

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;
import jgl.GLUT;

public class test extends GLCanvas {
	
    private static float a = 10;
    private static float b = 0.25f;
    private static float c = 1.25f;
    private static int delay = 0;
    private static boolean fall = true;

    private void myinit () {
    	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
	myGL.glShadeModel (GL.GL_FLAT);
    }

    public void display () {
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
	
	myGL.glPushMatrix();
		myGL.glPushMatrix();
		myGL.glBegin(GL.GL_POLYGON);
			myGL.glColor3f(1.0f, 1.0f, 0.0f);
			myGL.glVertex3d(-4.0, 0.0, 4.0);
			myGL.glVertex3d(-4.0, 0.0, -4.0);
			myGL.glVertex3d(4.0, 0.0, -4.0);
			myGL.glVertex3d(4.0, 0.0, 4.0);
		myGL.glEnd();
		myGL.glPopMatrix();
		
		myGL.glPushMatrix();
			myGL.glTranslatef(0.0f, a + 2.0f, 0.0f);
			myGL.glColor3f(0.0f, 1.0f, 0.0f);
			myGL.glScalef(b * 1.0f, c * 1.0f, b * 1.0f);
			myUT.glutWireSphere(2.0, 10, 10);
	//		myGL.glScalef(b * 1.0f, c * 1.0f, b * 1.0f);
		myGL.glPopMatrix();
	myGL.glPopMatrix();	
	myGL.glFlush ();
    }
    
    public void bounceDisplay () {
    	
    	if (fall) {
    		
    		if (a <= -0.99f) {
        		
    			a = -1.0f;
        		fall = false;
    		} else if (a <= 0) {
    			
    			a -= 0.33f;
    			b = 1.25f;
    			c = 0.25f;
    		} else {
    			
    			a -= 1.0f;
    			b = 0.25f;
    			c = 1.25f;
        		myUT.glutPostRedisplay ();
    		}
    	} else if (!fall) {
    		
    		if (a >= 8.99f) {
    			
    			a = 9.0f;
    			fall = true;
    		} else if (a >= 8) {
    			
    			a += 0.33f;
    			b = 1.0f;
    			c = 1.0f;
    		} else {
    			
    			a += 1.0f;
        		b = 0.25f;
    			c = 1.25f;
        		myUT.glutPostRedisplay ();
    		}
    	}
    }

    public void myReshape (int w, int h) {
	myGL.glViewport (0, 0, w, h);
	myGL.glMatrixMode (GL.GL_PROJECTION);
	myGL.glLoadIdentity ();
	myGLU.gluPerspective (60.0, (double)w/(double)h, 1.0, 20.0);
	myGLU.gluLookAt(0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	myGL.glMatrixMode (GL.GL_MODELVIEW);
	myGL.glLoadIdentity ();
	myGL.glTranslatef (0.0f, -6.0f, -15.0f);
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
		case 'a':
		a += 0.05f;
		myUT.glutPostRedisplay ();
		break;
	    case 'A':
    	a -= 0.05f;
		myUT.glutPostRedisplay ();
		break;
	    case 27:
		System.exit(0);
	    default:
		break;
	}
    }

    public void init () {
	myUT.glutInitWindowSize (500, 500);
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
	mainFrame.setSize (500, 600);
	test mainCanvas = new test ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
