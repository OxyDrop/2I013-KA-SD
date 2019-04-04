// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package worlds;

import DynamicObject.Agent;
import cellularautomata.ForestCA;
import javax.media.opengl.GL2;
import objects.*;
import javax.swing.JDialog;

public class WorldOfTrees_1 extends World {
	
	private static final int POPINI=800;
    protected ForestCA cellularAutomata;
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
				{	// snowy mountain
		        	color[0] = 1f;//height / (float)this.getMaxEverHeight();
					color[1] = 1f;//height / (float)this.getMaxEverHeight();
					color[2] = 1f;//height / (float)this.getMaxEverHeight();
					// green mountains
		        	/*
		        	color[0] = height / ( (float)this.getMaxEverHeight() );
					color[1] = 0.9f + 0.1f * height / ( (float)this.getMaxEverHeight() );
					color[2] = height / ( (float)this.getMaxEverHeight() );
					*/
		        }
		        else
		        {	// water
					color[0] = 0.1f;
					color[1] = 0.1f;
					color[2] = 0.5f;
		        }
		        this.ColorVal.setCellState(x, y, color);
    		}
		/*-------------------FIN COULEUR--------------------*/
    	/*-----------------AJOUT OBJETS--------------------*/
    	for ( int i = 0 ; i < 110 ; i++ )
    		if ( i%10 == 0 ){
    			LObjects.add(new Monolith(i*10,110+i,this)); // Colonnes de l arc
			}else{
    			LObjects.add(new BridgeBlock(i*10,110+i,this)); // Pont de l arc
			}
		
		for(int i=0;i<POPINI;i++) //AJOUT AGENT ALEATOIREMENT
				agent.add(new Agent( (int)(Math.random()*dxCA), (int)(Math.random()*dyCA), this ));
		
    	// AJOUT ARBRES ET HERBES
    	for (int i = 0 ; i < dxCA ; i++)
    		for (int j = 0 ; j < dyCA ; j++)
    		{
    			cellState = this.getCellValue(i, j);
    			if (cellState == 1)
    				if (Math.random() < 0.009)
    					LDynamicObjects.add(new GrandArbre(i,j,this));
    				else
						LDynamicObjects.add(new Herbe(i,j,this)); // Creation de l'herbe	
    		}
	/*---------------------------FIN AJOUT OBJETS--------------------------------------*/
    }
    
    protected void initCellularAutomata(int dx, int dy, double[][] landscape)
    {
    	cellularAutomata = new ForestCA(this,dx,dy,HeightVal);
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
		switch ( cellState )
		{
		case 1: 
			Tree.displayObjectAt(_myWorld,gl,cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);
			break;
		case 2:
			Tree.displayObjectAt(_myWorld,gl,cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);
			break;
		case 3:
			Tree.displayObjectAt(_myWorld,gl,cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);
			break;
		default:
			// nothing to display at this location.
		}
	}
	public void displayObject(World _myWorld, GL2 gl, float offset,
							float stepX, float stepY, float lenX, float lenY, 
							float heightFactor, double heightBooster
							)
	{
		
	} 
    

}
