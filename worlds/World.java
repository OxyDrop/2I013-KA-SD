// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package worlds;

import DynamicObject.Agent;
import DynamicObject.UniqueDynamicObject;
import cellularautomata.*;
import java.util.ArrayList;
import javax.media.opengl.GL2;
import objects.*;

public abstract class World {
	
	protected int iteration = 0;

	protected ArrayList<UniqueObject> uniqueObjects = new ArrayList<UniqueObject>();
	protected ArrayList<UniqueDynamicObject> uniqueDynamicObjects = new ArrayList<UniqueDynamicObject>();
	
	protected ArrayList<Agent> agent = new ArrayList<>();
    
	protected int dx;
	protected int dy;

	protected int indexCA;

	//protected CellularAutomataInteger cellularAutomata; // TO BE DEFINED IN CHILDREN CLASSES
    
	protected CellularAutomataDouble cellsHeightValuesCA; //valeur altitude
	protected CellularAutomataDouble cellsHeightAmplitudeCA; //valeur amplitude
	
	public CellularAutomataColor cellsColorValues;

	private double maxEverHeightValue = Double.NEGATIVE_INFINITY;
	private double minEverHeightValue = Double.POSITIVE_INFINITY;

    public World( )
    {
    	// ... cf. init() for initialization
    }
	
   
	
    public void init( int dx, int dy, double[][] landscape )
    {
    	this.dx = dx;
    	this.dy = dy;
    	
    	iteration = 0;

    	this.cellsHeightValuesCA = new CellularAutomataDouble (dx,dy,false);
    	this.cellsHeightAmplitudeCA = new CellularAutomataDouble (dx,dy,false);
    	
    	this.cellsColorValues = new CellularAutomataColor(dx,dy,false);
    	
    	// init altitude and color related information
    	for ( int x = 0 ; x != dx ; x++ )
    		for ( int y = 0 ; y != dy ; y++ )
    		{
    			// compute height values (and amplitude) from the landscape for this CA cell 
                        // Minimum entre (le minimum entre centre-droite et le minimum entrebas-basgauche)
    			double minHeightValue = Math.min(Math.min(landscape[x][y],landscape[x+1][y]),Math.min(landscape[x][y+1],landscape[x+1][y+1]));
    			double maxHeightValue = Math.max(Math.max(landscape[x][y],landscape[x+1][y]),Math.max(landscape[x][y+1],landscape[x+1][y+1])); 
    			
    			if ( this.maxEverHeightValue < maxHeightValue )
    				this.maxEverHeightValue = maxHeightValue;
    			if ( this.minEverHeightValue > minHeightValue )
    				this.minEverHeightValue = minHeightValue;
    			
    			cellsHeightAmplitudeCA.setCellState(x,y,maxHeightValue-minHeightValue);
    			cellsHeightValuesCA.setCellState(x,y,(minHeightValue+maxHeightValue)/2.0);

    			/* TODO! Default coloring
    	    	// init color information
    	        if ( this.cellsHeightAmplitudeCA.getCellState(x,y) >= 0.0 )
    	        {
    				float color[] = { (float)height*4.0f, 1.0f-(float)height*0.3f, (float)height*2.0f };
    				this.cellsColorValues.setCellState(x,y,color);
    	        }
    	        else
    	        {
    	        	// water
    				float color[] = { (float)(-height), 1.0f-(float)(-height)*0.3f, (float)1.0f };
    				this.cellsColorValues.setCellState(x,y,color);
    	        }
    	        */
    		}
    	
    	initCellularAutomata(dx,dy,landscape);

    }
    
    
    public void step()
    {
    	stepCellularAutomata();
    	stepAgents();
    	iteration++;
    }
    
    public int getIteration()
    {
    	return this.iteration;
    }
    
    abstract protected void stepAgents();
    
    // ----

    protected abstract void initCellularAutomata(int __dxCA, int __dyCA, double[][] landscape);
    
    protected abstract void stepCellularAutomata();
    
    // ---
    
    abstract public int getCellValue(int x, int y); // used by the visualization code to call specific object display.

    abstract public void setCellValue(int x, int y, int state);
    
    // ---- 
    
    public double getCellHeight(int x, int y) // used by the visualization code to set correct height values
    {
    	return cellsHeightValuesCA.getCellState(x%dx,y%dy);
    }
    
    // ---- 
    
    public float[] getCellColorValue(int x, int y) // used to display cell color
    {
    	float[] cellColor = this.cellsColorValues.getCellState(x%this.dx , y%this.dy );

    	float[] color  = {cellColor[0],cellColor[1],cellColor[2],1.0f};
        
        return color;
    }
	
	public ArrayList<UniqueDynamicObject> getUniqueDynamicListe()
	{
		return uniqueDynamicObjects;
	}
	public ArrayList<Agent> getAgentListe()
	{
		return agent;
	}
	
	abstract public void displayObjectAt(World _myWorld, GL2 gl, int cellState, int x,
			int y, double height, float offset,
			float stepX, float stepY, float lenX, float lenY,
			float normalizeHeight); 

	public void displayUniqueObjects(World _myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset,
			float stepX, float stepY, float lenX, float lenY, float normalizeHeight) 
	{
    	for ( int i = 0 ; i < uniqueObjects.size(); i++ )
    		uniqueObjects.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
    	for ( int i = 0 ; i < uniqueDynamicObjects.size(); i++ )
    		uniqueDynamicObjects.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
		for ( int i = 0 ; i < agent.size(); i++ )
    		agent.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
	}
    
	public int getWidth() { return dx; }
	public int getHeight() { return dx; }

	public double getMaxEverHeight() { return this.maxEverHeightValue; }
	public double getMinEverHeight() { return this.minEverHeightValue; }
	

}
