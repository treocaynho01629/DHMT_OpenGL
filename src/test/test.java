package test;

/*
 *  bezcurve.java			
 *  This program uses evaluators to draw a Bezier curve.
 */

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;

public class test extends GLCanvas {
	
	private final double[] testponts = {0.0f, 1.0f, 0.0f, 0.0f};
	private final double[] testponts1 = {-1.0f, 0.0f, 0.0f, 0.0f};
	
	 public void display () {
		/* clear all pixels */
		myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
		myGL.glColor3f (1.0f, 1.0f, 1.0f);
		myGL.glClipPlane(GL.GL_CLIP_PLANE0, testponts);
		myGL.glEnable(GL.GL_CLIP_PLANE0);
		myGL.glClipPlane(GL.GL_CLIP_PLANE1, testponts1);
		myGL.glEnable(GL.GL_CLIP_PLANE1);
		myUT.glutWireSphere(1.0, 10, 10);
		myGL.glFlush ();
    }

    private void myinit () {
    	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
    	myGL.glMatrixMode (GL.GL_PROJECTION);
    	myGL.glLoadIdentity ();
    	myGL.glOrtho (-5.0f, 5.0f, -5.0f, 5.0f, -5.0f, 5.0f);
    	myGLU.gluPerspective(30.0f, 2.0f, 1.0f, 20.0f);
    	float h = (float) (((1.0f + 20.0f) / 2) * Math.tan(30.0f / 2.0f * 3.1415926f / 180.0f));
    	float w = h * 2.0f;
//    	myGL.glFrustum(-0.536f, 0.536f, -0.268f, 0.268f, 1.0f, 20.0f); //Đề
//    	myGL.glOrtho(-5.627f, 5.627f, -2.813f, 2.813f, 1.0f, 20.0f);
//    	myGL.glOrtho(-w, w, -h, h, 1.0f, 20.0f);
    }

    public void init () {
    	myUT.glutInitWindowSize (500, 500);
    	myUT.glutInitWindowPosition (0, 0);
    	myUT.glutCreateWindow (this);
    	myinit ();
    	myUT.glutDisplayFunc ("display");
    	myUT.glutKeyboardFunc ("keyboard");
    	myUT.glutMainLoop ();
    }
    
    public void keyboard (char key, int x, int y) {
		switch (key) {
		    case 27:
			System.exit(0);
		    default:
			break;
		}
    }
	    
    static public void main (String args[]) throws IOException {
			Frame mainFrame = new Frame ();
			mainFrame.setSize (508, 527);
			test mainCanvas = new test();
			mainCanvas.init();
			mainFrame.add (mainCanvas);
			mainFrame.setVisible (true);
    }

}
