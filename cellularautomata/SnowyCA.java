package cellularautomata;

import worlds.World;

public class SnowyCA extends CellularAutomataInteger {
            
	CellularAutomataDouble HeightVal;
	
	World world;
	private final static double DENSITY_TREES = 0.01;
	
	public SnowyCA ( World world, int dx , int dy, CellularAutomataDouble HeightVal )
	{
		super(dx,dy,true); // buffering must be true.
		
		this.HeightVal = HeightVal;
		
		this.world = world;
	}
	
	public void init()
	{
		for ( int x = 0 ; x != dx ; x++ )
    		for ( int y = 0 ; y != dy ; y++ )
    		{
    			if ( HeightVal.getCellState(x,y) >= 0 )
    			{
    				if ( Math.random() < DENSITY_TREES ) // was: 0.71
    					this.setCellState(x, y, 1); // tree
    				else
    					this.setCellState(x, y, 0); // empty
    			}
    			else
    			{
    				this.setCellState(x, y, -1); // water (ignore)
    			}
    		}
    	this.swapBuffer();

	}
        
	public void step()
	{
    	//this.swapBuffer();
	}	
}

