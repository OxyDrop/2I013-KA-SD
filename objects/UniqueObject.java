// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package objects;

import javax.media.opengl.GL2;
import worlds.World;

abstract public class UniqueObject // UniqueObject are object defined with particular, unique, properties (ex.: particular location)
{
	protected int x,y;
	protected World world;
	
	public UniqueObject(int x, int y, World world) 
	{
		this.world = world;
		this.x = x;
		this.y = y;
	}
	
	public int[] getCoordinate()
	{
		int coordinate[] = new int[2];
		coordinate[0] = this.x;
		coordinate[1] = this.y;
		return coordinate;
	}
	
	abstract public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight );

}
