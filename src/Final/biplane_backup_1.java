package Final;

import java.awt.Color;

/*
 *  bezsurf.java			
 *  This program uses evaluators to draw a Bezier surface.
 */

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
import jgl.glu.GLUquadricObj;

public class biplane_backup_1 extends GLCanvas {
	
	private static float cameraX, cameraY, cameraZ = 0;
    private static float zoomZ = 0;
    private static int cameraAngle = 1;
    
    private float xequalzero[] = {1.0f, 0.0f, 0.0f, 0.0f};
    private float slanted[] = {1.0f, 1.0f, 1.0f, 0.0f};
    private float currentCoeff[];
    private int currentPlane;
    private int currentGenMode;
    
    private static float rudderR, elevatorR, aileronRR, aileronRL = 0;
    
    private static final float measurePoints [][] = {
			{-7.0f, 6.0f, 0.0f},
		{-6.0f, 6.0f, 0.0f},
		{-5.0f, 6.0f, 0.0f},
		{-4.0f, 6.0f, 0.0f},
		{-3.0f, 6.0f, 0.0f},
		{-2.0f, 6.0f, 0.0f},
		{-1.0f, 6.0f, 0.0f},
		{0.0f, 6.0f, 0.0f},
		{1.0f, 6.0f, 0.0f},
		{2.0f, 6.0f, 0.0f},
		{3.0f, 6.0f, 0.0f},
		{4.0f, 6.0f, 0.0f},
		{5.0f, 6.0f, 0.0f},
		{6.0f, 6.0f, 0.0f},
		{7.0f, 6.0f, 0.0f}};
    
    private static final float upperBody [][][] = {
    		//left
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, 0.0f, 2.5f},
			{0.0f, 0.0f, 1.5f},
			{3.5f, 0.0f, 0.35f},
		{ 7.0f,  0.0f, 0.05f}},
			
			//inbetween
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, 4.25f, 2.5f},
			{0.0f, 0.5f, 0.75f},
			{3.5f, 0.25f, 0.05f},
		{ 7.0f,  0.75f, 0.05f}},
			
			//mid
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, 2.5f, 0.0f},
			{0.0f, 1.5f, 0.0f},
			{3.5f, 0.75f, 0.0f},
		{ 7.0f,  0.5f, 0.0f}},
			
			//inbetween
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, 4.25f, -2.5f},
			{0.0f, 0.5f, -0.75f},
			{3.5f, 0.25f, -0.05f},
		{ 7.0f,  0.75f, -0.05f}},
			
			//left
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, 0.0f, -2.5f},
			{0.0f, 0.0f, -1.5f},
			{3.5f, 0.0f, -0.35f},
		{ 7.0f,  0.0f, -0.05f}}};
    
    private static final float lowerBody [][][] = {
    		//left
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, 0.0f, 2.5f},
			{0.0f, 0.0f, 1.5f},
			{3.5f, 0.0f, 0.35f},
		{ 7.0f,  0.0f, 0.05f}},
			
			//inbetween
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, -3.25f, 2.5f},
			{0.0f, -0.75f, 0.75f},
			{3.5f, -0.5f, 0.05f},
		{ 7.0f,  -0.75f, 0.05f}},
			
			//mid
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, -2.5f, 0.0f},
			{0.0f, -1.5f, 0.0f},
			{3.5f, -1.5f, 0.0f},
		{ 7.0f,  -0.5f, 0.05f}},
			
			//inbetween
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, -3.25f, -2.5f},
			{0.0f, -0.75f, -0.75f},
			{3.5f, -0.5f, -0.05f},
		{ 7.0f,  -0.75f, -0.05f}},
			
			//left
			{{-7.0f, 0.0f, 0.0f},
			{-7.5f, 0.0f, -2.5f},
			{0.0f, 0.0f, -1.5f},
			{3.5f, 0.0f, -0.35f},
		{ 7.0f,  0.0f, -0.05f}}};
    
	private static final float frontTail [][][] = {
			{{4.5f, 0.0f, 0.05f},
		{5.5f, 0.0f, 0.05f},
		{7.0f, 0.0f, 0.05f}},	  
			  
			{{4.5f, 0.0f, 0.0f},
		{5.5f, 5.0f, 0.0f},
		{7.0f, 5.0f, 0.0f}},
			
			{{4.5f, 0.0f, -0.05f},
		{5.5f, 0.0f, -0.05f},
		{7.0f, 0.0f, -0.05f}}};
   	  
    private static final float topRudder [][][] = {
    			{{7.0f, 0.0f, 0.05f},
 		{8.25f, 0.0f, 0.05f},
 		{8.5f, 0.0f, 0.05f}},	  
    			  
 			{{7.0f, -1.15f, 0.0f},
 		{8.25f, -1.0f, 0.0f},
 		{8.5f, 0.0f, 0.0f}},
 			
 			{{7.0f, 0.0f, -0.05f},
 		{8.25f, 0.0f, -0.05f},
 		{8.5f, 0.0f, -0.05f}}};
   	  
   	private static final float botRudder [][][] = {
 			{{7.0f, 0.0f, 0.05f},
		{8.25f, 0.0f, 0.05f},
		{8.5f, 0.0f, 0.05f}},	  
 			  
			{{7.0f, 5.0f, 0.0f},
		{8.25f, 5.0f, 0.0f},
		{8.5f, 0.0f, 0.0f}},
			
			{{7.0f, 0.0f, -0.05f},
		{8.25f, 0.0f, -0.05f},
		{8.5f, 0.0f, -0.05f}}};
    
	private static final float frontFin [][][] = {
 			{{5.5f, 0.05f, 0.0f},
		{5.5f, 0.15f, 0.0f},
		{7.0f, 0.15f, 0.0f}},	  
 			  
 			{{5.5f, 0.0f, 0.0f},
		{5.5f, 0.0f, 7.5f},
		{7.0f, 0.0f, 7.0f}},
			
 			{{5.5f, -0.05f, 0.0f},
		{5.5f, -0.15f, 0.0f},
		{7.0f, -0.15f, 0.0f}}};
   	
   	private static final float elevator [][][] = {
 			{{7.0f, 0.0f, 0.0f},
 		{7.0f, 0.0f, 0.0f},
		{8.0f, 0.0f, 0.5f},
		{8.0f, 0.0f, 1.0f}},	  
 			  
			{{7.0f, 0.0f, 4.75f},
		{9.0f, 0.0f, 4.75f},
		{9.0f, 0.0f, 4.0f},
		{8.0f, 0.0f, 1.0f}},
			
			{{7.0f, 0.0f, 4.75f},
		{9.0f, 0.0f, 4.75f},
		{9.0f, 0.0f, 4.0f},
		{8.0f, 0.0f, 1.0f}},
			
			{{7.0f, 0.0f, 0.0f},
 		{7.0f, 0.0f, 0.0f},
		{8.0f, 0.0f, 0.5f},
		{8.0f, 0.0f, 1.0f}}};
   	
 	private static final float mainWing [][][] = {
 			{{1.0f, 0.15f, -6.25f},
		{-2.25f, 0.15f, -6.25f},
		{-2.25f, -0.15f, -6.25f},
		{1.0f, -0.15f, -6.25f}},	  
 			  
 			{{1.0f, 0.15f, -3.125f},
		{-2.25f, 0.15f, -3.125f},
		{-2.25f, -0.15f, -3.125f},
		{1.0f, -0.15f, -3.125f}},	
 			
 			{{1.0f, 0.15f, 3.215f},
		{-2.25f, 0.15f, 3.125f},
		{-2.25f, -0.15f, 3.125f},
		{1.0f, -0.15f, 3.125f}},	
			
 			{{1.0f, 0.15f, 6.25f},
		{-2.25f, 0.15f, 6.25f},
		{-2.25f, -0.15f, 6.25f},
		{1.0f, -0.15f, 6.25f}}};
   	
   	private static final float wingEdge [][][] = {
   			{{-1.45f, 0.05f, 6.25f},
		{-1.45f, 0.15f, 6.25f},
		{1.0f, 0.15f, 6.25f}},	  
 			  
 			{{-1.45f, 0.0f, 6.25f},
		{-1.45f, 0.0f, 12.5f},
		{1.0f, 0.0f, 12.0f}},
			
 			{{-1.45f, -0.05f, 6.25f},
		{-1.45f, -0.15f, 6.25f},
		{1.0f, -0.15f, 6.25f}}};
   	
   	private static final float backWing [][][] = {
   			{{1.0f, 0.15f, 6.25f},
		{2.25f, 0.0f, 6.25f},
		{2.25f, -0.0f, 6.25f},
		{1.0f, -0.15f, 6.25f}},	  
 			  
 			{{1.0f, 0.15f, 5.0f},
		{2.25f, 0.0f, 5.0f},
		{2.25f, -0.0f, 5.0f},
		{1.0f, -0.15f, 5.0f}},	
 			
 			{{1.0f, 0.15f, 3.75f},
		{2.25f, 0.0f, 3.75f},
		{2.25f, -0.0f, 3.75f},
		{1.0f, -0.15f, 3.75f}},	
			
 			{{1.0f, 0.15f, 1.25f},
		{2.25f, 0.0f, 1.25f},
		{2.25f, -0.0f, 1.25f},
		{1.0f, -0.15f, 1.25f}}};
   	
   	private static final float curveWing [][][] = {
   			{{1.0f, 0.15f, 1.25f},
		{2.25f, 0.0f, 1.25f},
		{2.25f, -0.0f, 1.25f},
		{1.0f, -0.15f, 1.25f}},	  
 			  
 			{{1.0f, 0.15f, 0.625f},
		{1.0f, 0.0f, 0.625f},
		{1.0f, -0.0f, 0.625f},
		{1.0f, -0.15f, 0.625f}},	
 			
 			{{1.0f, 0.15f, -0.625f},
		{1.0f, 0.0f, -0.625f},
		{1.0f, -0.0f, -0.625f},
		{1.0f, -0.15f, -0.625f}},	
			
 			{{1.0f, 0.15f, -1.25f},
		{2.25f, 0.0f, -1.25f},
		{2.25f, -0.0f, -1.25f},
		{1.0f, -0.15f, -1.25f}}};
	
	private static final float aileron [][][] = {
   			{{1.0f, 0.15f, 6.25f},
		{1.8f, 0.0f, 6.25f},
		{1.8f, 0.0f, 6.25f}},	  
 			  
 			{{1.0f, 0.0f, 12.0f},
		{1.8f, 0.0f, 12.0f},
		{1.8f, 0.0f, 11.0f}},
			
 			{{1.0f, -0.15f, 6.25f},
		{1.8f, -0.0f, 6.25f},
		{1.8f, -0.0f, 6.25f}}};
   	
    private static final float texpts [][][] = {
			{{ 0.0f,  0.0f},
			 { 0.0f,  1.0f}},
			{{ 1.0f,  0.0f},
			 { 1.0f,  1.0f}}};

    private void myinit () {
    	
	float ambient [] = {0.0f,0.0f,0.0f,1.0f};
	float diffuse [] = {1.0f,1.0f,1.0f,1.0f};
	float specular [] = {1.0f,1.0f,1.0f,1.0f};
	float position [] = {-1.0f,5.0f,10.0f,0.0f};
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
	
    myGL.glClearColor (0.0f, 0.0f, 0.0f, 1.0f);
	myGL.glShadeModel (GL.GL_SMOOTH);
	myGL.glEnable (GL.GL_DEPTH_TEST);
	myGL.glMap2f (GL.GL_MAP2_TEXTURE_COORD_2, 0.0f, 1.0f, 2, 2,
		      0.0f, 1.0f, 4, 2, texpts);
	myGL.glEnable (GL.GL_MAP2_TEXTURE_COORD_2);
	
	myGL.glMapGrid2f (20, 0.0f, 1.0f, 20, 0.0f, 1.0f);
	
	makeCheckImage ();
	myGL.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
	
	myGL.glGenTextures (6, texName);

	myGL.glBindTexture (GL.GL_TEXTURE_2D, texName [4]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, checkImageWidth,
    	    checkImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, imageTop);

	myGL.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
	currentCoeff = xequalzero;
	currentGenMode = GL.GL_OBJECT_LINEAR;
	currentPlane = GL.GL_OBJECT_PLANE;
	myGL.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, currentGenMode);
	myGL.glTexGenfv(GL.GL_S, currentPlane, currentCoeff);

	myGL.glEnable (GL.GL_TEXTURE_2D);
//	myGL.glEnable (GL.GL_AUTO_NORMAL);
	myGL.glEnable (GL.GL_NORMALIZE);
	myGL.glShadeModel (GL.GL_FLAT);
    }

    public void display () {
    	
	GLUquadricObj qobj;
	qobj = myGLU.gluNewQuadric();	
    	
	int i, j;
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
	
	//measure
	myGL.glPushMatrix ();
		myGL.glPointSize (5.0f);
		myGL.glDisable (GL.GL_LIGHTING);
		myGL.glDisable (GL.GL_LIGHT0);
		myGL.glDisable(GL.GL_TEXTURE_2D);
		myGL.glColor3f (1.0f, 1.0f, 0.0f);
		myGL.glBegin (GL.GL_POINTS);
		for (i = 0; i < 15; i++)
			myGL.glVertex3fv (measurePoints[i]);
		myGL.glEnd ();
    myGL.glPopMatrix();
    
    //lighting
  	myGL.glTranslatef (1.25f, 0.0f, 0.0f);
	myGL.glMaterialfv (GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient);
	myGL.glMaterialfv (GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse);
	myGL.glMaterialfv (GL.GL_FRONT, GL.GL_SPECULAR, mat_specular);
	myGL.glMaterialfv (GL.GL_FRONT, GL.GL_SHININESS, high_shininess);
	myGL.glMaterialfv (GL.GL_FRONT, GL.GL_EMISSION, no_mat);
	myGL.glTranslatef (-1.25f, 0.0f, 0.0f);
	
	//fill biplane
	myGL.glPushMatrix ();
	
//	myGL.glEnable (GL.GL_LIGHTING); //bat lai den
//	myGL.glEnable (GL.GL_LIGHT0);
	myGL.glEnable(GL.GL_TEXTURE_2D);
//	myGL.glDisable(GL.GL_TEXTURE_2D);
	
	myGL.glColor3f (1.0f, 1.0f, 1.0f);
		
		//engine
		myGL.glPushMatrix();
	
			//ring
			myGL.glPushMatrix();
				myGL.glDisable(GL.GL_TEXTURE_2D);
				myGL.glTranslatef(-6.0f, 0.0f, 0.0f);
				myGL.glRotatef(90, 0.0f, 1.0f, 0.0f);
				myGL.glScalef(1.0f, 1.0f, 3.0f);
				myUT.glutSolidTorus(0.25, 1.35, 20, 20);
				myGL.glEnable(GL.GL_TEXTURE_2D);
			myGL.glPopMatrix();
		myGL.glPopMatrix();
		
		//seat??
		myGL.glPushMatrix();
			myGL.glDisable(GL.GL_TEXTURE_2D);
			myGL.glTranslatef(-2.5f, 1.3f, 0.0f);
			myGL.glRotatef(90, 1.0f, 0.0f, 0.0f);
			myGL.glRotatef(-5, 0.0f, 1.0f, 0.0f);
			myGL.glScalef(1.0f, 1.0f, 1.5f);
			myUT.glutSolidTorus(0.15, 1.1, 20, 20);
			myGL.glEnable(GL.GL_TEXTURE_2D);
		myGL.glPopMatrix();
		
		//upperbody
		myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 5,
			      0.0f, 1.0f, 12, 5, upperBody);
		myGL.glEnable (GL.GL_MAP2_VERTEX_3);
		myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
		
		//lowerbody
		myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 5,
			      0.0f, 1.0f, 12, 5, lowerBody);
		myGL.glEnable (GL.GL_MAP2_VERTEX_3);
		myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
		
		//frontail
		myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
			      0.0f, 1.0f, 12, 3, frontTail);
		myGL.glEnable (GL.GL_MAP2_VERTEX_3);
		myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
		
		//connector
		myGL.glPushMatrix ();
			myGL.glDisable(GL.GL_TEXTURE_2D);
		    myGL.glTranslatef(7.0f, 0.0f, 0.0f);
		    myGL.glTranslatef(0.0f, 2.5f, 0.0f);
		    myGL.glRotatef(90, 1.0f, 0.0f, 0.0f);
		    myGLU.gluCylinder (qobj, 0.05, 0.05, 3.0, 15, 5);
		    myGL.glTranslatef(0.0f, -2.5f, 0.0f);
		    myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
		    myGL.glEnable(GL.GL_TEXTURE_2D);
		myGL.glPopMatrix ();
	    
		//rudder
		myGL.glPushMatrix ();
			myGL.glTranslatef(0.1f, 0.0f, 0.0f);
		
			//rotate
			myGL.glTranslatef(7.0f, 0.0f, 0.0f);
			myGL.glRotatef(rudderR, 0.0f, 1.0f, 0.0f);
			myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
			
			//back1
			myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
				      0.0f, 1.0f, 12, 3, topRudder);
			myGL.glEnable (GL.GL_MAP2_VERTEX_3);
			myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			
			//back2
			myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
				      0.0f, 1.0f, 12, 3, botRudder);
			myGL.glEnable (GL.GL_MAP2_VERTEX_3);
			myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			
			myGL.glTranslatef(-0.1f, 0.0f, 0.0f);
		myGL.glPopMatrix ();
		
		//front fin
		myGL.glPushMatrix ();
			myGL.glTranslatef(7.0f, 0.0f, 0.0f);
			myGL.glRotatef(-10, 0.0f, 0.0f, 1.0f);
			myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
			myGL.glTranslatef(0.0f, 0.1f, 0.0f);
			
			//left
			myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
				      0.0f, 1.0f, 12, 3, frontFin);
			myGL.glEnable (GL.GL_MAP2_VERTEX_3);
			myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			
			//right
			myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
			myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
				      0.0f, 1.0f, 12, 3, frontFin);
			myGL.glEnable (GL.GL_MAP2_VERTEX_3);
			myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			
			myGL.glTranslatef(-0.0f, -0.1f, 0.0f);
		myGL.glPopMatrix ();
		
		//connector
		myGL.glPushMatrix ();
			myGL.glDisable(GL.GL_TEXTURE_2D);
		    myGL.glTranslatef(7.0f, 0.0f, 0.0f);
		    myGL.glTranslatef(0.0f, 0.2f, -3.5f);
		    myGLU.gluCylinder (qobj, 0.05, 0.05, 7.0, 15, 5);
		    myGL.glTranslatef(0.0f, -0.2f, 3.5f);
		    myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
		    myGL.glEnable(GL.GL_TEXTURE_2D);
		myGL.glPopMatrix ();
		
		//elevator
		myGL.glPushMatrix ();
			myGL.glTranslatef(7.0f, 0.0f, 0.0f);
			myGL.glRotatef(-10, 0.0f, 0.0f, 1.0f);
			myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
			myGL.glTranslatef(0.1f, 0.1f, 0.0f);
			
			//rotate
			myGL.glTranslatef(7.0f, 0.0f, 0.0f);
			myGL.glRotatef(elevatorR, 0.0f, 0.0f, 1.0f);
			myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
			
			//left
			myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
				      0.0f, 1.0f, 12, 4, elevator);
			myGL.glEnable (GL.GL_MAP2_VERTEX_3);
			myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			
			//right
			myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
			myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
				      0.0f, 1.0f, 12, 4, elevator);
			myGL.glEnable (GL.GL_MAP2_VERTEX_3);
			myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			
			myGL.glTranslatef(-0.1f, -0.1f, 0.0f);
		myGL.glPopMatrix ();
		
		//top Wing
		myGL.glPushMatrix ();
			
			myGL.glRotatef(-5, 0.0f, 0.0f, 1.0f);
			myGL.glTranslatef(-3.0f, 2.5f, 0.0f);
			
			//mid
			myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
				      0.0f, 1.0f, 12, 4, mainWing);
			myGL.glEnable (GL.GL_MAP2_VERTEX_3);
			myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			
			//leftEdge
			myGL.glPushMatrix ();
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
					      0.0f, 1.0f, 12, 3, wingEdge);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//rightEdge
			myGL.glPushMatrix ();
				myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
					      0.0f, 1.0f, 12, 3, wingEdge);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//leftBack
			myGL.glPushMatrix ();
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
					      0.0f, 1.0f, 12, 4, backWing);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//rightBack
			myGL.glPushMatrix ();
				myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
					      0.0f, 1.0f, 12, 4, backWing);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//curveBack
			myGL.glPushMatrix ();
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
					      0.0f, 1.0f, 12, 4, curveWing);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//connector
			myGL.glPushMatrix ();
				myGL.glDisable(GL.GL_TEXTURE_2D);
				//left
			    myGL.glTranslatef(1.125f, 0.0f, 6.25f);
			    myGLU.gluCylinder (qobj, 0.085, 0.085, 2.75, 15, 5);
			    myGL.glTranslatef(-1.125f, 0.0f, -6.25f);
			    
			    //right
			    myGL.glTranslatef(1.125f, 0.0f, -9.0f);
			    myGLU.gluCylinder (qobj, 0.085, 0.085, 2.75, 15, 5);
			    myGL.glTranslatef(-1.125f, 0.0f, 9.0f);
			    myGL.glEnable(GL.GL_TEXTURE_2D);
			myGL.glPopMatrix ();
			
			//aileron
			myGL.glPushMatrix ();
				
				myGL.glTranslatef(0.1f, 0.0f, 0.0f);
			
				//left
				myGL.glPushMatrix ();
					//rotate
					myGL.glTranslatef(1.0f, 0.0f, 0.0f);
					myGL.glRotatef(aileronRL, 0.0f, 0.0f, 1.0f);
					myGL.glTranslatef(-1.0f, 0.0f, 0.0f);
					
			
					myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
						      0.0f, 1.0f, 12, 3, aileron);
					myGL.glEnable (GL.GL_MAP2_VERTEX_3);
					myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
				myGL.glPopMatrix ();
				
				//right
				myGL.glPushMatrix ();
					//rotate
					myGL.glTranslatef(1.0f, 0.0f, 0.0f);
					myGL.glRotatef(aileronRR, 0.0f, 0.0f, 1.0f);
					myGL.glTranslatef(-1.0f, 0.0f, 0.0f);
				
					myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
					myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
						      0.0f, 1.0f, 12, 3, aileron);
					myGL.glEnable (GL.GL_MAP2_VERTEX_3);
					myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
				myGL.glPopMatrix ();
				
				myGL.glTranslatef(-0.1f, 0.0f, 0.0f);
			myGL.glPopMatrix ();
		
			myGL.glTranslatef(3.0f, -2.5f, 0.0f);
				
		myGL.glPopMatrix ();
		
		//wingConnector
		myGL.glPushMatrix ();
		
			//edge left
			myGL.glPushMatrix();
			myGL.glTranslatef(0.0f, 0.0f, 7.0f);
				myGL.glPushMatrix ();
					myGL.glTranslatef(-3.25f, 1.0f, 0.0f);
					myGL.glRotatef(10, 0.0f, 0.0f, 1.0f);
					myGL.glScalef(0.05f, 0.9f, 0.025f);
					myUT.glutSolidCube(4.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef(-1.5f, 1.0f, 0.0f);
					myGL.glRotatef(10, 0.0f, 0.0f, 1.0f);
					myGL.glScalef(0.05f, 0.9f, 0.025f);
					myUT.glutSolidCube(4.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef(-2.3f, 1.0f, 0.0f);
					myGL.glRotatef(-15, 0.0f, 0.0f, 1.0f);
					myGL.glScalef(0.05f, 0.9f, 0.025f);
					myUT.glutSolidCube(4.0);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			
			//edge right
			myGL.glPushMatrix();
			myGL.glTranslatef(0.0f, 0.0f, -7.0f);
				myGL.glPushMatrix ();
					myGL.glTranslatef(-3.25f, 1.0f, 0.0f);
					myGL.glRotatef(10, 0.0f, 0.0f, 1.0f);
					myGL.glScalef(0.05f, 0.9f, 0.025f);
					myUT.glutSolidCube(4.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef(-1.5f, 1.0f, 0.0f);
					myGL.glRotatef(10, 0.0f, 0.0f, 1.0f);
					myGL.glScalef(0.05f, 0.9f, 0.025f);
					myUT.glutSolidCube(4.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef(-2.3f, 1.0f, 0.0f);
					myGL.glRotatef(-15, 0.0f, 0.0f, 1.0f);
					myGL.glScalef(0.05f, 0.9f, 0.025f);
					myUT.glutSolidCube(4.0);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			
			//seat left
			myGL.glPushMatrix ();
				myGL.glPushMatrix ();
					myGL.glTranslatef(-3.75f, 2.0f, 1.5f);
					myGL.glRotatef(25, 1.0f, 0.0f, 0.0f);
					myGL.glScalef((0.2f/2.0f), 1.0f, (0.1f/2.0f));
					myUT.glutSolidCube(2.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef(-1.75f, 2.0f, 1.5f);
					myGL.glRotatef(25, 1.0f, 0.0f, 0.0f);
					myGL.glScalef((0.2f/2.0f), 1.0f, (0.1f/2.0f));
					myUT.glutSolidCube(2.0);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			
			//seat right
			myGL.glPushMatrix ();
				myGL.glPushMatrix ();
					myGL.glTranslatef(-3.75f, 2.0f, -1.5f);
					myGL.glRotatef(-25, 1.0f, 0.0f, 0.0f);
					myGL.glScalef((0.2f/2.0f), 1.0f, (0.1f/2.0f));
					myUT.glutSolidCube(2.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef(-1.75f, 2.0f, -1.5f);
					myGL.glRotatef(-25, 1.0f, 0.0f, 0.0f);
					myGL.glScalef((0.2f/2.0f), 1.0f, (0.1f/2.0f));
					myUT.glutSolidCube(2.0);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
		myGL.glPopMatrix ();
		
		//botWing
		myGL.glPushMatrix ();
			
			myGL.glRotatef(-5, 0.0f, 0.0f, 1.0f);
			myGL.glTranslatef(-2.0f, -1.0f, 0.0f);
			
			//mid
			myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
				      0.0f, 1.0f, 12, 4, mainWing);
			myGL.glEnable (GL.GL_MAP2_VERTEX_3);
			myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			
			//leftEdge
			myGL.glPushMatrix ();
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
					      0.0f, 1.0f, 12, 3, wingEdge);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//rightEdge
			myGL.glPushMatrix ();
				myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
					      0.0f, 1.0f, 12, 3, wingEdge);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//leftBack
			myGL.glPushMatrix ();
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
					      0.0f, 1.0f, 12, 4, backWing);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//rightBack
			myGL.glPushMatrix ();
				myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
					      0.0f, 1.0f, 12, 4, backWing);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//curveBack
			myGL.glPushMatrix ();
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
					      0.0f, 1.0f, 12, 4, curveWing);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix ();
			
			//connector
			myGL.glPushMatrix ();
				myGL.glDisable(GL.GL_TEXTURE_2D);
				//left
			    myGL.glTranslatef(1.125f, 0.0f, 6.25f);
			    myGLU.gluCylinder (qobj, 0.085, 0.085, 2.75, 15, 5);
			    myGL.glTranslatef(-1.125f, 0.0f, -6.25f);
			    
			    //right
			    myGL.glTranslatef(1.125f, 0.0f, -9.0f);
			    myGLU.gluCylinder (qobj, 0.085, 0.085, 2.75, 15, 5);
			    myGL.glTranslatef(-1.125f, 0.0f, 9.0f);
			    myGL.glEnable(GL.GL_TEXTURE_2D);
			myGL.glPopMatrix ();
			
			//aileron
			myGL.glPushMatrix ();
				
				myGL.glTranslatef(0.1f, 0.0f, 0.0f);
			
				//left
				myGL.glPushMatrix ();
					//rotate
					myGL.glTranslatef(1.0f, 0.0f, 0.0f);
					myGL.glRotatef(aileronRL, 0.0f, 0.0f, 1.0f);
					myGL.glTranslatef(-1.0f, 0.0f, 0.0f);
					
			
					myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
						      0.0f, 1.0f, 12, 3, aileron);
					myGL.glEnable (GL.GL_MAP2_VERTEX_3);
					myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
				myGL.glPopMatrix ();
				
				//right
				myGL.glPushMatrix ();
					//rotate
					myGL.glTranslatef(1.0f, 0.0f, 0.0f);
					myGL.glRotatef(aileronRR, 0.0f, 0.0f, 1.0f);
					myGL.glTranslatef(-1.0f, 0.0f, 0.0f);
				
					myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
					myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
						      0.0f, 1.0f, 12, 3, aileron);
					myGL.glEnable (GL.GL_MAP2_VERTEX_3);
					myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
				myGL.glPopMatrix ();
				
				myGL.glTranslatef(-0.1f, 0.0f, 0.0f);
			myGL.glPopMatrix ();
		
			myGL.glTranslatef(2.0f, 1.0f, 0.0f);
				
		myGL.glPopMatrix ();
		
	myGL.glPopMatrix ();	
	myGL.glFlush ();
    }
    
  //Image
    private static final int checkImageWidth = 128;
    private static final int checkImageHeight = 128;
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
    	
    	String str = "E:/Homework/N3 - HK1/Graphic/Labs/TH6/texture/star/";
    	
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

    public void myReshape (int w, int h) {
        myGL.glViewport (0, 0, w, h);
        myGL.glMatrixMode (GL.GL_PROJECTION);
        myGL.glLoadIdentity ();
    	myGLU.gluPerspective (90.0, (double)w/(double)h, 1.0, 30.0);
		myGLU.gluLookAt(0.0, 0.0, 12.0, 0.0, 0.0, 0.0, 0.0, (double)w/(double)h, 0.0);
        
        myGL.glMatrixMode (GL.GL_MODELVIEW);
        myGL.glLoadIdentity ();
    }

    public void keyboard (char key, int x, int y) {
	switch (key) {
	
		//texture
		case 'e':
		case 'E':
		currentGenMode = GL.GL_EYE_LINEAR;
		currentPlane = GL.GL_EYE_PLANE;
		myGL.glTexGeni (GL.GL_S, GL.GL_TEXTURE_GEN_MODE, currentGenMode);
		myGL.glTexGenfv (GL.GL_S, currentPlane, currentCoeff);
		myUT.glutPostRedisplay ();
		break;
		case 'o':
		case 'O':
		currentGenMode = GL.GL_OBJECT_LINEAR;
		currentPlane = GL.GL_OBJECT_PLANE;
		myGL.glTexGeni (GL.GL_S, GL.GL_TEXTURE_GEN_MODE, currentGenMode);
		myGL.glTexGenfv (GL.GL_S, currentPlane, currentCoeff);
		myUT.glutPostRedisplay ();
		break;
		case 's':
		case 'S':
		currentCoeff = slanted;
		myGL.glTexGenfv (GL.GL_S, currentPlane, currentCoeff);
		myUT.glutPostRedisplay ();
		break;
		case 'r':
		case 'R':
		currentCoeff = xequalzero;
		myGL.glTexGenfv (GL.GL_S, currentPlane, currentCoeff);
		myUT.glutPostRedisplay ();
		break;
	
		//biplane
		case '1':
		rudderR = (rudderR + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		case '!':
		rudderR = (rudderR - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		case '2':
		elevatorR = (elevatorR + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		case '@':
		elevatorR = (elevatorR - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		case '3':
		aileronRL = (aileronRL + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		case '#':
		aileronRL = (aileronRL - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		case '4':
		aileronRR = (aileronRR + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		case '$':
		aileronRR = (aileronRR - 5) % 360;
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
		
		case 'M':
			System.out.println(cameraX + " " + cameraY + " "  + cameraZ);
			break;
		case 'v':
		if (cameraAngle == 0) {
			
			cameraX = 0;
			cameraY = 0;
			cameraZ = 0;
			cameraAngle++;
		} else if (cameraAngle == 1) {
			
			//top down
			cameraX = 90;
			cameraY = -90;
			cameraZ = 0;
			cameraAngle++;
		} else if (cameraAngle == 2) {
			
			//behind
			cameraZ = 90;
			cameraAngle++;
		} else if (cameraAngle == 3) {
			
			//front
			cameraY = 90;
			cameraZ = -90;
			cameraAngle++;
		} else if (cameraAngle == 4) {
			
			//custom
			cameraX = -15;
			cameraY = 55;
			cameraZ = 25;
			cameraAngle = 0;
		}
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
	myUT.glutReshapeFunc ("myReshape");
	myUT.glutDisplayFunc ("display");
	myUT.glutKeyboardFunc ("keyboard");
	myUT.glutMainLoop ();
    }
    
    static public void main (String args[]) throws IOException {
	Frame mainFrame = new Frame ();
	mainFrame.setSize (960, 540);
	biplane_backup_1 mainCanvas = new biplane_backup_1 ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
