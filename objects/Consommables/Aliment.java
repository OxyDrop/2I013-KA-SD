package objects.Consommables;

import Interfaces.Consommable;
import javax.media.opengl.GL2;
import objects.UniqueObject;
import worlds.World;

/**
 *
 * @author Serero
 */
public abstract class Aliment extends UniqueObject implements Consommable 
{
	
	public Aliment(int x, int y, World world) {
		super(x, y, world);
	}

	@Override
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, 
			float stepX, float stepY, float lenX, float lenY, float normalizeHeight) 
	{
	}
	
}
