package TH2;

import java.awt.Frame;
import java.io.IOException;

import jgl.GL;
import jgl.GLCanvas;

public class bt2 extends GLCanvas {
	
double x, y;
double r = 0.2;
double r2 = 0.3;
int count = 3;
int rate = 1;
double t = 0;

public void display () {
	/* clear all pixels */
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);

	//draw line with points calculate on math and rate
	
	for (int i = 0; i < 3 * rate; i++) {
		
		myGL.glColor3f (1.0f, 1.0f, 1.0f);
		myGL.glBegin (GL.GL_TRIANGLE_FAN);
		
		myGL.glVertex3d(x + r2 * Math.cos(t), y + r2 * Math.sin(t), 0.0f); //diem 1
		System.out.println(t + "k");
		
		for (int j = -2 + 4 * i; j < count + 4 * i; j++) {
			
			t = (Math.PI / (2 * rate * count)) * j;
			myGL.glVertex3d(x + r * Math.cos(t), y + r * Math.sin(t), 0.0f);
			System.out.println(t);
		}
		
		myGL.glEnd (); 
		
		
		myGL.glColor3f(0.0f, 1.0f, 0.0f);
		
		myGL.glBegin(GL.GL_TRIANGLE_FAN);
		
		myGL.glVertex3d(x + r * Math.cos(t), y + r * Math.sin(t), 0.0f); //diem 1
		
		for (int j = 4 * i; j < count + 2 + 4 * i; j++) {
			
			t = (Math.PI / (2 * rate * count)) * j;
			myGL.glVertex3d(x + r2 * Math.cos(t), y + r2 * Math.sin(t), 0.0f);
		}
		
		myGL.glEnd ();
	}
	
	myGL.glFlush ();
    }

	public void keyboard (char key, int x, int y) {
		switch (key) {
		
		    case 27:
			System.exit(0);
		    default:
			break;
			
		    case 'a':
		    	
		    	if(rate == 1) {
		    		
		    		System.out.println("No");
		    		break;
		    	} else {
		    		
		    		rate--;
		 		    myUT.glutPostRedisplay();
		 		   break;
		    	}
		    
		    case 'A':
		    rate++;
		    myUT.glutPostRedisplay();
		    break;
			    
		    case 'd':
		    	
		    	if(r == 0.01) {
		    		
		    		System.out.println("No");
		    		break;
		    	} else {
		    		
		    		r-=0.01;
		 		    myUT.glutPostRedisplay();
		 		   break;
		    	}
		    
		    case 'D':
		    r+=0.01;
		    myUT.glutPostRedisplay();
		    break;
		}
    }

    private void myinit () {
	/* select clearing color */
	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);

	/* initialize viewing values */
	myGL.glMatrixMode (GL.GL_PROJECTION);
	myGL.glLoadIdentity ();
	myGL.glOrtho (-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
    }

    /* 
     * Declare initial window size, position, and display mode
     * (single buffer and RGBA).  Open window with "hello"
     * in its title bar.  Call initialization routines.
     * Register callback function to display graphics.
     * Enter main loop and process events.
     */
    public void init () {
    	myUT.glutInitWindowSize (500, 500);
    	myUT.glutInitWindowPosition (0, 0);
    	myUT.glutCreateWindow (this);
    	myinit ();
    	myUT.glutDisplayFunc ("display");
    	myUT.glutKeyboardFunc ("keyboard");
    	myUT.glutMainLoop ();
    }
    
    static public void main (String args[]) throws IOException {
		Frame mainFrame = new Frame ();
		mainFrame.setSize (508, 527);
		bt2 mainCanvas = new bt2();
		mainCanvas.init();
		mainFrame.add (mainCanvas);
		mainFrame.setVisible (true);
    }
}

