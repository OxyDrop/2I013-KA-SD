package objects.Architect;

import DynamicObject.Agent;
import DynamicObject.FAgent;
import DynamicObject.MAgent;
import DynamicObject.Zombie;
import java.util.ArrayList;
import javax.media.opengl.GL2;
import objects.UniqueObject;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Teleporteur extends UniqueObject{ //Lie deux mondes entre eux;
	
	private final static int DISTANCEMIN = 2; //utilisé pour definir la distance minimale
	private final int xdest;
	private final int ydest;
	private static int cpt = 0;
	private final int id;
	
	public Teleporteur(int x, int y, int xdest, int ydest, World world) 
	{
		super(x, y, world);
		id=++cpt;
		this.xdest=xdest;
		this.ydest=ydest;
	}
	
	public boolean distanceSuffisante(Agent a)
	{
		return (Math.sqrt(Math.pow( a.getX() - x , 2 ) + Math.pow( a.getY() - y , 2 )) <= DISTANCEMIN);
		
	}
	public boolean distanceSuffisante(MAgent a)
	{
		return (Math.sqrt(Math.pow( a.getX() - x , 2 ) + Math.pow( a.getY() - y , 2 )) <= DISTANCEMIN);
		
	}
	public boolean distanceSuffisante(FAgent a)
	{
		return (Math.sqrt(Math.pow( a.getX() - x , 2 ) + Math.pow( a.getY() - y , 2 )) <= DISTANCEMIN);
		
	}
	public boolean distanceSuffisante(Zombie a)
	{
		return (Math.sqrt(Math.pow( a.getX() - x , 2 ) + Math.pow( a.getY() - y , 2 )) <= DISTANCEMIN);
		
	}
	
	public void passeTeleporteur(ArrayList<Agent> agentList)
	{	
		for(Agent a : agentList)
			if (distanceSuffisante(a)) 
			{
				a.setX(xdest);
				a.setY(ydest);
				System.out.println("Un agent a emprunté le teleporteur " +id
					+ " en (" + x + "," + y + ") menant à ("+xdest+","+ydest+")");
			}
	}
	public void passeTeleporteurM(ArrayList<MAgent> agentList)
	{	
		for(MAgent a : agentList)
			if (distanceSuffisante(a)) 
			{
				a.setX(xdest);
				a.setY(ydest);
				System.out.println("Un Magent a emprunté le teleporteur " +id
					+ " en (" + x + "," + y + ") menant à ("+xdest+","+ydest+")");
			}
	}
	public void passeTeleporteurF(ArrayList<FAgent> agentList)
	{	
		for(FAgent a : agentList)
			if (distanceSuffisante(a)) 
			{
				a.setX(xdest);
				a.setY(ydest);
				System.out.println("Un FAgent a emprunté le teleporteur " +id
					+ " en (" + x + "," + y + ") menant à ("+xdest+","+ydest+")");
			}
	}
	public void passeTeleporteurZ(ArrayList<Zombie> agentList)
	{	
		for(Zombie a : agentList)
			if (distanceSuffisante(a)) 
			{
				a.setX(xdest);
				a.setY(ydest);
				System.out.println("Un zombie a emprunté le teleporteur " +id
					+ " en (" + x + "," + y + ") menant à ("+xdest+","+ydest+")");
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
		
		gl.glColor3f(1f,0.25f,0.066f);
		
		gl.glVertex3f( (offset+x2*stepX-lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight); //- - -
        gl.glVertex3f( (offset+x2*stepX-lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight + 40f); // - - +
        gl.glVertex3f( (offset+x2*stepX+lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight + 40f); // + - +
		gl.glVertex3f( (offset+x2*stepX+lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight); // + - -
		/*Cote*/
		gl.glVertex3f( offset+x2*stepX+lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight); //+ + -
        gl.glVertex3f( offset+x2*stepX+lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight + 40.f); ///+ + +
		gl.glVertex3f( offset+x2*stepX-lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight + 40.f); //- + +
        gl.glVertex3f( offset+x2*stepX-lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight); //- + -
		/*Cote*/
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight); //+ - -
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 40.f); //+ - +
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 40.f); //+ + + 
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight); // + + -
		/*Cote */
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight); //- + -
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 40.f); // - + +
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 40.f); // - - +
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight); // - - -
		/*Chapeau*/
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 20.f); // - + +
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 20.f); // - + +
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 20.f); // + + +
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 20.f); //+ + -
	
	
	}
	
}
