package TH4;

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;
import jgl.GLUT;

public class my_robot3 extends GLCanvas {
	
    private static int x, y, z = 0;
    private static int f, v = 0; //chan phai
    private static int g, b = 0; //tay phai
    private static int j, n = 0; //chan trai
    private static int k, m = 0; //tay trai
    private static int o, p = 0; //co
    private static int e = 0; //nguc
    private static int r = 0; //mong
    private static boolean l = true;
    private static float cameraX, cameraY, cameraZ = 0;
    private static float zoomZ = 0;
    
    private static boolean animating = false;
    private static int milliseconds_per_frame = 100;

    private void myinit () {
    	
		float ambient [] = {0.0f,0.0f,0.0f,1.0f};
		float diffuse [] = {1.0f,1.0f,1.0f,1.0f};
		float specular [] = {1.0f,1.0f,1.0f,1.0f};
		float position [] = {0.0f,3.0f,2.0f,0.0f};
		float lmodel_ambient [] = {0.4f,0.4f,0.4f,1.0f};
		float local_view [] = {0.0f};
		
		myGL.glEnable (GL.GL_DEPTH_TEST);
		myGL.glDepthFunc (GL.GL_LESS);

		myGL.glLightfv (GL.GL_LIGHT0, GL.GL_AMBIENT, ambient);
		myGL.glLightfv (GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse);
		myGL.glLightfv (GL.GL_LIGHT0, GL.GL_POSITION, position);
		myGL.glLightModelfv (GL.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient);
		myGL.glLightModelfv (GL.GL_LIGHT_MODEL_LOCAL_VIEWER, local_view);

		myGL.glEnable (GL.GL_LIGHTING);
		myGL.glEnable (GL.GL_LIGHT0);
		
	    myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
		myGL.glShadeModel (GL.GL_SMOOTH);
    }
    
    public void timer (int id) {
    	
    	cameraY = (cameraY + 15) % 360;
    	
    	if (l) {
    		
    		if (g > 25) {
    			
    			l = false;
    		}
    		
    		//right arm
    		g = (g + 15) % 360;
    		b = (b + 10) % 360;
    		
    		//left arm
    		j = (j - 15) % 360;
    		n = (n - 10) % 360;
    		
    		//right leg
    		f = (f - 15) % 360;
    		v = (v - 10) % 360;
    		
    		//left leg
    		k = (k + 15) % 360;
    		m = (m + 10) % 360;
    		
    		//chest
    		e = (e - 2) % 360;
    		
    		//butt
    		r = (r + 2) % 360;
    		
    		//head
    		o = (o + 1) % 360;
    		p = (p - 5) % 360;
    		
			myUT.glutPostRedisplay ();
    	} else if (!l) {
    		
    		if (g < -15){
    			
    			l = true;
    		}
    		
    		//right arm
    		g = (g - 15) % 360;
    		b = (b - 10) % 360;
    		
    		//left arm
    		j = (j + 15) % 360;
    		n = (n + 10) % 360;
    		
    		//right leg
    		f = (f + 15) % 360;
    		v = (v + 10) % 360;
    		
    		//left leg
    		k = (k - 15) % 360;
    		m = (m - 10) % 360;
    		
    		//chest
    		e = (e + 2) % 360;
    		
    		//butt
    		r = (r - 2) % 360;
    		
    		//head
    		o = (o - 1) % 360;
    		p = (p + 5) % 360;
    		
    		myUT.glutPostRedisplay();
    	}
	}

    public void display () {
    	
    	if (animating) {
            // Cause display to be called again after milliseconds_per_frame.
         myUT.glutTimerFunc(milliseconds_per_frame,"timer",1);  
    	}
    	
		float no_mat[]={0.0f,0.0f,0.0f,1.0f};
		float mat_ambient[]={0.7f,0.7f,0.7f,1.0f};
		float mat_diffuse[]={0.1f,0.5f,0.8f,1.0f};
		float mat_specular[]={1.0f,1.0f,1.0f,1.0f};
		float high_shininess[]={100.0f};
	    	
		myGL.glClear (GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		myGL.glMatrixMode (GL.GL_MODELVIEW);
    	myGL.glLoadIdentity ();
    	myGLU.gluLookAt(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
    	
    	//zoom camera
    	myGL.glTranslatef(0.0f, 0.0f, zoomZ);
    	
    	//rorate camera
    	myGL.glRotatef(cameraX, 1.0f, 0, 0);
    	myGL.glRotatef(cameraY, 0, 1.0f, 0);
    	myGL.glRotatef(cameraZ, 0, 0, 1.0f);
	
	myGL.glPushMatrix();
	
		//lighting
		myGL.glTranslatef (1.25f, 0.0f, 0.0f);
	    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient);
	    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse);
	    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_SPECULAR, mat_specular);
	    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_SHININESS, high_shininess);
	    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_EMISSION, no_mat);
	    myGL.glTranslatef (-1.25f, 0.0f, 0.0f);
	    
	    //rotate full model
		myGL.glRotatef ((float)x, 1.0f, 0.0f, 0.0f);
		myGL.glRotatef ((float)y, 0.0f, 1.0f, 0.0f);
		myGL.glRotatef ((float)z, 0.0f, 0.0f, 1.0f);
		//body
		
		//chest
		myGL.glTranslatef (0.0f, 0.75f, 0.25f);
		myGL.glPushMatrix ();
		
			//rotate chest
			myGL.glRotatef ((float)e, 0.0f, 1.0f, 0.0f);
		
			myGL.glPushMatrix ();
			myGL.glScalef (3.0f, 1.5f, 1.5f);
			myUT.glutSolidCube (1.0);
			myGL.glPopMatrix ();
			
			//neck
			myGL.glTranslatef (0.0f, 0.75f, 0.75f);
			myGL.glPushMatrix ();
			myGL.glRotatef (25.0f, 1.0f, 0.0f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.0f, 0.5f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, 0.325f, 0.0f);
				myUT.glutSolidSphere(0.35, 8, 8);
				myGL.glTranslatef (0.0f, -0.325f, 0.0f);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			myGL.glTranslatef (0.0f, -0.75f, -0.75f);
			
			//head
			myGL.glTranslatef (0.0f, 1.75f, 1.25f);
			
			myGL.glPushMatrix ();
			
			//rotate head at neck :v
			myGL.glTranslatef (0.0f, -0.75f, 0.0f);
			myGL.glRotatef ((float)o, 1.0f, 0.0f, 0.0f);
			myGL.glRotatef ((float)p, 0.0f, 1.0f, 0.0f);
			myGL.glTranslatef (0.0f, 0.75f, 0.0f);
			
				myGL.glPushMatrix ();
				myGL.glScalef (1.5f, 1.5f, 1.5f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				//eyes
				myGL.glPushMatrix ();
				myGL.glTranslatef (-0.375f, 0.3f, 0.75f);
				myUT.glutSolidSphere (0.25, 6, 6);
				myGL.glTranslatef (0.375f, -0.3f, -0.75f);
				
				myGL.glTranslatef (0.375f, 0.3f, 0.75f);
				myUT.glutSolidSphere (0.25, 6, 6);
				myGL.glTranslatef (-0.375f, -0.3f, -0.75f);
				myGL.glPopMatrix ();
				
				//mouth
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.6f, 0.45f);
				myGL.glScalef (1.8f, 0.5f, 0.8f);
				myUT.glutSolidCube (1.0);
				myGL.glTranslatef (0.0f, 0.6f, -0.45f);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			myGL.glTranslatef (0.0f, -1.75f, -1.25f);
			
			//right arm
			//shoulder
			myGL.glTranslatef (-1.75f, 0.5f, 0.0f);
			
			myGL.glPushMatrix ();
			myGL.glScalef (0.75f, 0.5f, 0.5f);
			myUT.glutSolidCube (1.0);
			myGL.glPopMatrix ();
			
			myGL.glPushMatrix ();
				
				//rotate shoulder
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glRotatef ((float)g, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myUT.glutSolidSphere(0.35, 8, 8);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (1.75f, -0.5f, 0.0f);
				
				//up arm
				myGL.glTranslatef (-2.125f, -0.175f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.5f, 0.5f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.75f, 0.0f);
				myUT.glutSolidSphere(0.35, 8, 8);
				myGL.glTranslatef (0.0f, 0.75f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (2.125f, 0.175f, 0.0f);
				
				//arm + hand
				
				//rotate elbow
				myGL.glTranslatef (-2.125f, -1.0f, 0.0f);
				myGL.glRotatef ((float)b, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (2.125f, 1.0f, 0.0f);
				
				myGL.glTranslatef (-2.125f, -1.45f, 0.0f);
				myGL.glRotatef (-45.0f, 1.0f, 0.0f, 0.0f);
				
				myGL.glPushMatrix ();
				myGL.glScalef (1.0f, 1.0f, 1.0f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.0f, 0.0f);
				myGL.glScalef (1.25f, 1.0f, 1.25f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (-0.15f, -1.875f, 0.0f);
				myGL.glScalef (0.75f, 0.75f, 0.75f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				myGL.glTranslatef (2.125f, 1.45f, 0.0f);
			myGL.glPopMatrix ();
			myGL.glTranslatef (1.75f, -0.5f, 0.0f);	
			
			//left arm
			//shoulder
			myGL.glTranslatef (1.75f, 0.5f, 0.0f);
			
			myGL.glPushMatrix ();
			myGL.glScalef (0.75f, 0.5f, 0.5f);
			myUT.glutSolidCube (1.0);
			myGL.glPopMatrix ();
			
			myGL.glPushMatrix ();
			
				//rotate shoulder
				myGL.glTranslatef (+0.375f, 0.0f, 0.0f);
				myGL.glRotatef ((float)j, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
			
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myUT.glutSolidSphere(0.35, 8, 8);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-1.75f, -0.5f, 0.0f);
				
				//up arm
				myGL.glTranslatef (2.125f, -0.175f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.5f, 0.5f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.75f, 0.0f);
				myUT.glutSolidSphere(0.35, 8, 8);
				myGL.glTranslatef (0.0f, 0.75f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-2.125f, 0.175f, 0.0f);
				
				//arm + hand
				
				//rotate elbow
				myGL.glTranslatef (2.125f, -1.0f, 0.0f);
				myGL.glRotatef ((float)n, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-2.125f, 1.0f, 0.0f);
				
				myGL.glTranslatef (2.125f, -1.45f, 0.0f);
				myGL.glRotatef (-45.0f, 1.0f, 0.0f, 0.0f);
				
				myGL.glPushMatrix ();
				myGL.glScalef (1.0f, 1.0f, 1.0f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.0f, 0.0f);
				myGL.glScalef (1.25f, 1.0f, 1.25f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.15f, -1.875f, 0.0f);
				myGL.glScalef (0.75f, 0.75f, 0.75f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-2.125f, 1.45f, 0.0f);
			myGL.glPopMatrix ();
			myGL.glTranslatef (-1.75f, -0.5f, 0.0f);
				
		myGL.glPopMatrix ();
		myGL.glTranslatef (0.0f, -0.75f, -0.25f);
		
		
		//belly
		myGL.glTranslatef (0.0f, -0.625f, 0.125f);
		myGL.glPushMatrix ();
		myGL.glScalef (2.5f, 1.25f, 1.25f);
		myUT.glutSolidCube (1.0);
		myGL.glPopMatrix ();
		myGL.glTranslatef (0.0f, 0.625f, -0.125f);
		
		//butt
		myGL.glTranslatef (0.0f, -1.625f, 0.0f);
		myGL.glPushMatrix ();
		
			//rorate butt
			myGL.glRotatef ((float)r, 0.0f, 1.0f, 0.0f);
		
			myGL.glPushMatrix ();
			myGL.glScalef (1.5f, 1.0f, 1.0f);
			myUT.glutSolidCube (1.0);
			myGL.glPopMatrix ();
			
			//right leg
			myGL.glTranslatef (-1.125f, -0.25f, 0.0f);
			
			myGL.glPushMatrix ();
			myGL.glScalef (0.75f, 0.5f, 0.5f);
			myUT.glutSolidCube (1.0);
			myGL.glPopMatrix ();
			
			//butt cheek
			myGL.glPushMatrix ();
			myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
			myGL.glRotatef (-15.0f, 1.0f, 0.0f, 0.0f);
			myGL.glTranslatef (0.375f, 0.0f, 0.0f);
			
				//rotate leg
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glRotatef ((float)f, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myUT.glutSolidSphere(0.35, 8, 8);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (1.75f, -0.5f, 0.0f);
				
				//up leg
				myGL.glTranslatef (-2.125f, -0.15f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.25f, 0.5f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.5f, 0.0f);
				myUT.glutSolidSphere(0.35, 8, 8);
				myGL.glTranslatef (0.0f, 0.5f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (2.125f, 0.15f, 0.0f);
				
				//leg + foot
				myGL.glTranslatef (-2.125f, -0.65f, 0.0f);
				myGL.glRotatef (20.0f, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (2.125f, 0.65f, 0.0f);
				
				//rotate knee
				myGL.glTranslatef (-2.125f, -0.65f, 0.0f);
				myGL.glRotatef ((float)v, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (2.125f, 0.65f, 0.0f);
				
				myGL.glTranslatef (-2.125f, -1.2f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (1.0f, 1.0f, 1.0f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.25f, 0.0f);
				myGL.glScalef (1.25f, 1.5f, 1.25f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.75f, 0.35f);
				myGL.glScalef (1.25f, 0.5f, 1.6f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				myGL.glTranslatef (2.125f, 1.2f, 0.0f);
				
			myGL.glPopMatrix ();
			myGL.glTranslatef (1.125f, 0.25f, 0.0f);
			
			//left leg
			myGL.glTranslatef (1.125f, -0.25f, 0.0f);
			
			myGL.glPushMatrix ();
			myGL.glScalef (0.75f, 0.5f, 0.5f);
			myUT.glutSolidCube (1.0);
			myGL.glPopMatrix ();
			
			//butt cheek
			myGL.glPushMatrix ();
			myGL.glTranslatef (0.375f, 0.0f, 0.0f);
			myGL.glRotatef (-15.0f, 1.0f, 0.0f, 0.0f);
			myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
			
				//rotate leg
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myGL.glRotatef ((float)k, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
			
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myUT.glutSolidSphere(0.35, 8, 8);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-1.75f, -0.5f, 0.0f);
				
				//up leg
				myGL.glTranslatef (2.125f, -0.15f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (0.5f, 1.25f, 0.5f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -0.5f, 0.0f);
				myUT.glutSolidSphere(-0.35, 8, 8);
				myGL.glTranslatef (0.0f, 0.5f, 0.0f);
				myGL.glPopMatrix ();
				myGL.glTranslatef (-2.125f, 0.15f, 0.0f);
				
				//leg + foot
				myGL.glTranslatef (2.125f, -0.65f, 0.0f);
				myGL.glRotatef (20.0f, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-2.125f, 0.65f, 0.0f);
				
				//rotate knee
				myGL.glTranslatef (2.125f, -0.65f, 0.0f);
				myGL.glRotatef ((float)m, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-2.125f, 0.65f, 0.0f);
				
				myGL.glTranslatef (2.125f, -1.2f, 0.0f);
				myGL.glPushMatrix ();
				myGL.glScalef (1.0f, 1.0f, 1.0f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.25f, 0.0f);
				myGL.glScalef (1.25f, 1.5f, 1.25f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.0f, -1.75f, 0.35f);
				myGL.glScalef (1.25f, 0.5f, 1.6f);
				myUT.glutSolidCube (1.0);
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
    	
    	cameraY = (cameraY + 15) % 360;
    	
    	if (l) {
    		
    		if (g > 25) {
    			
    			l = false;
    		}
    		
    		//right arm
    		g = (g + 15) % 360;
    		b = (b + 10) % 360;
    		
    		//left arm
    		j = (j - 15) % 360;
    		n = (n - 10) % 360;
    		
    		//right leg
    		f = (f - 15) % 360;
    		v = (v - 10) % 360;
    		
    		//left leg
    		k = (k + 15) % 360;
    		m = (m + 10) % 360;
    		
    		//chest
    		e = (e - 2) % 360;
    		
    		//butt
    		r = (r + 2) % 360;
    		
    		//head
    		o = (o + 1) % 360;
    		p = (p - 5) % 360;
    		
			myUT.glutPostRedisplay ();
    	} else if (!l) {
    		
    		if (g < -15){
    			
    			l = true;
    		}
    		
    		//right arm
    		g = (g - 15) % 360;
    		b = (b - 10) % 360;
    		
    		//left arm
    		j = (j + 15) % 360;
    		n = (n + 10) % 360;
    		
    		//right leg
    		f = (f + 15) % 360;
    		v = (v + 10) % 360;
    		
    		//left leg
    		k = (k - 15) % 360;
    		m = (m - 10) % 360;
    		
    		//chest
    		e = (e + 2) % 360;
    		
    		//butt
    		r = (r - 2) % 360;
    		
    		//head
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
		myGLU.gluLookAt(0.0, 0.0, 8.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
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
		
		case '1':
		animating = !animating;
		myUT.glutPostRedisplay ();
		break;
	
		//model
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
	    	
		//camera
	    case 'q':
		zoomZ += 0.25;
		myUT.glutPostRedisplay ();
		break;
		case 'Q':
		zoomZ -= 0.25;
		myUT.glutPostRedisplay ();
		break;
	    case 'z':
		cameraX = (cameraX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'Z':
	    cameraX = (cameraX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'x':
	    cameraY = (cameraY + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'X':
	    cameraY = (cameraY - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'c':
	    cameraZ = (cameraZ + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'C':
	    cameraZ = (cameraZ - 5) % 360;
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
		myUT.glutReshapeFunc ("myReshape");
		myUT.glutMouseFunc ("mouse");
		myUT.glutKeyboardFunc ("keyboard");
		myUT.glutMainLoop ();
    }
    
	static public void main (String args[]) throws IOException {
		Frame mainFrame = new Frame ();
		mainFrame.setSize (540, 540);
		my_robot3 mainCanvas = new my_robot3 ();
		mainCanvas.init();
		mainFrame.add (mainCanvas);
		mainFrame.setVisible (true);
    }

}
