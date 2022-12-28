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
import jgl.GLU;
import jgl.GLUT;
import jgl.glu.GLUquadricObj;

public class my_robot extends GLCanvas {
	
    private static int x, y, z = 0;
    private static float rightLegX, rightLegY, rightLegZ, rightKneeX, rightKneeY, rightKneeZ = 0; //chan phai
    private static float rightArmX, rightArmY, rightArmZ, rightElbowX, rightElbowY, rightElbowZ = 0; //tay phai
    private static float leftLegX, leftLegY, leftLegZ, leftKneeX, leftKneeY, leftKneeZ = 0; //chan trai
    private static float leftArmX, leftArmY, leftArmZ, leftElbowX, leftElbowY, leftElbowZ = 0; //tay trai
    private static float headX, headY, headZ = 0; //co
    private static float chestR = 0; //nguc
    private static float buttR = 0; //mong
    private static boolean l = true;
    private static float cameraX, cameraY, cameraZ = 0;
    private static float zoomZ = 0;
    
    private static boolean animating = false;
    private static int milliseconds_per_frame = 130;
    
    //Image
    private static final int checkImageWidth = 512;
    private static final int checkImageHeight = 512;
    private byte imageCenter [][][] =
    		   new byte [checkImageWidth][checkImageHeight][4];
    private byte imageLeft [][][] =
    		   new byte [checkImageWidth][checkImageHeight][4];
    private byte imageRight [][][] =
 		   new byte [checkImageWidth][checkImageHeight][4];
    private byte imageFront [][][] =
 		   new byte [checkImageWidth][checkImageHeight][4];
    private byte imageTop [][][] =
 		   new byte [checkImageWidth][checkImageHeight][4];
    private byte imageBottom [][][] =
 		   new byte [checkImageWidth][checkImageHeight][4];

    private int texName [] = new int [6];

    private void makeCheckImage () {
    	
    	int i, j;
    	float ti, tj;
    	
//    	String str = "E:/Homework/N3 - HK1/Graphic/Labs/TH6/texture/sky/";
    	String str = "E:/Homework/N3 - HK1/Graphic/Labs/Final/Texture/";
    	
    	File bmpCenter = new File(str + "center.bmp");
    	File bmpLeft = new File(str + "left.bmp");
    	File bmpRight = new File(str + "right.bmp");
    	File bmpFront = new File(str + "back.bmp");
    	File bmpTop = new File(str + "top.bmp");
    	File bmpBottom = new File(str + "bot.bmp");
    	
    	try {
    		
    		BufferedImage iCenter = ImageIO.read(bmpCenter);
    		BufferedImage iLeft = ImageIO.read(bmpLeft);
    		BufferedImage iRight = ImageIO.read(bmpRight);
    		BufferedImage iFront = ImageIO.read(bmpFront);
    		BufferedImage iTop = ImageIO.read(bmpTop);
    		BufferedImage iBottom = ImageIO.read(bmpBottom);
    		
    		for (i = 0; i < checkImageWidth; i++) {
    			
    			for (j = 0; j < checkImageHeight; j++) {
    				
    				Color cCenter = new Color(iCenter.getRGB(i, j));
    				Color cLeft = new Color(iLeft.getRGB(i, j));
    				Color cRight = new Color(iRight.getRGB(i, j));
    				Color cFront = new Color(iFront.getRGB(i, j));
    				Color cTop = new Color(iTop.getRGB(i, j));
    				Color cBottom = new Color(iBottom.getRGB(i, j));
    				
    				imageCenter[j][i][0] = (byte)(cCenter.getRed());
    				imageCenter[j][i][1] = (byte)(cCenter.getGreen());
    				imageCenter[j][i][2] = (byte)(cCenter.getBlue());
    				imageCenter[i][j][3] = (byte) 255;
    				
    				imageLeft[j][i][0] = (byte)(cLeft.getRed());
    				imageLeft[j][i][1] = (byte)(cLeft.getGreen());
    				imageLeft[j][i][2] = (byte)(cLeft.getBlue());
    				imageLeft[i][j][3] = (byte) 255;
    				
    				imageRight[j][i][0] = (byte)(cRight.getRed());
    				imageRight[j][i][1] = (byte)(cRight.getGreen());
    				imageRight[j][i][2] = (byte)(cRight.getBlue());
    				imageRight[i][j][3] = (byte) 255;
    				
    				imageFront[j][i][0] = (byte)(cFront.getRed());
    				imageFront[j][i][1] = (byte)(cFront.getGreen());
    				imageFront[j][i][2] = (byte)(cFront.getBlue());
    				imageFront[i][j][3] = (byte) 255;
    				
    				imageTop[j][i][0] = (byte)(cTop.getRed());
    				imageTop[j][i][1] = (byte)(cTop.getGreen());
    				imageTop[j][i][2] = (byte)(cTop.getBlue());
    				imageTop[i][j][3] = (byte) 255;
    				
    				imageBottom[j][i][0] = (byte)(cBottom.getRed());
    				imageBottom[j][i][1] = (byte)(cBottom.getGreen());
    				imageBottom[j][i][2] = (byte)(cBottom.getBlue());
    				imageBottom[i][j][3] = (byte) 255;
    			}
    		}
    	} catch (IOException e) {
    		
    		e.printStackTrace();
    	}
    }

    private void myinit () {
    	
		float ambient [] = {0.0f,0.0f,0.0f,1.0f};
		float diffuse [] = {1.0f,1.0f,1.0f,1.0f};
		float specular [] = {1.0f,1.0f,1.0f,1.0f};
//		float position [] = {0.0f,3.0f,3.0f,0.0f};
		float lmodel_ambient [] = {0.4f,0.4f,0.4f,1.0f};
		float local_view [] = {0.0f};
		
		myGL.glEnable (GL.GL_DEPTH_TEST);
		myGL.glDepthFunc (GL.GL_LESS);

		myGL.glLightfv (GL.GL_LIGHT0, GL.GL_AMBIENT, ambient);
		myGL.glLightfv (GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse);
//		myGL.glLightfv (GL.GL_LIGHT0, GL.GL_POSITION, position);
		myGL.glLightModelfv (GL.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient);
		myGL.glLightModelfv (GL.GL_LIGHT_MODEL_LOCAL_VIEWER, local_view);

		myGL.glEnable (GL.GL_LIGHTING);
		myGL.glEnable (GL.GL_LIGHT0);
		
	    myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
		myGL.glShadeModel (GL.GL_SMOOTH);
		myGL.glEnable (GL.GL_DEPTH_TEST);

    	makeCheckImage ();
    	myGL.glPixelStorei (GL.GL_UNPACK_ALIGNMENT, 1);

    	myGL.glGenTextures (6, texName);
    	
    	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [0]);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, checkImageWidth,
    	    checkImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, imageCenter);
    	
    	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [1]);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, checkImageWidth,
    	    checkImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, imageLeft);

    	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [2]);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    	myGL.glTexEnvf (GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_DECAL);
    	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, checkImageWidth,
    	    checkImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, imageRight);
    	
    	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [3]);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, checkImageWidth,
    	    checkImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, imageFront);
    	
    	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [4]);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, checkImageWidth,
    	    checkImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, imageTop);
    	
    	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [5]);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, checkImageWidth,
    	    checkImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, imageBottom);
    	
    	myGL.glEnable (GL.GL_TEXTURE_2D);
    }

    public void timer (int id) {
    	
    	cameraY = (cameraY + 15) % 360;
    	
    	if (l) {
    		
    		if (rightArmX > 25) {
    			
    			l = false;
    		}
    		
    		//right arm
    		rightArmX = (rightArmX + 15) % 360;
    		rightElbowX = (rightElbowX + 10) % 360;
    		
    		//left arm
    		leftArmX = (leftArmX - 15) % 360;
    		leftElbowX = (leftElbowX - 10) % 360;
    		
    		//right leg
    		rightLegX = (rightLegX - 15) % 360;
    		rightKneeX = (rightKneeX - 10) % 360;
    		
    		//left leg
    		leftLegX = (leftLegX + 15) % 360;
    		leftKneeX = (leftKneeX + 10) % 360;
    		
    		//chest
    		chestR = (chestR - 2) % 360;
    		
    		//butt
    		buttR = (buttR + 2) % 360;
    		
    		//head
    		headX = (headX + 1) % 360;
    		headY = (headY - 5) % 360;
    		
    		myUT.glutPostRedisplay ();
    	} else if (!l) {
    		
    		if (rightArmX < -15){
    			
    			l = true;
    		}
    		
    		//right arm
    		rightArmX = (rightArmX - 15) % 360;
    		rightElbowX = (rightElbowX - 10) % 360;
    		
    		//left arm
    		leftArmX = (leftArmX + 15) % 360;
    		leftElbowX = (leftElbowX + 10) % 360;
    		
    		//right leg
    		rightLegX = (rightLegX + 15) % 360;
    		rightKneeX = (rightKneeX + 10) % 360;
    		
    		//left leg
    		leftLegX = (leftLegX - 15) % 360;
    		leftKneeX = (leftKneeX - 10) % 360;
    		
    		//chest
    		chestR = (chestR + 2) % 360;
    		
    		//butt
    		buttR = (buttR - 2) % 360;
    		
    		//head
    		headX = (headX - 1) % 360;
    		headY = (headY + 5) % 360;
    		
    		myUT.glutPostRedisplay ();
    	}
    }
    
    public void display () {
    	
    	if (animating) {
            // Cause display to be called again after milliseconds_per_frame.
         myUT.glutTimerFunc(milliseconds_per_frame,"timer",1);  
    	}
    	
    	GLUquadricObj rail;
    	rail = myGLU.gluNewQuadric();	
    	
		float no_mat[]={0.0f,0.0f,0.0f,1.0f};
		float mat_ambient[]={0.7f,0.7f,0.7f,1.0f};
		float mat_diffuse[]={0.1f,0.5f,0.8f,1.0f};
		float mat_specular[]={1.0f,1.0f,1.0f,1.0f};
		float high_shininess[]={100.0f};
		float position [] = {0.0f,3.0f,3.0f,0.0f};
	    	
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
    	
	//room
	myGL.glDisable(GL.GL_LIGHT0); //tat den
	myGL.glDisable(GL.GL_LIGHTING);
	myGL.glEnable (GL.GL_TEXTURE_2D);
	
	myGL.glPushMatrix();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [0]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-8.0f, 8.0f, -8.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (8.0f, 8.0f, -8.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (8.0f, -8.0f, -8.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-8.0f, -8.0f, -8.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [1]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-8.0f, 8.0f, 8.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (-8.0f, 8.0f, -8.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (-8.0f, -8.0f, -8.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-8.0f, -8.0f, 8.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [2]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (8.0f, 8.0f, -8.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (8.0f, 8.0f, 8.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (8.0f, -8.0f, 8.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (8.0f, -8.0f, -8.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [3]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (8.0f, 8.0f, 8.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (-8.0f, 8.0f, 8.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (-8.0f, -8.0f, 8.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (8.0f, -8.0f, 8.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [4]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-8.0f, 8.0f, 8.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (8.0f, 8.0f, 8.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (8.0f, 8.0f, -8.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-8.0f, 8.0f, -8.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [5]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-8.0f, -8.0f, -8.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (8.0f, -8.0f, -8.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (8.0f, -8.0f, 8.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-8.0f, -8.0f, 8.0f);
		myGL.glEnd ();
	
	myGL.glPopMatrix();
	
    	
    //Robot
	myGL.glPushMatrix();
		
		myGL.glEnable (GL.GL_LIGHTING); //bat lai den
		myGL.glEnable (GL.GL_LIGHT0);
		myGL.glDisable(GL.GL_TEXTURE_2D); //disable texture trc khi ve :V
		
		//test
//		myGL.glEnable(GL.GL_TEXTURE_2D);
//    	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [0]);
//    	myGL.glTranslatef(0.0f, 0.0f, 3.0f);
//		drawCube(4.0f);
//    	myUT.glutSolidSphere(1.0, 20, 20);
//    	myUT.glutSolidTorus(0.5, 1.0, 30, 30);
//    	myGL.glTranslatef(0.0f, 0.0f, -3.0f);
//		myGL.glDisable(GL.GL_TEXTURE_2D); 
    	
		
		//lighting
		myGL.glPushMatrix();
			myGL.glRotatef(rightArmX, 1.0f, 0.0f, 0.0f);
			myGL.glLightfv (GL.GL_LIGHT0, GL.GL_POSITION, position);
		    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient);
		    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse);
		    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_SPECULAR, mat_specular);
		    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_SHININESS, high_shininess);
		    myGL.glMaterialfv (GL.GL_FRONT, GL.GL_EMISSION, no_mat);
	    myGL.glPopMatrix();
	    
	    //rotate full model
		myGL.glRotatef ((float)x, 1.0f, 0.0f, 0.0f);
		myGL.glRotatef ((float)y, 0.0f, 1.0f, 0.0f);
		myGL.glRotatef ((float)z, 0.0f, 0.0f, 1.0f);
		//body
		
		//chest
		myGL.glTranslatef (0.0f, 0.75f, 0.25f);
		myGL.glPushMatrix ();
		
			//rotate chest
			myGL.glRotatef (chestR, 0.0f, 1.0f, 0.0f);
		
			myGL.glPushMatrix ();
			myGL.glScalef (3.0f, 1.5f, 1.5f);
			myUT.glutSolidCube (1.0);
			myGL.glPopMatrix ();
			
			//neck
			myGL.glPushMatrix ();
			myGL.glTranslatef (0.0f, 0.75f, 1.0f);
				myGL.glTranslatef(0.0f, 1.0f/2f, 0.0f);
				myGL.glRotatef(120, 1, 0, 0);
			    myGLU.gluCylinder (rail, 0.2, 0.2, 1.0, 10, 5);
		    myGL.glPopMatrix ();
			
			//head
			myGL.glTranslatef (0.0f, 1.75f, 1.25f);
			
			myGL.glPushMatrix ();
			
			//rotate head at neck :v
			myGL.glTranslatef (0.0f, -0.75f, 0.0f);
			myGL.glRotatef (headX, 1.0f, 0.0f, 0.0f);
			myGL.glRotatef (headY, 0.0f, 1.0f, 0.0f);
			myGL.glRotatef (headZ, 0.0f, 0.0f, 1.0f);
			myGL.glTranslatef (0.0f, 0.75f, 0.0f);
			
				myGL.glPushMatrix ();
					myGL.glScalef (1.5f, 1.5f, 1.5f);
					myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				//ring
				myGL.glPushMatrix();
					myGL.glTranslatef(0, -1.7f/2f, -0.325f);
					myGL.glRotatef(90, 1, 0, 0);
					myUT.glutSolidTorus(0.1f, 0.3f, 8, 8);
				myGL.glPopMatrix();
				
				//eyes
					//left
				myGL.glPushMatrix();
					myGL.glTranslatef (-0.39f, 0.25f, 0.75f);
					myUT.glutSolidTorus(0.1f, 0.25f, 8, 8);
				myGL.glPopMatrix();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef (-0.39f, 0.25f, 0.75f);
					myUT.glutSolidSphere (0.25, 8, 8);
				myGL.glPopMatrix();
				
					//right
				myGL.glPushMatrix();
					myGL.glTranslatef (0.39f, 0.25f, 0.75f);
					myUT.glutSolidTorus(0.1f, 0.25f, 8, 8);
				myGL.glPopMatrix();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef (0.39f, 0.25f, 0.75f);
					myUT.glutSolidSphere (0.25, 8, 8);
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
			myGL.glTranslatef (-1.75f, 0.4f, 0.3f);
			
			myGL.glPushMatrix ();
				myGL.glPushMatrix();
					myGL.glTranslatef(0.2f, 0, 0);
					myGL.glRotatef(90, 0, 1, 0);
					myUT.glutSolidTorus(0.1f, 0.3f, 8, 8);
				myGL.glPopMatrix();
			
				myGL.glTranslatef(-0.75f/2f, 0.0f, 0.0f);
				myGL.glRotatef(90, 0, 1, 0);
			    myGLU.gluCylinder (rail, 0.2, 0.2, 0.75, 10, 5);
		    myGL.glPopMatrix ();
			
			myGL.glPushMatrix ();
			
				//rotate shoulder
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glRotatef (rightArmX, 1.0f, 0.0f, 0.0f);
				myGL.glRotatef (rightArmY, 0.0f, 1.0f, 0.0f);
				myGL.glRotatef (rightArmZ, 0.0f, 0.0f, 1.0f);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
			
				myGL.glPushMatrix ();
					myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
					myUT.glutSolidSphere(0.3, 8, 8);
				myGL.glPopMatrix ();
				
				myGL.glTranslatef (1.75f, -0.5f, 0.0f);
				
				//up arm
				myGL.glTranslatef (-2.125f, -0.175f, 0.0f);
				
				myGL.glPushMatrix ();
					myGL.glTranslatef (0, 1.5f/2f, 0.0f);
					myGL.glRotatef(90, 1, 0, 0);
				    myGLU.gluCylinder (rail, 0.2, 0.2, 1.5, 10, 5);
			    myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef (0.0f, -0.75f, 0.0f);
					myUT.glutSolidSphere(0.3, 8, 8);
				myGL.glPopMatrix ();
				
				myGL.glTranslatef (2.125f, 0.175f, 0.0f);
				
				//arm + hand
				
				//rotate elbow
				myGL.glTranslatef (-2.125f, -1.0f, 0.0f);
				myGL.glRotatef (rightElbowX, 1.0f, 0.0f, 0.0f);
				myGL.glRotatef (rightElbowY, 0.0f, 1.0f, 0.0f);
				myGL.glRotatef (rightElbowZ, 0.0f, 0.0f, 1.0f);
				myGL.glTranslatef (2.125f, 1.0f, 0.0f);
				
				myGL.glTranslatef (-2.125f, -1.5f, 0.25f);
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
				
			myGL.glPopMatrix ();
			
			myGL.glTranslatef (1.75f, -0.4f, -0.3f);	
			
			//left arm
			//shoulder
			myGL.glTranslatef (1.75f, 0.4f, 0.3f);
			
			myGL.glPushMatrix ();
				myGL.glPushMatrix();
					myGL.glTranslatef(-0.2f, 0, 0);
					myGL.glRotatef(90, 0, 1, 0);
					myUT.glutSolidTorus(0.1f, 0.3f, 8, 8);
				myGL.glPopMatrix();
			
				myGL.glTranslatef(-0.75f/2f, 0.0f, 0.0f);
				myGL.glRotatef(90, 0, 1, 0);
			    myGLU.gluCylinder (rail, 0.2, 0.2, 0.75, 10, 5);
		    myGL.glPopMatrix ();
			
			myGL.glPushMatrix ();
			
				//rotate shoulder
				myGL.glTranslatef (+0.375f, 0.0f, 0.0f);
				myGL.glRotatef (leftArmX, 1.0f, 0.0f, 0.0f);
				myGL.glRotatef (leftArmY, 0.0f, 1.0f, 0.0f);
				myGL.glRotatef (leftArmZ, 0.0f, 0.0f, 1.0f);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
			
				myGL.glPushMatrix ();
					myGL.glTranslatef (0.375f, 0.0f, 0.0f);
					myUT.glutSolidSphere(0.3, 8, 8);
				myGL.glPopMatrix ();
				
				myGL.glTranslatef (-1.75f, -0.5f, 0.0f);
				
				//up arm
				myGL.glTranslatef (2.125f, -0.175f, 0.0f);
				
				myGL.glPushMatrix ();
					myGL.glTranslatef (0, 1.5f/2f, 0.0f);
					myGL.glRotatef(90, 1, 0, 0);
				    myGLU.gluCylinder (rail, 0.2, 0.2, 1.5, 10, 5);
			    myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef (0.0f, -0.75f, 0.0f);
					myUT.glutSolidSphere(0.3, 8, 8);
				myGL.glPopMatrix ();
				
				myGL.glTranslatef (-2.125f, 0.175f, 0.0f);
				
				//arm + hand
				
				//rotate elbow
				myGL.glTranslatef (2.125f, -1.0f, 0.0f);
				myGL.glRotatef (leftElbowX, 1.0f, 0.0f, 0.0f);
				myGL.glRotatef (leftElbowY, 0.0f, 1.0f, 0.0f);
				myGL.glRotatef (leftElbowZ, 0.0f, 0.0f, 1.0f);
				myGL.glTranslatef (-2.125f, 1.0f, 0.0f);
				
				myGL.glTranslatef (2.125f, -1.5f, 0.25f);
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
				
			myGL.glPopMatrix ();
			
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
			myGL.glRotatef (buttR, 0.0f, 1.0f, 0.0f);
		
			myGL.glPushMatrix ();
				myGL.glScalef (1.5f, 1.0f, 1.0f);
				myUT.glutSolidCube (1.0);
			myGL.glPopMatrix ();
			
			//right leg
			myGL.glPushMatrix();
			myGL.glTranslatef (-1.125f, -0.25f, 0.0f);
			
				//butt
				myGL.glPushMatrix ();
					myGL.glPushMatrix();
						myGL.glTranslatef(0.3f, 0, 0);
						myGL.glRotatef(90, 0, 1, 0);
						myUT.glutSolidTorus(0.1f, 0.3f, 8, 8);
					myGL.glPopMatrix();
					
					myGL.glTranslatef(-0.75f/2f, 0.0f, 0.0f);
					myGL.glRotatef(90, 0, 1, 0);
				    myGLU.gluCylinder (rail, 0.2, 0.2, 0.75, 10, 5);
				myGL.glPopMatrix ();
				
				//rotate prefix
				myGL.glPushMatrix ();
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				myGL.glRotatef (-15.0f, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				
					//rotate leg
					myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
					myGL.glRotatef (rightLegX, 1.0f, 0.0f, 0.0f);
					myGL.glRotatef (rightLegY, 0.0f, 1.0f, 0.0f);
					myGL.glRotatef (rightLegZ, 0.0f, 0.0f, 1.0f);
					myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				
					//ball
					myGL.glPushMatrix ();
						myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
						myUT.glutSolidSphere(0.3, 8, 8);
					myGL.glPopMatrix ();
					myGL.glTranslatef (1.75f, -0.5f, 0.0f);
					
					//up leg
					myGL.glPushMatrix ();
						myGL.glTranslatef (-2.125f, 1.25f/2f, 0.0f);
						myGL.glRotatef(90, 1, 0, 0);
					    myGLU.gluCylinder (rail, 0.2, 0.2, 1.25, 10, 5);
				    myGL.glPopMatrix ();
					
				    //knee
					myGL.glPushMatrix ();
						myGL.glTranslatef (-2.125f, -1.25f/2f, 0.0f);
						myUT.glutSolidSphere(0.3, 8, 8);
					myGL.glPopMatrix ();
					
					//leg + foot
					//rotate prefix
					myGL.glPushMatrix();
					myGL.glTranslatef (-2.125f, -0.65f, -0.15f);
					myGL.glRotatef (20.0f, 1.0f, 0.0f, 0.0f);
					
					//rotate knee
					myGL.glRotatef (rightKneeX, 1.0f, 0.0f, 0.0f);
					myGL.glRotatef (rightKneeY, 0.0f, 1.0f, 0.0f);
					myGL.glRotatef (rightKneeZ, 0.0f, 0.0f, 1.0f);
					
					//knee
					myGL.glPushMatrix ();
						myGL.glTranslatef(0, -0.6f, 0);
						myGL.glScalef (1.0f, 1.0f, 1.0f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
						myGL.glTranslatef (0.0f, -1.85f, 0.0f);
						myGL.glScalef (1.25f, 1.5f, 1.25f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
						myGL.glTranslatef (0.0f, -2.35f, 0.35f);
						myGL.glScalef (1.2f, 0.5f, 1.6f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					myGL.glTranslatef (2.125f, 1.2f, 0.0f);
				myGL.glPopMatrix ();
			
			myGL.glPopMatrix();
			
			myGL.glTranslatef (1.125f, 0.25f, 0.0f);
			
			//left leg
			myGL.glPushMatrix();
			myGL.glTranslatef (1.125f, -0.25f, 0.0f);
			
				//butt
				myGL.glPushMatrix ();
					myGL.glPushMatrix();
						myGL.glTranslatef(-0.3f, 0, 0);
						myGL.glRotatef(90, 0, 1, 0);
						myUT.glutSolidTorus(0.1f, 0.3f, 8, 8);
					myGL.glPopMatrix();
				
					myGL.glTranslatef(-0.75f/2f, 0.0f, 0.0f);
					myGL.glRotatef(90, 0, 1, 0);
				    myGLU.gluCylinder (rail, 0.2, 0.2, 0.75, 10, 5);
				myGL.glPopMatrix ();
				
				//rotate prefix
				myGL.glPushMatrix ();
				myGL.glTranslatef (0.375f, 0.0f, 0.0f);
				myGL.glRotatef (-15.0f, 1.0f, 0.0f, 0.0f);
				myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				
					//rotate leg
					myGL.glTranslatef (0.375f, 0.0f, 0.0f);
					myGL.glRotatef (leftLegX, 1.0f, 0.0f, 0.0f);
					myGL.glRotatef (leftLegY, 0.0f, 1.0f, 0.0f);
					myGL.glRotatef (leftLegZ, 0.0f, 0.0f, 1.0f);
					myGL.glTranslatef (-0.375f, 0.0f, 0.0f);
				
					//ball
					myGL.glPushMatrix ();
						myGL.glTranslatef (0.375f, 0.0f, 0.0f);
						myUT.glutSolidSphere(0.3, 8, 8);
					myGL.glPopMatrix ();
					myGL.glTranslatef (-1.75f, -0.5f, 0.0f);
					
					//up leg
					myGL.glPushMatrix ();
						myGL.glTranslatef (2.125f, 1.25f/2f, 0.0f);
						myGL.glRotatef(90, 1, 0, 0);
					    myGLU.gluCylinder (rail, 0.2, 0.2, 1.25, 10, 5);
				    myGL.glPopMatrix ();
					
				    //knee
					myGL.glPushMatrix ();
						myGL.glTranslatef (2.125f, -1.25f/2f, 0.0f);
						myUT.glutSolidSphere(0.3, 8, 8);
					myGL.glPopMatrix ();
					
					//leg + foot
					//rotate prefix
					myGL.glPushMatrix();
					myGL.glTranslatef (2.125f, -0.65f, -0.15f);
					myGL.glRotatef (20.0f, 1.0f, 0.0f, 0.0f);
					
					//rotate knee
					myGL.glRotatef (leftKneeX, 1.0f, 0.0f, 0.0f);
					myGL.glRotatef (leftKneeY, 0.0f, 1.0f, 0.0f);
					myGL.glRotatef (leftKneeZ, 0.0f, 0.0f, 1.0f);
					
					//knee
					myGL.glPushMatrix ();
						myGL.glTranslatef(0, -0.6f, 0);
						myGL.glScalef (1.0f, 1.0f, 1.0f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
						myGL.glTranslatef (0.0f, -1.85f, 0.0f);
						myGL.glScalef (1.25f, 1.5f, 1.25f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
						myGL.glTranslatef (0.0f, -2.35f, 0.35f);
						myGL.glScalef (1.2f, 0.5f, 1.6f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					myGL.glTranslatef (-2.125f, 1.2f, 0.0f);
				myGL.glPopMatrix ();
			
			myGL.glPopMatrix();
			
		myGL.glPopMatrix ();
		myGL.glTranslatef (0.0f, 1.625f, 0.0f);
	
	myGL.glEnable(GL.GL_TEXTURE_2D);
	
	myGL.glPopMatrix();	
	myGL.glFlush ();
    }

    public void robotDisplay () {
    	
//    	cameraY = (cameraY + 15) % 360;
    	
    	if (l) {
    		
    		if (rightArmX > 25) {
    			
    			l = false;
    		}
    		
    		//right arm
    		rightArmX = (rightArmX + 15) % 360;
    		rightElbowX = (rightElbowX + 10) % 360;
    		
    		//left arm
    		leftArmX = (leftArmX - 15) % 360;
    		leftElbowX = (leftElbowX - 10) % 360;
    		
    		//right leg
    		rightLegX = (rightLegX - 15) % 360;
    		rightKneeX = (rightKneeX - 10) % 360;
    		
    		//left leg
    		leftLegX = (leftLegX + 15) % 360;
    		leftKneeX = (leftKneeX + 10) % 360;
    		
    		//chest
    		chestR = (chestR - 2) % 360;
    		
    		//butt
    		buttR = (buttR + 2) % 360;
    		
    		//head
    		headX = (headX + 1) % 360;
    		headY = (headY - 5) % 360;
    		
    		myUT.glutPostRedisplay ();
    	} else if (!l) {
    		
    		if (rightArmX < -15){
    			
    			l = true;
    		}
    		
    		//right arm
    		rightArmX = (rightArmX - 15) % 360;
    		rightElbowX = (rightElbowX - 10) % 360;
    		
    		//left arm
    		leftArmX = (leftArmX + 15) % 360;
    		leftElbowX = (leftElbowX + 10) % 360;
    		
    		//right leg
    		rightLegX = (rightLegX + 15) % 360;
    		rightKneeX = (rightKneeX + 10) % 360;
    		
    		//left leg
    		leftLegX = (leftLegX - 15) % 360;
    		leftKneeX = (leftKneeX - 10) % 360;
    		
    		//chest
    		chestR = (chestR + 2) % 360;
    		
    		//butt
    		buttR = (buttR - 2) % 360;
    		
    		//head
    		headX = (headX - 1) % 360;
    		headY = (headY + 5) % 360;
    		
    		myUT.glutPostRedisplay ();
    	}
    }
    
    public void myReshape (int w, int h) {
    	
		myGL.glViewport (0, 0, w, h);
		myGL.glMatrixMode (GL.GL_PROJECTION);
		myGL.glLoadIdentity ();
		myGLU.gluPerspective (90.0, (double)w/(double)h, 1.0, 30.0);
		myGLU.gluLookAt(0.0, 0.0, 7.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		myGL.glMatrixMode (GL.GL_MODELVIEW);
		myGL.glLoadIdentity ();
		myGL.glTranslatef (0.0f, 0.0f, 0.0f);
    }

    public void mouse (int button, int state, int x, int y) {
    	
    	if (button == GLUT.GLUT_RIGHT_BUTTON) {
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
	
		//test animation
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
		rightArmX = (rightArmX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'G':
		rightArmX = (rightArmX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'b':
		rightElbowX = (rightElbowX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'B':
		rightElbowX = (rightElbowX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'j':
		leftArmX = (leftArmX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'J':
		leftArmX = (leftArmX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'n':
		leftElbowX = (leftElbowX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'N':
		leftElbowX = (leftElbowX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		
	    case 'f':
		rightLegX = (rightLegX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'F':
		rightLegX = (rightLegX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'v':
		rightKneeX = (rightKneeX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'V':
		rightKneeX = (rightKneeX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'k':
		rightLegX = (rightLegX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'K':
		rightLegX = (rightLegX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'm':
		rightKneeX = (rightKneeX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'M':
		rightKneeX = (rightKneeX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		
	    case 'o':
		headX = (headX + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'O':
		headX = (headX - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'p':
		headY = (headY + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'P':
		headY = (headY - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		
	    case 'e':
		chestR = (chestR + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'E':
		chestR = (chestR - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'r':
		buttR = (buttR + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
	    case 'R':
		buttR = (buttR - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		
	    case 27:
		System.exit(0);
	    default:
		break;
	}
    }
    
    public void drawCube(float size) {
    	
    	myGL.glPushMatrix();
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-(size/2), (size/2), -(size/2));
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f ((size/2), (size/2), -(size/2));
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f ((size/2), -(size/2), -(size/2));
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-(size/2), -(size/2), -(size/2));
		myGL.glEnd ();
		
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-(size/2), (size/2), (size/2));
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (-(size/2), (size/2), -(size/2));
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (-(size/2), -(size/2), -(size/2));
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-(size/2), -(size/2), (size/2));
		myGL.glEnd ();
		
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f ((size/2), (size/2), -(size/2));
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f ((size/2), (size/2), (size/2));
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f ((size/2), -(size/2), (size/2));
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f ((size/2), -(size/2), -(size/2));
		myGL.glEnd ();
		
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f ((size/2), (size/2), (size/2));
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (-(size/2), (size/2), (size/2));
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (-(size/2), -(size/2), (size/2));
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f ((size/2), -(size/2), (size/2));
		myGL.glEnd ();
		
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-(size/2), (size/2), (size/2));
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f ((size/2), (size/2), (size/2));
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f ((size/2), (size/2), -(size/2));
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-(size/2), (size/2), -(size/2));
		myGL.glEnd ();
		
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-(size/2), -(size/2), -(size/2));
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f ((size/2), -(size/2), -(size/2));
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f ((size/2), -(size/2), (size/2));
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-(size/2), -(size/2), (size/2));
		myGL.glEnd ();
	
	myGL.glPopMatrix();
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
		my_robot mainCanvas = new my_robot ();
		mainCanvas.init();
		mainFrame.add (mainCanvas);
		mainFrame.setVisible (true);
    }

}
