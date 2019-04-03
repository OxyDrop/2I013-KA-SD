package DynamicObject;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import input.KeyInput;
import java.awt.DisplayMode;
import java.io.IOException;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Player extends Agent implements GLEventListener {
	private static GLU glu = new GLU();
	private float angle = 0.0f;
	public static DisplayMode dm, dm_old;
 
   private float runSpeed = 2.0f;
   private int texture;
   
	public Player(int x, int y, World world) {
		super(x, y, world);
	}
	
	public void update()
	{
		float xinput=0;
		float yinput=0;
		
		if(KeyInput.getKey(KeyEvent.VK_LEFT))
			xinput--;
		if(KeyInput.getKey(KeyEvent.VK_RIGHT))
			xinput++;
		if(KeyInput.getKey(KeyEvent.VK_UP))
			yinput++;
		if(KeyInput.getKey(KeyEvent.VK_DOWN))
			yinput--;
		
		//x+=updateDelta*xinput*runSpeed;
		//y+=updateDelta*yinput*runSpeed;
		//dans boucle principale, 1.0f*NANO*targetTime(NANO/FPS) = updateDelta
		//angle = (float) Math.toDegrees(Math.atan2(MouseInput.getWorldX() - x, MouseInput.getWorldY() - y)
		//fenetre.cameraX=x;
		//fenetre.cameraY=y;
	}

	@Override
	public void init(GLAutoDrawable drawable) 
	{
		final GL2 gl = drawable.getGL().getGL2();

		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		gl.glEnable(GL2.GL_TEXTURE_2D);
		try {
			Texture t = TextureIO.newTexture(getClass().getResource("earthmap1K.jpg"), true, "jpg");
			texture = t.getTextureObject(gl);

		}catch (IOException e){
			e.printStackTrace();
		}
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void display(GLAutoDrawable drawable) //TODO
	{
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The View
		gl.glTranslatef(0f, 0f, -5.0f);


		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
		gl.glBegin(GL2.GL_LINES);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{	
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		if(height<=0)
			height = 1;
		final float h = (float) width / (float) height;
		
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
}
