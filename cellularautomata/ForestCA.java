// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package cellularautomata;

import worlds.World;

//-1 = WATER ; 0 = NULL ; 1 = TREE ; 2 = BURNING ; 3 = BURNT
//
public class ForestCA extends CellularAutomataInteger {
            
	CellularAutomataDouble HeightVal;
	
	World world;
	private final static double DENSITY_TREES = 0.55;
	private static double burnP = 0.000001;
	private static double growP = 0.0003;
	private static double heightSable = 0.05;
	
	public ForestCA ( World world, int dx , int dy, CellularAutomataDouble HeightVal )
	{
		super(dx,dy,true); // buffering must be true.
		
		this.HeightVal = HeightVal;
		
		this.world = world;
	}
	
	public void init()
	{
		for (int x = 0; x != dx; x++) {
			for (int y = 0; y != dy; y++) {
				if (HeightVal.getCellState(x, y) >= 0) 
				{
					if (HeightVal.getCellState(x, y) <= heightSable) //SABLE
					{
						this.setCellState(x, y, 4);
					}
					else
					{
						if ( Math.random() < DENSITY_TREES ) // was: 0.71
							this.setCellState(x, y, 1); // tree
						else 
							this.setCellState(x, y, 0); // empty
					}
				} else {
					this.setCellState(x, y, -1); // water (ignore)
				}
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
				//////////----------FEU DE FORET---------------------//////////////
				int current = this.getCellState(i, j);
				double height = this.HeightVal.getCellState(i, j);
    			if ((current == 0 && height>0) || current == 1 || current == 2 || current == 3 || current==4 )
    			{
	    			if (current == 1 ) // tree?
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
	    					if ( Math.random() < burnP ) // spontaneously take fire ?
	    						this.setCellState(i,j,2);
	    					else
	    						this.setCellState(i,j,1); // copied unchanged
	    			}
					else if( current == 2 )
	    				this.setCellState(i,j,3); // burnt
							
					else if(current == 3)
						this.setCellState(i,j,0); 
				
				//////////----------MAJ COULEURS---------------------//////////////
	    		float color[] = new float[3];
	    		switch (current)
	    		{
	    			case 0: //Accelere la remise en forme du terrain
						if(this.HeightVal.getCellState(i, j)>0)
						{
							color = this.world.getCellColorValue(i, j);
						}
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
					case 4 :  //Sable ne propage pas le feu
						if(Math.random()<0.5 && this.HeightVal.getCellState(i, j)<0.006) //EAU dynamique
						{
							color[0]= 0.1f;
							color[1]= 0.1f;
							color[2]= 0.5f;		
						}
						else
						{
							color[0] = 1f;
							color[1] = 1f;
							color[2] = 0.8f;
						}
						break;
					default:
						color[0] = 0.5f; //127
						color[1] = 0.5f; //127
						color[2] = 0.5f; //127
						System.out.print("cannot interpret CA state: " + this.getCellState(i, j));
						System.out.println(" (at: " + i + "," + j + " -- height: " + this.world.getCellHeight(i, j) + " )");
	    		}	   
	    		this.world.ColorVal.setCellState(i, j, color);
			}	
			if(current == 0 && height>heightSable && Math.random()<growP) //Repousse d'arbre
				this.setCellState(i, j, 1);	
			
		}
		this.swapBuffer();
    }
    	
	//Are useful for season and climate
	public static double getBurnP() {
		return burnP;
	}

	public static void setBurnP(double burnP) {
		ForestCA.burnP = burnP;
	}

	public static double getGrowP() {
		return growP;
	}

	public static void setGrowP(double growP) {
		ForestCA.growP = growP;
	}

	
}
