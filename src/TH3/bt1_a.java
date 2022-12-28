package TH3;

/*
 * robot.java
 * This program shows how to composite modeling transformations
 * to draw translated and rotated hierarchical models.
 * Interaction:  pressing the s and e keys (shoulder and elbow)
 * alters the rotation of the robot arm.
 */

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;
import jgl.GLUT;

public class bt1_a extends GLCanvas {
	
    private static int a = 0;
    private static float b, c = 0;

    private void myinit () {
    	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
    	myGL.glShadeModel (GL.GL_FLAT);
    }

    public void display () {
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
	
	myGL.glBegin(GL.GL_QUADS);
		myGL.glColor3d(0.0, 1.0, 0.0);
		myGL.glVertex3d(-1.0, -1.0, 0.0);
		myGL.glVertex3d(1.2, -1.0, 0.0);
		myGL.glVertex3d(3.0, 2.0, 0.0);
		myGL.glVertex3d(1.0, 2.0, 0.0);
		
	myGL.glEnd();
	
	myGL.glColor3d(1.0, 1.0, 1.0);
	myGL.glPushMatrix ();
		myGL.glTranslatef(2.0f, 2.0f, 0.0f);
		myGL.glTranslatef(0.0f + b, 0.0f + c, 0.0f);
		myGL.glRotatef ((float)a, 1.0f, 0.0f, -1.0f);
	    myUT.glutWireSphere(0.5, 10, 8);
	myGL.glPopMatrix ();
	myGL.glFlush ();
    }
    
    public void rollDisplay () {
    	
    	a = ((a - 3) % 360) * 10;
		b -= 0.02 * 10f;
		c -= 0.03 * 10f;
		
		if (c < -2.4f) {
			
			b = 0.0f;
			c = 0.0f;
		}
		myUT.glutPostRedisplay ();
    }

    public void myReshape (int w, int h) {
	myGL.glViewport (0, 0, w, h);
	myGL.glMatrixMode (GL.GL_PROJECTION);
	myGL.glLoadIdentity ();
	myGLU.gluPerspective (60.0, (double)w/(double)h, 1.0, 20.0);
	myGLU.gluLookAt(0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	myGL.glMatrixMode (GL.GL_MODELVIEW);
	myGL.glLoadIdentity ();
	myGL.glTranslatef (-1.0f, 0.0f, -4.0f);
    }
    
    public void mouse (int button, int state, int x, int y) {
    	
    	if (button == GLUT.GLUT_LEFT_BUTTON) {
    	    if (state == GLUT.GLUT_DOWN) {
    		myUT.glutIdleFunc ("rollDisplay");
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
		a = (a - 3) % 360;
		b -= 0.02f;
		c -= 0.03f;
		myUT.glutPostRedisplay ();
		break;
	    case 'A':
		a = (a + 3) % 360;
		b += 0.02f;
		c += 0.03f;
		myUT.glutPostRedisplay ();
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
	mainFrame.setSize (400, 427);
	bt1_a mainCanvas = new bt1_a ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
