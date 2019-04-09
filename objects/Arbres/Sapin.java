package objects.Arbres;

import Interfaces.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.media.opengl.GL2;
import objects.Consommables.Pomme;
import objects.UniqueObject;
import worlds.World;

//Classe au fonctionnement identique à GrandArbre, couleur differente
public class Sapin extends UniqueObject implements Eliminable {

	private final ArrayList<Pomme> pomme;
	private int health;
	private int age;
	
	private final static int NBPOMME = 3;
	private final int lifeEsperance; //Vie entre 70 et 300 ans;
	private final static int TAUXMURATION = 10;
	private final static int TAUXMURATIONARBRE = 20;
	private final static double PROBAPOUSSE = 0.2;
	private static boolean vide = false;
	
	public Sapin ( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		
		health = 6000;
		age = 0;
		lifeEsperance = (int)(Math.random()%(300-70+1)+70);
		
		pomme = new ArrayList<>();
	}
	
	public void step() //met à jour les consommables de l'arbre
	{
		if(!pomme.isEmpty())
		{
			for(Iterator<Pomme> it = pomme.iterator() ; it.hasNext();)
			{
				Pomme p = it.next();
				if(Math.random()<p.getPdrop())
				{
					this.world.getAlimentListe().add(p);
					System.out.println("Une pomme est tombé d'un sapin");
					it.remove();
				}
			}
		
			if(Math.random()<lifeEsperance/1000)
			{
				Pomme tombe = popPomme();
				System.out.println("Une pomme est tombé d'un sapin");
				this.world.getAlimentListe().add(tombe);
			}
			murirTous();
		}
		viellir();
	}
	public boolean die()
	{
		return (health <= 0 || age>lifeEsperance);
	}
	
	public void viellir()
	{
		if(world.getIteration()%TAUXMURATIONARBRE==0)
			age ++;
		
		if(pomme.size()<2 && Math.random()<PROBAPOUSSE)
			addPomme();
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
		Sapin.vide=vide;
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

	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight) {

		// display a tree
		//gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));
		int x2 = (x - (offsetCA_x % myWorld.getWidth()));
		if (x2 < 0) {
			x2 += myWorld.getWidth();
		}
		int y2 = (y - (offsetCA_y % myWorld.getHeight()));
		if (y2 < 0) {
			y2 += myWorld.getHeight();
		}

		float height = Math.max(0, (float) myWorld.getCellHeight(x, y));

		gl.glColor3f(0.5f, 0.1f - (float) (0.2 * Math.random()), 1.0f);
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
		gl.glColor3f(0.5f, 0.2f - (float) (0.1 * Math.random()), 0.9f);
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
