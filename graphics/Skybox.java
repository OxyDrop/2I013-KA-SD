package graphics;

import Tools.ImageResources;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.texture.Texture;
import objects.UniqueObject;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Skybox extends UniqueObject{
	
	private GLU glu = new GLU();
	
	private int skyboxT1 , skyboxT2, skyboxT3, skyboxT4, skyboxT5,	skyboxT6;
	private Texture t1, t2, t3, t4, t5, t6;

	public Skybox(int x, int y, World world) 
	{
		super(x, y, world);
	}

	@Override
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, 
			float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight) 
	{
	
		t1=ImageResources.createTexture("/res/skyfront.png");
		t2=ImageResources.createTexture("/res/skyback.png");
		t3=ImageResources.createTexture("/res/skytop.png");
		//t4=ImageResources.createTexture("/res/bottom.png");
		//t5=ImageResources.createTexture("/res/skyright.png");
		//t6=ImageResources.createTexture("/res/skyleft.png");
		
		 skyboxT1=t1.getTextureObject(gl);
		 skyboxT2=t2.getTextureObject(gl);
		 skyboxT3=t3.getTextureObject(gl);
		 //skyboxT4=t4.getTextureObject(gl);
		//skyboxT5=t5.getTextureObject(gl);
		 //skyboxT6=t6.getTextureObject(gl)
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP,GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP,GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE); 
		
		gl.glEnable(GL2.GL_BLEND);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glTranslatef(0f, 0f, -5.0f);
		gl.glColor4f(1,1,1,1);
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxT1);
		gl.glBegin(GL2.GL_QUADS);

		// Front Face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);

		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxT2);
		// Back Face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		
			gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxT3);
		// Top Face
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxT1);
		// Bottom Face
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);

		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxT2);
		// Right face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxT3);
		// Left Face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glEnd();
		gl.glFlush();
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		
	}
}
