package objects;

import applications.simpleworld.Agent;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
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
 
   private float xrot,yrot,zrot;
   private int texture;
   
	public Player(int x, int y, World world) {
		super(x, y, world);
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
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void display(GLAutoDrawable drawable) {
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}
	
}
