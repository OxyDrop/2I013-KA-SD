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
	private final static double TAUXTRANSFERT = 1.6;
	
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
		///////GAUCHE A DROITE ////////////////////
    	for ( int i = 0 ; i != dx ; i++ ){
    		for ( int j = 0 ; j != dy ; j++ )
    		{
				int iplus = (i+1+dx)%dx;
				int jplus = (j+1+dy)%dy;
	
				int current = this.getCellState(i, j);
				int currentiplus1 = this.getCellState(iplus, j);
				int currentjplus1 = this.getCellState(i,jplus);
				
				double height = world.getCellHeight(i, j);
				double heightiplus1 = world.getCellHeight(iplus,j);
				double heightjplus1 = world.getCellHeight(i,jplus);
				
				int liquidCell = this.liquid.getCellState(i, j);
				int liquidcelliplus1 = this.liquid.getCellState(iplus,j);
				int liquidcelljplus1 = this.liquid.getCellState(i,jplus);
			
				CellularAutomataColor colorcpy = this.world.ColorVal;
				
				if(height>=heightiplus1 && Math.random()<pEcoulement) //Si altitude superieure
				{					
					liquid.setCellState(iplus, j, (int) (liquidcelliplus1+liquidCell/TAUXTRANSFERT));
					liquid.setCellState(i, j, (int)(liquidCell/TAUXTRANSFERT));		
	
					if(liquid.getCellState(iplus,j)>liquid.getLiqLim())
						liquid.setCellState(iplus, j, liquid.getLiqLim());
					
					if(liquid.getCellState(i, j)<1)
						liquid.setCellState(i, j, 1);
				}
				
				if(height>=heightjplus1 && Math.random()<pEcoulement) //Si altitude superieure
				{					
					liquid.setCellState(i, jplus, (int) (liquidcelljplus1+liquidCell/TAUXTRANSFERT));
					liquid.setCellState(i, j, (int)(liquidCell/TAUXTRANSFERT));		
	
					if(liquid.getCellState(i,jplus)>liquid.getLiqLim())
						liquid.setCellState(i, jplus, liquid.getLiqLim());
					
					if(liquid.getCellState(i, j)<1)
						liquid.setCellState(i, j, 1);
				}
				
				
				/////////////////RECOLORISATION/////////////////////////
					liquidcelliplus1 = this.liquid.getCellState(iplus,j);
					liquidcelljplus1 = this.liquid.getCellState(i,jplus);
					
					if(liquidcelliplus1>95)
						colorcpy.setCellState(iplus,j,JAUNE); //la couleur est donc bien celle de lave
					
					else if(liquidcelliplus1>80 && liquidcelliplus1 <= 95)
						colorcpy.setCellState(iplus,j,ORANGEJAUNE);
					
					else if(liquidcelliplus1>60 && liquidcelliplus1 <= 80)
						colorcpy.setCellState(iplus,j,ORANGE);
					
					else if(liquidcelliplus1>40 && liquidcelliplus1 <=60)
						colorcpy.setCellState(iplus,j,ORANGEROUGE);
					
					else if(liquidcelliplus1>20 && liquidcelliplus1 <=40)
						colorcpy.setCellState(iplus,j,ROUGE);
					
					else if(liquidcelliplus1<=20)
						colorcpy.setCellState(iplus,j,NOIR);
					
					
					if(liquidcelljplus1>95)
						colorcpy.setCellState(i,jplus,JAUNE); //la couleur est donc bien celle de lave
					
					else if(liquidcelljplus1>80 && liquidcelljplus1 <= 95)
						colorcpy.setCellState(i,jplus,ORANGEJAUNE);
					
					else if(liquidcelljplus1>60 && liquidcelljplus1 <= 80)
						colorcpy.setCellState(i,jplus,ORANGE);
					
					else if(liquidcelljplus1>40 && liquidcelljplus1 <=60)
						colorcpy.setCellState(i,jplus,ORANGEROUGE);
					
					else if(liquidcelljplus1>20 && liquidcelljplus1 <=40)
						colorcpy.setCellState(i,jplus,ROUGE);
					
					else if(liquidcelljplus1<=20)
						colorcpy.setCellState(i,jplus,NOIR);
					
				if(height > lavatitude)
				{
					liquid.setCellState(i, j, liquid.getLiqLim());
					world.ColorVal.setCellState(i, j, 1f, 0.7f, 0f);
				}		
			}	
		} //FIN FOR
			//DROITE A GAUCHE//////////
			for ( int i = dx-1 ; i != 0 ; i-- ){
				for ( int j = dy-1 ; j != 0 ; j-- )
				{
					int imoins = (i - 1 + dx) % dx;
					int jmoins = (j - 1 + dy) % dy;

					int current = this.getCellState(i, j);
					int currentimoins1 = this.getCellState(imoins, j);
					int currentjmoins1 = this.getCellState(i, jmoins);

					double height = world.getCellHeight(i, j);
					double heightimoins1 = world.getCellHeight(imoins, j);
					double heightjmoins1 = world.getCellHeight(i, jmoins);

					int liquidCell = this.liquid.getCellState(i, j);
					int liquidCellimoins1 = this.liquid.getCellState(imoins, j);
					int liquidCelljmoins1 = this.liquid.getCellState(i, jmoins);
			
					CellularAutomataColor colorcpy = this.world.ColorVal;

					if(height>=heightimoins1 && Math.random()<pEcoulement) //Si altitude superieure
				{					
					liquid.setCellState(imoins, j, (int) (liquidCellimoins1+liquidCell/TAUXTRANSFERT));
					liquid.setCellState(i, j, (int)(liquidCell/3));		
	
					if(liquid.getCellState(imoins,j)>liquid.getLiqLim())
						liquid.setCellState(imoins, j, liquid.getLiqLim());
					
					if(liquid.getCellState(i, j)<1)
						liquid.setCellState(i, j, 1);
				}
				
				if(height>=heightjmoins1 && Math.random()<pEcoulement) //Si altitude superieure
				{					
					liquid.setCellState(i, jmoins, (int) (liquidCelljmoins1+liquidCell/TAUXTRANSFERT));
					liquid.setCellState(i, j, (int)(liquidCell/TAUXTRANSFERT));		
	
					if(liquid.getCellState(i,jmoins)>liquid.getLiqLim())
						liquid.setCellState(i, jmoins, liquid.getLiqLim());
					
					if(liquid.getCellState(i, j)<1)
						liquid.setCellState(i, j, 1);
				}
				
				
				/////////////////RECOLORISATION/////////////////////////
					liquidCellimoins1 = this.liquid.getCellState(imoins,j);
					liquidCelljmoins1 = this.liquid.getCellState(i,jmoins);
					
					if(liquidCellimoins1>95)
						colorcpy.setCellState(imoins,j,JAUNE); //la couleur est donc bien celle de lave
					
					else if(liquidCellimoins1>80 && liquidCellimoins1 <= 95)
						colorcpy.setCellState(imoins,j,ORANGEJAUNE);
					
					else if(liquidCellimoins1>60 && liquidCellimoins1 <= 80)
						colorcpy.setCellState(imoins,j,ORANGE);
					
					else if(liquidCellimoins1>40 && liquidCellimoins1 <=60)
						colorcpy.setCellState(imoins,j,ORANGEROUGE);
					
					else if(liquidCellimoins1>20 && liquidCellimoins1 <=40)
						colorcpy.setCellState(imoins,j,ROUGE);
					
					else if(liquidCellimoins1<=20)
						colorcpy.setCellState(imoins,j,NOIR);
					
					
					if(liquidCelljmoins1>95)
						colorcpy.setCellState(i,jmoins,JAUNE); //la couleur est donc bien celle de lave
					
					else if(liquidCelljmoins1>80 && liquidCelljmoins1 <= 95)
						colorcpy.setCellState(i,jmoins,ORANGEJAUNE);
					
					else if(liquidCelljmoins1>60 && liquidCelljmoins1 <= 80)
						colorcpy.setCellState(i,jmoins,ORANGE);
					
					else if(liquidCelljmoins1>40 && liquidCelljmoins1 <=60)
						colorcpy.setCellState(i,jmoins,ORANGEROUGE);
					
					else if(liquidCelljmoins1>20 && liquidCelljmoins1 <=40)
						colorcpy.setCellState(i,jmoins,ROUGE);
					
					else if(liquidCelljmoins1<=20)
						colorcpy.setCellState(i,jmoins,NOIR);
					
					if(height > lavatitude)
					{
						liquid.setCellState(i, j, liquid.getLiqLim());
						world.ColorVal.setCellState(i, j, 1f, 0.7f, 0f);
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

