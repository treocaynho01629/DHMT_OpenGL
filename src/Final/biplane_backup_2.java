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

public class biplane_backup_2 extends GLCanvas {
	
	private static float cameraX, cameraY, cameraZ = 0.1f;
    private static float zoomZ = 0;
    private static int cameraAngle = 1;
    
    private static float planeX, planeY, planeZ = 0.0f;
    private static float planeLR, planeUD, planeFB = 0.0f;
    private static boolean turnR = false;
    private static boolean turnU, turnF = true;
    
    private float xequalzero[] = {1.0f, 0.0f, 0.0f, 0.0f};
    private float slanted[] = {1.0f, 1.0f, 1.0f, 0.0f};
    private float currentCoeff[];
    private int currentPlane;
    private int currentGenMode;
    
    private static float test = 0;
    
    private static boolean lighting = false;
    private static boolean animating = false;
    private static int milliseconds_per_frame = 400;
    
    private static float rudderR, elevatorR, aileronRR, aileronRL, propellerR = 0;
    
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
			
			{{7.0f, -0.0f, 4.75f},
		{9.0f, -0.0f, 4.75f},
		{9.0f, -0.0f, 4.0f},
		{8.0f, -0.0f, 1.0f}},
			
			{{7.0f, -0.0f, 0.0f},
 		{7.0f, -0.0f, 0.0f},
		{8.0f, -0.0f, 0.5f},
		{8.0f, -0.0f, 1.0f}}};
   	
   	private static final float elevator1 [][][] = {
   			{{7.0f, 0.15f, 2.0f},
		{8.25f, 0.05f, 2.0f},
		{8.25f, 0.05f, 2.0f}},	  
 			  
 			{{7.0f, 0.0f, 5.0f},
		{8.25f, 0.0f, 5.0f},
		{8.25f, 0.0f, 3.0f}},
			
 			{{7.0f, -0.15f, 2.0f},
		{8.25f, -0.05f, 2.0f},
		{8.25f, -0.05f, 2.0f}}};
   	
	private static final float elevator2 [][][] = {
   			{{7.0f, 0.15f, 2.0f},
		{8.25f, 0.05f, 2.0f},
		{8.25f, 0.05f, 2.0f}},	  
 			  
 			{{7.0f, 0.0f, -2.0f},
		{8.25f, 0.0f, -1.0f},
		{8.25f, 0.0f, 1.0f}},
			
 			{{7.0f, -0.15f, 2.0f},
		{8.25f, -0.05f, 2.0f},
		{8.25f, -0.05f, 2.0f}}};
   	
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
   			{{-1.4f, 0.05f, 6.25f},
		{-1.4f, 0.15f, 6.25f},
		{1.0f, 0.15f, 6.25f}},	  
 			  
 			{{-1.4f, 0.0f, 6.25f},
		{-1.4f, 0.0f, 12.5f},
		{1.0f, 0.0f, 12.0f}},
			
 			{{-1.4f, -0.05f, 6.25f},
		{-1.4f, -0.15f, 6.25f},
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
   	
	private static final float fan [][][] = {
   			{{0.01f, 0.2f, 0.0f},
		{0.025f, 0.45f, 2.5f},
		{0.05f, 0.25f, 3.0f}},	  
 			  
   			{{0.0f, 0.0f, 0.0f},
		{0.0f, 0.0f, 2.5f},
		{0.0f, 0.0f, 4.0f}},
			
   			{{-0.01f, -0.2f, 0.0f},
		{-0.025f, -0.45f, 2.5f},
		{-0.05f, -0.25f, 3.0f}}};
	
    private static final float texpts [][][] = {
			{{ 0.0f,  0.0f},
			 { 0.0f,  1.0f}},
			{{ 1.0f,  0.0f},
			 { 1.0f,  1.0f}}};

    private void myinit () {
    	
	float ambient [] = {0.0f,0.0f,0.0f,1.0f};
	float diffuse [] = {1.0f,1.0f,1.0f,1.0f};
	float specular [] = {1.0f,1.0f,1.0f,1.0f};
//	float position [] = {1.0f,5.0f,1.0f,0.0f};
	float lmodel_ambient [] = {0.4f,0.4f,0.4f,1.0f};
	float local_view [] = {0.0f};
	float modelTwoside[] = {1.0f};
	
	myGL.glEnable (GL.GL_DEPTH_TEST);
	myGL.glDepthFunc (GL.GL_LESS);

	myGL.glLightfv (GL.GL_LIGHT0, GL.GL_AMBIENT, ambient);
	myGL.glLightfv (GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse);
//	myGL.glLightfv (GL.GL_LIGHT0, GL.GL_POSITION, position);
	myGL.glLightModelfv (GL.GL_LIGHT_MODEL_TWO_SIDE, modelTwoside);
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
	
	//Plane texture bind
	myGL.glGenTextures (3, planeTex);

	myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [0]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
    	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeColor1);
    
    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [1]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
    	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeColor2);
    
    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [2]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
    	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeColor3);

	myGL.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
	
	makeCheckImage ();
	myGL.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
	
	//Skybox texture bind
	myGL.glGenTextures (6, skyBoxTex);

	myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [0]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, skyBoxImageWidth,
    	    skyBoxImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, skyBoxColorCenter);
    
	myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [1]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, skyBoxImageWidth,
    	    skyBoxImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, skyBoxColorLeft);
    
	myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [2]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, skyBoxImageWidth,
    	    skyBoxImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, skyBoxColorRight);
    
	myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [3]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, skyBoxImageWidth,
    	    skyBoxImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, skyBoxColorBack);
    
	myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [4]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, skyBoxImageWidth,
    	    skyBoxImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, skyBoxColorTop);
    
	myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [5]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, skyBoxImageWidth,
    	    skyBoxImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, skyBoxColorBot);
    
	myGL.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
	
	currentCoeff = xequalzero;
	currentGenMode = GL.GL_OBJECT_LINEAR;
	currentPlane = GL.GL_OBJECT_PLANE;
	myGL.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, currentGenMode);
	myGL.glTexGenfv(GL.GL_S, currentPlane, currentCoeff);
	
	myGL.glEnable (GL.GL_TEXTURE_2D);
	myGL.glEnable (GL.GL_AUTO_NORMAL);
	myGL.glEnable (GL.GL_NORMALIZE);
	myGL.glEnable (GL.GL_CULL_FACE);
	myGL.glCullFace (GL.GL_BACK);
	myGL.glShadeModel (GL.GL_SMOOTH);
    }

    public void biplaneDisplay () {
    	
    	propellerR = (propellerR - 50) % 360;
    	
    	if (turnR) {
    		
    		//right
    		if (planeLR == -10) {
    			
    			turnR = !turnR;
    		}
    		
    		//rotate plane
    		if (aileronRL != -15 || aileronRR != 15 || rudderR != 9) {
    			
    			aileronRL = (aileronRL - 0.75f) % 360;
    			aileronRR = (aileronRR + 0.75f) % 360;
    			rudderR = (rudderR + 0.45f) % 360;
    		}
    	
	    	if (planeX != -15 || planeY != -1.5f) {
				
				planeX = (planeX - 0.75f);
				planeY = (planeY - 0.075f);
			}
    		
    		//move plane
    		planeLR = (planeLR - 0.5f);
    	} else {
    		
    		//left
    		
    		if (planeLR == 10) {
    			
    			turnR = !turnR;
    		}
    		
    		//rotate plane
    		if (aileronRL != 15 || aileronRR != -15 || rudderR != -9) {
    			
    			aileronRL = (aileronRL + 0.75f) % 360;
    			aileronRR = (aileronRR - 0.75f) % 360;
    			rudderR = (rudderR - 0.45f) % 360;
    		}
	    	
	    	if (planeX != 15 || planeY != 1.5f) {
    			
    			planeX = (planeX + 0.75f);
    			planeY = (planeY + 0.075f);
    		}
    		
    		//move plane
    		planeLR = (planeLR + 0.5f);
    	}
    	
    	if (turnU) {
    			
			//up
    		if (planeUD >= 1) {
    			
    			turnU = !turnU;
    		}
    		
    		//rotate plane
    		if (elevatorR != 15) {
    			
    			elevatorR = (elevatorR + 0.75f);
    		}
    		
    		if (planeZ != -1.5f) {
    			
    			planeZ = (planeZ - 0.075f);
    		}
    		
    		//move plane
    		planeUD = (planeUD + 0.05f);
    	} else {
    		
			//down
    		if (planeUD <= 0) {
    			
    			turnU = !turnU;
    		}
    		
    		//rotate plane
    		if (elevatorR != -15) {
    			
    			elevatorR = (elevatorR - 0.75f);
    		}
    		
    		if (planeZ != 1.5f) {
    			
    			planeZ = (planeZ + 0.075f);
    		}
    		
    		
    		//move plane
    		planeUD = (planeUD - 0.05f);
    	}
    	
    	if (turnF) {
    		
    		if (planeFB <= 0) {
    			
    			turnF = !turnF;
    		}
    		
			planeFB = (planeFB - 0.05f);
    		
    	} else {
    		
    		if (planeFB >= 1) {
    			
    			turnF = !turnF;
    		}
    		
			planeFB = (planeFB + 0.05f) % 360;
    	}
    	
		myUT.glutPostRedisplay ();
    }
    
    public void timer (int id) {
    	
    	propellerR = (propellerR - 50) % 360;
    	
    	if (turnR) {
    		
    		//right
    		if (planeLR == -10) {
    			
    			turnR = !turnR;
    		}
    		
    		//rotate plane
    		if (aileronRL != -15 || aileronRR != 15 || rudderR != 9) {
    			
    			aileronRL = (aileronRL - 0.75f) % 360;
    			aileronRR = (aileronRR + 0.75f) % 360;
    			rudderR = (rudderR + 0.45f) % 360;
    		}
    	
	    	if (planeX != -15 || planeY != -1.5f) {
				
				planeX = (planeX - 0.75f);
				planeY = (planeY - 0.075f);
			}
    		
    		//move plane
    		planeLR = (planeLR - 0.5f);
    	} else {
    		
    		//left
    		
    		if (planeLR == 10) {
    			
    			turnR = !turnR;
    		}
    		
    		//rotate plane
    		if (aileronRL != 15 || aileronRR != -15 || rudderR != -9) {
    			
    			aileronRL = (aileronRL + 0.75f) % 360;
    			aileronRR = (aileronRR - 0.75f) % 360;
    			rudderR = (rudderR - 0.45f) % 360;
    		}
	    	
	    	if (planeX != 15 || planeY != 1.5f) {
    			
    			planeX = (planeX + 0.75f);
    			planeY = (planeY + 0.075f);
    		}
    		
    		//move plane
    		planeLR = (planeLR + 0.5f);
    	}
    	
    	if (turnU) {
    			
			//up
    		if (planeUD >= 1) {
    			
    			turnU = !turnU;
    		}
    		
    		//rotate plane
    		if (elevatorR != 15) {
    			
    			elevatorR = (elevatorR + 0.75f);
    		}
    		
    		if (planeZ != -1.5f) {
    			
    			planeZ = (planeZ - 0.075f);
    		}
    		
    		//move plane
    		planeUD = (planeUD + 0.05f);
    	} else {
    		
			//down
    		if (planeUD <= 0) {
    			
    			turnU = !turnU;
    		}
    		
    		//rotate plane
    		if (elevatorR != -15) {
    			
    			elevatorR = (elevatorR - 0.75f);
    		}
    		
    		if (planeZ != 1.5f) {
    			
    			planeZ = (planeZ + 0.075f);
    		}
    		
    		
    		//move plane
    		planeUD = (planeUD - 0.05f);
    	}
    	
    	if (turnF) {
    		
    		if (planeFB <= 0) {
    			
    			turnF = !turnF;
    		}
    		
			planeFB = (planeFB - 0.05f);
    		
    	} else {
    		
    		if (planeFB >= 1) {
    			
    			turnF = !turnF;
    		}
    		
			planeFB = (planeFB + 0.05f) % 360;
    	}
    	
		myUT.glutPostRedisplay ();
    }
    
    public void display () {
    	
	GLUquadricObj qobj;
	qobj = myGLU.gluNewQuadric();	
    	
	int i, j;
	float no_mat[]={0.0f,0.0f,0.0f,1.0f};
	float mat_ambient[]={0.7f,0.7f,0.7f,1.0f};
	float mat_diffuse[]={0.1f,0.5f,0.8f,1.0f};
	float mat_specular[]={1.0f,1.0f,1.0f,1.0f};
	float high_shininess[]={10.0f};
	float position [] = {1.0f,5.0f,1.0f,0.0f};
	
	//Animation
    if (animating) {
        // Cause display to be called again after milliseconds_per_frame.
    	myUT.glutTimerFunc(milliseconds_per_frame,"timer",1);  
	}
    	
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	
	myGL.glMatrixMode (GL.GL_MODELVIEW);
	myGL.glLoadIdentity ();
	
	myGLU.gluLookAt(1.0 * cameraX, 1.0 * cameraY, 1.0 * cameraZ, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	
	//fake zoom
	myGL.glTranslatef(0.0f, 0.0f, zoomZ);
	
	//measure
//	myGL.glPushMatrix ();
//		myGL.glPointSize (5.0f);
//		myGL.glDisable(GL.GL_TEXTURE_2D);
//		myGL.glColor3f (1.0f, 1.0f, 0.0f);
//		myGL.glBegin (GL.GL_POINTS);
//		for (i = 0; i < 15; i++)
//			myGL.glVertex3fv (measurePoints[i]);
//		myGL.glEnd ();
//    myGL.glPopMatrix();
	
	//skybox
	myGL.glDisable(GL.GL_LIGHT0); //tat den
	myGL.glDisable(GL.GL_LIGHTING);
	myGL.glEnable (GL.GL_TEXTURE_2D);
	
	myGL.glPushMatrix();
	myGL.glEnable(GL.GL_CULL_FACE);
	myGL.glFrontFace (GL.GL_CW);
	
		myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [0]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-24.0f, 24.0f, -24.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (24.0f, 24.0f, -24.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (24.0f, -24.0f, -24.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-24.0f, -24.0f, -24.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [1]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-24.0f, 24.0f, 24.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (-24.0f, 24.0f, -24.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (-24.0f, -24.0f, -24.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-24.0f, -24.0f, 24.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [2]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (24.0f, 24.0f, -24.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (24.0f, 24.0f, 24.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (24.0f, -24.0f, 24.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (24.0f, -24.0f, -24.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [3]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (24.0f, 24.0f, 24.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (-24.0f, 24.0f, 24.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (-24.0f, -24.0f, 24.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (24.0f, -24.0f, 24.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [4]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-24.0f, 24.0f, 24.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (24.0f, 24.0f, 24.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (24.0f, 24.0f, -24.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-24.0f, 24.0f, -24.0f);
		myGL.glEnd ();
		
		myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [5]);
		myGL.glBegin (GL.GL_QUADS);
		myGL.glTexCoord2f (0.0f, 0.0f); myGL.glVertex3f (-24.0f, -24.0f, -24.0f);
		myGL.glTexCoord2f (0.0f, 1.0f); myGL.glVertex3f (24.0f, -24.0f, -24.0f);
		myGL.glTexCoord2f (1.0f, 1.0f); myGL.glVertex3f (24.0f, -24.0f, 24.0f);
		myGL.glTexCoord2f (1.0f, 0.0f); myGL.glVertex3f (-24.0f, -24.0f, 24.0f);
		myGL.glEnd ();
	
	myGL.glDisable(GL.GL_CULL_FACE);
	myGL.glPopMatrix();
    
	//fill biplane
	myGL.glPushMatrix ();
	
	//lighting
	myGL.glPushMatrix ();
	myGL.glTranslatef(-5.0f, 15.0f, 0.0f);
		//rotate
		myGL.glTranslatef(-5.0f, -15.0f, 0.0f);
		myGL.glRotatef(-30, 1.0f, 0.0f, 0.0f);
		myGL.glRotatef(25, 0.0f, 0.0f, 1.0f);
		myGL.glRotatef(test, 1.0f, 0.0f, 0.0f);
		myGL.glTranslatef(5.0f, 15.0f, 0.0f);
		
		//cube
		myGL.glTranslated (0.0, 0.0, 1.5);
		myGL.glDisable (GL.GL_LIGHTING);
		myGL.glDisable (GL.GL_LIGHT0);
		myGL.glDisable(GL.GL_TEXTURE_2D);
		myGL.glColor3f (0.0f, 1.0f, 1.0f);
		myUT.glutSolidCube (1.0f);
		myGL.glEnable (GL.GL_LIGHTING);
		myGL.glEnable (GL.GL_LIGHT0);
		myGL.glEnable(GL.GL_TEXTURE_2D);
		myGL.glTranslated (0.0, 0.0, -1.5);
		
		//light
		myGL.glLightfv (GL.GL_LIGHT0, GL.GL_POSITION, position);
		myGL.glMaterialfv (GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, mat_ambient);
		myGL.glMaterialfv (GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, mat_diffuse);
		myGL.glMaterialfv (GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, mat_specular);
		myGL.glMaterialfv (GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, high_shininess);
		myGL.glMaterialfv (GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, no_mat);
		
		myGL.glDisable (GL.GL_LIGHTING);
		myGL.glDisable (GL.GL_LIGHT0);
	myGL.glTranslatef(5.0f, -15.0f, 0.0f);
	myGL.glPopMatrix();
	
	if (lighting) {
		
		myGL.glEnable (GL.GL_LIGHTING); //bat lai den
		myGL.glEnable (GL.GL_LIGHT0);
	}
	myGL.glEnable(GL.GL_TEXTURE_2D);
//	myGL.glDisable(GL.GL_TEXTURE_2D);
	
	myGL.glColor3f (1.0f, 1.0f, 1.0f);
	
	//rotate plane
	myGL.glRotatef(planeX, 1.0f, 0.0f, 0.0f);
	myGL.glRotatef(planeY, 0.0f, 1.0f, 0.0f);
	myGL.glRotatef(planeZ, 0.0f, 0.0f, 1.0f);
	
	//move plane
	myGL.glTranslatef(planeFB, planeUD, planeLR);
	
		//engine
		myGL.glPushMatrix();
		myGL.glFrontFace (GL.GL_CCW);
		
			//ring
			myGL.glPushMatrix();
				myGL.glDisable(GL.GL_TEXTURE_2D);
				myGL.glTranslatef(-6.0f, 0.0f, 0.0f);
				myGL.glRotatef(90, 0.0f, 1.0f, 0.0f);
				myGL.glScalef(1.0f, 1.0f, 3.0f);
				myUT.glutSolidTorus(0.25, 1.35, 20, 20);
				myGL.glEnable(GL.GL_TEXTURE_2D);
			myGL.glPopMatrix();
			
			//propeller
			myGL.glPushMatrix();
			myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [2]);
			myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
			myGL.glRotatef(propellerR, 1.0f, 0.0f, 0.0f);
				
				//ball?
				myGL.glPushMatrix();
					myGL.glRotatef(90, 0.0f, 1.0f, 0.0f);
					myGL.glScalef(1.0f, 1.0f, 1.5f);
					myUT.glutSolidSphere(0.5, 20, 20);
				myGL.glPopMatrix();
				
				//fan
				myGL.glPushMatrix();
					myGL.glDisable(GL.GL_CULL_FACE);
					myGL.glTranslatef(-0.25f, 0.0f, 0.0f);
					myGL.glRotatef(25, 0.0f, 0.0f, 1.0f);
					myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
						      0.0f, 1.0f, 12, 3, fan);
					myGL.glEnable (GL.GL_MAP2_VERTEX_3);
					myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
					myGL.glEnable(GL.GL_CULL_FACE);
				myGL.glPopMatrix();
				
				myGL.glPushMatrix();
					myGL.glDisable(GL.GL_CULL_FACE);
					myGL.glTranslatef(-0.25f, 0.0f, 0.0f);
					myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
					myGL.glRotatef(25, 0.0f, 0.0f, 1.0f);
					myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
						      0.0f, 1.0f, 12, 3, fan);
					myGL.glEnable (GL.GL_MAP2_VERTEX_3);
					myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
					myGL.glEnable(GL.GL_CULL_FACE);
				myGL.glPopMatrix();
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
		myGL.glFrontFace (GL.GL_CW);
		myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [1]);
		myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 5,
			      0.0f, 1.0f, 12, 5, upperBody);
		myGL.glEnable (GL.GL_MAP2_VERTEX_3);
		myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
		
		//lowerbody
		myGL.glFrontFace (GL.GL_CCW);
		myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 5,
			      0.0f, 1.0f, 12, 5, lowerBody);
		myGL.glEnable (GL.GL_MAP2_VERTEX_3);
		myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
		
		//wheel
		myGL.glPushMatrix ();
		myGL.glEnable(GL.GL_CULL_FACE);
		myGL.glFrontFace (GL.GL_CCW);
		myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [1]);
		
			//support left
			myGL.glPushMatrix ();
				myGL.glPushMatrix ();
					myGL.glTranslatef(-4.5f, -1.75f, 1.1f);
					myGL.glRotatef(-25, 1.0f, 0.0f, 0.0f);
					myGL.glRotatef(15, 0.0f, 0.0f, 1.0f);
					myGL.glScalef((0.2f/3.0f), 1.0f, (0.1f/3.0f));
					myUT.glutSolidCube(3.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef(-3.0f, -1.75f, 1.1f);
					myGL.glRotatef(-25, 1.0f, 0.0f, 0.0f);
					myGL.glRotatef(-38, 0.0f, 0.0f, 1.0f);
					myGL.glScalef((0.2f/3.5f), 1.0f, (0.1f/3.5f));
					myUT.glutSolidCube(3.5);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			
			//support right
			myGL.glPushMatrix ();
				myGL.glPushMatrix ();
					myGL.glTranslatef(-4.5f, -1.75f, -1.1f);
					myGL.glRotatef(25, 1.0f, 0.0f, 0.0f);
					myGL.glRotatef(15, 0.0f, 0.0f, 1.0f);
					myGL.glScalef((0.2f/3.0f), 1.0f, (0.1f/3.0f));
					myUT.glutSolidCube(3.0);
				myGL.glPopMatrix ();
			
				myGL.glPushMatrix ();
					myGL.glTranslatef(-3.0f, -1.75f, -1.1f);
					myGL.glRotatef(25, 1.0f, 0.0f, 0.0f);
					myGL.glRotatef(-38, 0.0f, 0.0f, 1.0f);
					myGL.glScalef((0.2f/3.5f), 1.0f, (0.1f/3.5f));
					myUT.glutSolidCube(3.5);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			
			//support back
			myGL.glPushMatrix ();
				myGL.glPushMatrix ();
					myGL.glTranslatef(6.5f, -0.5f, -0.0f);
					myGL.glRotatef(35, 0.0f, 0.0f, 1.0f);
					myGL.glScalef((0.2f/2.0f), 1.0f, (0.1f/2.0f));
					myUT.glutSolidCube(2.0);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
			
			//front rail
			myGL.glPushMatrix ();
			myGL.glDisable(GL.GL_TEXTURE_2D);
			    myGL.glTranslatef(-4.1f, -3.0f, -2.5f);
			    myGLU.gluCylinder (qobj, 0.1, 0.1, 5.0, 15, 5);
		    myGL.glPopMatrix ();
		    
		    //back rail
			myGL.glPushMatrix ();
			myGL.glDisable(GL.GL_TEXTURE_2D);
			    myGL.glTranslatef(7.0f, -1.25f, -0.25f);
			    myGLU.gluCylinder (qobj, 0.05, 0.05, 0.5, 15, 5);
		    myGL.glPopMatrix ();
		    
		    //leftWheel
		    myGL.glPushMatrix ();
		    myGL.glEnable(GL.GL_TEXTURE_2D);
		    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [2]);
	    	
			    myGL.glPushMatrix ();
			    	myGL.glTranslatef(-4.1f, -3.0f, 2.5f);
					myUT.glutSolidTorus(0.25, 1.0, 15, 15);
			    myGL.glPopMatrix ();
			    
			    myGL.glPushMatrix ();
			    myGL.glDisable(GL.GL_TEXTURE_2D);
			    	myGL.glTranslatef(-4.1f, -3.0f, 2.5f);
			    	myGL.glRotatef(90, 1, 0, 0);
					myUT.glutSolidSphere(0.8, 2, 6);
				myGL.glEnable(GL.GL_TEXTURE_2D);
				myGL.glPopMatrix ();
				
				 myGL.glPushMatrix ();
			    myGL.glDisable(GL.GL_TEXTURE_2D);
			    	myGL.glTranslatef(-4.1f, -3.0f, 2.5f);
					myUT.glutSolidSphere(0.1, 6, 6);
				myGL.glEnable(GL.GL_TEXTURE_2D);
				myGL.glPopMatrix ();
		    myGL.glPopMatrix ();
		    
		    //rightWheel
		    myGL.glPushMatrix ();
		    myGL.glEnable(GL.GL_TEXTURE_2D);
		    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [2]);
	    	
			    myGL.glPushMatrix ();
			    	myGL.glTranslatef(-4.1f, -3.0f, -2.5f);
					myUT.glutSolidTorus(0.25, 1.0, 15, 15);
			    myGL.glPopMatrix ();
			    
			    myGL.glPushMatrix ();
			    myGL.glDisable(GL.GL_TEXTURE_2D);
			    	myGL.glTranslatef(-4.1f, -3.0f, -2.5f);
			    	myGL.glRotatef(90, 1, 0, 0);
					myUT.glutSolidSphere(0.8, 2, 6);
				myGL.glEnable(GL.GL_TEXTURE_2D);
				myGL.glPopMatrix ();
				
				 myGL.glPushMatrix ();
			    myGL.glDisable(GL.GL_TEXTURE_2D);
			    	myGL.glTranslatef(-4.1f, -3.0f, -2.5f);
					myUT.glutSolidSphere(0.1, 6, 6);
				myGL.glEnable(GL.GL_TEXTURE_2D);
				myGL.glPopMatrix ();
		    myGL.glPopMatrix ();
		    
		    //back wheel
		    myGL.glPushMatrix ();
		    myGL.glEnable(GL.GL_TEXTURE_2D);
		    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [2]);
	    		
		    	//left
			    myGL.glPushMatrix ();
			    	myGL.glTranslatef(7.0f, -1.25f, -0.25f);
					myUT.glutSolidTorus(0.15, 0.25, 10, 10);
					myGL.glTranslatef(4.1f, 3.0f, 2.5f);
			    myGL.glPopMatrix ();
			    
			    myGL.glPushMatrix ();
			    myGL.glDisable(GL.GL_TEXTURE_2D);
			    	myGL.glTranslatef(7.0f, -1.25f, -0.25f);
			    	myGL.glRotatef(90, 1, 0, 0);
					myUT.glutSolidSphere(0.2, 2, 6);
					myGL.glTranslatef(4.1f, 3.0f, 2.5f);	
				myGL.glEnable(GL.GL_TEXTURE_2D);
				myGL.glPopMatrix ();
				
				//right
			    myGL.glPushMatrix ();
			    	myGL.glTranslatef(7.0f, -1.25f, 0.25f);
					myUT.glutSolidTorus(0.15, 0.25, 10, 10);
					myGL.glTranslatef(4.1f, 3.0f, 2.5f);
			    myGL.glPopMatrix ();
			    
			    myGL.glPushMatrix ();
			    myGL.glDisable(GL.GL_TEXTURE_2D);
			    	myGL.glTranslatef(7.0f, -1.25f, 0.25f);
			    	myGL.glRotatef(90, 1, 0, 0);
					myUT.glutSolidSphere(0.2, 2, 6);
					myGL.glTranslatef(4.1f, 3.0f, 2.5f);	
				myGL.glEnable(GL.GL_TEXTURE_2D);
				myGL.glPopMatrix ();
				
		    myGL.glPopMatrix ();
		   
		myGL.glPopMatrix ();
		
		//frontail
		myGL.glDisable(GL.GL_CULL_FACE);
		myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [0]);
		myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
			      0.0f, 1.0f, 12, 3, frontTail);
		myGL.glEnable (GL.GL_MAP2_VERTEX_3);
		myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
		
		//connector
		myGL.glPushMatrix ();
		    myGL.glTranslatef(7.0f, 0.0f, 0.0f);
		    myGL.glTranslatef(0.0f, 2.5f, 0.0f);
		    myGL.glRotatef(90, 1.0f, 0.0f, 0.0f);
		    myGLU.gluCylinder (qobj, 0.05, 0.05, 3.0, 15, 5);
		    myGL.glTranslatef(0.0f, -2.5f, 0.0f);
		    myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
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
		    myGL.glTranslatef(7.0f, 0.0f, 0.0f);
		    myGL.glTranslatef(0.0f, 0.175f, -3.5f);
		    myGLU.gluCylinder (qobj, 0.05, 0.05, 7.0, 15, 5);
		    myGL.glTranslatef(0.0f, -0.175f, 3.5f);
		    myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
		myGL.glPopMatrix ();
		
		//elevator
		myGL.glPushMatrix ();
			myGL.glTranslatef(7.0f, 0.0f, 0.0f);
			myGL.glRotatef(-10, 0.0f, 0.0f, 1.0f);
			myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
			myGL.glTranslatef(0.0f, 0.1f, 0.0f);
			
			//rotate
			myGL.glTranslatef(7.0f, 0.0f, 0.0f);
			myGL.glRotatef(elevatorR, 0.0f, 0.0f, 1.0f);
			myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
			
			//left
			myGL.glPushMatrix();
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
					      0.0f, 1.0f, 12, 3, elevator1);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
				
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
					      0.0f, 1.0f, 12, 3, elevator2);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix();
			
			//left
			myGL.glPushMatrix();
				myGL.glRotatef(180, 1.0f, 0.0f, 0.0f);
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
					      0.0f, 1.0f, 12, 3, elevator1);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
				
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
					      0.0f, 1.0f, 12, 3, elevator2);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			myGL.glPopMatrix();
			
			myGL.glTranslatef(-0.0f, -0.1f, 0.0f);
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
				//left
			    myGL.glTranslatef(1.1f, 0.0f, 6.25f);
			    myGLU.gluCylinder (qobj, 0.085, 0.085, 2.85, 15, 5);
			    myGL.glTranslatef(-1.1f, -0.0f, -6.25f);
			    
			    //right
			    myGL.glTranslatef(1.1f, 0.0f, -9.1f);
			    myGLU.gluCylinder (qobj, 0.085, 0.085, 2.85, 15, 5);
			    myGL.glTranslatef(-1.1f, 0.0f, 9.1f);
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
		myGL.glEnable(GL.GL_CULL_FACE);
		myGL.glFrontFace (GL.GL_CCW);
		myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [1]);
		
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
					myGL.glTranslatef(-1.7f, 1.0f, 0.0f);
					myGL.glRotatef(10, 0.0f, 0.0f, 1.0f);
					myGL.glScalef(0.05f, 0.9f, 0.025f);
					myUT.glutSolidCube(4.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef(-2.45f, 1.0f, 0.0f);
					myGL.glRotatef(-10, 0.0f, 0.0f, 1.0f);
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
					myGL.glTranslatef(-1.7f, 1.0f, 0.0f);
					myGL.glRotatef(10, 0.0f, 0.0f, 1.0f);
					myGL.glScalef(0.05f, 0.9f, 0.025f);
					myUT.glutSolidCube(4.0);
				myGL.glPopMatrix ();
				
				myGL.glPushMatrix ();
					myGL.glTranslatef(-2.45f, 1.0f, 0.0f);
					myGL.glRotatef(-10, 0.0f, 0.0f, 1.0f);
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
					myGL.glTranslatef(-1.75f, 1.85f, 1.5f);
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
					myGL.glTranslatef(-1.75f, 1.85f, -1.5f);
					myGL.glRotatef(-25, 1.0f, 0.0f, 0.0f);
					myGL.glScalef((0.2f/2.0f), 1.0f, (0.1f/2.0f));
					myUT.glutSolidCube(2.0);
				myGL.glPopMatrix ();
			myGL.glPopMatrix ();
		myGL.glPopMatrix ();
		
		//botWing
		myGL.glPushMatrix ();
		myGL.glDisable(GL.GL_CULL_FACE);
		myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [0]);
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
				//left
			    myGL.glTranslatef(1.1f, 0.0f, 6.25f);
			    myGLU.gluCylinder (qobj, 0.085, 0.085, 2.85, 15, 5);
			    myGL.glTranslatef(-1.1f, -0.0f, -6.25f);
			    
			    //right
			    myGL.glTranslatef(1.1f, 0.0f, -9.1f);
			    myGLU.gluCylinder (qobj, 0.085, 0.085, 2.85, 15, 5);
			    myGL.glTranslatef(-1.1f, 0.0f, 9.1f);
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
    private static final int planeImageWidth = 128;
    private static final int planeImageHeight = 128;
    
    private static final int skyBoxImageWidth = 512;
    private static final int skyBoxImageHeight = 512;
    
    private byte planeColor1 [][][] =
    		   new byte [planeImageWidth][planeImageHeight][4];
    private byte planeColor2 [][][] =
    		   new byte [planeImageWidth][planeImageHeight][4];
    private byte planeColor3 [][][] =
 		   new byte [planeImageWidth][planeImageHeight][4];
    
    private byte skyBoxColorLeft [][][] =
 		   new byte [skyBoxImageWidth][skyBoxImageHeight][4];
    private byte skyBoxColorRight [][][] =
    		new byte [skyBoxImageWidth][skyBoxImageHeight][4];
    private byte skyBoxColorCenter [][][] =
    		new byte [skyBoxImageWidth][skyBoxImageHeight][4];
    private byte skyBoxColorBack [][][] =
    		new byte [skyBoxImageWidth][skyBoxImageHeight][4];
    private byte skyBoxColorTop [][][] =
    		new byte [skyBoxImageWidth][skyBoxImageHeight][4];
    private byte skyBoxColorBot [][][] =
    		new byte [skyBoxImageWidth][skyBoxImageHeight][4];
	
    private int planeTex [] = new int [3];
    private int skyBoxTex [] = new int[6];

    private void makeCheckImage () {
    	
    	int i, j;
    	float ti, tj;
    	
//    	String str = "E:/Homework/N3 - HK1/Graphic/Labs/Final/Texture/";
    	String str = "src/texture/";
    	
    	File bmpTex1 = new File(str + "tex_1.bmp");
    	File bmpTex2 = new File(str + "tex_2.bmp");
    	File bmpTex3 = new File(str + "tex_3.bmp");
    	
    	File bmpSkyLeft = new File(str + "left.bmp");
    	File bmpSkyRight = new File(str + "right.bmp");
    	File bmpSkyCenter = new File(str + "center.bmp");
    	File bmpSkyBack = new File(str + "back.bmp");
    	File bmpSkyTop = new File(str + "top.bmp");
    	File bmpSkyBot = new File(str + "bot.bmp");
    	
    	try {
    		
    		BufferedImage iTex1 = ImageIO.read(bmpTex1);
    		BufferedImage iTex2 = ImageIO.read(bmpTex2);
    		BufferedImage iTex3 = ImageIO.read(bmpTex3);
    		
    		BufferedImage iSkyLeft = ImageIO.read(bmpSkyLeft);
    		BufferedImage iSkyRight = ImageIO.read(bmpSkyRight);
    		BufferedImage iSkyCenter = ImageIO.read(bmpSkyCenter);
    		BufferedImage iSkyBack = ImageIO.read(bmpSkyBack);
    		BufferedImage iSkyTop = ImageIO.read(bmpSkyTop);
    		BufferedImage iSkyBot = ImageIO.read(bmpSkyBot);
    		
    		for (i = 0; i < planeImageWidth; i++) {
    			
    			for (j = 0; j < planeImageHeight; j++) {
    				
    				Color cTex1 = new Color(iTex1.getRGB(i, j));
    				Color cTex2 = new Color(iTex2.getRGB(i, j));
    				Color cTex3 = new Color(iTex3.getRGB(i, j));
    				
    				planeColor1[j][i][0] = (byte)(cTex1.getRed());
    				planeColor1[j][i][1] = (byte)(cTex1.getGreen());
    				planeColor1[j][i][2] = (byte)(cTex1.getBlue());
    				planeColor1[i][j][3] = (byte) 255;
    				
    				planeColor2[j][i][0] = (byte)(cTex2.getRed());
    				planeColor2[j][i][1] = (byte)(cTex2.getGreen());
    				planeColor2[j][i][2] = (byte)(cTex2.getBlue());
    				planeColor2[i][j][3] = (byte) 255;
    				
    				planeColor3[j][i][0] = (byte)(cTex3.getRed());
    				planeColor3[j][i][1] = (byte)(cTex3.getGreen());
    				planeColor3[j][i][2] = (byte)(cTex3.getBlue());
    				planeColor3[i][j][3] = (byte) 255;
    			}
    		}
    		
    		for (i = 0; i < skyBoxImageWidth; i++) {
    			
    			for (j = 0; j < skyBoxImageHeight; j++) {
    				
    				Color cSkyLeft = new Color(iSkyLeft.getRGB(i, j));
    				Color cSkyRight = new Color(iSkyRight.getRGB(i, j));
    				Color cSkyCenter = new Color(iSkyCenter.getRGB(i, j));
    				Color cSkyBack = new Color(iSkyBack.getRGB(i, j));
    				Color cSkyTop = new Color(iSkyTop.getRGB(i, j));
    				Color cSkyBot = new Color(iSkyBot.getRGB(i, j));
    				
    				skyBoxColorLeft[j][i][0] = (byte)(cSkyLeft.getRed());
    				skyBoxColorLeft[j][i][1] = (byte)(cSkyLeft.getGreen());
    				skyBoxColorLeft[j][i][2] = (byte)(cSkyLeft.getBlue());
    				skyBoxColorLeft[i][j][3] = (byte) 255;
    				
    				skyBoxColorRight[j][i][0] = (byte)(cSkyRight.getRed());
    				skyBoxColorRight[j][i][1] = (byte)(cSkyRight.getGreen());
    				skyBoxColorRight[j][i][2] = (byte)(cSkyRight.getBlue());
    				skyBoxColorRight[i][j][3] = (byte) 255;
    				
    				skyBoxColorCenter[j][i][0] = (byte)(cSkyCenter.getRed());
    				skyBoxColorCenter[j][i][1] = (byte)(cSkyCenter.getGreen());
    				skyBoxColorCenter[j][i][2] = (byte)(cSkyCenter.getBlue());
    				skyBoxColorCenter[i][j][3] = (byte) 255;
    				
    				skyBoxColorBack[j][i][0] = (byte)(cSkyBack.getRed());
    				skyBoxColorBack[j][i][1] = (byte)(cSkyBack.getGreen());
    				skyBoxColorBack[j][i][2] = (byte)(cSkyBack.getBlue());
    				skyBoxColorBack[i][j][3] = (byte) 255;
    				
    				skyBoxColorTop[j][i][0] = (byte)(cSkyTop.getRed());
    				skyBoxColorTop[j][i][1] = (byte)(cSkyTop.getGreen());
    				skyBoxColorTop[j][i][2] = (byte)(cSkyTop.getBlue());
    				skyBoxColorTop[i][j][3] = (byte) 255;
    				
    				skyBoxColorBot[j][i][0] = (byte)(cSkyBot.getRed());
    				skyBoxColorBot[j][i][1] = (byte)(cSkyBot.getGreen());
    				skyBoxColorBot[j][i][2] = (byte)(cSkyBot.getBlue());
    				skyBoxColorBot[i][j][3] = (byte) 255;
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
    	myGLU.gluPerspective (90.0, (double)w/(double)h, 1.0, 56.0);
		myGLU.gluLookAt(0.0, 0.0, 12.0, 0.0, 0.0, 0.0, 0.0, (double)w/(double)h, 0.0);
        
        myGL.glMatrixMode (GL.GL_MODELVIEW);
        myGL.glLoadIdentity ();
    }

    public void mouse (int button, int state, int x, int y) {
    	
    	if (button == GLUT.GLUT_RIGHT_BUTTON) {
    	    if (state == GLUT.GLUT_DOWN) {
    		myUT.glutIdleFunc ("biplaneDisplay");
    	    }
    	} else if (button == GLUT.GLUT_MIDDLE_BUTTON) {
    	    if (state == GLUT.GLUT_DOWN) {
    		myUT.glutIdleFunc (null);
    	    }
    	}
    }
    
    public void keyboard (char key, int x, int y) {
	switch (key) {
	
		//timer animation
		case '9':
		animating = !animating;
		myUT.glutPostRedisplay ();
		break;
	
		//texture
		case 'i':
		case 'I':
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
		case 'u':
		case 'U':
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
		case '5':
		propellerR = (propellerR + 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		case '%':
		propellerR = (propellerR - 5) % 360;
		myUT.glutPostRedisplay ();
		break;
		
		case 'a':
		if (aileronRL != 15 || aileronRR != -15 || rudderR != -9) {
			
			aileronRL = (aileronRL + 5) % 360;
			aileronRR = (aileronRR - 5) % 360;
			rudderR = (rudderR - 3) % 360;
		}
		
		if (planeX != 15 || planeY != 1.5f) {
			
			planeX = (planeX + 5) % 360;
			planeY = (planeY + 0.5f) % 360;
		}
		
		if (planeLR != 12) {
			
			planeLR = (planeLR + 0.25f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		case 'd':
		if (aileronRL != -15 || aileronRR != 15 || rudderR != 9) {
			
			aileronRL = (aileronRL - 5) % 360;
			aileronRR = (aileronRR + 5) % 360;
			rudderR = (rudderR + 3) % 360;
		}
		
		if (planeX != -15 || planeY != -1.5f) {
			
			planeX = (planeX - 5) % 360;
			planeY = (planeY - 0.5f) % 360;
		}
		
		if (planeLR != -12) {
			
			planeLR = (planeLR - 0.25f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		case 'w':
		if (elevatorR != 15) {
			
			elevatorR = (elevatorR + 5) % 360;
		}
		
		if (planeZ != -1.5f) {
			
			planeZ = (planeZ - 0.5f) % 360;
		}
		
		if (planeUD != 8) {
			
			planeUD = (planeUD + 0.05f) % 360;
		}
		
		myUT.glutPostRedisplay ();
		break;
		case 's':
		if (elevatorR != -15) {
			
			elevatorR = (elevatorR - 5) % 360;
		}
		
		if (planeZ != 1.5f) {
			
			planeZ = (planeZ + 0.5f) % 360;
		}
		
		if (planeUD != -8) {
			
			planeUD = (planeUD - 0.05f) % 360;
		}
		
		myUT.glutPostRedisplay ();
		break;
		case 'q':
		if (planeFB != -8) {
			
			planeFB = (planeFB - 0.25f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		
		case 'e':
		if (planeFB != 8) {
			
			planeFB = (planeFB + 0.25f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
	
		//camera
		case 'b':
		zoomZ += 0.25;
		myUT.glutPostRedisplay ();
		break;
		case 'B':
		zoomZ -= 0.25;
		myUT.glutPostRedisplay ();
		break;
		case 'z':
		cameraX = (cameraX + 0.1f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'Z':
		cameraX = (cameraX - 0.1f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'x':
		cameraY = (cameraY + 0.1f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'X':
		cameraY = (cameraY - 0.1f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'c':
		cameraZ = (cameraZ + 0.1f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'C':
		cameraZ = (cameraZ - 0.1f) % 360;
		myUT.glutPostRedisplay ();
		break;
		
		//test
		case 'm':
		lighting = !lighting;
		myUT.glutPostRedisplay ();
		break;
		case 'M':
		System.out.println(cameraX + " " + cameraY + " "  + cameraZ);
		break;
		case 't':
		test = (test + 5.0f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'T':
		test = (test - 5.0f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'v':
		if (cameraAngle == 0) {
			
			cameraX = 0;
			cameraY = 0;
			cameraZ = 0.1f;
			cameraAngle++;
		} else if (cameraAngle == 1) {
			
			//top down
			cameraX = 7.4505806E-8f;
			cameraY = 0.3f;
			cameraZ = 0;
			cameraAngle++;
		} else if (cameraAngle == 2) {
			
			//behind
			cameraX = 0.2f;
			cameraY = 1.4901161E-8f;
			cameraZ = 0;
			cameraAngle++;
		} else if (cameraAngle == 3) {
			
			//front
			cameraX = -0.3f;
			cameraY = 0;
			cameraZ = 0;
			cameraAngle++;
		} else if (cameraAngle == 4) {
			
			//custom
			cameraX = -2;
			cameraY = 0.7f;
			cameraZ = 1.4f;
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
	myUT.glutMouseFunc ("mouse");
	myUT.glutKeyboardFunc ("keyboard");
	myUT.glutMainLoop ();
    }
    
    static public void main (String args[]) throws IOException {
	Frame mainFrame = new Frame ();
	mainFrame.setSize (960, 540);
	biplane_backup_2 mainCanvas = new biplane_backup_2 ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
