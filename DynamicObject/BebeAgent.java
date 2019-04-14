// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12
package DynamicObject;

import cellularautomata.CellularAutomata;
import javax.media.opengl.GL2;
import objects.Architect.Mur1;
import objects.Architect.Mur2;
import objects.UniqueObject;
import worlds.World;

public class BebeAgent extends UniqueDynamicObject {

	public int __Fuite = 0;
	public int type;
	public int Bois = 0;
	public int roche = 0;
	public int vie = 1;
	public int attaque = 10;
	public int tauxfecondite = 0;
	public boolean arme = false;
	public int energie = 100;//baisse que lorsque l'agent est affamer
	public boolean odeur = false;
	public boolean brule = false;
	public int kill = 0;
	public boolean endanger = false;
	public boolean stop = false;
	public int boost = 3;
	public boolean boostb = false;
	int it;
	boolean hide = false;
	public boolean var = false;
	int move;
	private int dx;
	private int dy;

	CellularAutomata c;

	//odeur
	public int getVie() {
		return vie;
	}

	public BebeAgent(int x, int y, World world) {
		super(x, y, world);
		dx=world.getWidth()-1;
		dy=world.getHeight()-1;
		it=world.getIteration();
	}

	public void type() {
		if (Math.random() < 0.7) {
			type = 1;
		} else {
			type = 2;
		}

		var = true;
	}

	public boolean equals(Object o) { //verifie l'egalité des arguments

		if (o == null) {
			return false;
		}

		if (getClass() != o.getClass()) {
			return false;
		}

		BebeAgent other = (BebeAgent) o;
		if (x != other.x && y != other.y--) {
			return false;
		}

		return true;
	}

	public int ZombiePlusProche() {

		// l'indice de l'arbre le plus proche 
		int indice_arbre_proche = 0;
		double distance_min = 1; // une grande distance dans un premier temps 
		// parcourir tous les arbres
		for (int i = 0; i < world.getZombieListe().size(); i++) {

			// verifier que c'est un arbre 
			if (world.getZombieListe().get(i) instanceof Zombie) {

				// calculer la distance euclidienne entre l'fagent et cette arbre //
				double distance_fagent_arbre = distanceTo(this, world.getZombieListe().get(i));
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

	public static double distanceTo(BebeAgent a, Zombie z) //verifie la distance relative entre deux agents
	{
		return Math.sqrt(Math.pow(a.x - z.getX(), 2) + Math.pow(a.y - z.getY(), 2));
	}

	public boolean death() {
		if (this.vie <= 0) {
			return true;
		}
		return false;
	}

	public void grandit() {

		if (this.it + 500 == world.getIteration() && world.getbebeAgentListe().size() > 0) 
		{
			world.getbebeAgentListe().remove(this);
			if (type == 1) {
				world.getMAgentListe().add(new MAgent(this.getX(), this.getY(), world));
			} else {
				world.getFAgentListe().add(new FAgent(this.getX(), this.getY(), world));
			}

		}

	}

	public boolean distanceBZ(int dist) {
		if (world.getZombieListe().size() > 0) {
			int z = ZombiePlusProche();
			return distanceTo(this, world.getZombieListe().get(z)) <= dist;
		}
		return false;
	}

	public void Goto(int i, int j) {

		if (i > this.x && j > this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x + 1) % dx;
			} else {
				this.y = (this.y + 1) % dy;
			}
		}
		if (i == this.x && j > this.y) {
			this.y = (this.y + 1) % dy;
		}
		if (i > this.x && j == this.y) {
			this.x = (this.x + 1) % dx;
		}
		if (i < this.x && j > this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x - 1) % dx;

			} else {
				this.y = (this.y + 1) % dy;
			}
		}
		if (i < this.x && j == this.y) {
			this.x = (this.x - 1) % dx;
		}
		if (i < this.x && j < this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x - 1) % dx;

			} else {
				this.y = (this.y - 1) % dy;
			}
		}
		if (i == this.x && j < this.y) {
			this.y = (this.y - 1) % dy;
		}
		if (i > this.x && j < this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x + 1) % dx;

			} else {
				this.y = (this.y - 1) % dy;
			}

		}

	}

	public void move() {
		if (move == 0) {
			this.y = this.y + 1;

		}
		if (move == 1) {
			this.x = this.x - 1;

		}
		if (move == 2) {
			this.x = this.x + 1;

		}
		if (move == 3) {
			this.y = this.y - 1;
		}
	}

	public boolean obstacle() {

		for (UniqueObject u : world.getLObjects()) {
			if (u instanceof Mur1 || u instanceof Mur2) {
				if (this.y - 1 == 112) {
					move = 0;
					//this.x=this.x-1;
					return true;
				}
				if (this.x + 1 == 134) {
					move = 1;

					//this.x=this.x-1;
					return true;
				}
				if (this.x - 1 == 110) {
					move = 2;

					//this.x=this.x-1;
					return true;
				}
				if (this.y + 1 == 128) {
					move = 3;

					//this.x=this.x-1;
					return true;
				}

			}

		}
		return false;
	}

	public void Fuite(int i, int j) {
		//enfuite=true;
		if (i > this.x && j > this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x - 1) % dx;
			} else {
				this.y = (this.y - 1) % dy;
			}
		}
		if (i == this.x && j > this.y) {
			this.y = (this.y - 1) % dy;
		}
		if (i > this.x && j == this.y) {
			this.x = (this.x - 1) % dx;
		}
		if (i < this.x && j > this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x + 1) % dx;

			} else {
				this.y = (this.y - 1) % dy;
			}
		}
		if (i < this.x && j == this.y) {
			this.x = (this.x + 1) % dx;
		}
		if (i < this.x && j < this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x + 1) % dx;

			} else {
				this.y = (this.y + 1) % dy;
			}
		}
		if (i == this.x && j < this.y) {
			this.y = (this.y + 1) % dy;
		}
		if (i > this.x && j < this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x - 1) % dx;

			} else {
				this.y = (this.y + 1) % dy;
			}

		}

	}

	public void FuiteAZ() {

		if (world.getZombieListe().size() > 0) {

			Fuite(world.getZombieListe().get(ZombiePlusProche()).getX(), world.getZombieListe().get(ZombiePlusProche()).getY());

		}

	}

	public void step() {

		//grandit();
		if (!var) {
			type();
		} else if (world.getIteration() % 5 == 0) {
			if (world.getbebeAgentListe().size() > 0) {
				grandit();
			}

			if (distanceBZ(5)) {
				FuiteAZ();

			} else {
				if (!obstacle()) {
					randomstep();
				} else {
					move();
				}
			}

		}

	}

	public void randomstep() {

		double dice = Math.random();
		if (dice < 0.25) {
			this.x = (this.x + 1) % dx;
		} else if (dice < 0.5) {
			this.x = (this.x - 1 + dx) % dx;
		} else if (dice < 0.75) {
			this.y = (this.y + 1) % dy;
		} else {
			this.y = (this.y - 1 + dy) % dy;
		}
	}

	@Override
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight) {

		// display a monolith
		//gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));
		int x2 = (x - (offsetCA_x % dx));
		if (x2 < 0) {
			x2 += dx;
		}
		int y2 = (y - (offsetCA_y % dy));
		if (y2 < 0) {
			y2 += dy;
		}

		try
		{
			float height = Math.max(0, (float) myWorld.getCellHeight(x, y));
			float altitude = (float) height * normalizeHeight; // test, a enlever apres

			if (type == 1) {
				gl.glColor3f(0.0f, 1.0f, 1.0f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

			
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);

			} else {
				gl.glColor3f(1.0f, 0.75f, 0.75f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

		
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

		
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

				gl.glColor3f(1.0f, 0.f, 1.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 2.f);
				gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 2.f);
			}
		} 
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public UniqueDynamicObject clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
