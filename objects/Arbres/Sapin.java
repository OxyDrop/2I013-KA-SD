package objects.Arbres;


import Interfaces.*;
import javax.media.opengl.GL2;
import objects.UniqueObject;
import worlds.World;

public class Sapin extends UniqueObject implements Eliminable
{
	
	int health;
	public Sapin ( int __x , int __y , World __world )
	{
		super(__x, __y, __world);
		health = 6000;
	}
	
	public void step()
	{
	
	}
	public boolean die()
	{
		return health <= 0;
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
    	
    	
        gl.glColor3f(0.4f, 0.3f - (float) (0.2 * Math.random()), 0.0f);
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
		gl.glColor3f(0.5f, 0.9f - (float) (0.1 * Math.random()), 1f);
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