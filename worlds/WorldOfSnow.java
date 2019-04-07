// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package worlds;

import DynamicObject.Agent;
import cellularautomata.SnowyCA;
import javax.media.opengl.GL2;
import objects.Arbres.Sapin;

public class WorldOfSnow extends World {
	
	private static final int POPINI=100;
    protected SnowyCA cellularAutomata;
	/*
	protected int iteration = 0;
	indexCA;
	protected CellularAutomataInteger cellularAutomata; // TO BE DEFINED IN CHILDREN CLASSES
	protected CellularAutomataDouble cellsHeightValuesCA;
	protected CellularAutomataDouble cellsHeightAmplitudeCA;
	*/
	
    public void init ( int dxCA, int dyCA, double[][] landscape )
    {
    	super.init(dxCA, dyCA, landscape);
    	int cellState;
    	/*-----------------COULEURS-----------------------------*/
    	for ( int x = 0 ; x < dxCA ; x++ )
    		for ( int y = 0 ; y < dyCA ; y++ )
    		{
	        	float color[] = new float[3];

	        	float height = (float) this.getCellHeight(x, y);
		    	
		        if ( height >= 0 )
				{	
					//snowy mountain
		        	color[0] = 0.9f;//height / (float)this.getMaxEverHeight();
					color[1] = 0.99f;//height / (float)this.getMaxEverHeight();
					color[2] = 1f;//height / (float)this.getMaxEverHeight();
					
		        }
		        else
		        {	// water
					color[0] = 0.2f;
					color[1] = 0.2f;
					color[2] = 1f;
		        }
		        this.ColorVal.setCellState(x, y, color);
    		}
		/*-------------------FIN COULEUR--------------------*/
    	/*-----------------AJOUT OBJETS--------------------*/
		
		for(int i=0;i<POPINI;i++) //AJOUT AGENT ALEATOIREMENT
				agent.add(new Agent( (int)(Math.random()*dxCA), (int)(Math.random()*dyCA), this ));
		
    	for (int i = 0 ; i < dxCA ; i++)
    		for (int j = 0 ; j < dyCA ; j++)
    		{
    			cellState = this.getCellValue(i, j);
    			if (cellState == 1 && Math.random()<0.7)
    				LObjects.add(new Sapin(i,j,this));
    		}
	/*---------------------------FIN AJOUT OBJETS--------------------------------------*/
    }
    
    protected void initCellularAutomata(int dx, int dy, double[][] landscape)
    {
    	cellularAutomata = new SnowyCA(this,dx,dy,HeightVal);
    	cellularAutomata.init();
    }
    
	@Override
    protected void stepCellularAutomata()
    {
    	if ( iteration%10 == 0 )
    		cellularAutomata.step();
    }
    
	@Override
    protected void stepAgents()
    {
    	// nothing to do.
    	for ( int i = 0 ; i < this.agent.size() ; i++ )
    	{
    		this.agent.get(i).step();
    	}
		if(iteration%30==0)
			System.out.println("Nombre agent = "+agent.size());
    }

    public int getCellValue(int x, int y) // used by the visualization code to call specific object display.
    {
    	return cellularAutomata.getCellState(x%dx,y%dy);
    }

    public void setCellValue(int x, int y, int state)
    {
    	cellularAutomata.setCellState( x%dx, y%dy, state);
    }
    
	public void displayObjectAt(World _myWorld, GL2 gl, int cellState, int x,int y, double height, float offset,
			float stepX, float stepY, float lenX, float lenY,
			float normalizeHeight) 
	{
		
	}
	public void displayObject(World _myWorld, GL2 gl, float offset,
							float stepX, float stepY, float lenX, float lenY, 
							float heightFactor, double heightBooster
							)
	{
		
	} 
    

}
