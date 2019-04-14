// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package worlds;

import DynamicObject.*;
import cellularautomata.ForestCA;
import java.util.Iterator;
import javax.media.opengl.GL2;
import objects.Arbres.*;
import objects.Architect.*;
import objects.Consommables.Herbe;
import objects.UniqueObject;

public class WorldOfTrees extends World {
	
	
    
	private static final int POPINI=80;
	private static final int NBMAXPORTAILS=3;
	private static final int NBMAXTELEPORTEUR = 5;
	private static final int NOTIFYITERATION = 100; //Used to display messages every number of iteration 
	private  double pgrowga = 0.005;
	
	protected ForestCA cellularAutomata;
	private int xabrand, yabrand;
	private int xportrand, yportrand;
	private int xteleprand, yteleprand;
	private boolean switchabrsapin=false;
	private World w1,w2,w3;
	/*
	protected int iteration = 0;
	indexCA;
	
	TO BE DEFINED IN CHILDREN CLASSES
	protected CellularAutomataDouble HeightVal; //valeur altitude
	protected CellularAutomataDouble HeightAmpli; //valeur amplitude
	public CellularAutomataColor ColorVal;
	*/
	
	public WorldOfTrees(){}
	
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
					else if (Math.random() < 0.009)
    				{
    						DynamicObject.buisson b = new DynamicObject.buisson(i,j,this);
        					buisson.add(b);
    				}
					else if(Math.random()<0.0005)
					{
						Sapin sa = new Sapin(i,j,this);
						sa.init();
    					LObjects.add(sa);
					}
					else if(Math.random()<0.009)
						LObjects.add(new Herbe(i,j,this)); // Creation de l'herbe
				}
			}
		 }
		  /////////////////CONSTRUCTION//////////////////////
		  
		 Home.add(new Home(115,122,this));
		 LObjects.add(new Mur4(134, 120, this));
         LObjects.add(new Mur1(122, 128, this));
         LObjects.add(new Mur3(122, 112, this));
         LObjects.add(new Mur2(110, 120, this));
		 
		 /////////////////PORTAILS//////////////////////
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
			 else if(port==1)
				LObjects.add(new Portail(xportrand,yportrand,this,w2));
			 else
			 {
				LObjects.add(new Portail(xportrand,yportrand,this,w3));
				
				DarkArbre da1=new DarkArbre(xportrand-10, yportrand, this);
				DarkArbre da2=new DarkArbre(xportrand+10, yportrand, this);
				DarkArbre da3=new DarkArbre(xportrand+10, yportrand+10, this);
				DarkArbre da4=new DarkArbre(xportrand-10, yportrand+10, this);
				da1.init(); da2.init(); da3.init(); da4.init();
				LObjects.add(da1); LObjects.add(da2); LObjects.add(da3); LObjects.add(da4);
				
			 }
			}
		 }
		 ///////////////TELEPORTEURS/////////////////////
		 for(int port = 0 ; port <NBMAXTELEPORTEUR ; port++)
		 {
			 do{
				xportrand = (int)(Math.random()*dxCA);
				yportrand = (int)(Math.random()*dyCA);
			 }while(this.getCellHeight(xportrand, yportrand)<=0);
	
			LObjects.add(new Teleporteur(xportrand,yportrand,(int)(Math.random()*dxCA),(int)(Math.random()*dyCA),this));
		}
		/*------------------AJOUTS AGENTS ----------------------*/	
		
		for (int i = 0; i < POPINI; i++) 
		{
			int dxRand = 0;
			int dyRand = 0;
			do {
				dxRand = (int) (Math.random() * dxCA);
				dyRand = (int) (Math.random() * dyCA);
			} while (this.getCellHeight(dxRand, dyRand) <= 0); //On s'assure que les agentListes ne soient pas generés sur l'eau
			
		agentListe.add(new Agent(dxRand, dyRand, this));
		}
		
		for (int d = 97; d < 147; d++) 
		{
			for (int j = 112; j < 128; j++) 
			{
				cellState = this.getCellValue(d, j);
				if (cellState == 1) 
				{
					if (Math.random() < 0.03) 
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
		for (int i = 0; i < POPINI; i++) 
		{
			int dxRand = 0;
			int dyRand = 0;
			do {
				dxRand = (int) (Math.random() * dxCA);
				dyRand = (int) (Math.random() * dyCA);
			} while (this.getCellHeight(dxRand, dyRand) <= 0); //On s'assure que les agentListes ne soient pas generés sur l'eau

		zombie.add(new Zombie(dxRand, dyRand, this));
		}
	}
	/*---------------------------FIN AJOUT OBJETS--------------------------------------*/

	
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
		
		//Met a jour les arbres : vielissement, repousse et perte des fruits//////
		for(UniqueObject abr : LObjects) 
			if(abr instanceof GrandArbre)
				((GrandArbre)abr).step();
			else if(abr instanceof Sapin)
				((Sapin)abr).step();
		
		//////////Fin de vie des arbres//////////////
		for(Iterator<UniqueObject> it = LObjects.iterator(); it.hasNext();)
		{
			UniqueObject arbre = it.next();
			
			if(arbre instanceof GrandArbre && ((GrandArbre) arbre).die()){
				it.remove();
				System.out.println("Un grand arbre est mort :sob:");
			}else if(arbre instanceof Sapin && ((Sapin) arbre).die() ){
				System.out.println("Un sapin est mort :sob:");
				it.remove();
			}
		}
		
		if (Math.random() < pgrowga) {
			do {
				xabrand = (int) (Math.random() * dx);
				yabrand = (int) (Math.random() * dy);
			} while (this.getCellHeight(xabrand, yabrand) <= 0);

			switchabrsapin = !switchabrsapin;
			if (switchabrsapin) 
			{
				GrandArbre gagrow = new GrandArbre(xabrand, yabrand, this);
				gagrow.init();
				LObjects.add(gagrow);
				System.out.println("Un nouveau Grand Arbre à poussé en (" + xabrand + "," + yabrand + ")");
			} 
			else 
			{
				Sapin sagrow = new Sapin(xabrand, yabrand, this);
				sagrow.init();
				LObjects.add(sagrow);
				System.out.println("Un nouveau Sapin à poussé en (" + xabrand + "," + yabrand + ")");
			}
		}
		//Accelere la repousse des arbres s'ils sont en sous-nombre
		if(getNombreGA()<(int)(Math.random()%(20-11+1)+11))
			pgrowga=0.1;
		else
			pgrowga=0.005;
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
	@Override
	public World getW3()
	{
		return w3;
	}
	@Override
	public void setW3(World w3)
	{
		this.w3=w3;
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

}
