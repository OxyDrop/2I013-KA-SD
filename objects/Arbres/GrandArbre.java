package objects.Arbres;


import Interfaces.*;
import java.util.ArrayList;
import javax.media.opengl.GL2;
import objects.Consommables.Pomme;
import objects.UniqueObject;
import worlds.World;

public class GrandArbre extends UniqueObject implements Eliminable
{
	private ArrayList<Pomme> pomme;
	private int health;
	private int age;
	
	private final static int NBPOMME = 3;
	private final int lifeEsperance; //Vie entre 70 et 300 ans;
	private final static int TAUXMURATION = 100;
	private final static int TAUXMURATIONARBRE = 200;

	private static boolean vide = false;
	
	public GrandArbre ( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		
		health = 6000;
		age = 0;
		lifeEsperance = (int)(Math.random()%(300-70+1)+70);
		
		pomme = new ArrayList<>();
	}
	
	public void step() //met à jour les consommables de l'arbre
	{
		for(Pomme p : pomme)
			if(Math.random()<p.getPdrop())
				pomme.remove(p);
		
		if(Math.random()<lifeEsperance/1000)
			popPomme();
		
		viellir();
		murirTous();
	}
	public boolean die()
	{
		return (health <= 0 || age>lifeEsperance);
	}
	
	public void viellir()
	{
		if(world.getIteration()%TAUXMURATIONARBRE==0)
			age ++;
	}
	public void murirTous()
	{
		if(world.getIteration() % TAUXMURATION == 0)
			for(Pomme p : pomme)
				p.murir();
	}
	
	public void addPomme() //ajoute une pomme à l'arbre;
	{
		pomme.add(new Pomme(x,y,world));
	} 
	
	public void removePomme(Pomme p){ //supprime la pomme indiquée
		pomme.remove(p);
	}
	
	public Pomme popPomme() //retourne la pomme en fin de liste
	{
		return pomme.remove(pomme.size()-1);
	}
	
	public final void init() //Initialise le 
	{
		for(int i = 0; i<NBPOMME; i++)
			addPomme();
	}
	
	public static boolean getVide()
	{
		return vide;
	}
	
	public static void setVide(boolean vide)
	{
		GrandArbre.vide=vide;
	}

	public ArrayList<Pomme> getPomme() {
		return pomme;
	}

	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health){
		this.health=health;
	}
	
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
    {

        // display a tree
        
        //gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));
        
    	int x2 = (x-(offsetCA_x%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
    	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();

    	float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );
    	
    	float altitude = (float)height * normalizeHeight ; 
    	//gl.glColor3f(1.f,1.f,0.f);
    	
    	int cellState = myWorld.getCellValue(x, y);
    	if (health < 0)
    	{
    		cellState = 3;
    	}
		
		//for(Pomme p : pomme)
		//	p.displayUniqueObject(myWorld, gl, x2, y2, offset, stepX, stepY, lenX, lenY, normalizeHeight);
		switch ( cellState )
        {
        	case 1:
        	
        		gl.glColor3f(0.4f,0.3f-(float)(0.2*Math.random()),0.0f);
        		/* Tronc de l'arbre */
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                /*Chapeau*/
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                
                /* Feuillage de l'arbre */
        		gl.glColor3f(0.1f,0.5f-(float)(0.1*Math.random()),0.1f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 8.f);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 8.f);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 8.f);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 8.f);
                /*Chapeau*/
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                
                
                
        		break;
        	case 2: // Burning
        		
        		gl.glColor3f(0.4f,0.3f-(float)(0.2*Math.random()),0.0f);
        		/* Tronc de l'arbre */
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                /*Chapeau*/
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                
                /* Feuillage de l'arbre */
        		gl.glColor3f(0.5f-(float)(0.2*Math.random()),0.1f-(float)(0.1*Math.random()),0.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 8.f);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 8.f);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 8.f);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 8.f);
                /*Chapeau*/
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX-lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY+lenY*4.f, height*normalizeHeight + 16.f);
                gl.glVertex3f( offset+x2*stepX+lenX*4.f, offset+y2*stepY-lenY*4.f, height*normalizeHeight + 16.f);
        		
        		
        		break;
        	case 3: // Burnt
        		/**/
        		gl.glColor3f(0.f+(float)(0.2*Math.random()),0.f,0.0f);
        		/* Tronc de l'arbre */
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                /*Cote */
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
                /*Chapeau*/
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 8.f);
                gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 8.f);
        		break;
        		/**/
        }
    	
    	
    }
}