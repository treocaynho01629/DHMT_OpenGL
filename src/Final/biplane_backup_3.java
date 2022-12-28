package Final;

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
import jgl.glu.GLUquadricObj;

public class biplane_backup_3 extends GLCanvas {
	
	GLUquadricObj rail;
	
	//camera stuff
	private static float cameraX, cameraY, cameraZ = 0.1f;
    private static float zoomZ = 0;
    private static int cameraAngle = 1;
    
    //sky control
    private static float turnSky = 0.0f;
    
    //robot control
    private static float rightLegX, rightLegY, rightLegZ, rightKneeX, rightKneeY, rightKneeZ = 0; //chan phai
    private static float rightArmX, rightArmY, rightArmZ, rightElbowX, rightElbowY, rightElbowZ = 0; //tay phai
    private static float leftLegX, leftLegY, leftLegZ, leftKneeX, leftKneeY, leftKneeZ = 0; //chan trai
    private static float leftArmX, leftArmY, leftArmZ, leftElbowX, leftElbowY, leftElbowZ = 0; //tay trai
    private static float headX, headY, headZ = 0; //co
    private static float chestR = 0; //nguc
    private static float buttR = 0; //mong
    
    //texgen
    private float xequalzero[] = {1.0f, 0.0f, 0.0f, 0.0f};
    private float slanted[] = {1.0f, 1.0f, 1.0f, 0.0f};
    private float currentCoeff[];
    private int currentPlane;
    private int currentGenMode;
    
    //enable stuff
    private static boolean measure = false;
    private static boolean lightCube = false;
    private static boolean lighting = true;
    private static boolean animating = false;
    private static int milliseconds_per_frame = 100; //animation frame rate
    
    //biplane control
    private static float planeX, planeY, planeZ = 0.0f;
    private static float planeLR, planeUD, planeFB = 0.0f;
    private static boolean turnR = false;
    private static boolean turnU, turnF = true;
    
    private static float rudderR, elevatorR, aileronRR, aileronRL, propellerR = 0;
    private static float controlLR, controlFB = 0;
    
    //lighting + material
    private static float lightR = 0;
	private float no_mat[]={0.0f,0.0f,0.0f,1.0f};
	private float mat_ambient[]={0.7f,0.7f,0.7f,1.0f};
	private float mat_diffuse[]={0.1f,0.5f,0.8f,1.0f};
	private float mat_specular[]={1.0f,1.0f,1.0f,1.0f};
	private float high_shininess[]={7.0f};
	private float position [] = {1.0f,5.0f,1.0f,0.0f};
	
	//Image
    private static final int planeImageWidth = 128;
    private static final int planeImageHeight = 128;
    
    private static final int skyBoxImageWidth = 512;
    private static final int skyBoxImageHeight = 512;
    
    private static final int robotImageWidth = 64;
    private static final int robotImageHeight = 64;
    
    private byte planeColor1 [][][] =
    		   new byte [planeImageWidth][planeImageHeight][4];
    private byte planeColor2 [][][] =
    		   new byte [planeImageWidth][planeImageHeight][4];
    private byte planeColor3 [][][] =
 		   new byte [planeImageWidth][planeImageHeight][4];
    private byte planeColor4 [][][] =
    		new byte [planeImageWidth][planeImageHeight][4];
    private byte planeBodyTop [][][] =
    		new byte [planeImageWidth][planeImageHeight][4];
    private byte planeBodyBot [][][] =
    		new byte [planeImageWidth][planeImageHeight][4];
    private byte planeColor5 [][][] =
    		new byte [planeImageWidth][planeImageHeight][4];
    private byte planeWing [][][] =
    		new byte [planeImageWidth][planeImageHeight][4];
    private byte planePropeller [][][] =
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
    private byte cloudColor [][][] =
    		new byte [skyBoxImageWidth][skyBoxImageHeight][4];
    
    private byte robotColor1 [][][] =
    		new byte [robotImageWidth][robotImageHeight][4];
    private byte robotColor2 [][][] =
    		new byte [robotImageWidth][robotImageHeight][4];
    private byte robotColor3 [][][] =
    		new byte [robotImageWidth][robotImageHeight][4];
	
    private int planeTex [] = new int[10];
    private int skyBoxTex [] = new int[10];
    private int robotTex[] = new int[10];
    
    
    //Plane point
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
   	
   	private static final float backWing2 [][][] = {
   			{{1.0f, 0.15f, 6.25f},
		{2.25f, 0.0f, 6.25f},
		{2.25f, -0.0f, 6.25f},
		{1.0f, -0.15f, 6.25f}},	  
 			  
 			{{1.0f, 0.15f, 3.125f},
		{2.25f, 0.0f, 3.125f},
		{2.25f, -0.0f, 3.125f},
		{1.0f, -0.15f, 3.125f}},	
 			
 			{{1.0f, 0.15f, -3.125f},
		{2.25f, 0.0f, -3.125f},
		{2.25f, -0.0f, -3.125f},
		{1.0f, -0.15f, -3.125f}},	
			
 			{{1.0f, 0.15f, -6.25f},
		{2.25f, 0.0f, -6.25f},
		{2.25f, -0.0f, -6.25f},
		{1.0f, -0.15f, -6.25f}}};
   	
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
    
    //quadric
	rail = myGLU.gluNewQuadric();
    	
    //model material
	float ambient [] = {0.0f,0.0f,0.0f,1.0f};
	float diffuse [] = {1.0f,1.0f,1.0f,1.0f};
	float lmodel_ambient [] = {0.4f,0.4f,0.4f,1.0f};
	float local_view [] = {0.0f};
	float modelTwoside[] = {1.0f};
	
	//robot prefix position
	leftLegX = -70;
	rightLegX = -70;
	leftArmX = -30;
	rightArmX = -30;
	leftArmZ = -10;
	rightArmZ = 10;
	leftElbowZ = -55;
	rightElbowZ = 55;
	
	//light setting
	myGL.glEnable (GL.GL_DEPTH_TEST);
	myGL.glDepthFunc (GL.GL_LESS);

	myGL.glLightfv (GL.GL_LIGHT0, GL.GL_AMBIENT, ambient);
	myGL.glLightfv (GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse);
	myGL.glLightModelfv (GL.GL_LIGHT_MODEL_TWO_SIDE, modelTwoside);
	myGL.glLightModelfv (GL.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient);
	myGL.glLightModelfv (GL.GL_LIGHT_MODEL_LOCAL_VIEWER, local_view);

	myGL.glEnable (GL.GL_LIGHTING);
	myGL.glEnable (GL.GL_LIGHT0);
	
	//Texture stuff
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
	myGL.glGenTextures (10, planeTex);

	myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [0]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
    	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeColor1);
    
    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [1]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
    	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeColor2);
    
    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [2]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
    	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeColor3);
    
    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [3]);
  	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
	  	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeBodyTop);
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [4]);
  	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
	  	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeBodyBot);
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [5]);
  	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
	  	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeColor4);
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
  	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
	  	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeColor5);
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [7]);
  	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
	  	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planeWing);
	
	myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [8]);
  	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, planeImageWidth,
	  	    planeImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, planePropeller);
	
	myGL.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
	
	myGL.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
	
	//Skybox texture bind
	myGL.glGenTextures (10, skyBoxTex);

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
    
    myGL.glBindTexture (GL.GL_TEXTURE_2D, skyBoxTex [6]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, skyBoxImageWidth,
    	    skyBoxImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, cloudColor);
    
	myGL.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
	
	myGL.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
	
	//Robot texture bind
	myGL.glGenTextures (10, robotTex);

    myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [0]);
	myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, robotImageWidth,
    		robotImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, robotColor1);
    
    myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [1]);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, robotImageWidth,
    		robotImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, robotColor2);
    
    myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    myGL.glTexParameterf (GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    myGL.glTexImage2D (GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, robotImageWidth,
    		robotImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, robotColor3);
    
	myGL.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
	
	//texgen
	currentCoeff = xequalzero;
	currentGenMode = GL.GL_OBJECT_LINEAR;
	currentPlane = GL.GL_OBJECT_PLANE;
	myGL.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, currentGenMode);
	myGL.glTexGenfv(GL.GL_S, currentPlane, currentCoeff);
	
	//enable setting
	myGL.glEnable (GL.GL_TEXTURE_2D);
	myGL.glEnable (GL.GL_AUTO_NORMAL);
	myGL.glEnable (GL.GL_NORMALIZE);
	myGL.glEnable (GL.GL_CULL_FACE);
	myGL.glCullFace (GL.GL_BACK);
	myGL.glShadeModel (GL.GL_SMOOTH);
    }

    public void biplaneDisplay () {
    	
    	turnSky = (turnSky - 0.1f) % 360;
    	propellerR = (propellerR - 50) % 360;
    	
    	if (turnR) {
    		
    		//right
    		if (planeLR <= -6) {
    			
    			turnR = !turnR;
    		}
    		
    		//rotate plane
    		if (aileronRL != -25 || aileronRR != 25 || rudderR != 15 || controlLR != -15) {
    			
    			aileronRL = (aileronRL - 1.25f) % 360;
    			aileronRR = (aileronRR + 1.25f) % 360;
    			rudderR = (rudderR + 0.75f) % 360;
    			controlLR = (controlLR - 0.75f) % 360;
    			
    			//robot
    			leftElbowZ = (leftElbowZ - 0.15f) % 360;
    			leftElbowY = (leftElbowY - 0.15f) % 360;
    			rightElbowZ = (rightElbowZ - 0.15f) % 360;
    			rightElbowY = (rightElbowY - 0.15f) % 360;
    		}
    	
	    	if (planeX != -15 || planeY != -1.5f) {
				
				planeX = (planeX - 0.75f);
				planeY = (planeY - 0.075f);
			}
    		
    		//move plane
    		planeLR = (planeLR - 0.3f);
    	} else {
    		
    		//left
    		
    		if (planeLR >= 6) {
    			
    			turnR = !turnR;
    		}
    		
    		//rotate plane
    		if (aileronRL != 25 || aileronRR != -25 || rudderR != -15 || controlLR != 15) {
    			
    			aileronRL = (aileronRL + 1.25f) % 360;
    			aileronRR = (aileronRR - 1.25f) % 360;
    			rudderR = (rudderR - 0.75f) % 360;
    			controlLR = (controlLR + 0.75f) % 360;
    			
    			//robot
    			leftElbowZ = (leftElbowZ + 0.15f) % 360;
    			leftElbowY = (leftElbowY + 0.15f) % 360;
    			rightElbowZ = (rightElbowZ + 0.15f) % 360;
    			rightElbowY = (rightElbowY + 0.15f) % 360;
    		}
	    	
	    	if (planeX != 15 || planeY != 1.5f) {
    			
    			planeX = (planeX + 0.75f);
    			planeY = (planeY + 0.075f);
    		}
    		
    		//move plane
    		planeLR = (planeLR + 0.3f);
    	}
    	
    	if (turnU) {
    			
			//up
    		if (planeUD >= 1) {
    			
    			turnU = !turnU;
    		}
    		
    		//rotate plane
    		if (elevatorR != 20 || controlFB != -15) {
    			
    			elevatorR = (elevatorR + 1.0f);
    			controlFB = (controlFB - 0.75f) % 360;
    			
    			//robot
    			leftArmX = (leftArmX - 0.15f) % 360;
    			rightArmX = (rightArmX - 0.15f) % 360;
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
    		if (elevatorR != -20 || controlFB != 15) {
    			
    			elevatorR = (elevatorR - 1.0f);
    			controlFB = (controlFB + 0.75f) % 360;
    			
    			//robot
    			leftArmX = (leftArmX + 0.15f) % 360;
    			rightArmX = (rightArmX + 0.15f) % 360;
    		}
    		
    		if (planeZ != 2.0f) {
    			
    			planeZ = (planeZ + 0.01f);
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
    	
    	turnSky = (turnSky - 0.1f) % 360;
    	propellerR = (propellerR - 50) % 360;
    	
    	if (turnR) {
    		
    		//right
    		if (planeLR <= -6) {
    			
    			turnR = !turnR;
    		}
    		
    		//rotate plane
    		if (aileronRL != -25 || aileronRR != 25 || rudderR != 15 || controlLR != -15) {
    			
    			aileronRL = (aileronRL - 1.25f) % 360;
    			aileronRR = (aileronRR + 1.25f) % 360;
    			rudderR = (rudderR + 0.75f) % 360;
    			controlLR = (controlLR - 0.75f) % 360;
    			
    			//robot
    			leftElbowZ = (leftElbowZ - 0.15f) % 360;
    			leftElbowY = (leftElbowY - 0.15f) % 360;
    			rightElbowZ = (rightElbowZ - 0.15f) % 360;
    			rightElbowY = (rightElbowY - 0.15f) % 360;
    		}
    	
	    	if (planeX != -15 || planeY != -1.5f) {
				
				planeX = (planeX - 0.75f);
				planeY = (planeY - 0.075f);
			}
    		
    		//move plane
    		planeLR = (planeLR - 0.3f);
    	} else {
    		
    		//left
    		
    		if (planeLR >= 6) {
    			
    			turnR = !turnR;
    		}
    		
    		//rotate plane
    		if (aileronRL != 25 || aileronRR != -25 || rudderR != -15 || controlLR != 15) {
    			
    			aileronRL = (aileronRL + 1.25f) % 360;
    			aileronRR = (aileronRR - 1.25f) % 360;
    			rudderR = (rudderR - 0.75f) % 360;
    			controlLR = (controlLR + 0.75f) % 360;
    			
    			//robot
    			leftElbowZ = (leftElbowZ + 0.15f) % 360;
    			leftElbowY = (leftElbowY + 0.15f) % 360;
    			rightElbowZ = (rightElbowZ + 0.15f) % 360;
    			rightElbowY = (rightElbowY + 0.15f) % 360;
    		}
	    	
	    	if (planeX != 15 || planeY != 1.5f) {
    			
    			planeX = (planeX + 0.75f);
    			planeY = (planeY + 0.075f);
    		}
    		
    		//move plane
    		planeLR = (planeLR + 0.3f);
    	}
    	
    	if (turnU) {
    			
			//up
    		if (planeUD >= 1) {
    			
    			turnU = !turnU;
    		}
    		
    		//rotate plane
    		if (elevatorR != 20 || controlFB != -15) {
    			
    			elevatorR = (elevatorR + 1.0f);
    			controlFB = (controlFB - 0.75f) % 360;
    			
    			//robot
    			leftArmX = (leftArmX - 0.15f) % 360;
    			rightArmX = (rightArmX - 0.15f) % 360;
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
    		if (elevatorR != -20 || controlFB != 15) {
    			
    			elevatorR = (elevatorR - 1.0f);
    			controlFB = (controlFB + 0.75f) % 360;
    			
    			//robot
    			leftArmX = (leftArmX + 0.15f) % 360;
    			rightArmX = (rightArmX + 0.15f) % 360;
    		}
    		
    		if (planeZ != 2.0f) {
    			
    			planeZ = (planeZ + 0.01f);
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
    
    public void display() {
    	
	myGL.glClear (GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	
	myGL.glMatrixMode (GL.GL_MODELVIEW);
	myGL.glLoadIdentity ();
	
	myGLU.gluLookAt(1.0 * cameraX, 1.0 * cameraY, 1.0 * cameraZ, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	
	//fake zoom
	myGL.glTranslatef(0.0f, 0.0f, zoomZ);
	
	//measure
	drawMeasure();
	
	//skybox
	drawSkyBox();
	
	//lighting
	litLight();
    
	//biplane
	drawBiplane();
		
	//Robot
	drawRobot();
		
	myGL.glPopMatrix ();	

	myGL.glFlush ();
	
		//Animation
	    if (animating) {
	        // Cause display to be called again after milliseconds_per_frame.
	    	myUT.glutTimerFunc(milliseconds_per_frame,"timer",0);  
		}
    }
    
    private void drawMeasure() {
    	
    	if (measure) {
    		
    		myGL.glPushMatrix ();
			myGL.glPointSize (5.0f);
			myGL.glDisable(GL.GL_TEXTURE_2D);
			myGL.glColor3f (1.0f, 1.0f, 0.0f);
			myGL.glBegin (GL.GL_POINTS);
			
			for (int i = 0; i < 15; i++)
				myGL.glVertex3fv (measurePoints[i]);
			myGL.glEnd ();
			
		myGL.glPopMatrix();
    	}
    }
    
    private void drawSkyBox() {
    	
    	//skybox
    	myGL.glDisable(GL.GL_LIGHT0); //tat den
    	myGL.glDisable(GL.GL_LIGHTING);
    	myGL.glEnable (GL.GL_TEXTURE_2D);
    	
    	myGL.glPushMatrix();
    	myGL.glEnable(GL.GL_CULL_FACE);
    	myGL.glFrontFace (GL.GL_CW);
    	myGL.glRotatef(turnSky, 0.0f, 1.0f, 0.0f);
    	
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
    }
    
    private void litLight() {
    	
    	myGL.glPushMatrix ();
    	myGL.glTranslatef(-5.0f, 15.0f, 0.0f);
    		//rotate
    		myGL.glTranslatef(-5.0f, -15.0f, 0.0f);
    		myGL.glRotatef(-60, 1.0f, 0.0f, 0.0f);
    		myGL.glRotatef(25, 0.0f, 0.0f, 1.0f);
    		myGL.glRotatef(lightR, 1.0f, 0.0f, 0.0f);
    		myGL.glTranslatef(5.0f, 15.0f, 0.0f);
    		
    		//cube
    		if (lightCube) {
    			
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
    		}
    		
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
    	
    	myGL.glColor3f (1.0f, 1.0f, 1.0f);
    }
    
    private void drawRobot() {
    	
    	myGL.glPushMatrix();
		myGL.glScalef(0.35f, 0.35f, 0.35f);
		myGL.glTranslatef(-5.25f, 4, 0);
		myGL.glRotatef(-90, 0, 1, 0);
		
		myGL.glEnable(GL.GL_CULL_FACE);
			//body
			
			//chest
			myGL.glTranslatef (0.0f, 0.75f, 0.25f);
			myGL.glPushMatrix ();
			myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [1]);
			
				//rotate chest
				myGL.glRotatef (chestR, 0.0f, 1.0f, 0.0f);
			
				myGL.glPushMatrix ();
				myGL.glScalef (3.0f, 1.5f, 1.5f);
				myUT.glutSolidCube (1.0);
				myGL.glPopMatrix ();
				
				//neck
				myGL.glPushMatrix ();
				myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
				
				myGL.glTranslatef (0.0f, 0.75f, 1.0f);
					myGL.glTranslatef(0.0f, 1.0f/2f, 0.0f);
					myGL.glRotatef(120, 1, 0, 0);
				    myGLU.gluCylinder (rail, 0.2, 0.2, 1.0, 10, 5);
			    myGL.glPopMatrix ();
				
				//head
				myGL.glTranslatef (0.0f, 1.75f, 1.25f);
				
				myGL.glPushMatrix ();
				myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [1]);
				
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
					myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
					
						myGL.glTranslatef (-0.39f, 0.25f, 0.75f);
						myUT.glutSolidTorus(0.1f, 0.25f, 8, 8);
					myGL.glPopMatrix();
					
					myGL.glPushMatrix ();
					myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [0]);
					
						myGL.glTranslatef (-0.39f, 0.25f, 0.75f);
						myUT.glutSolidSphere (0.25, 8, 8);
					myGL.glPopMatrix();
					
						//right
					myGL.glPushMatrix();
					myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
					
						myGL.glTranslatef (0.39f, 0.25f, 0.75f);
						myUT.glutSolidTorus(0.1f, 0.25f, 8, 8);
					myGL.glPopMatrix();
					
					myGL.glPushMatrix ();
					myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [0]);
					
						myGL.glTranslatef (0.39f, 0.25f, 0.75f);
						myUT.glutSolidSphere (0.25, 8, 8);
					myGL.glPopMatrix ();
					
					//mouth
					myGL.glPushMatrix ();
					myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
						myGL.glTranslatef (0.0f, -0.6f, 0.45f);
						myGL.glScalef (1.8f, 0.5f, 1.2f);
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
					myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [1]);
						myGL.glScalef (1.0f, 1.0f, 1.0f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
						myGL.glTranslatef (0.0f, -1.0f, 0.0f);
						myGL.glScalef (1.25f, 1.0f, 1.25f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
					myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
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
				myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
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
					myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [1]);
						myGL.glScalef (1.0f, 1.0f, 1.0f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
						myGL.glTranslatef (0.0f, -1.0f, 0.0f);
						myGL.glScalef (1.25f, 1.0f, 1.25f);
						myUT.glutSolidCube (1.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
					myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
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
			myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [1]);
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
					myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
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
						myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [1]);
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
						myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
							myGL.glTranslatef (0.0f, -2.3f, 0.35f);
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
					myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
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
						myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [1]);
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
						myGL.glBindTexture (GL.GL_TEXTURE_2D, robotTex [2]);
							myGL.glTranslatef (0.0f, -2.3f, 0.35f);
							myGL.glScalef (1.2f, 0.5f, 1.6f);
							myUT.glutSolidCube (1.0);
						myGL.glPopMatrix ();
						myGL.glTranslatef (-2.125f, 1.2f, 0.0f);
					myGL.glPopMatrix ();
				
				myGL.glPopMatrix();
				
			myGL.glPopMatrix ();
			myGL.glTranslatef (0.0f, 1.625f, 0.0f);
		
		myGL.glDisable(GL.GL_CULL_FACE);
			
		myGL.glPopMatrix();	
    }
    
    private void drawBiplane() {
    	
		//fill biplane
		myGL.glPushMatrix ();
		
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
					myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
					myGL.glTranslatef(-6.0f, 0.0f, 0.0f);
					myGL.glRotatef(90, 0.0f, 1.0f, 0.0f);
					myGL.glScalef(1.0f, 1.0f, 3.0f);
					myUT.glutSolidTorus(0.25, 1.35, 20, 20);
				myGL.glPopMatrix();
				
				//propeller
				myGL.glPushMatrix();
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
				myGL.glRotatef(propellerR, 1.0f, 0.0f, 0.0f);
					
					//ball?
					myGL.glPushMatrix();
						myGL.glRotatef(90, 0.0f, 1.0f, 0.0f);
						myGL.glScalef(1.0f, 1.0f, 1.5f);
						myUT.glutSolidSphere(0.5, 20, 20);
					myGL.glPopMatrix();
					
					//propeller
					myGL.glPushMatrix();
					myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [8]);
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
			myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [1]);
				myGL.glTranslatef(-2.5f, 1.25f, 0.0f);
				myGL.glRotatef(90, 1.0f, 0.0f, 0.0f);
				myGL.glRotatef(-5, 0.0f, 1.0f, 0.0f);
				myGL.glScalef(1.0f, 1.0f, 2.0f);
				myUT.glutSolidTorus(0.15, 1.1, 20, 20);
			myGL.glPopMatrix();
			
			//upperbody
			myGL.glFrontFace (GL.GL_CW);
			myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [3]);
			myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 5,
				      0.0f, 1.0f, 12, 5, upperBody);
			myGL.glEnable (GL.GL_MAP2_VERTEX_3);
			myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
			
			//lowerbody
			myGL.glFrontFace (GL.GL_CCW);
			myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [4]);
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
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				    myGL.glTranslatef(-4.1f, -3.0f, -2.5f);
				    myGLU.gluCylinder (rail, 0.1, 0.1, 5.0, 15, 5);
			    myGL.glPopMatrix ();
			    
			    //back rail
				myGL.glPushMatrix ();
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				    myGL.glTranslatef(7.0f, -1.25f, -0.25f);
				    myGLU.gluCylinder (rail, 0.05, 0.05, 0.5, 15, 5);
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
				    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				    	myGL.glTranslatef(-4.1f, -3.0f, 2.5f);
				    	myGL.glRotatef(90, 1, 0, 0);
						myUT.glutSolidSphere(0.8, 2, 6);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
					myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				    	myGL.glTranslatef(-4.1f, -3.0f, 2.5f);
						myUT.glutSolidSphere(0.1, 6, 6);
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
				    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				    	myGL.glTranslatef(-4.1f, -3.0f, -2.5f);
				    	myGL.glRotatef(90, 1, 0, 0);
						myUT.glutSolidSphere(0.8, 2, 6);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
					myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				    	myGL.glTranslatef(-4.1f, -3.0f, -2.5f);
						myUT.glutSolidSphere(0.1, 6, 6);
					myGL.glPopMatrix ();
			    myGL.glPopMatrix ();
			    
			    //back wheel
			    myGL.glPushMatrix ();
			    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [2]);
		    		
			    	//left
				    myGL.glPushMatrix ();
				    	myGL.glTranslatef(7.0f, -1.25f, -0.25f);
						myUT.glutSolidTorus(0.15, 0.25, 10, 10);
						myGL.glTranslatef(4.1f, 3.0f, 2.5f);
				    myGL.glPopMatrix ();
				    
				    myGL.glPushMatrix ();
				    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				    	myGL.glTranslatef(7.0f, -1.25f, -0.25f);
				    	myGL.glRotatef(90, 1, 0, 0);
						myUT.glutSolidSphere(0.2, 2, 6);
						myGL.glTranslatef(4.1f, 3.0f, 2.5f);	
					myGL.glPopMatrix ();
					
					//right
				    myGL.glPushMatrix ();
				    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [2]);
				    	myGL.glTranslatef(7.0f, -1.25f, 0.25f);
						myUT.glutSolidTorus(0.15, 0.25, 10, 10);
						myGL.glTranslatef(4.1f, 3.0f, 2.5f);
				    myGL.glPopMatrix ();
				    
				    myGL.glPushMatrix ();
				    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				    	myGL.glTranslatef(7.0f, -1.25f, 0.25f);
				    	myGL.glRotatef(90, 1, 0, 0);
						myUT.glutSolidSphere(0.2, 2, 6);
						myGL.glTranslatef(4.1f, 3.0f, 2.5f);	
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
			myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [0]);
			    myGL.glTranslatef(7.0f, 0.0f, 0.0f);
			    myGL.glTranslatef(0.0f, 2.5f, 0.0f);
			    myGL.glRotatef(90, 1.0f, 0.0f, 0.0f);
			    myGLU.gluCylinder (rail, 0.075, 0.075, 3.0, 15, 5);
			    myGL.glTranslatef(0.0f, -2.5f, 0.0f);
			    myGL.glTranslatef(-7.0f, 0.0f, 0.0f);
			myGL.glPopMatrix ();
		    
			//rudder
			myGL.glPushMatrix ();
			myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [5]);
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
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [0]);
				myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 3,
					      0.0f, 1.0f, 12, 3, botRudder);
				myGL.glEnable (GL.GL_MAP2_VERTEX_3);
				myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
				
				myGL.glTranslatef(-0.1f, 0.0f, 0.0f);
			myGL.glPopMatrix ();
			
			//front fin
			myGL.glPushMatrix ();
			myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [0]);
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
			    myGLU.gluCylinder (rail, 0.075, 0.075, 7.0, 15, 5);
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
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [7]);
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
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [1]);
					//left
				    myGL.glTranslatef(1.1f, 0.05f, 6.25f);
				    myGL.glScalef(1.0f, 1.75f, 1.0f);
				    myGLU.gluCylinder (rail, 0.085, 0.085, 2.85, 15, 5);
				    myGL.glTranslatef(-1.1f, -0.05f, -6.25f);
				    
				    //right
				    myGL.glTranslatef(1.1f, 0.05f, -9.1f);
				    myGL.glScalef(1.0f, 1.75f, 1.0f);
				    myGLU.gluCylinder (rail, 0.085, 0.085, 2.85, 15, 5);
				    myGL.glTranslatef(-1.1f, -0.05f, 9.1f);
				myGL.glPopMatrix ();
				
				//aileron
				myGL.glPushMatrix ();
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [7]);
					
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
						myGL.glTranslatef(-3.75f, 1.95f, 1.5f);
						myGL.glRotatef(25, 1.0f, 0.0f, 0.0f);
						myGL.glScalef((0.2f/2.0f), 1.0f, (0.1f/2.0f));
						myUT.glutSolidCube(2.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
						myGL.glTranslatef(-1.75f, 1.8f, 1.5f);
						myGL.glRotatef(25, 1.0f, 0.0f, 0.0f);
						myGL.glScalef((0.2f/2.0f), 1.0f, (0.1f/2.0f));
						myUT.glutSolidCube(2.0);
					myGL.glPopMatrix ();
				myGL.glPopMatrix ();
				
				//seat right
				myGL.glPushMatrix ();
					myGL.glPushMatrix ();
						myGL.glTranslatef(-3.75f, 1.95f, -1.5f);
						myGL.glRotatef(-25, 1.0f, 0.0f, 0.0f);
						myGL.glScalef((0.2f/2.0f), 1.0f, (0.1f/2.0f));
						myUT.glutSolidCube(2.0);
					myGL.glPopMatrix ();
					
					myGL.glPushMatrix ();
						myGL.glTranslatef(-1.75f, 1.8f, -1.5f);
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
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [7]);
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
				
				//back
				myGL.glPushMatrix ();
					myGL.glMap2f (GL.GL_MAP2_VERTEX_3, 0.0f, 1.0f, 12, 4,
						      0.0f, 1.0f, 12, 4, backWing2);
					myGL.glEnable (GL.GL_MAP2_VERTEX_3);
					myGL.glEvalMesh2 (GL.GL_FILL, 0, 20, 0, 20);
				myGL.glPopMatrix ();
				
				//connector
				myGL.glPushMatrix ();
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [1]);
					//left
				    myGL.glTranslatef(1.1f, 0.05f, 6.25f);
				    myGL.glScalef(1.0f, 1.75f, 1.0f);
				    myGLU.gluCylinder (rail, 0.085, 0.085, 2.85, 15, 5);
				    myGL.glTranslatef(-1.1f, -0.05f, -6.25f);
				    
				    //right
				    myGL.glTranslatef(1.1f, 0.05f, -9.1f);
				    myGL.glScalef(1.0f, 1.75f, 1.0f);
				    myGLU.gluCylinder (rail, 0.085, 0.085, 2.85, 15, 5);
				    myGL.glTranslatef(-1.1f, -0.05f, 9.1f);
				myGL.glPopMatrix ();
				
				//aileron
				myGL.glPushMatrix ();
				myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [7]);
					
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
			
			//controller
			myGL.glPushMatrix();
			myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [6]);
				
				myGL.glTranslatef(-3.0f, 1.75f, 0);
				
				myGL.glTranslatef(0, -0.45f, 0);
				myGL.glRotatef(controlFB, 0, 0, 1);
				myGL.glRotatef(controlLR, 1, 0, 0);
				myGL.glTranslatef(0, 0.45f, 0);
				
				myGL.glRotatef(90, 1, 0, 0);
				
			    myGLU.gluCylinder (rail, 0.035, 0.035, 0.45, 15, 5);
			    myGL.glBindTexture (GL.GL_TEXTURE_2D, planeTex [0]);
			    myUT.glutSolidSphere(0.1, 4, 4);
			myGL.glPopMatrix();
    }
    
    private void makeCheckImage () {
    	
    	int i, j;
    	
    	String str = "src/texture/";
    	
    	File bmpTex1 = new File(str + "tex_1.bmp");
    	File bmpTex2 = new File(str + "tex_2.bmp");
    	File bmpTex3 = new File(str + "tex_3.bmp");
    	File bmpTex4 = new File(str + "tex_4.bmp");
    	File bmpBodyTop = new File(str + "body_top.bmp");
    	File bmpBodyBot = new File(str + "body_bot.bmp");
    	File bmpTex5 = new File(str + "tex_5.bmp");
    	File bmpWing = new File(str + "wing.bmp");
    	File bmpPropeller = new File(str + "propeller.bmp");
    	
    	File bmpSkyLeft = new File(str + "left.bmp");
    	File bmpSkyRight = new File(str + "right.bmp");
    	File bmpSkyCenter = new File(str + "center.bmp");
    	File bmpSkyBack = new File(str + "back.bmp");
    	File bmpSkyTop = new File(str + "top.bmp");
    	File bmpSkyBot = new File(str + "bot.bmp");
    	File bmpCloud = new File(str + "cloud.bmp");
    	
    	File bmpRobot1 = new File(str + "robot1.bmp");
    	File bmpRobot2 = new File(str + "robot2.bmp");
    	File bmpRobot3 = new File(str + "robot3.bmp");
    	
    	try {
    		
    		BufferedImage iTex1 = ImageIO.read(bmpTex1);
    		BufferedImage iTex2 = ImageIO.read(bmpTex2);
    		BufferedImage iTex3 = ImageIO.read(bmpTex3);
    		BufferedImage iTex4 = ImageIO.read(bmpTex4);
    		BufferedImage iBodyTop = ImageIO.read(bmpBodyTop);
    		BufferedImage iBodyBot = ImageIO.read(bmpBodyBot);
    		BufferedImage iTex5 = ImageIO.read(bmpTex5);
    		BufferedImage iWing = ImageIO.read(bmpWing);
    		BufferedImage iPropeller = ImageIO.read(bmpPropeller);
    		
    		BufferedImage iSkyLeft = ImageIO.read(bmpSkyLeft);
    		BufferedImage iSkyRight = ImageIO.read(bmpSkyRight);
    		BufferedImage iSkyCenter = ImageIO.read(bmpSkyCenter);
    		BufferedImage iSkyBack = ImageIO.read(bmpSkyBack);
    		BufferedImage iSkyTop = ImageIO.read(bmpSkyTop);
    		BufferedImage iSkyBot = ImageIO.read(bmpSkyBot);
    		BufferedImage iCloud = ImageIO.read(bmpCloud);
    		
    		BufferedImage iRobot1 = ImageIO.read(bmpRobot1);
    		BufferedImage iRobot2 = ImageIO.read(bmpRobot2);
    		BufferedImage iRobot3 = ImageIO.read(bmpRobot3);
    		
    		for (i = 0; i < planeImageWidth; i++) {
    			
    			for (j = 0; j < planeImageHeight; j++) {
    				
    				Color cTex1 = new Color(iTex1.getRGB(i, j));
    				Color cTex2 = new Color(iTex2.getRGB(i, j));
    				Color cTex3 = new Color(iTex3.getRGB(i, j));
    				Color cTex4 = new Color(iTex4.getRGB(i, j));
    				Color cBodyTop = new Color(iBodyTop.getRGB(i, j));
    				Color cBodyBot = new Color(iBodyBot.getRGB(i, j));
    				Color cTex5 = new Color(iTex5.getRGB(i, j));
    				Color cWing = new Color(iWing.getRGB(i, j));
    				Color cPropeller = new Color(iPropeller.getRGB(i, j));
    				
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
    				
    				planeColor4[j][i][0] = (byte)(cTex4.getRed());
    				planeColor4[j][i][1] = (byte)(cTex4.getGreen());
    				planeColor4[j][i][2] = (byte)(cTex4.getBlue());
    				planeColor4[i][j][3] = (byte) 255;
    				
    				planeBodyTop[j][i][0] = (byte)(cBodyTop.getRed());
    				planeBodyTop[j][i][1] = (byte)(cBodyTop.getGreen());
    				planeBodyTop[j][i][2] = (byte)(cBodyTop.getBlue());
    				planeBodyTop[i][j][3] = (byte) 255;
    				
    				planeBodyBot[j][i][0] = (byte)(cBodyBot.getRed());
    				planeBodyBot[j][i][1] = (byte)(cBodyBot.getGreen());
    				planeBodyBot[j][i][2] = (byte)(cBodyBot.getBlue());
    				planeBodyBot[i][j][3] = (byte) 255;
    				
    				planeColor5[j][i][0] = (byte)(cTex5.getRed());
    				planeColor5[j][i][1] = (byte)(cTex5.getGreen());
    				planeColor5[j][i][2] = (byte)(cTex5.getBlue());
    				planeColor5[i][j][3] = (byte) 255;
    				
    				planeWing[j][i][0] = (byte)(cWing.getRed());
    				planeWing[j][i][1] = (byte)(cWing.getGreen());
    				planeWing[j][i][2] = (byte)(cWing.getBlue());
    				planeWing[i][j][3] = (byte) 255;
    				
    				planePropeller[j][i][0] = (byte)(cPropeller.getRed());
    				planePropeller[j][i][1] = (byte)(cPropeller.getGreen());
    				planePropeller[j][i][2] = (byte)(cPropeller.getBlue());
    				planePropeller[i][j][3] = (byte) 255;
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
    				Color cCloud = new Color(iCloud.getRGB(i, j));
    				
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
    				
    				cloudColor[j][i][0] = (byte)(cCloud.getRed());
    				cloudColor[j][i][1] = (byte)(cCloud.getGreen());
    				cloudColor[j][i][2] = (byte)(cCloud.getBlue());
    				cloudColor[i][j][3] = (byte) 255;
    			}
    		}
    		
    		for (i = 0; i < robotImageWidth; i++) {
    			
    			for (j = 0; j < robotImageHeight; j++) {
    				
    				Color cRobot1 = new Color(iRobot1.getRGB(i, j));
    				Color cRobot2 = new Color(iRobot2.getRGB(i, j));
    				Color cRobot3 = new Color(iRobot3.getRGB(i, j));
    				
    				robotColor1[j][i][0] = (byte)(cRobot1.getRed());
    				robotColor1[j][i][1] = (byte)(cRobot1.getGreen());
    				robotColor1[j][i][2] = (byte)(cRobot1.getBlue());
    				robotColor1[i][j][3] = (byte) 255;
    				
    				robotColor2[j][i][0] = (byte)(cRobot2.getRed());
    				robotColor2[j][i][1] = (byte)(cRobot2.getGreen());
    				robotColor2[j][i][2] = (byte)(cRobot2.getBlue());
    				robotColor2[i][j][3] = (byte) 255;
    				
    				robotColor3[j][i][0] = (byte)(cRobot3.getRed());
    				robotColor3[j][i][1] = (byte)(cRobot3.getGreen());
    				robotColor3[j][i][2] = (byte)(cRobot3.getBlue());
    				robotColor3[i][j][3] = (byte) 255;
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
		
		//control
		case 'a':
		if (aileronRL <= 25 || aileronRR >= -25 || rudderR >= -15 || controlLR <= 15) {
			
			aileronRL = (aileronRL + 6.25f) % 360;
			aileronRR = (aileronRR - 6.25f) % 360;
			rudderR = (rudderR - 3.75f) % 360;
			controlLR = (controlLR + 3.75f) % 360;
			
			//robot
			leftElbowZ = (leftElbowZ + 0.75f) % 360;
			leftElbowY = (leftElbowY + 0.75f) % 360;
			rightElbowZ = (rightElbowZ + 0.75f) % 360;
			rightElbowY = (rightElbowY + 0.75f) % 360;
		}
		
		if (planeX <= 15 || planeY <= 1.5f) {
			
			planeX = (planeX + 5) % 360;
			planeY = (planeY + 0.5f) % 360;
		}
		
		if (planeLR <= 12) {
			
			planeLR = (planeLR + 0.25f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		case 'd':
		if (aileronRL >= -25 || aileronRR <= 25 || rudderR <= 15 || controlLR >= -15) {
			
			aileronRL = (aileronRL - 6.25f) % 360;
			aileronRR = (aileronRR + 6.25f) % 360;
			rudderR = (rudderR + 3.75f) % 360;
			controlLR = (controlLR - 3.75f) % 360;
			
			//robot
			leftElbowZ = (leftElbowZ - 0.75f) % 360;
			leftElbowY = (leftElbowY - 0.75f) % 360;
			rightElbowZ = (rightElbowZ - 0.75f) % 360;
			rightElbowY = (rightElbowY - 0.75f) % 360;
		}
		
		if (planeX >= -15 || planeY >= -1.5f) {
			
			planeX = (planeX - 5) % 360;
			planeY = (planeY - 0.5f) % 360;
		}
		
		if (planeLR >= -12) {
			
			planeLR = (planeLR - 0.25f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		case 'w':
		if (elevatorR <= 20 || controlFB >= -15) {
			
			elevatorR = (elevatorR + 5) % 360;
			controlFB = (controlFB - 3.75f) % 360;
			
			//robot
			leftArmX = (leftArmX - 0.75f) % 360;
			rightArmX = (rightArmX - 0.75f) % 360;
		}
		
		if (planeZ >= -1.5f) {
			
			planeZ = (planeZ - 0.5f) % 360;
		}
		
		if (planeUD <= 8) {
			
			planeUD = (planeUD + 0.05f) % 360;
		}
		
		myUT.glutPostRedisplay ();
		break;
		case 's':
		if (elevatorR >= -20 || controlFB <= 15) {
			
			elevatorR = (elevatorR - 5) % 360;
			controlFB = (controlFB + 3.75f) % 360;
			
			//robot
			leftArmX = (leftArmX + 0.75f) % 360;
			rightArmX = (rightArmX + 0.75f) % 360;
		}
		
		if (planeZ <= 2.0f) {
			
			planeZ = (planeZ + 0.5f) % 360;
		}
		
		if (planeUD >= -8) {
			
			planeUD = (planeUD - 0.05f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		
		case 'q':
		if (planeFB >= -8) {
			
			planeFB = (planeFB - 0.25f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		
		case 'e':
		if (planeFB <= 8) {
			
			planeFB = (planeFB + 0.25f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		
		//test turn
		case 'A':
		if (aileronRL <= 25 || aileronRR >= -25 || rudderR >= -15 || controlLR <= 15) {
			
			aileronRL = (aileronRL + 6.25f) % 360;
			aileronRR = (aileronRR - 6.25f) % 360;
			rudderR = (rudderR - 3.75f) % 360;
			controlLR = (controlLR + 3.75f) % 360;
			
			//robot
			leftElbowZ = (leftElbowZ + 0.75f) % 360;
			leftElbowY = (leftElbowY + 0.75f) % 360;
			rightElbowZ = (rightElbowZ + 0.75f) % 360;
			rightElbowY = (rightElbowY + 0.75f) % 360;
			
		}
		myUT.glutPostRedisplay ();
		break;
		case 'D':
		if (aileronRL >= -25 || aileronRR <= 25 || rudderR <= 15 || controlLR >= -15) {
			
			aileronRL = (aileronRL - 6.25f) % 360;
			aileronRR = (aileronRR + 6.25f) % 360;
			rudderR = (rudderR + 3.75f) % 360;
			controlLR = (controlLR - 3.75f) % 360;
			
			//robot
			leftElbowZ = (leftElbowZ - 0.75f) % 360;
			leftElbowY = (leftElbowY - 0.75f) % 360;
			rightElbowZ = (rightElbowZ - 0.75f) % 360;
			rightElbowY = (rightElbowY - 0.75f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		case 'W':
		if (elevatorR <= 20 || controlFB >= -15) {
			
			elevatorR = (elevatorR + 5) % 360;
			controlFB = (controlFB - 3.75f) % 360;
			
			//robot
			leftArmX = (leftArmX - 0.75f) % 360;
			rightArmX = (rightArmX - 0.75f) % 360;
		}
		myUT.glutPostRedisplay ();
		break;
		case 'S':
		if (elevatorR >= -20 || controlFB <= 15) {
			
			elevatorR = (elevatorR - 5) % 360;
			controlFB = (controlFB + 3.75f) % 360;
			
			//robot
			leftArmX = (leftArmX + 0.75f) % 360;
			rightArmX = (rightArmX + 0.75f) % 360;
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
		cameraX = (cameraX + 0.05f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'Z':
		cameraX = (cameraX - 0.05f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'x':
		cameraY = (cameraY + 0.05f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'X':
		cameraY = (cameraY - 0.05f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'c':
		cameraZ = (cameraZ + 0.05f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'C':
		cameraZ = (cameraZ - 0.05f) % 360;
		myUT.glutPostRedisplay ();
		break;
		
		//measure
		case 'm':
		measure = !measure;
		myUT.glutPostRedisplay ();
		break;
		
		//sky
		case 'n':
		turnSky = (turnSky - 0.1f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'N':
		turnSky = (turnSky + 0.1f) % 360;
		myUT.glutPostRedisplay ();
		break;
		
		//lighting
		case 'l': 
		lighting = !lighting; //toggle
		myUT.glutPostRedisplay ();
		break;
		case 'L':
		lightCube = !lightCube; //light location
		System.out.println(lightR);
		myUT.glutPostRedisplay ();
		break;
		
		//rotate light
		case 't':
		lightR = (lightR + 5.0f) % 360;
		myUT.glutPostRedisplay ();
		break;
		case 'T':
		lightR = (lightR - 5.0f) % 360;
		myUT.glutPostRedisplay ();
		break;
		
		//debug
		case 'p':
		System.out.println(cameraX + " " + cameraY + " "  + cameraZ);
		break;
		
		//camera angle
		case 'v':
		if (cameraAngle == 0) {
			
			//default
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
			cameraY = 0.1f;
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
	myUT.glutInitWindowSize (640, 360);
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
	mainFrame.setSize (640, 360);
	biplane_backup_3 mainCanvas = new biplane_backup_3 ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
