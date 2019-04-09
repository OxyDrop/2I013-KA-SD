// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12
package objects.Architect;

import javax.media.opengl.GL2;

import objects.UniqueObject;
import worlds.World;

public class Mur4 extends UniqueObject {

	public Mur4(int __x, int __y, World __world) {
		super(__x, __y, __world);
	}

	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight) {

		// display a monolith
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

		gl.glColor3f(0.5f, 0.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight);

		gl.glColor3f(0.5f, 0.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight);

		gl.glColor3f(0.5f, 0.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight);

		gl.glColor3f(0.5f, 0.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight);
		gl.glColor3f(0.5f, 0.f, 0.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY * 15, height * normalizeHeight + 5.f);
		gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY * 15, height * normalizeHeight + 5.f);

	}
}
