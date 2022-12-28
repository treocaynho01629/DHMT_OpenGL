package TH2;

import java.awt.Frame;
import java.io.IOException;

import jgl.GL;
import jgl.GLCanvas;

public class bt2_2 extends GLCanvas {
	
double x, y;
double r = 0.2;
int rate = 8;

public void display () {
	/* clear all pixels */
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);

	//draw line with points calculate on math and rate
	
	myGL.glColor3f (1.0f, 1.0f, 1.0f);
	
	myGL.glBegin (GL.GL_TRIANGLE_STRIP);
		
		for (int i = 0; i < rate + 1; i++) {
			
			double t = ((2 * Math.PI) / rate) * i;
			myGL.glVertex3d(x + r * Math.cos(t), y + r * Math.sin(t), 0.0f);
			
			t = ((2 * Math.PI) / rate) * (i + 1) ;
			myGL.glVertex3d(x + r * Math.cos(t), y + r * Math.sin(t), 0.0f);
			
			t = ((2 * Math.PI) / rate) * (i + 1) ;
			myGL.glVertex3d(x + (r * 2) * Math.cos(t), y + (r * 2) * Math.sin(t), 0.0f);
		}
		
		
	myGL.glEnd (); 
	
	myGL.glBegin (GL.GL_LINE_LOOP);
	
	for (int i = 0; i < rate; i++) {
		
		double t = ((2 * Math.PI) / rate) * i;
		myGL.glVertex3d(x + (r * 2) * Math.cos(t), y + (r * 2) * Math.sin(t), 0.0f);
	}
	
	myGL.glEnd ();

	
	
	myGL.glFlush ();
    }

	public void keyboard (char key, int x, int y) {
		switch (key) {
		
		    case 27:
			System.exit(0);
		    default:
			break;
			
		    case 'a':
		    	
		    	if(rate == 3) {
		    		
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
	myGL.glOrtho (-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f); //cam ?
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
		bt2_2 mainCanvas = new bt2_2();
		mainCanvas.init();
		mainFrame.add (mainCanvas);
		mainFrame.setVisible (true);
    }
}

