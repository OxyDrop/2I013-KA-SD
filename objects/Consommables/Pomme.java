package objects.Consommables;

import Interfaces.Eliminable;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Pomme extends Aliment implements Eliminable{
	private final boolean isConsommable;
	private final int vie;
	private int age;
	private double pdrop;
	
	private static final GLU glu = new GLU();
	private static final GLUT glut = new GLUT();
	
	public Pomme(int x, int y, World world)
	{	
		super(x,y,world);
		vie = 10;
		age = 1;
		pdrop = 0.0000001;
		
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
	
	public void murir() //fait murir la pomme, augmentant les chances de tomber de l'arbre;
	{
		age++;
		pdrop += 0.0000001;
	}

	public double getPdrop() {
		return pdrop;
	}
	
	public void setPdrop(double pdrop)
	{
		this.pdrop=pdrop;
	}
	
	@Override
	public boolean die()
	{
		return vie <= 0;
	}
		public void displayUniqueObject(World myWorld, GL2 gl, int offX, int offY, float offset, 
			float stepX, float stepY, float lenX, float lenY, float normalizeHeight) 
	{
		int x2 = (x-(offX%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
    	int y2 = (y-(offY%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();

    	float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );
		float altitude = (float)height * normalizeHeight ;
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(1f,0f,0f);
		//gl.glBindTexture(GL2.GL_TEXTURE_2D, textureCode);
		
		gl.glVertex3f(offX+x2*stepX+lenX,offY+y2*stepY+lenY,altitude+4f); // Top Right Of The Quad (Top)
		gl.glVertex3f(offX+x2*stepX-lenX,offY+y2*stepY+lenY,altitude+4f); // Top Left Of The Quad (Top)
		gl.glVertex3f(offX+x2*stepX-lenX, offY+y2*stepY+lenY, altitude+4f); // Bottom Left Of The Quad (Top)
		gl.glVertex3f(offX+x2*stepX+lenX,offY+y2*stepY+lenY, altitude+4f); // Bottom Right Of The Quad (Top)

		gl.glVertex3f(offX+x2*stepX+lenX, offX+y2*stepY-lenY, altitude+4f); // Top Right Of The Quad
		gl.glVertex3f(offX+x2*stepX-lenX, offX+y2*stepY-lenY, altitude+4f); // Top Left Of The Quad
		gl.glVertex3f(offX+x2*stepX-lenX, offX+y2*stepY-lenY, altitude); // Bottom Left Of The Quad
		gl.glVertex3f(offX+x2*stepX+lenX, offX+y2*stepY-lenY, altitude); // Bottom Right Of The Quad 

		gl.glVertex3f(offX+x2*stepX+lenX,offY+y2*stepY+lenY, altitude+4f); // Top Right Of The Quad (Front)
		gl.glVertex3f(offX+x2*stepX-lenX,offY+y2*stepY+lenY, altitude+4f); // Top Left Of The Quad (Front)
		gl.glVertex3f(offX+x2*stepX-lenX, offX+y2*stepY-lenY, altitude+4f); // Bottom Left Of The Quad
		gl.glVertex3f(offX+x2*stepX+lenX, offX+y2*stepY-lenY, altitude+4f); // Bottom Right Of The Quad 

		gl.glColor3f(1f, 0.5f, 0f); //orange (red + green)
		
		gl.glVertex3f(offX+x2*stepX+lenX, offX+y2*stepY-lenY, altitude); // Bottom Left Of The Quad
		gl.glVertex3f(offX+x2*stepX-lenX, offX+y2*stepY-lenY, altitude); // Bottom Right Of The Quad
		gl.glVertex3f(offX+x2*stepX-lenX,offY+y2*stepY+lenY, altitude); // Top Right Of The Quad (Back)
		gl.glVertex3f(offX+x2*stepX+lenX,offY+y2*stepY+lenY, altitude); // Top Left Of The Quad (Back)

		gl.glVertex3f(offX+x2*stepX-lenX,offY+y2*stepY+lenY, altitude+4f); // Top Right Of The Quad (Left)
		gl.glVertex3f(offX+x2*stepX-lenX,offY+y2*stepY+lenY, altitude); // Top Left Of The Quad (Left)
		gl.glVertex3f(offX+x2*stepX-lenX, offX+y2*stepY-lenY, altitude); // Bottom Left Of The Quad
		gl.glVertex3f(offX+x2*stepX-lenX, offX+y2*stepY-lenY, altitude+4f); // Bottom Right Of The Quad 

		gl.glVertex3f(offX+x2*stepX+lenX,offY+y2*stepY+lenY, altitude); // Top Right Of The Quad (Right)
		gl.glVertex3f(offX+x2*stepX+lenX,offY+y2*stepY+lenY, altitude+4f); // Top Left Of The Quad
		gl.glVertex3f(offX+x2*stepX+lenX, offX+y2*stepY-lenY, altitude+4f); // Bottom Left Of The Quad
		gl.glVertex3f(offX+x2*stepX+lenX, offX+y2*stepY-lenY, altitude); // Bottom Right Of The Quad
	}
}
