package TH4;

import java.awt.Color;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import javax.imageio.ImageIO;

import jgl.GL;
import jgl.GLCanvas;
import jgl.GLUT;

public class my_robot2 extends GLCanvas {
	
    private static int x, y, z = 0;
    private static int f, v = 0; //chan phai
    private static int g, b = 0; //tay phai
    private static int j, n = 0; //chan trai
    private static int k, m = 0; //tay trai
    private static int o, p = 0; //co
    private static int e = 0; //nguc
    private static int r = 0; //mong
    private static boolean l = true;
    
    private void myinit () {
    	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
    	myGL.glShadeModel (GL.GL_FLAT);
    }

    public void display () {
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
	
	myGL.glPushMatrix();
//		myGL.glRotatef ((float)x, 1.0f, 0.0f, 0.0f);
//		myGL.glRotatef ((float)y, 0.0f, 1.0f, 0.0f);
//		myGL.glRotatef ((float)z, 0.0f, 0.0f, 1.0f);
		//body
		
		//nguc
		myGL.glTranslatef (0.0f, 0.75f, 0.25f);
		myGL.glPushMatrix ();
		
			//xoay nguc
			myGL.glRotatef ((float)e, 0.0f, 1.0f, 0.0f);
		
			myGL.glPushMatrix ();
			myGL.glScalef (3.0f, 1.5f, 1.5f);
			myUT.glutWireCube (1.0);
			myGL.glPopMatrix ();
			
			//co
			myGL.glTranslatef (0.0f, 0.75f, 0.75f);
			myGL.glPushMatrix ();
			myGL.glRotatef (25.0f, 1.0f, 0.0f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.0f, 0.5f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, 0.325f, 0.0f);
				myUT.glutWireSphere(0.35, 8, 8);
				myGL.glTranslatef (0.0f, -0.325f, 0.0f);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			myGL.glTranslatef (0.0f, -0.75f, -0.75f);
			
			//dau
			
			myGL.glTranslatef (0.0f, 1.75f, 1.25f);
			
			myGL.glPushMatrix ();
			
			//xoay co ngay dau
			myGL.glTranslatef (0.0f, -0.75f, 0.0f);
			myGL.glRotatef ((float)o, 1.0f, 0.0f, 0.0f);
			myGL.glRotatef ((float)p, 0.0f, 1.0f, 0.0f);
			myGL.glTranslatef (0.0f, 0.75f, 0.0f);
			
				myGL.glPushMatrix ();
				myGL.glScalef (1.5f, 1.5f, 1.5f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				//mat
				myGL.glPushMatrix ();
				myGL.glTranslatef (-0.375f, 0.3f, 0.75f);
				myUT.glutWireSphere (0.25, 6, 6);
				myGL.glTranslatef (0.375f, -0.3f, -0.75f);
				
				myGL.glTranslatef (0.375f, 0.3f, 0.75f);
				myUT.glutWireSphere (0.25, 6, 6);
				myGL.glTranslatef (-0.375f, -0.3f, -0.75f);
				myGL.glPopMatrix ();
				
				//mom :v
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.6f, 0.45f);
				myGL.glScalef (1.8f, 0.5f, 0.8f);
				myUT.glutWireCube (1.0);
				myGL.glTranslatef (0.0f, 0.6f, -0.45f);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			myGL.glTranslatef (0.0f, -1.75f, -1.25f);
			
			//tay phai
			//vai	
			myGL.glTranslatef (-1.75f, 0.5f, 0.0f);
			
			myGL.glPushMatrix ();
			myGL.glScalef (0.75f, 0.5f, 0.5f);
			myUT.glutWireCube (1.0);
			myGL.glPopMatrix ();
			
			myGL.glPushMatrix ();
				
				//rotate vai
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glRotatef ((float)g, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myUT.glutWireSphere(0.35, 8, 8);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (1.75f, -0.5f, 0.0f);
				
				//bap tay
				myGL.glTranslatef (-2.125f, -0.175f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.5f, 0.5f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.75f, 0.0f);
				myUT.glutWireSphere(0.35, 8, 8);
				myGL.glTranslatef (0.0f, 0.75f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (2.125f, 0.175f, 0.0f);
				
				//canh tay + ban tay
				
				//rotate khuyu tay
				myGL.glTranslatef (-2.125f, -1.0f, 0.0f);
				myGL.glRotatef ((float)b, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (2.125f, 1.0f, 0.0f);
				
				myGL.glTranslatef (-2.125f, -1.45f, 0.0f);
				myGL.glRotatef (-45.0f, 1.0f, 0.0f, 0.0f);
				
				myGL.glPushMatrix ();
				myGL.glScalef (1.0f, 1.0f, 1.0f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.0f, 0.0f);
				myGL.glScalef (1.25f, 1.0f, 1.25f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (-0.15f, -1.875f, 0.0f);
				myGL.glScalef (0.75f, 0.75f, 0.75f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				myGL.glTranslatef (2.125f, 1.45f, 0.0f);
			myGL.glPopMatrix ();
			myGL.glTranslatef (1.75f, -0.5f, 0.0f);	
			
			//tay trai
			//vai	
			myGL.glTranslatef (1.75f, 0.5f, 0.0f);
			
			myGL.glPushMatrix ();
			myGL.glScalef (0.75f, 0.5f, 0.5f);
			myUT.glutWireCube (1.0);
			myGL.glPopMatrix ();
			
			myGL.glPushMatrix ();
			
				//rotate vai
				myGL.glTranslatef (+0.375f, 0.0f, 0.0f);
				myGL.glRotatef ((float)j, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
			
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myUT.glutWireSphere(0.35, 8, 8);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-1.75f, -0.5f, 0.0f);
				
				//bap tay
				myGL.glTranslatef (2.125f, -0.175f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.5f, 0.5f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.75f, 0.0f);
				myUT.glutWireSphere(0.35, 8, 8);
				myGL.glTranslatef (0.0f, 0.75f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-2.125f, 0.175f, 0.0f);
				
				//canh tay + ban tay
				
				//rotate khuyu tay
				myGL.glTranslatef (2.125f, -1.0f, 0.0f);
				myGL.glRotatef ((float)n, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-2.125f, 1.0f, 0.0f);
				
				myGL.glTranslatef (2.125f, -1.45f, 0.0f);
				myGL.glRotatef (-45.0f, 1.0f, 0.0f, 0.0f);
				
				myGL.glPushMatrix ();
				myGL.glScalef (1.0f, 1.0f, 1.0f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.0f, 0.0f);
				myGL.glScalef (1.25f, 1.0f, 1.25f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.15f, -1.875f, 0.0f);
				myGL.glScalef (0.75f, 0.75f, 0.75f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-2.125f, 1.45f, 0.0f);
			myGL.glPopMatrix ();
			myGL.glTranslatef (-1.75f, -0.5f, 0.0f);
				
		myGL.glPopMatrix ();
		myGL.glTranslatef (0.0f, -0.75f, -0.25f);
		
		
		//bung
		myGL.glTranslatef (0.0f, -0.625f, 0.125f);
		myGL.glPushMatrix ();
		myGL.glScalef (2.5f, 1.25f, 1.25f);
		myUT.glutWireCube (1.0);
		myGL.glPopMatrix ();
		myGL.glTranslatef (0.0f, 0.625f, -0.125f);
		
		//mong
		myGL.glTranslatef (0.0f, -1.625f, 0.0f);
		myGL.glPushMatrix ();
		
			//xoay mong
			myGL.glRotatef ((float)r, 0.0f, 1.0f, 0.0f);
		
			myGL.glPushMatrix ();
			myGL.glScalef (1.5f, 1.0f, 1.0f);
			myUT.glutWireCube (1.0);
			myGL.glPopMatrix ();
			
			//chan phai
			myGL.glTranslatef (-1.125f, -0.25f, 0.0f);
			
			myGL.glPushMatrix ();
			myGL.glScalef (0.75f, 0.5f, 0.5f);
			myUT.glutWireCube (1.0);
			myGL.glPopMatrix ();
			
			//mong
			myGL.glPushMatrix ();
			myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
			myGL.glRotatef (-15.0f, 1.0f, 0.0f, 0.0f);
			myGL.glTranslatef (0.375f, 0.0f, 0.0f);
			
				//rotate chan
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glRotatef ((float)f, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myUT.glutWireSphere(0.35, 8, 8);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (1.75f, -0.5f, 0.0f);
				
				//bap chan
				myGL.glTranslatef (-2.125f, -0.15f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.25f, 0.5f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.5f, 0.0f);
				myUT.glutWireSphere(0.35, 8, 8);
				myGL.glTranslatef (0.0f, 0.5f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (2.125f, 0.15f, 0.0f);
				
				//cang chan + ban chan
				myGL.glTranslatef (-2.125f, -0.65f, 0.0f);
				myGL.glRotatef (20.0f, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (2.125f, 0.65f, 0.0f);
				
				//rotate dau goi
				myGL.glTranslatef (-2.125f, -0.65f, 0.0f);
				myGL.glRotatef ((float)v, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (2.125f, 0.65f, 0.0f);
				
				myGL.glTranslatef (-2.125f, -1.2f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (1.0f, 1.0f, 1.0f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.25f, 0.0f);
				myGL.glScalef (1.25f, 1.5f, 1.25f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.75f, 0.35f);
				myGL.glScalef (1.25f, 0.5f, 1.6f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				myGL.glTranslatef (2.125f, 1.2f, 0.0f);
				
			myGL.glPopMatrix ();
			myGL.glTranslatef (1.125f, 0.25f, 0.0f);
			
			//chan trai
			myGL.glTranslatef (1.125f, -0.25f, 0.0f);
			
			myGL.glPushMatrix ();
			myGL.glScalef (0.75f, 0.5f, 0.5f);
			myUT.glutWireCube (1.0);
			myGL.glPopMatrix ();
			
			//mong
			myGL.glPushMatrix ();
			myGL.glTranslatef (0.375f, 0.0f, 0.0f);
			myGL.glRotatef (-15.0f, 1.0f, 0.0f, 0.0f);
			myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
			
				//rotate chan
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myGL.glRotatef ((float)k, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
			
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myUT.glutWireSphere(0.35, 8, 8);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-1.75f, -0.5f, 0.0f);
				
				//bap chan
				myGL.glTranslatef (2.125f, -0.15f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.25f, 0.5f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.5f, 0.0f);
				myUT.glutWireSphere(-0.35, 8, 8);
				myGL.glTranslatef (0.0f, 0.5f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-2.125f, 0.15f, 0.0f);
				
				//cang chan + ban chan
				myGL.glTranslatef (2.125f, -0.65f, 0.0f);
				myGL.glRotatef (20.0f, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-2.125f, 0.65f, 0.0f);
				
				//rotate dau goi
				myGL.glTranslatef (2.125f, -0.65f, 0.0f);
				myGL.glRotatef ((float)m, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-2.125f, 0.65f, 0.0f);
				
				myGL.glTranslatef (2.125f, -1.2f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (1.0f, 1.0f, 1.0f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.25f, 0.0f);
				myGL.glScalef (1.25f, 1.5f, 1.25f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.75f, 0.35f);
				myGL.glScalef (1.25f, 0.5f, 1.6f);
				myUT.glutWireCube (1.0);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-2.125f, 1.2f, 0.0f);
				
			myGL.glPopMatrix ();
			myGL.glTranslatef (-1.125f, 0.25f, 0.0f);
			
		myGL.glPopMatrix ();
		myGL.glTranslatef (0.0f, 1.625f, 0.0f);
		
	myGL.glPopMatrix();	
	myGL.glFlush ();
    }

    public void robotDisplay () {
    
    	if (l) {
    		
    		if (g > 25) {
    			
    			l = false;
    		}
    		
    		//tay phai
    		g = (g + 15) % 360;
    		b = (b + 10) % 360;
    		
    		//tay trai
    		j = (j - 15) % 360;
    		n = (n - 10) % 360;
    		
    		//chan phai
    		f = (f - 15) % 360;
    		v = (v - 10) % 360;
    		
    		//chan trai
    		k = (k + 15) % 360;
    		m = (m + 10) % 360;
    		
    		//nguc
    		e = (e - 2) % 360;
    		
    		//mong
    		r = (r + 2) % 360;
    		
    		//dau
    		o = (o + 1) % 360;
    		p = (p - 5) % 360;
    		
			myUT.glutPostRedisplay ();
    	} else if (!l) {
    		
    		if (g < -15){
    			
    			l = true;
    		}
    		
    		//tay phai
    		g = (g - 15) % 360;
    		b = (b - 10) % 360;
    		
    		//tay trai
    		j = (j + 15) % 360;
    		n = (n + 10) % 360;
    		
    		//chan phai
    		f = (f + 15) % 360;
    		v = (v + 10) % 360;
    		
    		//chan trai
    		k = (k - 15) % 360;
    		m = (m - 10) % 360;
    		
    		//nguc
    		e = (e + 2) % 360;
    		
    		//mong
    		r = (r - 2) % 360;
    		
    		//dau
    		o = (o - 1) % 360;
    		p = (p + 5) % 360;
    		
			myUT.glutPostRedisplay ();
    	}
    }
    
    public void myReshape (int w, int h) {
	myGL.glViewport (0, 0, w, h);
	myGL.glMatrixMode (GL.GL_PROJECTION);
	myGL.glLoadIdentity ();
	myGLU.gluPerspective (90.0, (double)w/(double)h, 1.0, 20.0);
	myGLU.gluLookAt(1.0, 0.0, 8.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	myGL.glMatrixMode (GL.GL_MODELVIEW);
	myGL.glLoadIdentity ();
	myGL.glTranslatef (0.0f, 0.0f, 0.0f);
    }

    public void mouse (int button, int state, int x, int y) {
    	if (button == GLUT.GLUT_LEFT_BUTTON) {
    	    if (state == GLUT.GLUT_DOWN) {
    		myUT.glutIdleFunc ("robotDisplay");
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
		x = (x + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'A':
		x = (x - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 's':
		y = (y + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'S':
		y = (y - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'd':
		z = (z + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'D':
		z = (z - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    	
	    case 'g':
		g = (g + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'G':
		g = (g - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'b':
		b = (b + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'B':
		b = (b - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'j':
		j = (j + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'J':
		j = (j - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'n':
		n = (n + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'N':
		n = (n - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		
	    case 'f':
		f = (f + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'F':
		f = (f - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'v':
		v = (v + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'V':
		v = (v - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'k':
		k = (k + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'K':
		k = (k - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'm':
		m = (m + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'M':
		m = (m - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		
	    case 'o':
		o = (o + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'O':
		o = (o - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'p':
		p = (p + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'P':
		p = (p - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		
	    case 'e':
		e = (e + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'E':
		e = (e - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'r':
		r = (r + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'R':
		r = (r - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		
	    case 27:
		System.exit(0);
	    default:
		break;
	}
    }

    public void init () {
	myUT.glutInitWindowSize (540, 540);
	myUT.glutInitWindowPosition (0, 0);
	myUT.glutCreateWindow (this);
	myinit ();
	myUT.glutDisplayFunc ("display");
	myUT.glutDisplayFunc ("display");
	myUT.glutReshapeFunc ("myReshape");
	myUT.glutMouseFunc ("mouse");
	myUT.glutKeyboardFunc ("keyboard");
	myUT.glutMainLoop ();
    }

    static public void main (String args[]) throws IOException {
	Frame mainFrame = new Frame ();
	mainFrame.setSize (540, 540);
	my_robot2 mainCanvas = new my_robot2 ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
