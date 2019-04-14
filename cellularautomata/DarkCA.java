package cellularautomata;

import worlds.World;

public class DarkCA extends CellularAutomataInteger {
            
	CellularAutomataDouble HeightVal;
	private double lavatitude;
	private final static double LAVATITUDE = 0.3;
	private double pEcoulement;
	private static final double PECOULEMENT = 0.5;
	private LiquideCA liquid;
	private final static int LIQLIM = 100;
	
	private final static float[] JAUNE = {1f,0.7f,0f};
	private final static float[] ORANGEJAUNE = {1f,0.6f,0f};
	private final static float[] ORANGE = {1f,0.4f,0f};
	private final static float[] ORANGEROUGE = {1f,0.25f,0f};
	private final static float[] ROUGE = {1f,0.15f,0f};
	private final static float[] NOIR = {0.1f,0f,0f};
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
		
		liquid = new LiquideCA(dx, dy,false,this, LIQLIM); //quantité de liquide
    	this.swapBuffer();

	}
       
	//Ecoulements
	public void step()
	{
    	for ( int i = 0 ; i != dx ; i++ ){
    		for ( int j = 0 ; j != dy ; j++ )
    		{
				int iplus = (i+1+dx)%dx;
				int imoins = (i-1+dx)%dx;
				
				int current = this.getCellState(i, j);
				int currentplus1 = this.getCellState(iplus, j);
				int currentmoins1 = this.getCellState(imoins, j);
				
				double height = world.getCellHeight(i, j);
				double heightplus1 = world.getCellHeight(iplus,j);
				double heightmoins1 = world.getCellHeight(imoins, j);
				
				int liquidCell = this.liquid.getCellState(i, j);
				int liquidCellplus1 = this.liquid.getCellState(iplus,j);
				int liquidCellmoins1 = this.liquid.getCellState(imoins, j);
				
				if(height>=heightplus1 && Math.random()<pEcoulement) //Si altitude superieure
				{																					 
					CellularAutomataColor colorcpy = this.world.ColorVal;
					
					liquid.setCellState(iplus, j, (int) (liquidCellplus1+liquidCell/1.4));
					liquid.setCellState(i, j, (int)(liquidCell/1.4));
				
					liquidCellplus1 = this.liquid.getCellState(iplus,j);
					
					if(liquidCellplus1>95)
						colorcpy.setCellState(iplus,j,JAUNE); //la couleur est donc bien celle de lave
					else if(liquidCellplus1>80 && liquidCellplus1 <= 95)
						colorcpy.setCellState(iplus,j,ORANGE);
					else if(liquidCellplus1>60 && liquidCellplus1 <= 80)
						colorcpy.setCellState(iplus,j,ORANGE);
					else if(liquidCellplus1>40 && liquidCellplus1 <=60)
						colorcpy.setCellState(iplus,j,ORANGEROUGE);
					else if(liquidCellplus1>20 && liquidCellplus1 <=40)
						colorcpy.setCellState(iplus,j,ROUGE);
					else if(liquidCellplus1<=20)
						colorcpy.setCellState(iplus,j,NOIR);
					
	
					if(liquid.getCellState(iplus,j)>liquid.getLiqLim())
						liquid.setCellState(iplus, j, liquid.getLiqLim());
					
					if(liquid.getCellState(i, j)<1)
						liquid.setCellState(i, j, 1);
				}
				
				if(height>=heightmoins1 && Math.random()<pEcoulement)
				{
					CellularAutomataColor colorcpy = this.world.ColorVal;
					
					liquid.setCellState(iplus, j, liquidCellmoins1+liquidCell/2);
					liquid.setCellState(i, j, liquidCell/2);
				
					liquidCellmoins1 = this.liquid.getCellState(imoins,j);
					
					if(liquidCellmoins1>95)
						colorcpy.setCellState(iplus,j,JAUNE); //la couleur est donc bien celle de lave
					
					else if(liquidCellmoins1>80 && liquidCellmoins1 <= 95)
						colorcpy.setCellState(iplus,j,ORANGE);
					
					else if(liquidCellmoins1>60 && liquidCellmoins1 <= 80)
						colorcpy.setCellState(iplus,j,ORANGE);
					
					else if(liquidCellmoins1>40 && liquidCellmoins1 <=60)
						colorcpy.setCellState(iplus,j,ORANGEROUGE);
					
					else if(liquidCellmoins1>20 && liquidCellmoins1 <=40)
						colorcpy.setCellState(iplus,j,ROUGE);
					
					else if(liquidCellmoins1<=20)
						colorcpy.setCellState(iplus,j,NOIR);
					
	
					if(liquid.getCellState(imoins,j)>liquid.getLiqLim())
						liquid.setCellState(imoins, j, liquid.getLiqLim());
					
					if(liquid.getCellState(i, j)<1)
						liquid.setCellState(i, j, 1);
				}
				
				if(height > lavatitude)
				{
					liquid.setCellState(i, j, liquid.getLiqLim());
					world.ColorVal.setCellState(i, j, 1f, 0.7f, 0f);
				}
					
			}
		}
		
		for ( int i = 0 ; i != dx ; i++ ){
    		for ( int j = 0 ; j != dy ; j++ ){
				
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

