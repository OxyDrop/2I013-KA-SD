// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package worlds;

import DynamicObject.Agent;
import DynamicObject.FAgent;
import DynamicObject.MAgent;
import DynamicObject.Zombie;
import cellularautomata.DarkCA;
import java.util.Iterator;
import javax.media.opengl.GL2;
import objects.Arbres.*;
import objects.Architect.*;
import objects.UniqueObject;

//Si vous vous retrouvez dans le dark world, il n'y a plus de retour possible!!!!!!!!!!!!/////////////
public class DarkWorld extends World {

	private static final int POPINI=80;
	private static final int NBMAXPORTAILS=2;
	private static final int NBMAXTELEPORTEUR = 5;
	private static final int NOTIFYITERATION = 100; 
	private double pgrowga = 0.005;
	
	private int xportrand, yportrand;
	private int xteleprand, yteleprand;
	private int xabrand, yabrand;
	protected DarkCA cellularAutomata;
	private boolean switchabrsapin=false;
	
	//Used to display messages every number of iteration 
	
	/*
	protected int iteration = 0;
	indexCA;
	protected CellularAutomataInteger cellularAutomata; // TO BE DEFINED IN CHILDREN CLASSES
	protected CellularAutomataDouble cellsHeightValuesCA;
	protected CellularAutomataDouble cellsHeightAmplitudeCA;
	*/
	public DarkWorld(){}
	
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
		    	
		        if (getCellValue(x, y)==0)
				{	
		        	color[0] = 0.1f;//height / (float)this.getMaxEverHeight();
					color[1] = 0f;//height / (float)this.getMaxEverHeight();
					color[2] = 0f;//height / (float)this.getMaxEverHeight();
					
		        }
		        else
		        {	//lava
					color[0] = 1f;
					color[1] = 0.7f;
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
		
		for (int v = 0; v < POPINI/8; v++) 
		{
			int dxRand = 0;
			int dyRand = 0;
			do {
				dxRand = (int) (Math.random() * dxCA);
				dyRand = (int) (Math.random() * dyCA);
			} while (this.getCellHeight(dxRand, dyRand) <= 0); //On s'assure que les agentListes ne soient pas generés sur l'eau

		fagent.add(new FAgent(dxRand, dyRand, this));
		}
		for (int v = 0; v < POPINI/2; v++) 
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
		////////////////////////ARBRES//////////////////////////////////
		
    	for (int i = 0 ; i < dxCA ; i++){
    		for (int j = 0 ; j < dyCA ; j++)
    		{
    			cellState = this.getCellValue(i, j);
    			if (cellState == 0)
				{
					if(Math.random()<0.0005)
					{
						DarkArbre da = new DarkArbre(i,j,this);
						da.init();
    					LObjects.add(da);
					}
				}
			}
		}
		/////////////////CONSTRUCTION//////////////////////
		  
	/*---------------------------FIN AJOUT OBJETS--------------------------------------*/
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
			if(portal instanceof Teleporteur)
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

