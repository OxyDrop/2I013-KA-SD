package DynamicObject;

import Interfaces.*;
import javax.media.opengl.GL2;
import objects.Architect.Mur1;
import objects.Architect.Mur2;
import objects.Architect.Mur3;
import objects.Architect.Mur4;
import worlds.World;

public class Home extends MAgent implements Eliminable {

	public boolean mur1 = false;
	public boolean mur2 = false;
	public boolean mur3 = false;
	public boolean mur4 = false;
	public boolean estattaquer = false;
	public boolean protect = false;

	int health;

	public boolean niveau;

	public Home(int x, int y, World world) {
		super(x, y, world);
		health = 4048;
	}

	public static double distanceTo(Zombie b, MAgent a) //verifie la distance relative entre deux agents
	{
		return Math.sqrt(Math.pow(a.getX() - b.x, 2) + Math.pow(a.getY() - b.y, 2));
	}

	public void attaquer() {

		MAgent a = world.getMAgentListe().get(AgentPlusProche());
		Zombie z = world.getZombieListe().get(ZombiePlusProche());

		if (distanceHA(50)) {
			a.stop = true;
			a.Goto(z.getX(), z.getY());

			if (distanceTo(z, a) <= 1) {
				a.TueZombie(z);
				estattaquer = false;
				a.stop = false;
			}
		}

	}

	public int AgentPlusProche() {

		// l'indice de l'arbre le plus proche 
		int indice_arbre_proche = 0;
		double distance_min = 999999; // une grande distance dans un premier temps 
		// parcourir tous les arbres
		for (int i = 0; i < world.getMAgentListe().size(); i++) {

			// verifier que c'est un arbre 
			if (world.getMAgentListe().get(i) instanceof MAgent) {

				// calculer la distance euclidienne entre l'fagent et cette arbre //
				double distance_fagent_arbre = distanceTo(this, world.getMAgentListe().get(i));
				// si la distance avec cet arbre est moins que la distance minimale actuelle
				// on prend cet arbre comme la plus proche pour le moment
				if (distance_fagent_arbre < distance_min) {
					distance_min = distance_fagent_arbre;
					indice_arbre_proche = i;
				}
			}
		}

		return indice_arbre_proche;

	}

	public boolean distanceHA(int dist) {
		if (world.getMAgentListe().size() > 0) {
			int a = AgentPlusProche();
			return distanceTo(this, world.getMAgentListe().get(a)) <= dist;
		}
		return false;
	}

	public void step() {

		if (world.getIteration() % 20 == 0) {

			if (!mur1) {
				mur1();
			}
			if (!mur2) {
				mur2();
			}
			if (!mur3) {
				mur3();
			}
			if (!mur4) {
				mur4();

			}
			//System.out.print("x");
			if (estattaquer && world.getZombieListe().size() > 0) {
				attaquer();
			}

		}
	}

	public void mur1() {
		for (FAgent f : world.getFAgentListe()) {
			if (f.getBois() >= 20) {
				f.stop = true;
				f.Goto(120, 120);

				if (f.getX() == 120 && f.getY() == 120) {
					world.getLObjects().add(new Mur1(122, 128, world));

					f.Bois = 0;
					f.stop = false;
					mur1 = true;

				}

			}
		}
	}

	public void mur2() {
		for (FAgent f : world.getFAgentListe()) {
			if (f.getBois() >= 20) {
				f.stop = true;
				f.Goto(120, 120);

				if (f.getX() == 120 && f.getY() == 120) {
					world.getLObjects().add(new Mur2(110, 120, world));
					f.Bois = f.Bois - 20;
					f.stop = false;
					mur2 = true;
				}

			}
		}
	}

	public void mur3() {
		for (FAgent f : world.getFAgentListe()) {
			if (f.getBois() >= 20) {
				f.stop = true;
				f.Goto(120, 120);

				if (f.getX() == 120 && f.getY() == 120) {
					world.getLObjects().add(new Mur3(122, 112, world));

					f.Bois = 0;
					f.stop = false;
					mur3 = true;

				}

			}
		}
	}

	public void mur4() {
		for (FAgent f : world.getFAgentListe()) {
			if (f.getBois() >= 20) {
				f.stop = true;
				f.Goto(120, 120);

				if (f.getX() == 120 && f.getY() == 120) {
					world.getLObjects().add(new Mur4(134, 120, world));

					f.Bois = 0;
					f.stop = false;
					mur4 = true;

				}

			}
		}
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

		gl.glColor3f(0.0f, 0.0f, 0.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight);

		gl.glColor3f(0.0f, 0.0f, 0.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight);

		gl.glColor3f(0.0f, 0.0f, 0.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight);

		gl.glColor3f(0.0f, 0.0f, 0.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight);
		gl.glColor3f(0.4f, 0.3f, 0.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX - lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY + lenY * 4.f, height * normalizeHeight + 10.f);
		gl.glVertex3f(offset + x2 * stepX + lenX * 4.f, offset + y2 * stepY - lenY * 4.f, height * normalizeHeight + 10.f);

	}
}
