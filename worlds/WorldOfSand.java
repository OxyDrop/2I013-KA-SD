// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package worlds;

import DynamicObject.Agent;
import DynamicObject.FAgent;
import DynamicObject.MAgent;
import DynamicObject.Zombie;
import cellularautomata.DesertCA;
import java.util.Iterator;
import javax.media.opengl.GL2;
import objects.Arbres.Palmier;
import objects.Architect.Portail;
import objects.Architect.Teleporteur;
import objects.UniqueObject;

public class WorldOfSand extends World {
	
	private static final int POPINI=80;
    protected DesertCA cellularAutomata;
	
	private static final int NBMAXPORTAILS=3;
	private static final int NBMAXTELEPORTEUR = 5;
	private static final int NOTIFYITERATION = 100; //Used to display messages every number of iteration 
	
	private int xportrand, yportrand;
	private int xteleprand, yteleprand;
	
	private World w1,w2,w3;
	/*
	protected int iteration = 0;
	indexCA;
	protected CellularAutomataInteger cellularAutomata; // TO BE DEFINED IN CHILDREN CLASSES
	protected CellularAutomataDouble cellsHeightValuesCA;
	protected CellularAutomataDouble cellsHeightAmplitudeCA;
	*/
	private int xabrand;
	private int yabrand;
	private double pgrowga =  0.005;
	
	public WorldOfSand(){}
	
	public WorldOfSand(World w1, World w2)
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
				{
					
					//yellow mountain
		        	
		        	color[0] = 0.9f + 0.1f * height / ( (float)this.getMaxEverHeight() );
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
    	/*-----------------AJOUT OBJETS--------------------*/
		 for(int port = 0 ; port <NBMAXPORTAILS ; port++)
		 {
			 do{
				xportrand = (int)(Math.random()*dxCA);
				yportrand = (int)(Math.random()*dyCA);
			 }while(this.getCellHeight(xportrand, yportrand)<=0);
			 
			 if(w1 != null && w2 != null){ 
				if(port==0)
					LObjects.add(new Portail(xportrand,yportrand,this,w1));
				else if(port==1)
					LObjects.add(new Portail(xportrand,yportrand,this,w2));
				else
					LObjects.add(new Portail(xportrand,yportrand,this,w3));
			}
		}
		for (int port = 0; port < NBMAXTELEPORTEUR; port++) {
			do {
				xportrand = (int) (Math.random() * dxCA);
				yportrand = (int) (Math.random() * dyCA);
			} while (this.getCellHeight(xportrand, yportrand) <= 0);

			LObjects.add(new Teleporteur(xportrand, yportrand, (int) (Math.random() * dxCA), (int) (Math.random() * dyCA), this));
		}
		
		for(int i=0;i<POPINI;i++) //AJOUT AGENT ALEATOIREMENT
				agentListe.add(new Agent( (int)(Math.random()*dxCA), (int)(Math.random()*dyCA), this ));
		
		for (int d = 97; d < 147; d++) 
		{
			for (int j = 112; j < 128; j++) 
			{
				cellState = this.getCellValue(d, j);
				if (cellState == 1) 
				{
					if (Math.random() < 0.04) 
					{
						fagent.add(new FAgent(d, j, this));
					}
				}
			}
		}
		for (int v = 0; v < POPINI; v++) 
		{
			int dxRand = 0;
			int dyRand = 0;
			do {
				dxRand = (int) (Math.random() * dxCA);
				dyRand = (int) (Math.random() * dyCA);
			} while (this.getCellHeight(dxRand, dyRand) <= 0); //On s'assure que les agentListes ne soient pas generés sur l'eau

		agentM.add(new MAgent(dxRand, dyRand, this));
		}
		for (int i = 0; i < POPINI/2; i++) 
		{
			int dxRand = 0;
			int dyRand = 0;
			do {
				dxRand = (int) (Math.random() * dxCA);
				dyRand = (int) (Math.random() * dyCA);
			} while (this.getCellHeight(dxRand, dyRand) <= 0); //On s'assure que les agentListes ne soient pas generés sur l'eau

		zombie.add(new Zombie(dxRand, dyRand, this));
		}
		///////////////////AJOUTS ARBRES//////////////////
    	for (int i = 0 ; i < dxCA ; i++)
    		for (int j = 0 ; j < dyCA ; j++)
    		{
    			cellState = this.getCellValue(i, j);
    			if (cellState == 1)
				{
					if(Math.random()<0.9)
					{
						Palmier pa = new Palmier(i,j,this);
						pa.init();
    					LObjects.add(pa);
					}
				}
			}
	 }
	/*---------------------------FIN AJOUT OBJETS--------------------------------------*/
   
    
    protected void initCellularAutomata(int dx, int dy, double[][] landscape)
    {
    	cellularAutomata = new DesertCA(this,dx,dy,HeightVal);
    	cellularAutomata.init();
    }
    
	@Override
    protected void stepCellularAutomata()
    {
    	if ( iteration%10 == 0 )
    		cellularAutomata.step();
		
		for(UniqueObject abr : LObjects) //Met a jour les arbres
			if(abr instanceof Palmier)
				((Palmier)abr).step();
		
		for(Iterator<UniqueObject> it = LObjects.iterator(); it.hasNext();)
		{
			UniqueObject arbre = it.next();
			
			if(arbre instanceof Palmier && ((Palmier) arbre).die())
			{
				it.remove();
				System.out.println("Un palmier est s'est effrité :sob:");
			}
		}
		
		if(Math.random()<pgrowga)
		{
			 do{
				xabrand = (int)(Math.random()*dx);
				yabrand = (int)(Math.random()*dy);
			 }while(this.getCellHeight(xabrand,yabrand)<=0);
			 
			Palmier gagrow = new Palmier(xabrand,yabrand,this);
			gagrow.init();
			LObjects.add(gagrow);
			System.out.println("Un nouveau palmier à poussé en ("+xabrand+","+yabrand+")");
		}
	}
 
    
	@Override
    protected void stepAgents()
    {
		for(MAgent a1 : agentM)
    		a1.step();
    	for (int i = 0; i < this.zombie.size(); i++) {
			this.zombie.get(i).step();
		}
		for (int i = 0; i < this.fagent.size(); i++) {
			this.fagent.get(i).step();
		}
		for (int i = 0; i < this.bebe.size(); i++) {
			this.bebe.get(i).step();
		}
		for (int i = 0; i < this.buisson.size(); i++) {
			this.buisson.get(i).step();
		}
		for (int i = 0; i < this.arbreList.size(); i++) {
			this.arbreList.get(i).step();
		}
		for (int i = 0; i < this.Home.size(); i++) {
			this.Home.get(i).step();
		}
		
		for (Agent a : agentListe)
			a.step();
		
		for(UniqueObject portal : LObjects)
			if(portal instanceof Portail)
			{
				((Portail) portal).passePortail(agentListe);
				((Portail) portal).passePortailM(agentM);
				((Portail) portal).passePortailF(fagent);
				((Portail) portal).passePortailZ(zombie);
			}
			else if(portal instanceof Teleporteur)
			{
				((Teleporteur)portal).passeTeleporteur(agentListe);
				((Teleporteur)portal).passeTeleporteurM(agentM);
				((Teleporteur)portal).passeTeleporteurF(fagent);
				((Teleporteur)portal).passeTeleporteurZ(zombie);
			}
		
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
    public String getNom()
	{
		return "WorldOfSand";
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

	@Override
	public World getW3() {
		return w3;
	}

	@Override
	public void setW3(World w3) {
		this.w3=w3;
	}

}
