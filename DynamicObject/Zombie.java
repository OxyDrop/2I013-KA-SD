// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12
package DynamicObject;

import javax.media.opengl.GL2;
import objects.Architect.Mur3;
import objects.UniqueObject;
import worlds.World;

public class Zombie extends UniqueDynamicObject {

	public int vie = 100;
	public int attaque = (int) (Math.random() * 40);

	public boolean stop = false;

	public int niveau = 0;
	public boolean combat = false;
	public boolean istarget = false;
	public boolean enchasse = false;
	public int energie = 20;
	public boolean asec = false;
	public int move;
	public boolean chassef = false;

	public Zombie(int x, int y, World world) {
		super(x, y, world);
	}

	public void step() {
		if (!stop) {
			if (world.getIteration() % 20 == 0) {
				if (distanceAZ(5) && !asec && !istarget) {
					enchasse = true;
					chasseZ();

				} else {
					randomstep();
					energie = (energie + 1) % 20;
				}
				if (energie <= 0) {
					asec = true;
					stop = true;
				}
			}

		}

		if (asec && stop && world.getIteration() % 20 == 0) {
			recovery();
		}

	}

	/*
	public void step() {
		if (!stop && !obstacleMur() && !obstacleEau()) {
			if (world.getIteration() % 20 == 0) {
				if (distanceAZ(5) && !asec && !istarget) {
					enchasse = true;
					chasseZ();
				} else {
					if (distanceAF(3) && !asec && !istarget) {
						chasseFZ();
					} else {
						randomstep();
						energie = (energie + 1) % 20;
					}
				}
			}

			if (istarget) {

				fuiteZ();
			}

			if (energie <= 0) {
				asec = true;
				stop = true;
			}
		}
		if (asec && stop && world.getIteration() % 20 == 0) {
			recovery();
		}
		if (obstacleEau()) {
			move();
		}
		if (obstacleMur()) {
			if (Math.random() < 0) {
				move();
			} else {
				detruitmur();
			}
		}

	}*/
	public void chasseFZ() {

		FAgent FAgent = world.getFAgentListe().get(AgentPlusProche());

		if (!combat) {
			Goto(FAgent.getX(), FAgent.getY());

			if (distanceTo(FAgent, this) <= 1) {
				stop = true;
				FAgent.stop = true;
				Contamination(FAgent);
			}

		} else {
			chassef = false;
		}

		energie = energie - 1;

	}

	public void Contamination(MAgent a) {

		if (world.getMAgentListe().size() > 0) {

			world.getFAgentListe().remove(a);
			world.getZombieListe().add(new Zombie(a.getX(), a.getY(), world));
			chassef = false;

		}

	}

	public void Contamination(FAgent f) {

		if (world.getFAgentListe().size() > 0) {
			for (FAgent f2 : world.getFAgentListe()) {
				if (f == f2) {
					world.getFAgentListe().remove(f2);
					world.getZombieListe().add(new Zombie(f2.getX(), f2.getY(), world));
					chassef = false;

				}
			}
		}

	}

	public void fuiteZ() {

		MAgent a = world.getMAgentListe().get(this.AgentPlusProche());

		if (distanceTo(this, a) < 15) {
			Fuite(a.getX(), a.getY());
		} else {
			this.istarget = false;
		}

	}

	public void Fuite(int i, int j) {

		if (i > this.x && j > this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x - 1 + this.world.getWidth()) % this.world.getWidth();
			} else {
				this.y = (this.y - 1 + world.getHeight()) % world.getHeight();
			}
		}
		if (i == this.x && j > this.y) {
			this.y = (this.y - 1 + world.getHeight()) % world.getHeight();
		}
		if (i > this.x && j == this.y) {
			this.x = (this.x - 1 + this.world.getWidth()) % this.world.getWidth();
		}
		if (i < this.x && j > this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x + 1) % this.world.getWidth();

			} else {
				this.y = (this.y - 1 + world.getHeight()) % world.getHeight();
			}

		}
		if (i < this.x && j == this.y) {
			this.x = (this.x + 1) % this.world.getWidth();
		}
		if (i < this.x && j < this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x + 1) % this.world.getWidth();

			} else {
				this.y = (this.y + 1) % world.getHeight();
			}
		}
		if (i == this.x && j < this.y) {
			this.y = (this.y + 1) % world.getHeight();
		}
		if (i > this.x && j < this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x - 1 + this.world.getWidth()) % this.world.getWidth();

			} else {
				this.y = (this.y + 1) % world.getHeight();
			}
		}

	}

	/*public void Combat() {
		
		
		for(int i=0;i<world.getAgentListe().size();i++) {
				
			if(distanceTo(world.getMAgentListe().get(i),this)==1 ) {
					world.getAgentListe().remove(i);
					world.getZombieListe().add(new Zombie(x,y,world));
			}		
		}
				
	}*/
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

	public int BebePlusProche() {

		int indice_arbre_proche = 0;
		double distance_min = 999999; // une grande distance dans un premier temps 
		for (int i = 0; i < world.getbebeAgentListe().size(); i++) {

			// calculer la distance euclidienne entre l'fagent et cette arbre //
			double distance_fagent_arbre = distanceTo(this, world.getbebeAgentListe().get(i));

			if (distance_fagent_arbre < distance_min) {
				distance_min = distance_fagent_arbre;
				indice_arbre_proche = i;

			}
		}

		return indice_arbre_proche;

	}

	public void Goto(int i, int j) {
		if (i > this.x && j > this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x + 1) % this.world.getWidth();
			} else {
				this.y = (this.y + 1) % world.getHeight();
			}
		}
		if (i == this.x && j > this.y) {
			this.y = (this.y + 1) % world.getHeight();
		}
		if (i > this.x && j == this.y) {
			this.x = (this.x + 1) % this.world.getWidth();
		}
		if (i < this.x && j > this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x - 1 + this.world.getWidth()) % this.world.getWidth();

			} else {
				this.y = (this.y + 1) % world.getHeight();
			}

		}
		if (i < this.x && j == this.y) {
			this.x = (this.x - 1 + this.world.getWidth()) % this.world.getWidth();
		}
		if (i < this.x && j < this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x - 1 + this.world.getWidth()) % this.world.getWidth();

			} else {
				this.y = (this.y - 1 + world.getHeight()) % world.getHeight();
			}
		}
		if (i == this.x && j < this.y) {
			this.y = (this.y - 1 + world.getHeight()) % world.getHeight();
		}
		if (i > this.x && j < this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x + 1) % this.world.getWidth();

			} else {
				this.y = (this.y - 1 + world.getHeight()) % world.getHeight();
			}
		}

	}

	public void Combat(MAgent a, Zombie z) {

		if (Math.random() < 1) {
			Contamination(a);
			z.stop = false;
		} else {

			world.getZombieListe().remove(z);
			a.stop = false;
		}

	}

public void chasseZ() {

        MAgent Agent = world.getMAgentListe().get(AgentPlusProche());

        if (!combat && !Agent.hide) {
            Goto(Agent.getX(), Agent.getY());

            if (distanceTo(Agent, this) <= 1) {
                stop = true;
                Agent.stop = true;
                if (Agent.attaque < this.attaque) {
                    //world.getMAgentListe().remove(Agent);
                    Agent.vie=0;
                    //world.getZombieListe().add(new Zombie(x,y, world));
                    enchasse = false;
                    //stop = false;
                } else {
                    this.vie=0;
                    Agent.stop = false;

                }

            }

        } else {
            enchasse = false;
        }

        energie = energie - 1;

    }

	public void detruitmur() {

		if (move == 0) {

			for (UniqueObject o : world.getLObjects()) {
				if (o instanceof Mur3) {
					((Mur3) o).vie = ((Mur3) o).vie - this.attaque;
					for (Home h : world.getHome()) {
						h.estattaquer = true;
					}

					if (((Mur3) o).vie <= 0 && world.getLObjects().size() > 0) {
						world.getLObjects().remove(o);

					}

				}

			}

		}

	}

	public int FemmePlusProche() {

		// l'indice de l'arbre le plus proche 
		int indice_arbre_proche = 0;
		double distance_min = 1; // une grande distance dans un premier temps 
		// parcourir tous les arbres
		for (int i = 0; i < world.getFAgentListe().size(); i++) {

			// verifier que c'est un arbre 
			if (world.getFAgentListe().get(i) instanceof FAgent) {

				// calculer la distance euclidienne entre l'fagent et cette arbre //
				double distance_fagent_arbre = distanceTo(this, world.getFAgentListe().get(i));
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

	public boolean distanceAZ(int dist) {
		if (world.getMAgentListe().size() > 0) {
			int z = AgentPlusProche();
			return distanceTo(this, world.getMAgentListe().get(z)) <= dist;
		}
		return false;
	}

	public boolean distanceAF(int dist) {
		if (world.getFAgentListe().size() > 0) {
			int z = FemmePlusProche();
			return distanceTo(this, world.getFAgentListe().get(z)) <= dist;
		}
		return false;
	}

	public boolean distanceZB(int dist) {
		if (world.getbebeAgentListe().size() > 0) {
			int b = BebePlusProche();
			return distanceTo(this, world.getbebeAgentListe().get(b)) <= dist;
		}
		return false;
	}

	public boolean obstacle() {

		if (this.y + 1 == 111) {
			move = 0;
			return true;
		}
		if (this.x - 1 == 134) {
			move = 1;

			return true;
		}
		if (this.x + 1 == 110) {
			move = 2;

			return true;
		}
		if (this.y - 1 == 128) {
			move = 3;

			return true;

		}
		return false;
	}

	public void move() {
		if (move == 0) {
			this.y = (this.y - 1 + this.world.getHeight()) % this.world.getHeight();

		}
		if (move == 1) {
			this.x = (this.x + 1) % this.world.getWidth();

		}
		if (move == 2) {
			this.x = (this.x - 1 + this.world.getWidth()) % this.world.getWidth();

		}
		if (move == 3) {
			this.y = (this.y + 1) % this.world.getHeight();
		}
	}

	public boolean obstacleMur() {

		for (int i = 97; i <= 147; i++) {  //(97,112)  (147,112)
			if (this.x + 1 == i && this.y == 112 - 1) {
				move = 0;
				return true;
			}
		}

		for (int i = 97; i <= 147; i++) {
			if (this.x - 1 == i && this.y == 128 + 1) {
				move = 3;
				return true;
			}
		}

		for (int i = 112; i <= 128; i++) {
			if (this.y + 1 == i && this.x == 97 - 1) {
				move = 2;
				return true;
			}
		}
		for (int i = 112; i <= 128; i++) {
			if (this.y - 1 == i && this.x == 147 + 1) {
				move = 1;
				return true;
			}
		}

		return false;

	}

	public boolean obstacleEau() {

		if (world.getCellHeight(x, y - 1) <= 0) {
			move = 3;
			return true;

		}
		if (world.getCellHeight(x, y + 1) <= 0) {
			move = 0;
			return true;

		}
		if (world.getCellHeight(x - 1, y) <= 0) {
			move = 1;
			return true;

		}

		if (world.getCellHeight(x + 1, y) <= 0) {
			move = 2;
			return true;

		}
		return false;
	}

	public boolean equals(Object o) { //verifie l'egalité des arguments

		if (o == null) {
			return false;
		}

		if (getClass() != o.getClass()) {
			return false;
		}

		Zombie other = (Zombie) o;
		if (x != other.x && y != other.y--) {
			return false;
		}

		return true;
	}

	public void recovery() {

		if (this.energie < 60) {
			this.energie = this.energie + 20;
			//System.out.println(this.energie);
		} else {
			this.asec = false;
			this.stop = false;

		}
	}

	@Override
	public Zombie clone() {
		return new Zombie(x, y, world);
	}

	public static double distanceTo(Zombie zombie, BebeAgent bebeAgent) {
		return Math.sqrt(Math.pow(bebeAgent.getX() - zombie.x, 2) + Math.pow(bebeAgent.getY() - zombie.y, 2));
	}

	public static double distanceTo(Zombie zombie, FAgent fAgent) {
		return Math.sqrt(Math.pow(fAgent.getX() - zombie.x, 2) + Math.pow(fAgent.getY() - zombie.y, 2));
	}

	public static double distanceTo(Zombie zombie, MAgent mAgent) {
		return Math.sqrt(Math.pow(mAgent.getX() - zombie.x, 2) + Math.pow(mAgent.getY() - zombie.y, 2));
	}

	public static double distanceTo(FAgent fAgent, Zombie zombie) {
		return Math.sqrt(Math.pow(zombie.getX() - fAgent.x, 2) + Math.pow(zombie.getY() - fAgent.y, 2));
	}

	public static double distanceTo(MAgent a, Zombie b) {
		return Math.sqrt(Math.pow(a.getX() - b.x, 2) + Math.pow(a.getY() - b.y, 2));
	}

	public void randomstep() {
		double dice = Math.random();
		if (dice < 0.25) {
			this.x = (this.x + 1) % this.world.getWidth();
		} else if (dice < 0.5) {
			this.x = (this.x - 1 + this.world.getWidth()) % this.world.getWidth();
		} else if (dice < 0.75) {
			this.y = (this.y + 1) % this.world.getHeight();
		} else {
			this.y = (this.y - 1 + this.world.getHeight()) % this.world.getHeight();
		}
	}

	@Override
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight) {

		// display a monolith
		//gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));
		if (vie > 0) {
			int x2 = (x - (offsetCA_x % myWorld.getWidth()));
			if (x2 < 0) {
				x2 += myWorld.getWidth();
			}
			int y2 = (y - (offsetCA_y % myWorld.getHeight()));
			if (y2 < 0) {
				y2 += myWorld.getHeight();
			}

			float height = Math.max(0, (float) myWorld.getCellHeight(x, y));

			gl.glColor3f(0.f, 1.f, 0.5f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

			gl.glColor3f(0.f, 1.f, 0.5f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			gl.glColor3f(0.f, 1.f, 0.5f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			gl.glColor3f(0.f, 1.f, 0.5f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

			gl.glColor3f(1.0f, 1.f, 0.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);
		}
	}
}
