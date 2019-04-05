package objects;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Astre extends UniqueObject
{

	private static GLU glu = new GLU();
	private static GLUT glut = new GLUT();
	private static float angle = 0.0f;
	public static DisplayMode dm, dm_old;
	
	GLUquadric astre;
   private Texture texture;
   
   public Astre(int x, int y, World world)
   {
	   super(x,y,world);
	  
   }
     public void displayUniqueObject(World myWorld, GL2 gl, int offsetX, int offsetY, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight )
    {
		GLU glu = new GLU();
		astre = glu.gluNewQuadric();
		
		int x2 = (x-(offsetX%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
		
    	int y2 = (y-(offsetY%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();
		
		float arcx=0.1f;
		float arcy=0.1f;
		
		gl.glLoadIdentity(); // Reset The View
		arcx+=0.5f;
		arcy+=0.5f;
		
		gl.glTranslatef(arcx, arcy, -5f);
		gl.glRotatef(angle, 0f, 1.0f, 0f);
		
		gl.glColor3f(1f, 1f, 0f);
        glu.gluQuadricDrawStyle(astre, GLU.GLU_FILL);
        glu.gluQuadricNormals(astre, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(astre, GLU.GLU_OUTSIDE);
		glu.gluSphere(astre,0.1f, 16, 16);
		glu.gluDeleteQuadric(astre);
		
		//change the speeds here
		angle += .5f;
		
	}
	
}



