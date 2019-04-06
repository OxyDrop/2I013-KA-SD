package objects.Consommables;

import Interfaces.Eliminable;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Pomme extends Aliment implements Eliminable{
	private boolean isConsommable;
	private int vie;
	private static GLU glu = new GLU();
	private static GLUT glut = new GLUT();
	
	public Pomme(int x, int y, World world)
	{	
		super(x,y,world);
		vie = 10;
		isConsommable = true;
	}
	@Override
	public boolean isConsommable() 
	{
		return isConsommable;
	}
	
	public int getVie()
	{
		return vie;
	}

	@Override
	public boolean die()
	{
		return vie <= 0;
	}
		public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, 
			float stepX, float stepY, float lenX, float lenY, float normalizeHeight) 
	{
		//gl.glTranslatef(0f, 0f, -5.0f);
		gl.glColor3f(1f,0f,0f);
		//gl.glBindTexture(GL2.GL_TEXTURE_2D, textureCode);
		GLUquadric glpomme = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(glpomme, GLU.GLU_FILL);
        glu.gluQuadricNormals(glpomme, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(glpomme, GLU.GLU_OUTSIDE);
		glu.gluSphere(glpomme,2f, 20, 20);
		glu.gluDeleteQuadric(glpomme);
	}
}
