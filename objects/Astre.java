package objects;


import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.texture.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
/**
 *
 * @author Serero
 */
public class Astre implements GLEventListener
{

	private static GLU glu = new GLU();
	private static GLUT glut = new GLUT();
	private float angle = 0.0f;
	public static DisplayMode dm, dm_old;
 
   private float xrot,yrot,zrot;
   private int textureCode;
   private Texture texture;

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();

		float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {-30, 0, 0, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);

        // Set material properties.
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
		
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		gl.glEnable(GL2.GL_TEXTURE_2D);
		try {
			URL url = getClass().getResource("moon.png");
			texture = TextureIO.newTexture(url, false, "jpg");
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
			textureCode = texture.getTextureObject(gl);
			texture.enable(gl);
			texture.bind(gl);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void display(GLAutoDrawable drawable)
	{
		final GL2 gl = drawable.getGL().getGL2();
		
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The View
	
		gl.glTranslatef(0f, 0f, -5.0f);
		gl.glRotatef(xrot, 0f, 1.0f, 0f);
		
		//gl.glBindTexture(GL2.GL_TEXTURE_2D, textureCode);
		GLUquadric earth = glu.gluNewQuadric();
		glu.gluQuadricTexture(earth, true);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
		glu.gluSphere(earth,0.6f, 20, 20);
		glu.gluDeleteQuadric(earth);
		
		//change the speeds here
		xrot += .5f;

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
		gl.glPushMatrix();
		
		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPopMatrix();
	}
	
}




