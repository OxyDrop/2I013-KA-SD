// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package worlds;

import DynamicObject.Agent;
import cellularautomata.ForestCA;
import javax.media.opengl.GL2;
import objects.Arbres.GrandArbre;
import objects.Arbres.Tree;
import objects.Architect.Portail;
import objects.Architect.Teleporteur;
import objects.Consommables.Herbe;
import objects.UniqueObject;

public class WorldOfTrees extends World {
	
	private static final int POPINI=800;
    protected ForestCA cellularAutomata;
	
	private static final int NBMAXPORTAILS=2;
	private static final int NBMAXTELEPORTEUR = 5;
	private static final int NOTIFYITERATION = 100; //Used to display messages every number of iteration 
	
	private int xportrand, yportrand;
	private int xteleprand, yteleprand;
	World w1,w2;
	/*
	protected int iteration = 0;
	indexCA;
	
	TO BE DEFINED IN CHILDREN CLASSES
	protected CellularAutomataDouble HeightVal; //valeur altitude
	protected CellularAutomataDouble HeightAmpli; //valeur amplitude
	public CellularAutomataColor ColorVal;
	*/
	
	public WorldOfTrees(){}
	
	public WorldOfTrees(World w1, World w2)
	{
		this.w1=w1;
		this.w2=w2;
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
				{	/* snowy mountain
		        	color[0] = height / (float)this.getMaxEverHeight();
					color[1] = height / (float)this.getMaxEverHeight();
					color[2] = height / (float)this.getMaxEverHeight();
					*/
					// green mountains
		        	
		        	color[0] = height / ( (float)this.getMaxEverHeight() );
					color[1] = 0.9f + 0.1f * height / ( (float)this.getMaxEverHeight() );
					color[2] = height / ( (float)this.getMaxEverHeight() );
					//
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
    	/*-----------------AJOUT OBJETS EN BOUCLE--------------------*/
		 for (int i = 0 ; i < dxCA ; i++){
    		for (int j = 0 ; j < dyCA ; j++)
    		{
    			cellState = this.getCellValue(i, j);
				
    			if (cellState == 1)
				{
    				if (Math.random() < 0.009)
					{
						GrandArbre ga = new GrandArbre(i,j,this);
						ga.init();
    					LObjects.add(ga);
					}
					else if(Math.random()<0.009)
						LObjects.add(new Herbe(i,j,this)); // Creation de l'herbe
					
					
				}
			}
		 }
		 for(int port = 0 ; port <NBMAXPORTAILS ; port++)
		 {
			 do{
				xportrand = (int)(Math.random()*dxCA);
				yportrand = (int)(Math.random()*dyCA);
			 }while(this.getCellHeight(xportrand, yportrand)<=0);
			 
			if(w1 != null && w2 != null)
			{ 
			 if(port==0)
				LObjects.add(new Portail(xportrand,yportrand,this,w1));
			 else
				LObjects.add(new Portail(xportrand,yportrand,this,w2));
			}
		 }
		 
		 for(int port = 0 ; port <NBMAXTELEPORTEUR ; port++)
		 {
			 do{
				xportrand = (int)(Math.random()*dxCA);
				yportrand = (int)(Math.random()*dyCA);
			 }while(this.getCellHeight(xportrand, yportrand)<=0);
	
			LObjects.add(new Teleporteur(xportrand,yportrand,(int)(Math.random()*dxCA),(int)(Math.random()*dyCA),this));
		}
		/*------------------AJOUTS AGENTS ----------------------*/
		for(int i=0;i<POPINI;i++){
			int dxRand=0;
			int dyRand=0;
			do{
				dxRand = (int)(Math.random()*dxCA);
				dyRand = (int)(Math.random()*dyCA);
			}while(this.getCellHeight(dxRand, dyRand)<=0); //On s'assure que les agents ne soient pas generés sur l'eau
			
			agent.add(new Agent(dxRand, dyRand, this ));
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
		
		for(UniqueObject abr : LObjects) //Met a jour les arbres
			if(abr instanceof GrandArbre)
				((GrandArbre)abr).step();
    }
    
	@Override
    protected void stepAgents()
    {
    	// nothing to do.
    	for (Agent a : agent)
			a.step();
		
		for(UniqueObject portal : LObjects)
			if(portal instanceof Portail)
				((Portail) portal).passePortail(agent);
			else if(portal instanceof Teleporteur)
				((Teleporteur)portal).passeTeleporteur(agent);
		
		if(iteration%NOTIFYITERATION==0)
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
			//Tree.displayObjectAt(_myWorld,gl,cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);
			break;
		default:
		}
	}
	public void displayObject(World _myWorld, GL2 gl, float offset,
							float stepX, float stepY, float lenX, float lenY, 
							float heightFactor, double heightBooster
							)
	{
		
	} 
     public String getNom()
	{
		return "WorldOfTrees";
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

}
