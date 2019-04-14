package objects.Architect;

import DynamicObject.Agent;
import DynamicObject.FAgent;
import DynamicObject.MAgent;
import DynamicObject.Zombie;
import com.jogamp.opengl.util.texture.Texture;
import java.util.ArrayList;
import java.util.Iterator;
import javax.media.opengl.GL2;
import objects.UniqueObject;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Portail extends UniqueObject{ //Lie deux mondes entre eux;
	
	private final World passage; //le monde de passage
	private final static int DISTANCEMIN = 2; //utilisé pour definir la distance minimale
	private static int cpt = 0;
	private final int id;
	private Texture t;
	
	public Portail(int x, int y, World world, World passage) 
	{
		super(x, y, world);
		this.passage = passage;
		id=++cpt;
	}
	
	public boolean distanceSuffisante(Agent a)
	{
		return (Math.sqrt(Math.pow( a.getX() - x , 2 ) + Math.pow( a.getY() - y , 2 )) <= DISTANCEMIN);
		
	}
	
	public boolean distanceSuffisanteM(MAgent a)
	{
		return (Math.sqrt(Math.pow( a.getX() - x , 2 ) + Math.pow( a.getY() - y , 2 )) <= DISTANCEMIN);
		
	}
	public boolean distanceSuffisanteF(FAgent a)
	{
		return (Math.sqrt(Math.pow( a.getX() - x , 2 ) + Math.pow( a.getY() - y , 2 )) <= DISTANCEMIN);
		
	}
	public boolean distanceSuffisanteZ(Zombie a)
	{
		return (Math.sqrt(Math.pow( a.getX() - x , 2 ) + Math.pow( a.getY() - y , 2 )) <= DISTANCEMIN);
		
	}
	public void passePortail(ArrayList<Agent> agentList) //Methode principale pour la transition entre monde
	{
		if (!agentList.isEmpty()) {
			for (Iterator<Agent> it = agentList.iterator(); it.hasNext();) {
				Agent a = it.next();
				if (distanceSuffisante(a)) {
					Agent clone = a.clone();
					//Agent clone = new Agent(a.getX(),a.getX(),a.getWorld());

					if (clone != null) {
						//Propulse a un point aleatoire en dehors du portail
						clone.setX(a.getX() + (int) (Math.random() % (10 - 5 + 1) + 5));
						clone.setY(a.getY() + (int) (Math.random() % (10 - 5 + 1) + 5));
						try {
							passage.getAgentListe().add(clone);
						} catch (NullPointerException ex) {
							ex.printStackTrace();
							System.err.println("Problème survenu lors d'un voyage interdimensionnel");
						}
					}
					it.remove();

					if (passage.getNom().equals("DarkWorld")) {
						System.out.println("Un agent s'est fait aspiré par le portail noir en (" + x + "," + y + ") menant au " + passage.getNom() + ", paix à son âme");
					} else {
						System.out.println("Un agent a emprunté le portail " + id + " en (" + x + "," + y + ") menant au " + passage.getNom());
					}
				}
			}
		}
	}
	
	public void passePortailM(ArrayList<MAgent> mAgentList)
	{
		if (!mAgentList.isEmpty()) 
		{
			for (Iterator<MAgent> it = mAgentList.iterator(); it.hasNext();) {
				MAgent a = it.next();
				if (distanceSuffisanteM(a)) {
					MAgent clone = a.clone();
					//Agent clone = new Agent(a.getX(),a.getX(),a.getWorld());

					if (clone != null) {
						//Propulse a un point aleatoire en dehors du portail
						clone.setX(a.getX() + (int) (Math.random() % (10 - 5 + 1) + 5));
						clone.setY(a.getY() + (int) (Math.random() % (10 - 5 + 1) + 5));
						try {
							passage.getMAgentListe().add(clone);
						} catch (NullPointerException ex) {
							ex.printStackTrace();
							System.err.println("Problème survenu lors d'un voyage interdimensionnel");
						}
					}
					it.remove();

					if (passage.getNom().equals("DarkWorld")) {
						System.out.println("Un Magent s'est fait aspiré par le portail noir en (" + x + "," + y + ") menant au " + passage.getNom() + ", paix à son âme");
					} else {
						System.out.println("Un Magent a emprunté le portail " + id + " en (" + x + "," + y + ") menant au " + passage.getNom());
					}
				}
			}
		}
	}
	
	public void passePortailF(ArrayList<FAgent> fAgentList)
	{
		if (!fAgentList.isEmpty()) {
			for (Iterator<FAgent> it = fAgentList.iterator(); it.hasNext();) {
				FAgent a = it.next();
				if (distanceSuffisanteF(a)) {
					FAgent clone = a.clone();
					//Agent clone = new Agent(a.getX(),a.getX(),a.getWorld());

					if (clone != null) {
						//Propulse a un point aleatoire en dehors du portail
						clone.setX(a.getX() + (int) (Math.random() % (10 - 5 + 1) + 5));
						clone.setY(a.getY() + (int) (Math.random() % (10 - 5 + 1) + 5));
						try {
							passage.getFAgentListe().add(clone);
						} catch (NullPointerException ex) {
							ex.printStackTrace();
							System.err.println("Problème survenu lors d'un voyage interdimensionnel");
						}
					}
					it.remove();

					if (passage.getNom().equals("DarkWorld")) {
						System.out.println("Un Fagent s'est fait aspiré par le portail noir en (" + x + "," + y + ") menant au " + passage.getNom() + ", paix à son âme");
					} else {
						System.out.println("Un Fagent a emprunté le portail " + id + " en (" + x + "," + y + ") menant au " + passage.getNom());
					}
				}
			}
		}
	}
	
	public void passePortailZ(ArrayList<Zombie> ZombieList)
	{
		if (!ZombieList.isEmpty()) {
			for (Iterator<Zombie> it = ZombieList.iterator(); it.hasNext();) {
				Zombie z = it.next();
				if (distanceSuffisanteZ(z)) {
					Zombie clone = z.clone();
					//Agent clone = new Agent(a.getX(),a.getX(),a.getWorld());

					if (clone != null) {
						//Propulse a un point aleatoire en dehors du portail
						clone.setX(z.getX() + (int) (Math.random() % (10 - 5 + 1) + 5));
						clone.setY(z.getY() + (int) (Math.random() % (10 - 5 + 1) + 5));
						try {
							passage.getZombieListe().add(clone);
						} catch (NullPointerException ex) {
							ex.printStackTrace();
							System.err.println("Problème survenu lors d'un voyage interdimensionnel");
						}
					}
					it.remove();

					if (passage.getNom().equals("DarkWorld")) {
						System.out.println("Un Zombie s'est fait aspiré par le portail noir en (" + x + "," + y + ") menant au " + passage.getNom() + ", bon debarras !");
					} else {
						System.out.println("Un Zombie a emprunté le portail " + id + " en (" + x + "," + y + ") menant au " + passage.getNom());
					}
				}
			}
		}
	}

	public World getPassage() {
		return passage;
	}
	
	@Override
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, 
			float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight) 
	{
		
		Texture t = null;
		int x2 = (x-(offsetCA_x%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
		
    	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();
		
		float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );
    	float altitude = (float)height * normalizeHeight ; 
		/*
		gl.glEnable(GL2.GL_TEXTURE_2D);
		t = ImageResources.createTexture("/res/nightFront.png");
		texture = t.getTextureObject(gl);
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
		*/
		gl.glColor3f(0.16f,0.43f,1f);
		//FRONT
		gl.glVertex3f( (offset+x2*stepX-lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight);
		gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f( (offset+x2*stepX-lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight + 40f);
		gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f( (offset+x2*stepX+lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight + 40f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f( (offset+x2*stepX+lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight);
		/*Cote*/
		gl.glVertex3f( offset+x2*stepX+lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight + 40.f);
		gl.glVertex3f( offset+x2*stepX-lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight + 40.f);
        gl.glVertex3f( offset+x2*stepX-lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight);
		/*Cote*/
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 40.f);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 40.f);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight);
		/*Cote */
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 40.f);
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 40.f);
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight);
		/*Chapeau*/
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 20.f);
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 20.f);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 20.f);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 20.f);
		
	}
	
}
