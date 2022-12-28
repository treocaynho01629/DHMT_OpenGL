package Final;

/*
 *  bezcurve.java			
 *  This program uses evaluators to draw a Bezier curve.
 */

import java.awt.Frame;
import java.io.IOException;
import java.lang.String;
import java.lang.System;

import jgl.GL;
import jgl.GLCanvas;

public class bezcurve2 extends GLCanvas {

//    private static final float ctrlpoints [][] = {
//    			{-0.5f, 1.5f, 0.0f},
//    			{-1.5f, 0.5f, 0.0f},
//    			{-1.5f, -0.5f, 0.0f},
//    			{-0.5f, -1.5f, 0.0f},
//			{0.0f,  -1.5f, 0.0f},
//			{0.5f,  -1.5f, 0.0f},
//			{ 1.5f, -0.5f, 0.0f},
//			{ 1.5f,  0.5f, 0.0f},
//			{ 0.5f,  1.5f, 0.0f}};
    
	//than duoi
//    private static final float ctrlpoints [][] = {
//			{-7.0f, 0.0f, 0.0f},
//			{-7.0f, -1.25f, 0.0f},
//			{-7.0f, -1.5f, 0.0f},
//			{-6.75f, -1.5f, 0.0f},
//		{-3.5f,  -1.5f, 0.0f},
//		{0.0f,  -1.5f, 0.0f},
//		{ 3.5f, -0.5f, 0.0f},
//		{ 7.0f,  -1.0f, 0.0f},
//		{ 7.0f,  0.0f, 0.0f}};
    
//    //than ngang
//    private static final float ctrlpoints [][] = {
//			{-7.0f, 0.0f, 0.0f},
//			{-7.0f, -1.25f, 0.0f},
//			{-7.0f, -1.5f, 0.0f},
//			{-6.75f, -1.5f, 0.0f},
//		{-1.0f,  -1.5f, 0.0f},
//		{1.5f,  -1.0f, 0.0f},
//		{ 4.5f, -0.0f, 0.0f},
//		{ 7.0f,  -0.5f, 0.0f},
//		{ 7.0f,  0.0f, 0.0f}};
	
//	 //than duoi
//    private static final float ctrlpoints [][] = {
//			{-7.0f, 0.0f, 0.0f},
//			{-7.5f, -2.5f, 0.0f},
//			{0.0f, -1.5f, 0.0f},
//			{3.5f, -1.5f, 0.0f},
//		{ 7.0f,  -0.5f, 0.0f}};
    
//    //than ngang
//    private static final float ctrlpoints [][] = {
//			{-7.0f, 0.0f, 0.0f},
//			{-7.5f, -2.5f, 0.0f},
//			{0.0f, -1.5f, 0.0f},
//			{3.5f, -0.25f, 0.0f},
//		{ 7.0f,  -0.25f, 0.0f}};
	
//	 //than tren
//	private static final float ctrlpoints [][] = {
//			{-7.0f, 0.0f, 0.0f},
//			{-7.5f, 2.5f, 0.0f},
//			{0.0f, 1.5f, 0.0f},
//			{3.5f, 0.75f, 0.0f},
//		{ 7.0f,  0.5f, 0.0f}};
	
//		//tail 1
//	  private static final float ctrlpoints [][] = {
//				{4.0f, -0.5f, 0.0f},
//			{5.0f, 3.0f, 0.0f},
//			{ 7.0f,  3.0f, 0.0f}};
	  
	//test
	  private static final float ctrlpoints [][] = {
			  {-0.5f, 0.0f, 0.0f},
			  {0.5f, 0.5f, 0.0f},
				{0.75f, 0.0f, 0.0f}};
	  
	 private static float flagfl = 0.0f; 
	 private static float flagfl2 = 0.0f; 
	 private static boolean flagl = false; 
	  
    private void myinit () {
	myGL.glClearColor (0.0f, 0.0f, 0.0f, 1.0f);
	myGL.glShadeModel (GL.GL_FLAT);
//	myGL.glMap1f (GL.GL_MAP1_VERTEX_3, 0.0f, 1.0f, 3, 4, ctrlpoints);
//	myGL.glEnable (GL.GL_MAP1_VERTEX_3);
    }
    
    public void testDisplay() {
    	
    	if (!flagl) {
			
			if (flagfl >= 1.5f) {
				
				flagl = !flagl;
			} else {
				
				flagfl = (flagfl + 0.25f) % 360;
			}
		} else if (flagl){
			
			if (flagfl <= -1.5f) {
				
				flagl = !flagl;
			} else {
			
				flagfl = (flagfl - 0.25f) % 360;
			}
		}
    	
    	myUT.glutPostRedisplay ();
    }

    public void display () {
    	int i;
    	
	float ctrlpoints3 [][] = {
		  {-3.0f, 0.0f, 0.0f},
		  {-1.0f, flagfl * 1, 0.0f},
			{1.0f, -flagfl, 0.0f},
			{3.0f, flagfl, 0.0f}};	
	
	myGL.glMap1f (GL.GL_MAP1_VERTEX_3, 0.0f, 1.0f, 3, 3, ctrlpoints);
	myGL.glEnable (GL.GL_MAP1_VERTEX_3);

	myGL.glClear (GL.GL_COLOR_BUFFER_BIT);
	myGL.glColor3f (1.0f, 1.0f, 1.0f);
	myGL.glBegin (GL.GL_LINE_STRIP);
	    for (i = 0; i <= 30; i++)
	    	myGL.glEvalCoord1f ((float)i/30.0f);
	myGL.glEnd ();
	/* The following code displays the control points as dots. */
	myGL.glPointSize (5.0f);
	myGL.glColor3f (1.0f, 1.0f, 0.0f);
	myGL.glBegin (GL.GL_POINTS);
	    for (i = 0; i < 3; i++)
	    	myGL.glVertex3fv (ctrlpoints[i]);
	myGL.glEnd ();
	myGL.glFlush ();
    }

    public void myReshape (int w, int h) {
        myGL.glViewport (0, 0, w, h);
        myGL.glMatrixMode (GL.GL_PROJECTION);
        myGL.glLoadIdentity ();
	if (w <= h) {
	    myGL.glOrtho (-5.0f, 5.0f,
	    		  -5.0f *(float)h/(float)w,
			   5.0f *(float)h/(float)w,
			  -5.0f, 5.0f);
	} else {
	    myGL.glOrtho (-5.0f *(float)w/(float)h,
	    		   5.0f *(float)w/(float)h,
			  -5.0f, 5.0f,
			  -5.0f, 5.0f);
	}
        myGL.glMatrixMode (GL.GL_MODELVIEW);
        myGL.glLoadIdentity ();
    }

    /* ARGSUSED1 */
    public void keyboard (char key, int x, int y) {
	switch (key) {
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
	myUT.glutDisplayFunc ("display");
	myUT.glutReshapeFunc ("myReshape");
	myUT.glutKeyboardFunc ("keyboard");
//	myUT.glutIdleFunc("testDisplay");
	myUT.glutMainLoop ();
    }

    static public void main (String args[]) throws IOException {
	Frame mainFrame = new Frame ();
	mainFrame.setSize (960, 540);
	bezcurve2 mainCanvas = new bezcurve2 ();
	mainCanvas.init();
	mainFrame.add (mainCanvas);
	mainFrame.setVisible (true);
    }

}
