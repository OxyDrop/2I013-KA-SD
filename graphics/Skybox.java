package graphics;

import Tools.ImageResources;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import de.matthiasmann.twl.utils.PNGDecoder;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author Serero
 */
public class Skybox implements GLEventListener{
	
	private GLU glu = new GLU();
	private static final String[] TEXTFILEDAY = {"/res/skyback.png","/res/skyfront.png","/res/skyleft.png","/res/skyright.png","/res/skytop.png"};
	private static final String[] TEXTFILENIGHT = {"/res/nightBack.png","/res/nightFront.png","/res/nightLeft.png","/res/nightRight.png","/res/nightTop.png"};
	private Texture[] textureList;
	private int[] skyboxday;
	
	
	public Skybox()
	{
		textureList = new Texture[TEXTFILEDAY.length];
		skyboxday = new int[TEXTFILEDAY.length];
	}
	
	@Override
	public void init(GLAutoDrawable drawable) 
	{
		final GL2 gl = drawable.getGL().getGL2();
		/*
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		gl.glEnable(GL2.GL2.GL_TEXTURE_2D | GL2.GL_TEXTURE_3D);
		
		gl.glBindTexture(GL2.GL_TEXTURE_CUBE_MAP,GL.GL_ACTIVE_TEXTURE);
		for(int i = 0 ; i<textureFiles.length;i++)
		{
			TextureData data = decodeTextureFile(textureFiles[i]+"png");
			
			gl.glTexImage2D(GL2.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0,GL2.GL_RGBA, 
					data.getWidth(),data.getHeight(), 0 , GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, data.getBuffer());
			
		}
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP,GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP,GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL2.GL_TEXTURE_CUBE_MAP, GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE); 
		*/
		
		for(int i = 0 ; i<textureList.length;i++)
		{
				textureList[i]=ImageResources.createTexture(TEXTFILEDAY[i]);
				skyboxday[i]=textureList[i].getTextureObject(gl);
		}
			
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
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_BLEND);
		
		gl.glTranslatef(0f, 0f, -5.0f);
		gl.glColor4f(1,1,1,1);
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxday[0]);
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(0.5f, -0.5f, -0.5f);
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(-0.5f, -0.5f, -0.5f);
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(-0.5f, 0.5f, -0.5f);
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(0.5f, 0.5f, -0.5f);
		gl.glEnd();

		// Render the left quad
		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxday[1]);
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(0.5f, -0.5f, 0.5f);
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(0.5f, -0.5f, -0.5f);
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(0.5f, 0.5f, -0.5f);
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(0.5f, 0.5f, 0.5f);
		gl.glEnd();

		// Render the back quad
		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxday[2]);
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-0.5f, -0.5f, 0.5f);
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(0.5f, -0.5f, 0.5f);
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(0.5f, 0.5f, 0.5f);
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(-0.5f, 0.5f, 0.5f);

		gl.glEnd();

		// Render the right quad
		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxday[3]);
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-0.5f, -0.5f, -0.5f);
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(-0.5f, -0.5f, 0.5f);
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(-0.5f, 0.5f, 0.5f);
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(-0.5f, 0.5f, -0.5f);
		gl.glEnd();

		// Render the top quad
		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxday[4]);
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(-0.5f, 0.5f, -0.5f);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-0.5f, 0.5f, 0.5f);
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(0.5f, 0.5f, 0.5f);
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(0.5f, 0.5f, -0.5f);
		gl.glEnd();

		// Render the bottom quad
		gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxday[5]);
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(-0.5f, -0.5f, -0.5f);
		gl.glTexCoord2f(0, 1);
		gl.glVertex3f(-0.5f, -0.5f, 0.5f);
		gl.glTexCoord2f(1, 1);
		gl.glVertex3f(0.5f, -0.5f, 0.5f);
		gl.glTexCoord2f(1, 0);
		gl.glVertex3f(0.5f, -0.5f, -0.5f);
		gl.glEnd();
		gl.glFlush();
		gl.glDisable(GL2.GL_BLEND);
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		if(height <= 0) height = 1; 
       
		//preventing devided by 0 exception height = 1; 
		final float h = (float) width / (float) height; 
            
		// display area to cover the entire window 
		gl.glViewport(0, 0, width, height); 
            
		//transforming projection matrix 
		gl.glMatrixMode(GL2.GL_PROJECTION); 
		gl.glLoadIdentity(); 
		glu.gluPerspective(45.0f, h, 1.0, 20.0); 
		gl.glPushMatrix();
      
		//transforming model view gl.glLoadIdentity(); 
		gl.glMatrixMode(GL2.GL_MODELVIEW); 
		gl.glLoadIdentity(); 
		gl.glPopMatrix();
	}
	
	public ImageResources decodeTextureFile(String fileName)
	{
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try{
			FileInputStream in = new FileInputStream(fileName);
			PNGDecoder decoder = new PNGDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4*width*height);
			decoder.decode(buffer,width*4,PNGDecoder.Format.RGBA);
			buffer.flip();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Tried to load texture " + fileName + ", didn't work");
			System.exit(1);
		}
		return new ImageResources(buffer, width, height);
	}
}
