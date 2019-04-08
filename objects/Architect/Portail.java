package objects.Architect;

import DynamicObject.Agent;
import Tools.ImageResources;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.media.opengl.GL2;
import objects.UniqueObject;
import worlds.World;

/**
 *
 * @author Serero
 */
public class Portail extends UniqueObject{ //Lie deux mondes entre eux;
	
	private World passage; //le monde de passage
	private final static int DISTANCEMIN = 2; //utilisé pour definir la distance minimale
	private static int cpt = 0;
	private int id;
	private int texture;
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
	public void passePortail(ArrayList<Agent> agentList)
	{
		for (Iterator<Agent> it = agentList.iterator() ; it.hasNext();)
    	{
    		Agent a = it.next();
			if (distanceSuffisante(a)) 
			{
				Agent clone = a.clone();
				//Propulse a un point aleatoire en dehors du portail
				clone.setX(a.getX() + (int) (Math.random() % (10 - 5 + 1) + 5));
				clone.setY(a.getY() + (int) (Math.random() % (10 - 5 + 1) + 5));
				it.remove();
				passage.getAgentListe().add(clone);
				System.out.println("Un agent a emprunté le portail "+id 
									+ " en (" + x + "," + y + ") menant au " + passage.getNom());
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
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f( (offset+x2*stepX-lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight);
		gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f( (offset+x2*stepX-lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight + 40f);
		gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f( (offset+x2*stepX+lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight + 40f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f( (offset+x2*stepX+lenX*6f), (offset+y2*stepY-lenY), height*normalizeHeight);
		/*Cote*/
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f( offset+x2*stepX+lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight);
		gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f( offset+x2*stepX+lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight + 40.f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f( offset+x2*stepX-lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight + 40.f);
		gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f( offset+x2*stepX-lenX*6f, offset+y2*stepY+lenY, height*normalizeHeight);
		/*Cote*/
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 40.f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 40.f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight);
		gl.glTexCoord2f(1.0f, 1.0f);
		/*Cote */
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 40.f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 40.f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight);
		gl.glTexCoord2f(0.0f, 0.0f);
		/*Chapeau*/
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 20.f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(offset + x2 * stepX - lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 20.f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY + lenY, height * normalizeHeight + 20.f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(offset + x2 * stepX + lenX*6f, offset + y2 * stepY - lenY, height * normalizeHeight + 20.f);
		gl.glTexCoord2f(0.0f, 1.0f);
		
		gl.glDisable(GL2.GL_TEXTURE_2D);
		
	}
	
}
