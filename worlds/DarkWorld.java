// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package worlds;

import DynamicObject.Agent;
import cellularautomata.DarkCA;
import java.util.Iterator;
import javax.media.opengl.GL2;
import objects.Arbres.DarkArbre;
import objects.Arbres.GrandArbre;
import objects.Arbres.Sapin;
import objects.Architect.Portail;
import objects.Architect.Teleporteur;
import objects.UniqueObject;

//Si vous vous retrouvez dans le dark world, il n'y a plus de retour possible!!!!!!!!!!!!/////////////
public class DarkWorld extends World {

	private static final int POPINI=50;
	private static final int NBMAXPORTAILS=2;
	private static final int NBMAXTELEPORTEUR = 5;
	private static final int NOTIFYITERATION = 100; 
	private double pgrowga = 0.005;
	
	private int xportrand, yportrand;
	private int xteleprand, yteleprand;
	private int xabrand, yabrand;
	protected DarkCA cellularAutomata;
	private boolean switchabrsapin=false;
	private World w1,w2,w3;
	
	//Used to display messages every number of iteration 
	
	/*
	protected int iteration = 0;
	indexCA;
	protected CellularAutomataInteger cellularAutomata; // TO BE DEFINED IN CHILDREN CLASSES
	protected CellularAutomataDouble cellsHeightValuesCA;
	protected CellularAutomataDouble cellsHeightAmplitudeCA;
	*/
	public DarkWorld(){}
	
	public DarkWorld(World w1, World w2, World w3)
	{
		this.w1=w1;
		this.w2=w2;
		this.w3=w3;
	}
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
		        	color[0] = 0.1f;//height / (float)this.getMaxEverHeight();
					color[1] = 0f;//height / (float)this.getMaxEverHeight();
					color[2] = 0f;//height / (float)this.getMaxEverHeight();
					
		        }
		        else
		        {	//lava
					color[0] = 1f;
					color[1] = 0.5f;
					color[2] = 0f;
		        }
		        this.ColorVal.setCellState(x, y, color);
    		}
		/*-------------------FIN COULEUR--------------------*/
    	/*-----------------AJOUT OBJETS--------------------*/
		 
		for (int port = 0; port < NBMAXTELEPORTEUR; port++) {
			do {
				xportrand = (int) (Math.random() * dxCA);
				yportrand = (int) (Math.random() * dyCA);
			} while (this.getCellHeight(xportrand, yportrand) <= 0);

			LObjects.add(new Teleporteur(xportrand, yportrand, (int) (Math.random() * dxCA), (int) (Math.random() * dyCA), this));
		}
		///////////////////AJOUT AGENT ALEATOIREMENT////////////////////
		for(int i=0;i<POPINI;i++) 
				agentListe.add(new Agent( (int)(Math.random()*dxCA), (int)(Math.random()*dyCA), this ));
		////////////////////////ARBRES//////////////////////////////////
		
    	for (int i = 0 ; i < dxCA ; i++)
    		for (int j = 0 ; j < dyCA ; j++)
    		{
    			cellState = this.getCellValue(i, j);
    			if (cellState == 1)
				{
					if(Math.random()<0.05){
						DarkArbre da = new DarkArbre(i,j,this);
						da.init();
    					LObjects.add(da);
				}
    		}
	/*---------------------------FIN AJOUT OBJETS--------------------------------------*/
    }
	}
    
    protected void initCellularAutomata(int dx, int dy, double[][] landscape)
    {
    	cellularAutomata = new DarkCA(this,dx,dy,HeightVal);
    	cellularAutomata.init();
    }
    
	@Override
    protected void stepCellularAutomata()
    {
    	if ( iteration%10 == 0 )
    		cellularAutomata.step();
		for(UniqueObject abr : LObjects) //Met a jour les arbres
			if(abr instanceof DarkArbre)
				((DarkArbre)abr).step();
		
		for(Iterator<UniqueObject> it = LObjects.iterator(); it.hasNext();)
		{
			UniqueObject arbre = it.next();
			
			if(arbre instanceof DarkArbre && ((DarkArbre) arbre).die()){
				it.remove();
				System.out.println("Un arbre du mal n'est plus");
			}
		}
		
		if(Math.random()<pgrowga)
		{
			 do{
				xabrand = (int)(Math.random()*dx);
				yabrand = (int)(Math.random()*dy);
			 }while(this.getCellHeight(xabrand,yabrand)<=0);
			 
			DarkArbre gagrow = new DarkArbre(xabrand,yabrand,this);
			gagrow.init();
			LObjects.add(gagrow);
			System.out.println("Un arbre des tenebres à pris racine en ("+xabrand+","+yabrand+")");
		}
    }
    
	@Override
    protected void stepAgents()
    {
    	for (Agent a : agentListe)
			a.step();
		
		for(UniqueObject portal : LObjects)
			if(portal instanceof Portail)
				((Portail) portal).passePortail(agentListe);
			else if(portal instanceof Teleporteur)
				((Teleporteur)portal).passeTeleporteur(agentListe);
		
		if(iteration%NOTIFYITERATION==0)
			System.out.println("Nombre agent = "+agentListe.size());
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
	
	public int getNombreGA(){ //retourne le nombre de grand arbres
		int com=0;
		for(UniqueObject abr : LObjects)
			if(abr instanceof GrandArbre)
				com++;
		return com;
	}
	
	public int getNombreSapin(){ //retourne le nombre de sapins
		int com=0;
		for(UniqueObject abr : LObjects)
			if(abr instanceof Sapin)
				com++;
		return com;
	}
	 public String getNom()
	{
		return "DarkWorld";
	}

	@Override
	public World getw1()
	{
		return w1;
	}

	@Override
	public World getw2() 
	{
		return w2;
	}

	@Override
	public void setW2(World w2) 
	{
		this.w2=w2;
	}

	@Override
	public void setW1(World w1) 
	{
		this.w1=w1;
	}

	public World getW3() {
		return w3;
	}

	public void setW3(World w3) {
		this.w3 = w3;
	}
	
    

}

