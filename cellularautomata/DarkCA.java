package cellularautomata;

import worlds.World;

public class DarkCA extends CellularAutomataInteger {
            
	CellularAutomataDouble HeightVal;
	private double lavatitude;
	private final static double LAVATITUDE = 0.3;
	
	World world;
	
	public DarkCA ( World world, int dx , int dy, CellularAutomataDouble HeightVal )
	{
		super(dx,dy,true); // buffering must be true.
		
		this.HeightVal = HeightVal;
		
		this.lavatitude = LAVATITUDE;
		
		this.world = world;
	}
	
	public void init()
	{
		for ( int x = 0 ; x != dx ; x++ )
    		for ( int y = 0 ; y != dy ; y++ )
    		{
    			if ( HeightVal.getCellState(x,y) > 0 && HeightVal.getCellState(x, y)<=lavatitude)
					this.setCellState(x, y, 0);
    			
				if(HeightVal.getCellState(x, y)>lavatitude) //source volcanique
					this.setCellState(x, y, -1);
    				
				if(HeightVal.getCellState(x, y)<=0)
					this.setCellState(x, y, -1);
    		}
    	this.swapBuffer();

	}
       
	//Ecoulements
	public void step()
	{
//    	for ( int i = 0 ; i != dx ; i++ ){
//    		for ( int j = 0 ; j != dy ; j++ )
//    		{
//				
//				int current = this.getCellState(i, j);
//				double height = this.HeightVal.getCellState(i, j);
//   
//				
//				//////////----------MAJ COULEURS---------------------//////////////
//	    		float color[] = new float[3];
//	    		switch (current)
//	    		{
//					case 0:
//						color = this.world.getCellColorValue(i, j);
//						break;
//	    		}	   
//	    		this.world.ColorVal.setCellState(i, j, color);
//			}
//		}
		this.swapBuffer();
    }
}

