// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package DynamicObject;

import javax.media.opengl.GL2;
import worlds.World;

abstract public class UniqueDynamicObject // UniqueObject are object defined with particular, unique, properties (ex.: particular location)
{
	protected int x,y;
	protected World world;
	
	public UniqueDynamicObject(int x, int y, World world) 
	{
		this.x = x;
		this.y = y;
		this.world = world;
	}
	
	abstract public void step();
	
	public int[] getCoordinate()
	{
		int coordinate[] = new int[2];
		coordinate[0] = this.x;
		coordinate[1] = this.y;
		return coordinate;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	
	abstract public UniqueDynamicObject clone();
	@Override
	abstract public boolean equals(Object o);
	abstract public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight );

}
