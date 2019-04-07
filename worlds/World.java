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

	protected ArrayList<UniqueObject> LObjects = new ArrayList<>();
	protected ArrayList<UniqueDynamicObject> LDynamicObjects = new ArrayList<>();
	
	protected ArrayList<Agent> agent = new ArrayList<>();
    
	protected int dx;
	protected int dy;

	protected int indexCA;

	//protected CellularAutomataInteger cellularAutomata; // TO BE DEFINED IN CHILDREN CLASSES
    
	protected CellularAutomataDouble HeightVal; //valeur altitude
	protected CellularAutomataDouble HeightAmpli; //valeur amplitude
	
	public CellularAutomataColor ColorVal;

	private double maxEver = Double.NEGATIVE_INFINITY;
	private double minEver = Double.POSITIVE_INFINITY;
	
	protected World w1,w2;

    public World( )
    {
    	// ... cf. init() for initialization
    }
	
	public World(World w1, World w2)
	{
		this.w1=w1;
		this.w2=w2;
	}
	
   
	//Initialise le monde à partir des dimensions et d'une matrice de double
    public void init( int dx, int dy, double[][] landscape )
    { 
    	this.dx = dx;
    	this.dy = dy;
    	
    	iteration = 0;

    	this.HeightVal = new CellularAutomataDouble (dx,dy,false);
    	this.HeightAmpli = new CellularAutomataDouble (dx,dy,false);
    	
    	this.ColorVal = new CellularAutomataColor(dx,dy,false);
    	
    	// init altitude and color related information
    	for ( int x = 0 ; x != dx ; x++ )
    		for ( int y = 0 ; y != dy ; y++ )
    		{
						/*	xo
							oo		*/
				// compute height values (and amplitude) from the landscape for this CA cell 
				 // Minimum entre (le minimum entre centre-droite et le minimum entre bas-basdroite)
    			double minHeightValue = Math.min(Math.min(landscape[x][y],landscape[x+1][y]),Math.min(landscape[x][y+1],landscape[x+1][y+1]));
    			double maxHeightValue = Math.max(Math.max(landscape[x][y],landscape[x+1][y]),Math.max(landscape[x][y+1],landscape[x+1][y+1])); 
						//Maximum entre (le maximum entre centre-droite et le maximum entre bas-basdroite)
    			
    			if ( this.maxEver < maxHeightValue )
    				this.maxEver = maxHeightValue; //nouveau maximum
    			if ( this.minEver > minHeightValue )
    				this.minEver = minHeightValue; //nouveau minimum
    			
    			HeightAmpli.setCellState(x,y,maxHeightValue-minHeightValue); //Initialisation Amplitude
    			HeightVal.setCellState(x,y,(minHeightValue+maxHeightValue)/2.0); //Initialisation Valeur altitude

    			// TODO! Default coloring
    	    	// init color information
				float val = (float)HeightAmpli.getCellState(x, y);
    	        if ( val >= 0.0 )
    	        {
    				float color[] = { val*4f, 1f-val*0.3f, val*2f };
    				this.ColorVal.setCellState(x,y,color);
    	        }
    	        else
    	        {
    	        	// water
    				float color[] = { val , 1f-val*0.3f, 1f };
    				this.ColorVal.setCellState(x,y,color);
    	        }
    	        
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
    	return HeightVal.getCellState(x%dx,y%dy);
    }
    
    // ---- 
    
    public float[] getCellColorValue(int x, int y) // used to display cell color
    {
    	float[] cellColor = this.ColorVal.getCellState(x%this.dx , y%this.dy );

    	float[] color  = {cellColor[0],cellColor[1],cellColor[2],1.0f};
        
        return color;
    }
	
	public ArrayList<UniqueDynamicObject> getUniqueDynamicListe()
	{
		return LDynamicObjects;
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
    	for ( int i = 0 ; i < LObjects.size(); i++ )
    		LObjects.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
    	for ( int i = 0 ; i < LDynamicObjects.size(); i++ )
    		LDynamicObjects.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
		for ( int i = 0 ; i < agent.size(); i++ )
    		agent.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
	}
    
	public int getWidth() { return dx; }
	public int getHeight() { return dx; }

	public double getMaxEverHeight() { return this.maxEver; }
	public double getMinEverHeight() { return this.minEver; }
	abstract public String getNom();
	abstract public World getw1();
	abstract public World getw2();

	abstract public void setW2(World w2);

	abstract public void setW1(World w1);

	public ArrayList<UniqueObject> getLObjects() {
		return LObjects;
	}

	public ArrayList<UniqueDynamicObject> getLDynamicObjects() {
		return LDynamicObjects;
	}
	
	
	
}
