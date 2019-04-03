// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package cellularautomata;

import worlds.World;

//-1 = WATER ; 0 = NULL ; 1 = TREE ; 2 = BURNING ; 3 = BURNT
public class ForestCA extends CellularAutomataInteger {
            
	CellularAutomataDouble cellsHeightValuesCA;
	
	World world;
	
	public ForestCA ( World world, int dx , int dy, CellularAutomataDouble cellsHeightValuesCA )
	{
		super(dx,dy,true); // buffering must be true.
		
		this.cellsHeightValuesCA = cellsHeightValuesCA;
		
		this.world = world;
	}
	
	public void init()
	{
		for ( int x = 0 ; x != dx ; x++ )
    		for ( int y = 0 ; y != dy ; y++ )
    		{
    			if ( cellsHeightValuesCA.getCellState(x,y) >= 0 )
    			{
    				if ( Math.random() < 0.0 ) // was: 0.71
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
        
        /* Incendie et màj couleurs */
	public void step()
	{
    	for ( int i = 0 ; i != dx ; i++ )
    		for ( int j = 0 ; j != dy ; j++ )
    		{
    			if ( this.getCellState(i, j) == 1 || this.getCellState(i, j) == 2 || this.getCellState(i, j) == 3 )
    			{
	    			if ( this.getCellState(i,j) == 1 ) // tree?
	    			{
	    				// check if neighbors(von neumann) are burning
	    				if ( 
	    					this.getCellState( (i+dx-1)%(dx) , j ) == 2 ||
	    					this.getCellState( (i+dx+1)%(dx) , j ) == 2 ||
	    					this.getCellState( i , (j+dy+1)%(dy) ) == 2 ||
	    					this.getCellState( i , (j+dy-1)%(dy) ) == 2
                                            )
	    				{
	    					this.setCellState(i,j,2);
	
	    				}
	    				else
	    					if ( Math.random() < 0.00001 ) // spontaneously take fire ?
	    						this.setCellState(i,j,2);
	    					else
	    						this.setCellState(i,j,1); // copied unchanged
	    			}
	    			else
	    			{
	        				if ( this.getCellState( i , j ) == 2 ) // burning?
	        					this.setCellState(i,j,3); // burnt
	        				else
	        					this.setCellState(i,j, this.getCellState(i,j) ); // copied unchanged
	    			}
	    			
	    			float color[] = new float[3];
	    			switch ( this.getCellState(i, j) )
	    			{
	    				case 0:
	    					break;
	    				case 1:
	    					color[0] = 0.f; //0
	    					color[1] = 0.3f; //76
	    					color[2] = 0.f; //0
	    					break;
	    				case 2: // burning tree
	    					color[0] = 1.f; //25
	    					color[1] = 0.f; //0
	    					color[2] = 0.f; //0
	    					break;
	    				case 3: // burnt tree
	    					color[0] = 0.f; //0
	    					color[1] = 0.f; //0
	    					color[2] = 0.f; //0
	    					break;
	    				default:
	    					color[0] = 0.5f; //127
	    					color[1] = 0.5f; //127
	    					color[2] = 0.5f; //127
	    					System.out.print("cannot interpret CA state: " + this.getCellState(i, j));
	    					System.out.println(" (at: " + i + "," + j + " -- height: " + this.world.getCellHeight(i,j) + " )");
	    			}	   
	    			this.world.cellsColorValues.setCellState(i, j, color);
    			}
    		}
    	this.swapBuffer();
	}

	
}
