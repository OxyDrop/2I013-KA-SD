package objects.Arbres;


import Interfaces.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import objects.Consommables.Coco;
import objects.UniqueObject;
import worlds.World;

public class Palmier extends UniqueObject implements Eliminable
{
	private final ArrayList<Coco> coco;
	private int health;
	private int age;
	
	private final static int NBCoco = 5;
	private final int lifeEsperance; //Vie entre 70 et 300 ans;
	private final static int TAUXMURATION = 10;
	private final static int TAUXMURATIONARBRE = 20;
	private final static double PROBAPOUSSE = 0.2;
	private GLU glu;
	private static boolean vide = false;
	
	public Palmier ( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		
		health = 6000;
		age = 0;
		lifeEsperance = (int)(Math.random()%(300-70+1)+70);
		
		coco = new ArrayList<>();
	}
	
	public void step() //met à jour les consommables de l'arbre
	{
		if(!coco.isEmpty())
		{
			for (Iterator<Coco> it = coco.iterator(); it.hasNext();) 
			{
				Coco c = it.next();
				if (Math.random() < c.getPdrop()) 
				{
					this.world.getAlimentListe().add(c);
					System.out.println("Une coco est tombé d'un arbre");
					it.remove();
				}
			}

			if (Math.random() < lifeEsperance / 1000) 
			{
				Coco tombe = popCoco();
				this.world.getAlimentListe().add(tombe);
				System.out.println("Une coco est tombé d'un arbre");
			}
			murirTous();
		}
		viellir();
			
	}
	public boolean die()
	{
		return (health <= 0 || age>lifeEsperance);
	}
	
	//Fait viellir l'arbre et ajoute des cocos de temps à autre
	public void viellir()
	{
		if(world.getIteration()%TAUXMURATIONARBRE==0)
			age ++;
		
		if(coco.size()<2 && Math.random()<PROBAPOUSSE)
			addCoco();
	}
	
	//fait murir toutes les cocos de l'arbre
	public void murirTous()
	{
		if(world.getIteration() % TAUXMURATION == 0)
			for(Coco c : coco)
				c.murir();
	}
	
	public void addCoco() //ajoute une coco à l'arbre;
	{
		coco.add(new Coco(x,y,world));
	} 
	
	public void removeCoco(Coco p){ //supprime la coco indiquée
		coco.remove(p);
	}
	
	public Coco popCoco() //retourne la coco en fin de liste
	{
		return coco.remove(coco.size()-1);
	}
	
	public final void init() //Initialise le 
	{
		for(int i = 0; i<NBCoco; i++)
			addCoco();
	}
	
	public static boolean getVide()
	{
		return vide;
	}
	
	public static void setVide(boolean vide)
	{
		Palmier.vide=vide;
	}

	public ArrayList<Coco> getCoco() {
		return coco;
	}

	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health){
		this.health=health;
	}
	
	@Override
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

		gl.glColor3f(1f,0.5f,0.2f);
        		/* Tronc de l'arbre */
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
		/*Cote */
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
		/*Cote */
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
		/*Cote */
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
		/*Chapeau*/
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 8.f);

		/* Feuillage de l'arbre */
		gl.glColor3f(0.6f, 1.f, 0.3f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 8.f);
		/*Cote */
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 8.f);
		/*Cote */
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 8.f);
		/*Cote */
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 8.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 8.f);
		/*Chapeau*/
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 16.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 16.f);
	
		
    }
}