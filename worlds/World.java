// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package worlds;

import DynamicObject.Agent;
import DynamicObject.BebeAgent;
import DynamicObject.FAgent;
import DynamicObject.MAgent;
import DynamicObject.UniqueDynamicObject;
import DynamicObject.Zombie;
import DynamicObject.buisson;
import Periode.*;
import cellularautomata.*;
import java.util.ArrayList;
import javax.media.opengl.GL2;
import objects.*;
import objects.Arbres.GrandArbre;
import objects.Consommables.Aliment;

public abstract class World {
	
	protected int iteration = 0;

	protected ArrayList<UniqueObject> LObjects = new ArrayList<>();
	protected ArrayList<UniqueDynamicObject> LDynamicObjects = new ArrayList<>();
	protected ArrayList<Aliment> alimentListe = new ArrayList<>();
	protected ArrayList<Agent> agentListe = new ArrayList<>();
	protected ArrayList<DynamicObject.Home> Home = new ArrayList<>();
	
	protected ArrayList<MAgent> agentM = new ArrayList<>();
	protected ArrayList<Zombie> zombie = new ArrayList<>();
	protected ArrayList<BebeAgent> bebe = new ArrayList<>();
	protected ArrayList<FAgent> fagent = new ArrayList<>();
	protected ArrayList<DynamicObject.buisson> buisson = new ArrayList<>();
	protected ArrayList<GrandArbre> arbreList = new ArrayList<>();

	protected int dx;
	protected int dy;

	protected int indexCA;

	//protected CellularAutomataInteger cellularAutomata; // TO BE DEFINED IN CHILDREN CLASSES
    
	protected CellularAutomataDouble HeightVal; //valeur altitude
	protected CellularAutomataDouble HeightAmpli; //valeur amplitude
	
	public CellularAutomataColor ColorVal;

	private double maxEver = Double.NEGATIVE_INFINITY;
	private double minEver = Double.POSITIVE_INFINITY;
	
	protected World w1,w2,w3;
	protected static Mois mois = new Mois();
	protected static Annee annee = new Annee();
	protected static int passetemps=0;
	protected static int histoire = 0;
	protected static boolean unefois = true;
	protected static boolean checkunefois = false;
	protected static final int LIMITE = 12000;

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
		
		passetemps++;
		passetemps%=LIMITE;
		
		if(passetemps==0)
		{
			System.out.println("L'année"+histoire+" commence !");
			histoire++;
		}
		else if (passetemps >= 0 && passetemps < LIMITE / 12 && unefois) {
			System.out.println("Le mois de janvier commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 12 && passetemps < LIMITE / 11 && unefois) {
			System.out.println("Le mois de février commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 11 && passetemps < LIMITE / 10 && unefois) {
			System.out.println("Le mois de mars commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 10 && passetemps < LIMITE / 9 && unefois) {
			System.out.println("Le mois de avril commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 9 && passetemps < LIMITE / 8 && unefois) {
			System.out.println("Le mois de mai commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 8 && passetemps < LIMITE / 7 && unefois) {
			System.out.println("Le mois de juin commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 7 && passetemps < LIMITE / 6 && unefois) {
			System.out.println("Le mois de juillet commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 6 && passetemps < LIMITE / 5 && unefois) {
			System.out.println("Le mois de août commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 5 && passetemps < LIMITE / 4 && unefois) {
			System.out.println("Le mois de septembre commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 4 && passetemps < LIMITE / 3 && unefois) {
			System.out.println("Le mois de octobre commence !");
			unefois=false;
		} else if (passetemps >= LIMITE / 3 && passetemps < LIMITE / 2 && unefois) {
			System.out.println("Le mois de novembre commence !");
			unefois=false;
		} else if(passetemps >= LIMITE / 2 && passetemps < LIMITE && unefois){
			System.out.println("Le mois de decembre commence !");
			unefois=false;
		}
		if(passetemps%(LIMITE/12)==0)
			unefois=true;
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
	
	public ArrayList<UniqueObject> getLObjects() {
		return LObjects;
	}
	
	public ArrayList<Aliment> getAlimentListe(){
		return alimentListe;
	}
	
	public ArrayList<Agent> getAgentListe()
	{
		return this.agentListe;
	}
	public ArrayList<MAgent> getMAgentListe(){return agentM;}
	public ArrayList<BebeAgent> getbebeAgentListe(){return bebe;}
	public ArrayList<FAgent> getFAgentListe(){return fagent;}
	public ArrayList<Zombie> getZombieListe(){return zombie;}
	public ArrayList<DynamicObject.buisson> getBuissonListe(){return buisson;}
	public ArrayList<GrandArbre> getArbreListe(){return arbreList;}
	public ArrayList<DynamicObject.Home> getHome(){return Home;}


	
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
		
		for ( int i = 0 ; i < agentListe.size(); i++ )
    		agentListe.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
		for ( int i = 0 ; i < alimentListe.size(); i++ )
			alimentListe.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
	
		for ( int i = 0 ; i < zombie.size(); i++ )
    		zombie.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
			
		for ( int i = 0 ; i < agentM.size(); i++ )
    		agentM.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
		for ( int i = 0 ; i < bebe.size(); i++ )
    		bebe.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
		for ( int i = 0 ; i < fagent.size(); i++ )
    		fagent.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
		for ( int i = 0 ; i < buisson.size(); i++ )
    		buisson.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
		for ( int i = 0 ; i < arbreList.size(); i++ )
    		arbreList.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
		
		for ( int i = 0 ; i < Home.size(); i++ )
    		Home.get(i).displayUniqueObject(_myWorld,gl,offsetCA_x,offsetCA_y,offset,stepX,stepY,lenX,lenY,normalizeHeight);
	
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

	public ArrayList<UniqueDynamicObject> getLDynamicObjects() {
		return LDynamicObjects;
	}

	public void setLDynamicObjects(ArrayList<UniqueDynamicObject> LDynamicObjects) {
		this.LDynamicObjects = LDynamicObjects;
	}

	public ArrayList<MAgent> getAgentM() {
		return agentM;
	}

	public void setAgentM(ArrayList<MAgent> agentM) {
		this.agentM = agentM;
	}

	public ArrayList<Zombie> getZombie() {
		return zombie;
	}

	public void setZombie(ArrayList<Zombie> zombie) {
		this.zombie = zombie;
	}

	public ArrayList<BebeAgent> getBebe() {
		return bebe;
	}

	public void setBebe(ArrayList<BebeAgent> bebe) {
		this.bebe = bebe;
	}

	public ArrayList<FAgent> getFagent() {
		return fagent;
	}

	public void setFagent(ArrayList<FAgent> fagent) {
		this.fagent = fagent;
	}

	public ArrayList<buisson> getBuisson() {
		return buisson;
	}

	public void setBuisson(ArrayList<buisson> buisson) {
		this.buisson = buisson;
	}

	public ArrayList<GrandArbre> getArbreList() {
		return arbreList;
	}

	public void setArbreList(ArrayList<GrandArbre> arbreList) {
		this.arbreList = arbreList;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public int getIndexCA() {
		return indexCA;
	}

	public void setIndexCA(int indexCA) {
		this.indexCA = indexCA;
	}

	public CellularAutomataDouble getHeightVal() {
		return HeightVal;
	}

	public void setHeightVal(CellularAutomataDouble HeightVal) {
		this.HeightVal = HeightVal;
	}

	public CellularAutomataDouble getHeightAmpli() {
		return HeightAmpli;
	}

	public void setHeightAmpli(CellularAutomataDouble HeightAmpli) {
		this.HeightAmpli = HeightAmpli;
	}

	public CellularAutomataColor getColorVal() {
		return ColorVal;
	}

	public void setColorVal(CellularAutomataColor ColorVal) {
		this.ColorVal = ColorVal;
	}

	public double getMaxEver() {
		return maxEver;
	}

	public void setMaxEver(double maxEver) {
		this.maxEver = maxEver;
	}

	public double getMinEver() {
		return minEver;
	}

	public void setMinEver(double minEver) {
		this.minEver = minEver;
	}

	abstract public World getW3();

	abstract public void setW3(World w3);

	

	
	
	
}
