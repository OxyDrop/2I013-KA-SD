package objects.Architect;

import java.util.ArrayList;
import javax.media.opengl.GL2;
import objects.UniqueObject;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Portail extends UniqueObject{ //Lie deux mondes entre eux;
	
	private ArrayList<World> worlds;
	public Portail(int x, int y, World world) 
	{
		super(x, y, world);
		worlds = new ArrayList<>();

	}

	@Override
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight) 
	{
		
	}
	
}
