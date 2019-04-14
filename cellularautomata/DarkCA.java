package cellularautomata;

import worlds.World;

public class DarkCA extends CellularAutomataInteger {
            
	CellularAutomataDouble HeightVal;
	private double lavatitude;
	private final static double LAVATITUDE = 0.28;
	private double pEcoulement;
	private static final double PECOULEMENT = 0.1;
	private LiquideCA liquid;
	
	World world;
	
	public DarkCA ( World world, int dx , int dy, CellularAutomataDouble HeightVal )
	{
		super(dx,dy,true); // buffering must be true.
		
		this.HeightVal = HeightVal;
		
		this.lavatitude = LAVATITUDE;
		
		this.world = world;
		
		this.pEcoulement = PECOULEMENT;
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
		
		liquid = new LiquideCA(dx, dy,false,this);
    	this.swapBuffer();

	}
       
	//Ecoulements
	public void step()
	{
    	for ( int i = 0 ; i != dx ; i++ ){
    		for ( int j = 0 ; j != dy ; j++ )
    		{
				
				int current = this.getCellState(i, j);
				int currentplus1 = this.getCellState((i+1+dx)%dx, (j+1+dy)%dy);
				
				double height = world.getCellHeight(i, j);
				double heightplus1 = world.getCellHeight((i+1+dx)%dx, (j+1+dy)%dy);
				
				int liquidCell = this.liquid.getCellState(i, j);
				int liquidCellplus1 = this.liquid.getCellState((i+1+dx)%dx, (j+1+dy)%dy);
				
				if(height>=heightplus1 && current == -1 && currentplus1 == 0 && Math.random()<pEcoulement) //Si altitude superieure et case superieur est de la lave 
				{																						 //et case inferieure de la terre
					setCellState((i+1+dx) % dx , (j+1+dy) % dy , -1 );
					this.world.ColorVal.setCellState((i+dx+1)%dx,(j+1+dy)%dy,this.world.ColorVal.getCellState(i, j));
				}
				
			}
		}
		this.swapBuffer();
    }

	public CellularAutomataDouble getHeightVal() {
		return HeightVal;
	}

	public void setHeightVal(CellularAutomataDouble HeightVal) {
		this.HeightVal = HeightVal;
	}

	public double getLavatitude() {
		return lavatitude;
	}

	public void setLavatitude(double lavatitude) {
		this.lavatitude = lavatitude;
	}

	public double getpEcoulement() {
		return pEcoulement;
	}

	public void setpEcoulement(double pEcoulement) {
		this.pEcoulement = pEcoulement;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
}

