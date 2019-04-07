// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12
package graphics;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import input.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.fixedfunc.*;
import javax.swing.*;
import landscapegenerator.LoadFromFileLandscape;
import landscapegenerator.PerlinNoiseLandscapeGenerator;
import worlds.*;

/**
 * Self-contained code displaying a landscape generated with Perlin noise
 */
/*
         * benchmarking 
         *      Airbook (w/wo visible display) : 
         *          true:  frame per second  : frames per second  : 59 ; polygons per second: 966656 --- frames per second  : 82 ; polygons per second: 1343488
         *          false: frames per second  : 61 ; polygons per second: 999424 --- frames per second  : 254 ; polygons per second: 4161536 (!!!)
         * 
         * 
         * Bonnes pratiques:
         * - gl.Begin() ... gl.glEnd(); : faire un minimum d'appel, idealement un par it�ration. (gain de 50% a 100% ici)
         * - gl.glCullFace(GL.GL_FRONT); ... gl.glEnable(GL.GL_CULL_FACE); : si la scene le permet, reduit le nb de polyg a afficher.
         * - TRIANGLE SPLIT permet de r�duire le nombre d'appels a OpenGL (gl.begin et end)
         * - each call to gl.glColor3f costs a lot (speed is down by two if no call!) */

//  if you use a regular Animator object instead of the FPSAnimator, your program will render as fast as possible. 
//  You can, however, limit the framerate of a regular Animator 
//  by asking the graphics driver to synchronize with the refresh rate of the display (v-sync). 
//  Because the target framerate is often the same as the refresh rate, which is often 60-75 or so, 
//  this method is a great choice as it lets the driver do the work of limiting frame changes. 
//  However, some Intel GPUs may ignore this setting. In the init method, active v-sync as follows:
//  add : drawable.getGL().setSwapInterval(1); in the init method
//  then: in the main method, replace the FPSAnimator with a regular Animator.

public class Landscape implements GLEventListener{

	private World myWorld;
	
	private static GLCapabilities caps;  // GO FAST ???
	static Animator animator;

	static boolean MY_LIGHT_RENDERING = false; // true: nicer but slower
	final static boolean SMOOTH_AT_BORDER = true; // nicer (but wrong) rendering at border (smooth altitudes)
	//final static double landscapeAltitudeRatio = 0.6; // 0.5: half mountain, half water ; 0.3: fewer water
	static boolean VIEW_FROM_ABOVE = false; // also desactivate altitudes
	static boolean DISPLAY_OBJECTS = true; // useful to deactivate if view_from_above
	final static boolean DISPLAY_FPS = true; // on-screen display

	private float rotateX = 0.0f;
	private float rotationVelocity = 0f; // 0.2f
	
	int it = 0;
	int movingIt = 0;
	int dxView;
	int dyView;

	double[][] landscape;

	int lastFpsValue = 0;
	public static int lastItStamp = 0;
	public static long lastTimeStamp = 0;

	// visualization parameters
	float heightFactor; //64.0f; // was: 32.0f;
	double heightBooster; // applied to landscape values. increase heights.
	// -- NOTE that this could also be achieved using heighFactor but is decomposed to enable further pre-calc of height values
	// heightFactor deals with visualization
	// heigBooster will impact landscape array content 

	float offset;
	float stepX;
	float stepY;
	float lenX;
	float lenY;

	float smoothFactor[];
	int smoothingDistanceThreshold;

	int movingX = 0;
	int movingY = 0;
	
	float moduleAltitude;
	float moduleDepth;
	
		
	/**
	 * Initialise landscape à partir du bruit 
	 */
	public Landscape(World myWorld, int dx, int dy, double scaling, double altitudeRatio) {
		this.myWorld = myWorld;

		landscape = PerlinNoiseLandscapeGenerator.generatePerlinNoiseLandscape(dx, dy, scaling, altitudeRatio, 100); // 11
		//landscape = PerlinNoiseLandscapeGenerator.generatePNL(dx, dy, scaling, altitudeRatio);
		initLandscape();
	}

	public Landscape(World myWorld, String filename, double scaling, double landscapeAltitudeRatio) {
		this.myWorld = myWorld;

		landscape = LoadFromFileLandscape.load(filename, scaling, landscapeAltitudeRatio);

		initLandscape();
	}
	
	private void initLandscape() 
	{
		dxView = landscape.length;
		dyView = landscape[0].length;

		System.out.println("Landscape contains " + dxView * dyView + " tiles. (" + dxView + "x" + dyView + ")");

		myWorld.init(dxView - 1, dyView - 1, landscape);

		heightFactor = 32.0f; //64.0f; // was: 32.0f;
		heightBooster = 6.0; // default: 2.0 // 6.0 makes nice high mountains.

		offset = -260.0f; // was: -40.
		stepX = (-offset * 2.0f) / dxView;
		stepY = (-offset * 2.0f) / dxView;
		lenX = stepX / 2f;
		lenY = stepY / 2f;

		smoothFactor = new float[4];
		for (int i = 0; i < 4; i++) {
			smoothFactor[i] = 1.0f;
		}

		smoothingDistanceThreshold = 30; //30;
		moduleAltitude = -44f;
		moduleDepth = -130f;
		
	}

	/**
	 *Ajouter skybox, astres
	 */
	public static Landscape run(Landscape landscape) {
		
		PlayerInput play = new PlayerInput(landscape);

		caps = new GLCapabilities(null); //!n
		caps.setDoubleBuffered(true);  //!n

		final GLJPanel canvas = new GLJPanel(caps); // original

		final JFrame frame = new JFrame("World Of Cells");
		animator = new Animator(canvas);
		
		canvas.addGLEventListener(landscape);
		canvas.addMouseListener(play);// register mouse callback functions
		canvas.addKeyListener(play);// register keyboard callback functions
		canvas.addMouseWheelListener(play);
		frame.add(canvas);
		
		frame.setSize(1024, 768);
		frame.setResizable(false);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				animator.stop();
				frame.dispose();
				System.exit(0);
			}
		});
		frame.setVisible(true);
		animator.setRunAsFastAsPossible(true); // GO FAST!  --- DOES It WORK? 
		animator.start();
		//frame.setAlwaysOnTop(true);
		//frame.toFront();
		canvas.requestFocus();

		return landscape;
	}
	
	public static Landscape[] runAll(Landscape[] landscape) {
		
		/**********COMPOSANTS DE LA FENETRE*******************/
		JPanel mainPanel = new JPanel(new CardLayout());
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		
		final String wp1 = "WorldOfTrees";
		final String wp2 = "WorldOfSand";
		final String wp3 = "WorldOfSnow";
		String comboBoxItems[] = { wp1, wp2, wp3 };
		
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
		cb.setBackground(Color.black);
		cb.setForeground(Color.lightGray);
        cb.addItemListener((ItemEvent ie) -> {
			CardLayout cl = (CardLayout)(mainPanel.getLayout());
			cl.show(mainPanel, (String)ie.getItem());
		});
		comboBoxPane.setBackground(Color.black);
        comboBoxPane.add(cb);
		
	   	final JFrame frame = new JFrame("World Of Cells");
		
		/**********AJOUT CANVAS ET SES PROPRIETES A CHAQUE PANNEAU*******************/
		for(int i=0;i<landscape.length;i++)
		{
			PlayerInput play = new PlayerInput(landscape[i]);

			caps = new GLCapabilities(null); //!n
			caps.setDoubleBuffered(true);  //!n

			final GLJPanel canvas = new GLJPanel(caps); // original
			final JPanel worldPanel = new JPanel(new BorderLayout());
			
			animator = new Animator(canvas);

			canvas.addGLEventListener(landscape[i]);
			canvas.addMouseListener(play);// register mouse callback functions
			canvas.addKeyListener(play);// register keyboard callback functions
			canvas.addMouseWheelListener(play);
			
			//REACTIVE ININTOTIONNELEMENT LE PLAYERINPUT POUR LES TOUCHES, NE FONCTIONNE PAS UNE FOIS LE CHANGEMENT DE FENETRE
			canvas.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0),"Left"); 
			canvas.getActionMap().put("Left", new LeftAction(landscape[i]));
			
			canvas.requestFocus();
			canvas.setAnimator(animator);
			canvas.getAnimator().start();
			
			worldPanel.add(canvas);
			switch(i)
			{
				case 0: mainPanel.add("WorldOfTrees",worldPanel); break;
				case 1: mainPanel.add("WorldOfSand",worldPanel); break;
				case 2: mainPanel.add("WorldOfSnow",worldPanel); break;
			}
		}
		
		/**********DERNIERS AJOUTS FENETRE ET DEMARRAGE*******************/
		comboBoxPane.setFocusTraversalKeysEnabled(false);
		
		frame.getContentPane().add(mainPanel,BorderLayout.CENTER);
		frame.getContentPane().add(comboBoxPane, BorderLayout.SOUTH);
		
		frame.setSize(1024, 768);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);
		//animator.setRunAsFastAsPossible(true); // GO FAST!  --- DOES It WORK? 
		//animator.start();

		return landscape;
	}
	

	/**
	 * OpenGL Init method
	 */
	
	public static Landscape[] runAllTabbedPane(Landscape[] landscape) {
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel mainPanel = new JPanel(new BorderLayout());
	
		for(int i=0;i<landscape.length;i++)
		{
			PlayerInput play = new PlayerInput(landscape[i]);

			caps = new GLCapabilities(null); //!n
			caps.setDoubleBuffered(true);  //!n

			final GLJPanel canvas = new GLJPanel(caps); // original
			final JPanel worldPanel = new JPanel(new BorderLayout());
			
			animator = new Animator(canvas);

			canvas.addGLEventListener(landscape[i]);
			canvas.addMouseListener(play);// register mouse callback functions
			canvas.addKeyListener(play);// register keyboard callback functions
			canvas.addMouseWheelListener(play);
			
			canvas.setAnimator(animator);
			canvas.getAnimator().start();
			
			worldPanel.add(canvas);
			
			switch(i)
			{
				case 0: tabbedPane.add("WorldOfTrees",worldPanel); break;
				case 1: tabbedPane.add("WorldOfSand",worldPanel); break;
				case 2: tabbedPane.add("WorldOfSnow",worldPanel); break;
			}	
		}
		mainPanel.add(tabbedPane);
		
		final JFrame frame = new JFrame("World Of Cells");
		frame.getContentPane().add(mainPanel);
		
		frame.setSize(1024, 768);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		//animator.setRunAsFastAsPossible(true); // GO FAST!  --- DOES It WORK? 
		//animator.start();
		//frame.setAlwaysOnTop(true);
		//frame.toFront();

		return landscape;
	}
	
	@Override
	public void init(GLAutoDrawable glDrawable) 
	{
		GL2 gl = glDrawable.getGL().getGL2();

		gl.glEnable(GL2.GL_DOUBLEBUFFER);
		glDrawable.setAutoSwapBufferMode(true);
		
		//Depth
		gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

		// Culling - display only triangles facing the screen
		gl.glCullFace(GL.GL_FRONT);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glEnable(GL.GL_DITHER);
	}
	@Override
	public void display(GLAutoDrawable gLDrawable) {

		// ** compute FPS
		if (System.currentTimeMillis() - lastTimeStamp >= 1000) {
			int fps = (it - lastItStamp) / 1;

			if (Math.random() < 0.10) // display in console every ~10 updates
			{
				System.out.print("frames per second  : " + fps + " ; ");
				System.out.println();
			}
			lastItStamp = it;
			lastTimeStamp = System.currentTimeMillis();
			lastFpsValue = fps;
		}

		// ** clean screen
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		// ** display FPS on screen
		if (DISPLAY_FPS) {
			gl.glPushMatrix();
			gl.glColor3f((float) Math.random(), (float) Math.random(), (float) Math.random()); // do this before calling glWindowsPos2d
			gl.glWindowPos2d(0, 728);
			GLUT glut = new GLUT();
			gl.glTranslatef(0, 0, 0);
			glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "fps: " + lastFpsValue);
			gl.glPopMatrix();
		}

		// ** render all
		// *** ADD LIGHT
		if (MY_LIGHT_RENDERING) {
			// Prepare light parameters.
			float SHINE_ALL_DIRECTIONS = 1;
			//float[] lightPos = {120.f, 120.f, -200.f, SHINE_ALL_DIRECTIONS};
			//float[] lightPos = {40.f, 0.f, -300.f, SHINE_ALL_DIRECTIONS};
			float[] lightPos = {0.f, 40.f, -100.f, SHINE_ALL_DIRECTIONS};
			//float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
			float[] lightColorAmbient = {0.5f, 0.5f, 0.5f, 1f};
			float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

			// Set light parameters.
			gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
			gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
			gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);

			// Enable lighting in GL.
			gl.glEnable(GL2.GL_LIGHT1);
			gl.glEnable(GL2.GL_LIGHTING);
		}
		
		if (VIEW_FROM_ABOVE == true) {
			// as seen from above, no rotation (debug mode)
			gl.glTranslatef(0.0f, 0.0f, -500.0f); // 0,0,-5
		} else {
			// continuous rotation (default view) 
			gl.glTranslatef(0.0f, moduleAltitude, moduleDepth); // 0,0,-5
			gl.glRotatef(rotateX, 0.0f, 1.0f, 0.0f);
			gl.glRotatef(-90.f, 1.0f, 0.0f, 0.0f);
		}
		
		it++;
		// ** update Cellular Automata
		myWorld.step();
		// ** draw everything
		gl.glBegin(GL2.GL_QUADS);

		//movingX = movingIt;// it; // was: it
		//movingY = 0; // was: it
		for (int x = 0; x < dxView - 1; x++) {
			for (int y = 0; y < dyView - 1; y++) {

				double height = myWorld.getCellHeight(x + movingX, y + movingY);
				int cellState = myWorld.getCellValue(x + movingX, y + movingY);
				float[] color = myWorld.getCellColorValue(x + movingX, y + movingY);

				// compute CA-based coloring
				gl.glColor3f(color[0], color[1], color[2]);

				// * if light is on
				if (MY_LIGHT_RENDERING) {
					gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, color, 0);
					gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, color, 0);
					gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, color, 0);
					gl.glMateriali(GL.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 4);
					float[] colorBlack = {0.0f, 0.0f, 0.0f, 1.0f};
					gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_EMISSION, colorBlack, 0);
				}

				// Border visual smoothing : smooth altitudes near border (i.e. nicer rendering)
				if (SMOOTH_AT_BORDER == true && VIEW_FROM_ABOVE != true) {
					if (Math.min(Math.min(x, dxView - x - 1), Math.min(y, dyView - y - 1)) < smoothingDistanceThreshold) {

						for (int i = 0; i < 4; i++) {
							int xIt = i == 1 || i == 2 ? 1 : 0;
							int yIt = i == 0 || i == 1 ? 1 : 0;
							float xSign = i == 1 || i == 2 ? 1.f : -1.f;
							float ySign = i == 0 || i == 1 ? 1.f : -1.f;

							smoothFactor[i] = (float) Math.min(
									Math.min(1.0, (double) Math.min(x + xIt, dxView - x + xIt) / (double) smoothingDistanceThreshold), // check x-axis
									Math.min(1.0, (double) Math.min(y + yIt, dyView - y + yIt) / (double) smoothingDistanceThreshold) // check y-axis
							);
						}
					} else {
						for (int i = 0; i < 4; i++) {
							smoothFactor[i] = 1.0f;
						}
					}
				}

				// use dxCA instead of dxView to keep synchronization with CA states
				for (int i = 0; i < 4; i++) {
					int xIt = i == 1 || i == 2 ? 1 : 0;
					int yIt = i == 0 || i == 1 ? 1 : 0;
					float xSign = i == 1 || i == 2 ? 1.f : -1.f;
					float ySign = i == 0 || i == 1 ? 1.f : -1.f;

					float zValue = 0.f;

					if (VIEW_FROM_ABOVE == false) {
						//double altitude = landscape[(x+xIt+movingX)%(dxCA][(y+yIt+movingY)%dyCA] * heightBooster;
						double altitude = landscape[(x + xIt + movingX) % (dxView - 1)][(y + yIt + movingY) % (dyView - 1)] * heightBooster;
						if (altitude < 0) {
							zValue = 0;
						} else {
							zValue = heightFactor * (float) altitude * smoothFactor[i];
						}
					}

					gl.glVertex3f(offset + x * stepX + xSign * lenX, offset + y * stepY + ySign * lenY, zValue);
				}

				/**/
				// * display objects
				// TODO+: diplayObjectAt(x,y,...) ==> on y gagne quoi? les smoothFactors. C'est tout. Donc on externalise?
				if (DISPLAY_OBJECTS == true) // calls my world with the enough info to display anything at this location.
				{
					float normalizeHeight = (smoothFactor[0] + smoothFactor[1] + smoothFactor[2] + smoothFactor[3]) / 4.f * (float) heightBooster * heightFactor;
					myWorld.displayObjectAt(myWorld, gl, cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);
				}
			}
		}

		/**/
		// TODO+: displayObjects()
		if (DISPLAY_OBJECTS == true) // calls my world with enough info to display anything anywhere
		{
			float normalizeHeight = (float) heightBooster * heightFactor;
			myWorld.displayUniqueObjects(myWorld, gl, movingX, movingY, offset, stepX, stepY, lenX, lenY, normalizeHeight);
		}

		gl.glEnd();

		// increasing rotation for the next iteration                   
		rotateX += rotationVelocity;

		//gl.glFlush(); // GO FAST ???
		//gLDrawable.swapBuffers(); // GO FAST ???  // should be done at the end (http://stackoverflow.com/questions/1540928/jogl-double-buffering)
	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		if (this.it == 0) {
			System.out.println("W" + "o" + "r" + "l" + "d" + " " + "O"
					+ "f" + " " + "C" + "e" + "l" + "l" + "s" + " " + "-" + " " + "n" + "i"
					+ "c" + "o" + "l" + "a" + "s" + "." + "b" + "r" + "e" + "d" + "e" + "c"
					+ "h" + "e" + (char) (0x40) + "u" + "p" + "m" + "c" + "." + "f" + "r"
					+ "," + " " + "2" + "0" + "1" + "3" + "\n");
		}
		GL2 gl = gLDrawable.getGL().getGL2();
		final float aspect = (float) width / (float) height;
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		final float fh = 0.5f;
		final float fw = fh * aspect;
		gl.glFrustumf(-fw, fw, -fh, fh, 1.0f, 1000.0f);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
	}

	public static Animator getAnimator() {
		return animator;
	}

	public World getMyWorld() {
		return myWorld;
	}

	public void setMyWorld(World myWorld) {
		this.myWorld = myWorld;
	}

	public static GLCapabilities getCaps() {
		return caps;
	}

	public static void setCaps(GLCapabilities caps) {
		Landscape.caps = caps;
	}

	public static boolean isMY_LIGHT_RENDERING() {
		return MY_LIGHT_RENDERING;
	}

	public static void setMY_LIGHT_RENDERING(boolean MY_LIGHT_RENDERING) {
		Landscape.MY_LIGHT_RENDERING = MY_LIGHT_RENDERING;
	}

	public static boolean isVIEW_FROM_ABOVE() {
		return VIEW_FROM_ABOVE;
	}

	public static void setVIEW_FROM_ABOVE(boolean VIEW_FROM_ABOVE) {
		Landscape.VIEW_FROM_ABOVE = VIEW_FROM_ABOVE;
	}

	public static boolean isDISPLAY_OBJECTS() {
		return DISPLAY_OBJECTS;
	}

	public static void setDISPLAY_OBJECTS(boolean DISPLAY_OBJECTS) {
		Landscape.DISPLAY_OBJECTS = DISPLAY_OBJECTS;
	}

	public float getRotateX() {
		return rotateX;
	}

	public void setRotateX(float rotateX) {
		this.rotateX = rotateX;
	}

	public float getRotationVelocity() {
		return rotationVelocity;
	}

	public void setRotationVelocity(float rotationVelocity) {
		this.rotationVelocity = rotationVelocity;
	}

	public int getIt() {
		return it;
	}

	public void setIt(int it) {
		this.it = it;
	}

	public int getMovingIt() {
		return movingIt;
	}

	public void setMovingIt(int movingIt) {
		this.movingIt = movingIt;
	}

	public int getDxView() {
		return dxView;
	}

	public void setDxView(int dxView) {
		this.dxView = dxView;
	}

	public int getDyView() {
		return dyView;
	}

	public void setDyView(int dyView) {
		this.dyView = dyView;
	}

	public double[][] getLandscape() {
		return landscape;
	}

	public void setLandscape(double[][] landscape) {
		this.landscape = landscape;
	}

	public int getLastFpsValue() {
		return lastFpsValue;
	}

	public void setLastFpsValue(int lastFpsValue) {
		this.lastFpsValue = lastFpsValue;
	}

	public static int getLastItStamp() {
		return lastItStamp;
	}

	public static void setLastItStamp(int lastItStamp) {
		Landscape.lastItStamp = lastItStamp;
	}

	public static long getLastTimeStamp() {
		return lastTimeStamp;
	}

	public static void setLastTimeStamp(long lastTimeStamp) {
		Landscape.lastTimeStamp = lastTimeStamp;
	}

	public float getHeightFactor() {
		return heightFactor;
	}

	public void setHeightFactor(float heightFactor) {
		this.heightFactor = heightFactor;
	}

	public double getHeightBooster() {
		return heightBooster;
	}

	public void setHeightBooster(double heightBooster) {
		this.heightBooster = heightBooster;
	}

	public float getOffset() {
		return offset;
	}

	public void setOffset(float offset) {
		this.offset = offset;
	}

	public float getStepX() {
		return stepX;
	}

	public void setStepX(float stepX) {
		this.stepX = stepX;
	}

	public float getStepY() {
		return stepY;
	}

	public void setStepY(float stepY) {
		this.stepY = stepY;
	}

	public float getLenX() {
		return lenX;
	}

	public void setLenX(float lenX) {
		this.lenX = lenX;
	}

	public float getLenY() {
		return lenY;
	}

	public void setLenY(float lenY) {
		this.lenY = lenY;
	}

	public float[] getSmoothFactor() {
		return smoothFactor;
	}

	public void setSmoothFactor(float[] smoothFactor) {
		this.smoothFactor = smoothFactor;
	}

	public int getSmoothingDistanceThreshold() {
		return smoothingDistanceThreshold;
	}

	public void setSmoothingDistanceThreshold(int smoothingDistanceThreshold) {
		this.smoothingDistanceThreshold = smoothingDistanceThreshold;
	}

	public int getMovingX() {
		return movingX;
	}

	public void setMovingX(int movingX) {
		this.movingX = movingX;
	}

	public int getMovingY() {
		return movingY;
	}

	public void setMovingY(int movingY) {
		this.movingY = movingY;
	}

	public float getModuleAltitude() {
		return moduleAltitude;
	}

	public void setModuleAltitude(float moduleAltitude) {
		this.moduleAltitude = moduleAltitude;
	}

	public float getModuleDepth() {
		return moduleDepth;
	}

	public void setModuleDepth(float moduleDepth) {
		this.moduleDepth = moduleDepth;
	}
}

