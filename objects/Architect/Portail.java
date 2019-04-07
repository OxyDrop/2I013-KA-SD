package objects.Architect;

import DynamicObject.Agent;
import java.util.ArrayList;
import javax.media.opengl.GL2;
import objects.UniqueObject;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Portail extends UniqueObject{ //Lie deux mondes entre eux;
	
	private World passage;
	public Portail(int x, int y, World world, World passage) 
	{
		super(x, y, world);
		this.passage = passage;
	}

	
	
	public boolean distanceSuffisante(Agent a)
	{
		return (Math.sqrt(Math.pow( a.getX() - x , 2 ) + Math.pow( a.getY() - y , 2 )) <= 1);
		
	}
	public void passePortail(Agent a)
	{
		if(distanceSuffisante(a))
		{
			Agent clone = a.clone();
			//Propulse a un point aleatoire en dehors du portail
			a.setX(a.getX()+(int)Math.random()%(10-5+1)+5);
			a.setY(a.getY()+(int)Math.random()%(10-5+1)+5);
			world.getAgentListe().remove(a);
			passage.getAgentListe().add(clone);
			System.out.println("Un agent a emprunté le portail "+world.getNom()+" en ("+x+","+y+") menant au "+passage.getNom());
		}
	}
	@Override
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, 
			float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight) 
	{
		int x2 = (x-(offsetCA_x%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
		
    	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();
		
		float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );
    	float altitude = (float)height * normalizeHeight ; 
		
		gl.glColor3f(036f,0.87f,1f);
		gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 16.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 16.f);
		gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
	}
	
}
