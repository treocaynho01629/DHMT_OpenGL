package TH3;

/*
 * smooth.java
 * This program demonstrates smooth shading.
 * A smooth shaded polygon is drawn in a 2-D projection.
 */

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;

public class smooth extends GLCanvas {

    private void myinit () {
    	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
	myGL.glShadeModel (GL.GL_SMOOTH);
    }

    private void triangle () {
    	myGL.glBegin (GL.GL_TRIANGLES);
	    myGL.glColor3f (1.0f, 0.0f, 0.0f);
	    myGL.glVertex2f (5.0f, 5.0f);
	    myGL.glColor3f (0.0f, 1.0f, 0.0f);
	    myGL.glVertex2f (25.0f, 5.0f);
	    myGL.glColor3f (0.0f, 0.0f, 1.0f);
	    myGL.glVertex2f (5.0f, 25.0f);
	myGL.glEnd ();
    }

    public void display () {
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
	triangle ();
	myGL.glFlush ();
    }

    public void myReshape (int w, int h) {
        myGL.glViewport (0, 0, w, h);
        myGL.glMatrixMode (GL.GL_PROJECTION);
        myGL.glLoadIdentity ();
        if (w <= h) {
            myGLU.gluOrtho2D (0.0, 30.0, 0.0, 30.0 * (float)h /(float)w);
        } else {
            myGLU.gluOrtho2D (0.0, 30.0 * (float)w/(float)h, 0.0, 30.0);
        }
        myGL.glMatrixMode (GL.GL_MODELVIEW);
    }

    /* ARGSUSED1 */
    public void keyboard (char key, int x, int y) {
	switch (key) {
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
	myUT.glutKeyboardFunc ("keyboard");
	myUT.glutMainLoop ();
    }

    static public void main (String args[]) throws IOException {
	Frame mainFrame = new Frame ();
	mainFrame.setSize (508, 527);
	smooth mainCanvas = new smooth ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
