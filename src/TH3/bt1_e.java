package TH3;

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;

public class bt1_e extends GLCanvas {
	
    private static int shoulder = 0, elbow = 0;

    private void myinit () {
    	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
    	myGL.glShadeModel (GL.GL_FLAT);
    }

    public void display () {
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
	
	myGL.glPushMatrix();
		myGL.glTranslated(-0.5f, -0.5f, 0.0f); //day truc toa do ve sau
		myGL.glRotatef ((float)shoulder, 0.0f, 0.0f, 1.0f);
		//myGL.glTranslated(1.0f, 1.0f, 0.0f);
		myGL.glPushMatrix();
		myGL.glBegin (GL.GL_LINE_STRIP);
			myGL.glVertex3d(0.0, 0.0, 0.0);
			myGL.glVertex3d(0.0, 1.0, 0.0);
		myGL.glEnd ();
		myGL.glPopMatrix();
		
		myGL.glTranslated(0.0f, 1.0f, 0.0f);
		myGL.glRotatef ((float)elbow, 0.0f, 0.0f, 1.0f);
		myGL.glTranslated(0.0f, -1.0f, 0.0f);
		myGL.glPushMatrix();
		myGL.glBegin (GL.GL_LINE_STRIP);
			myGL.glVertex3d(0.0, 1.0, 0.0);
			myGL.glVertex3d(1.0, 1.0, 0.0);
		myGL.glEnd ();
		myGL.glPopMatrix();
		
		myGL.glTranslated(1.0f, 1.0f, 0.0f);
		myGL.glRotatef ((float)elbow, 0.0f, 0.0f, 1.0f);
		myGL.glTranslated(-1.0f, -1.0f, 0.0f);
		myGL.glPushMatrix();
		myGL.glBegin (GL.GL_LINE_STRIP);
			myGL.glVertex3d(1.0, 1.0, 0.0);
			myGL.glVertex3d(1.0, 0.0, 0.0);
		myGL.glEnd ();
		myGL.glPopMatrix();
		
		myGL.glTranslated(1.0f, 0.0f, 0.0f);
		myGL.glRotatef ((float)elbow, 0.0f, 0.0f, 1.0f);
		myGL.glTranslated(-1.0f, 0.0f, 0.0f);
		myGL.glPushMatrix();
		myGL.glBegin (GL.GL_LINE_STRIP);
			myGL.glVertex3d(1.0, 0.0, 0.0);
			myGL.glVertex3d(0.0, 0.0, 0.0);
		myGL.glEnd ();
		myGL.glPopMatrix();
		
	myGL.glPopMatrix();	
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
	    case 's':
		shoulder = (shoulder + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'S':
		shoulder = (shoulder - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'e':
		elbow = (elbow + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'E':
		elbow = (elbow - 5) % 360;
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
	bt1_e mainCanvas = new bt1_e ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
