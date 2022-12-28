package TH6;


import java.awt.Color;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import javax.imageio.ImageIO;

import jgl.GL;
import jgl.GLCanvas;

public class texbind2 extends GLCanvas {

    /* Create checkerboard texture */
    private static final int checkImageWidth = 256;
    private static final int checkImageHeight = 256;
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
    
    private static float r, y = 0;
    private static float t = 0;

    private void makeCheckImage () {
    	
    	int i, j;
    	float ti, tj;
    	
    	String str = "E:/Homework/N3 - HK1/Graphic/Labs/TH6/texture/sky/";
    	
    	File bmpCenter = new File(str + "center.bmp");
    	File bmpLeft = new File(str + "left.bmp");
    	File bmpRight = new File(str + "right.bmp");
    	File bmpFront = new File(str + "front.bmp");
    	File bmpTop = new File(str + "top.bmp");
    	File bmpBottom = new File(str + "bottom.bmp");
    	
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
	myGL.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
	myGL.glShadeModel (GL.GL_FLAT);
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

    public void display () {
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	myGL.glMatrixMode(GL.GL_MODELVIEW);
	myGL.glLoadIdentity();
	
	//room
	myGL.glTranslatef(0.0f, 0.0f, t);
	myGL.glRotatef(r, 0.0f, 1.0f, 0.0f);
	myGL.glRotatef(y, 1.0f, 0.0f, 0.0f);
	myGL.glEnable (GL.GL_TEXTURE_2D);
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [0]);
	
	myGL.glBegin (GL.GL_QUADS);
	myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-2.0f, 2.0f, -2.0f);
	myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (2.0f, 2.0f, -2.0f);
	myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (2.0f, -2.0f, -2.0f);
	myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-2.0f, -2.0f, -2.0f);
	myGL.glEnd ();
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [1]);
	myGL.glBegin (GL.GL_QUADS);
	myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-2.0f, 2.0f, 2.0f);
	myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (-2.0f, 2.0f, -2.0f);
	myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (-2.0f, -2.0f, -2.0f);
	myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-2.0f, -2.0f, 2.0f);
	myGL.glEnd ();
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [2]);
	myGL.glBegin (GL.GL_QUADS);
	myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (2.0f, 2.0f, -2.0f);
	myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (2.0f, 2.0f, 2.0f);
	myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (2.0f, -2.0f, 2.0f);
	myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (2.0f, -2.0f, -2.0f);
	myGL.glEnd ();
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [3]);
	myGL.glBegin (GL.GL_QUADS);
	myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (2.0f, 2.0f, 2.0f);
	myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (-2.0f, 2.0f, 2.0f);
	myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (-2.0f, -2.0f, 2.0f);
	myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (2.0f, -2.0f, 2.0f);
	myGL.glEnd ();
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [4]);
	myGL.glBegin (GL.GL_QUADS);
	myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-2.0f, 2.0f, 2.0f);
	myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (2.0f, 2.0f, 2.0f);
	myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (2.0f, 2.0f, -2.0f);
	myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-2.0f, 2.0f, -2.0f);
	myGL.glEnd ();
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [5]);
	myGL.glBegin (GL.GL_QUADS);
	myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-2.0f, -2.0f, -2.0f);
	myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (2.0f, -2.0f, -2.0f);
	myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (2.0f, -2.0f, 2.0f);
	myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-2.0f, -2.0f, 2.0f);
	myGL.glEnd ();
	
	myGL.glFlush ();
    }

    public void myReshape (int w, int h) {
        myGL.glViewport (0, 0, w, h);
        myGL.glMatrixMode (GL.GL_PROJECTION);
        myGL.glLoadIdentity ();
	myGLU.gluPerspective (60.0, 1.0 * (double)w/(double)h, 1.0, 30.0);
        myGL.glMatrixMode (GL.GL_MODELVIEW);
        myGL.glLoadIdentity ();
	myGL.glTranslatef (0.0f, 0.0f, -3.6f);
    }

    public void keyboard (char key, int x, int y) {
	switch (key) {
	
		case 'r':
		r += 0.75;
		myUT.glutPostRedisplay ();
		break;
		case 'R':
		r -= 0.75;
		myUT.glutPostRedisplay ();
		break;
		case 'y':
		y += 0.75;
		myUT.glutPostRedisplay ();
		break;
		case 'Y':
		y -= 0.75;
		myUT.glutPostRedisplay ();
		break;
	    case 't':
		t += 0.25;
		myUT.glutPostRedisplay ();
		break;
	    case 'T':
		t -= 0.25;
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
	myUT.glutReshapeFunc ("myReshape");
	myUT.glutDisplayFunc ("display");
	myUT.glutKeyboardFunc ("keyboard");
	myUT.glutMainLoop ();
    }

    static public void main (String args[]) throws IOException {
	Frame mainFrame = new Frame ();
	mainFrame.setSize (508, 527);
	texbind2 mainCanvas = new texbind2 ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
