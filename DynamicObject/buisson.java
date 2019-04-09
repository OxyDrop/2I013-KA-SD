package DynamicObject;

import Interfaces.*;

import javax.media.opengl.GL2;

import worlds.World;

public class buisson extends MAgent implements Eliminable {

	public int baie = 50;
	public boolean a = false;
	public int it = 0;
	public boolean repousse = false;

	int health;

	public buisson(int x, int y, World world) {
		super(x, y, world);
		health = 4048;
	}

	public void step() {
		if (world.getIteration() % 20 == 0) {

			if (a) {
				removenbBaie();
				a = false;

			}
			//System.out.println(it);

			if (this.it + 100 == world.getIteration() && baie == 0) {//a changer
				baie = 100;

			}
			//System.out.println(baie);
		}

	}

	public int getnbBaie() {

		return baie;

	}

	public void removenbBaie() {

		baie = 0;
		repousse = true;

	}

	public boolean die() {
		return health <= 0;
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

		float altitude = (float) height * normalizeHeight; // test, a enlever apres
		//gl.glColor3f(1.f,1.f,0.f);

		int cellState = myWorld.getCellValue(x, y);
		if (health < 0) {
			cellState = 3;
		}

		gl.glColor3f(0.f, 1.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

		gl.glColor3f(0.f, 1.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

		gl.glColor3f(0.f, 1.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

		gl.glColor3f(0.f, 1.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
		gl.glColor3f(0.f, 1.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 3.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 3.f);

	}
}
