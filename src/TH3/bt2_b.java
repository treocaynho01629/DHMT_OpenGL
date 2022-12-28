package TH3;

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;

public class bt2_b extends GLCanvas {
	
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
		myGL.glBegin(GL.GL_LINE_LOOP);
			myGL.glVertex3d(-2.0, -0.5, 0.0);
			myGL.glVertex3d(2.0, -0.5, 0.0);
			myGL.glVertex3d(4.0, 0.2, 0.0);
			myGL.glVertex3d(3.5, 0.2, 0.0);
			myGL.glVertex3d(3.0, 1.0, 0.0);
			myGL.glVertex3d(-1.5, 1.0, 0.0);
			myGL.glVertex3d(-2.0, 2.0, 0.0);
			myGL.glVertex3d(-3.0, 2.0, 0.0);
			myGL.glVertex3d(-2.75, -0.2, 0.0);
			myGL.glVertex3d(-2.4, -0.4, 0.0);
		myGL.glEnd();
		myGL.glPopMatrix();
		
		myGL.glPushMatrix();
		myGL.glBegin(GL.GL_LINE_LOOP);
			myGL.glVertex3d(-1.25, 0.0, 0.0);
			myGL.glVertex3d(-1.5, 0.0, 3.0);
			myGL.glVertex3d(-0.5, 0.0, 3.0);
			myGL.glVertex3d(1.0, 0.0, 0.0);
			myGL.glVertex3d(-0.5, 0.0, -3.0);
			myGL.glVertex3d(-1.5, 0.0, -3.0);
		myGL.glEnd();
		myGL.glPopMatrix();
	myGL.glPopMatrix();	
	myGL.glFlush ();
    }

    public void myReshape (int w, int h) {
	myGL.glViewport (0, 0, w, h);
	myGL.glMatrixMode (GL.GL_PROJECTION);
	myGL.glLoadIdentity ();
	myGLU.gluPerspective (50.0, (double)w/(double)h, 1.0, 20.0);
	myGLU.gluLookAt(1.0, 3.0, 8.0, 0.0, 0.0, 0.0, -1.0, 2.0, 0.0);
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
	myUT.glutInitWindowSize (960, 540);
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
	mainFrame.setSize (960, 540);
	bt2_b mainCanvas = new bt2_b ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
