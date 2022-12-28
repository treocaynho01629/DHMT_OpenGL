package TH3;

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;

public class bt2_a extends GLCanvas {
	
    private static int a, b, c = 0;

    private void myinit () {
    	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
	myGL.glShadeModel (GL.GL_FLAT);
    }

    public void display () {
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
	
	myGL.glPushMatrix();
		myGL.glRotatef ((float)a, 1.0f, 0.0f, 0.0f);
		myGL.glRotatef ((float)b, 0.0f, 1.0f, 0.0f);
		myGL.glRotatef ((float)c, 0.0f, 0.0f, 1.0f);
		myGL.glPushMatrix();
		myGL.glBegin(GL.GL_LINE_STRIP);
			myGL.glVertex3d(0.0, 0.0, 0.0);
			myGL.glVertex3d(0.0, 0.0, 2.0);
			myGL.glVertex3d(0.0, 0.3, 2.0);
		myGL.glEnd();
		myGL.glPopMatrix();
	myGL.glPopMatrix();	
	myGL.glFlush ();
    }

    public void myReshape (int w, int h) {
	myGL.glViewport (0, 0, w, h);
	myGL.glMatrixMode (GL.GL_PROJECTION);
	myGL.glLoadIdentity ();
	myGLU.gluPerspective (60.0, (double)w/(double)h, 1.0, 20.0);
	myGLU.gluLookAt(2.0, 3.0, 8.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0);
	myGL.glMatrixMode (GL.GL_MODELVIEW);
	myGL.glLoadIdentity ();
	myGL.glTranslatef (0.0f, 0.0f, 0.0f);
    }

    /* ARGSUSED1 */
    public void keyboard (char key, int x, int y) {
	switch (key) {
		case 'a':
		a = (a + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'A':
		a = (a - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 's':
		b = (b + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'S':
		b = (b - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'd':
		c = (c + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'D':
		c = (c - 5) % 360;
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
	myUT.glutKeyboardFunc ("keyboard");
	myUT.glutMainLoop ();
    }

    static public void main (String args[]) throws IOException {
	Frame mainFrame = new Frame ();
	mainFrame.setSize (500, 500);
	bt2_a mainCanvas = new bt2_a ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
