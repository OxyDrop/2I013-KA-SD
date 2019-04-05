// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package cellularautomata;

public abstract class CellularAutomata {

	protected int dx;
	protected int dy;

	boolean buffering;
	
	int activeIndex;
	
	public CellularAutomata(int dx, int dy, boolean buffering ) 
	{
		this.dx = dx;
		this.dy = dy;

		this.buffering = buffering; //false = 0 ; true = 1;
		
		activeIndex = 0; //0: write old buffer ; 1: read old buffer
	}

	public void checkBounds( int x, int y )
	{
		if ( x < 0 || x > dx || y < 0 || y > dy )
		{
			System.err.println("[error] out of bounds ("+x+","+y+")");
			//System.exit(-1);
		}
	}
	
	public int getWidth()
	{
		return dx;
	}
	
	public int getHeight()
	{
		return dy;
	}

	public void init()
	{
		// ...
	}
	
	public void step() 
	{ 
		if ( buffering )
			swapBuffer();
	}
	
	public void swapBuffer() // should be used carefully (except for initial step)
	{
		activeIndex = ( activeIndex+1 ) % 2; //activeIndex = 0 ou 1
	}
	
}
