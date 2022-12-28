package TH3;

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;

public class bt1_d extends GLCanvas {
	
    private static int a = 0, b = 0;

    private void myinit () {
    	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
    	myGL.glShadeModel (GL.GL_FLAT);
    }

    public void display () {
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
	
	myGL.glPushMatrix ();
		myGL.glTranslatef(1.5f, 1.5f, 0.0f);
		myGL.glRotatef ((float)a, 0.0f, 0.0f, 1.0f);
		myGL.glTranslatef(0.5f, 0.5f, 0.0f);
		myGL.glRotatef ((float)b, 0.0f, 0.0f, 0.01f);
		myGL.glTranslatef(-0.25f, -0.25f, 0.0f);
	    myGL.glBegin(GL.GL_TRIANGLE_STRIP);
	    	myGL.glVertex3d(0.0, 0.0, 0.0);
	    	myGL.glVertex3d(0.5, 0.0, 0.0);
	    	myGL.glVertex3d(0.25, Math.sqrt(0.5*0.5 - 0.25*0.25), 0.0);
	    myGL.glEnd();
	myGL.glPopMatrix ();
	myGL.glFlush ();
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

    /* ARGSUSED1 */
    public void keyboard (char key, int x, int y) {
	switch (key) {
	    case 'a':
		a = (a + 5) % 360;
		b = (b + 1) % 360;
		myGL.glTranslatef(-0.01f, -0.01f, 0.0f);
		myUT.glutPostRedisplay ();
		break;
	    case 'A':
		a = (a - 5) % 360;
		b = (b - 1) % 360;
		myGL.glTranslatef(0.01f, 0.01f, 0.0f);
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
	myUT.glutKeyboardFunc ("keyboard");
	myUT.glutMainLoop ();
    }

    static public void main (String args[]) throws IOException {
	Frame mainFrame = new Frame ();
	mainFrame.setSize (408, 427);
	bt1_d mainCanvas = new bt1_d ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
