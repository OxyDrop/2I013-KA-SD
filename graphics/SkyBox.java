package graphics;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import javax.media.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.*;
import com.jogamp.newt.opengl.*;
import com.jogamp.newt.event.*;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 *
 * @author Serero
 */
public class SkyBox implements GLEventListener{
	private GLU glu = new GLU();
	@Override
	public void init(GLAutoDrawable drawable) {
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void display(GLAutoDrawable drawable) {
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
		//(gl.glPushMatrix())
      
		//transforming model view gl.glLoadIdentity(); 
		gl.glMatrixMode(GL2.GL_MODELVIEW); 
		gl.glLoadIdentity(); 
		//(gl.glPopMatrix())
	}
	
}
