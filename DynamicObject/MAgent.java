// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12
package DynamicObject;

import javax.media.opengl.GL2;
import objects.Arbres.GrandArbre;
import worlds.World;

public class MAgent extends UniqueDynamicObject {

	public int Bois = 0;
	public int vie = 100;
	public int attaque = (int) Math.random() * 20;
	public boolean arme = false;
	public int energie = 20;
	public boolean endanger = false;
	public boolean stop = false;
	public boolean hide = false;
	public int action = 0;
	public int action2 = 0;
	public int it;
	public boolean fuite = false;
	public int faim = 0;
	public boolean var = true;
	public boolean var2 = true;
	public boolean boostb = false;
	public int target;
	public boolean enfuite = false;
	public boolean combat = false;
	public boolean equipe = false;
	public boolean asec = false;
	public boolean nothing = false;
	public boolean eat = false;
	public boolean swim = false;
	public boolean burn = false;

	public void step() {
		UdpadeAtck();

		if (nage()) {
			swim = true;
		} else {
			swim = false;
		}

		if (EnFeu()) {
			burn = true;
		} else {
			burn = false;
		}

		if (!stop) {
			if (world.getIteration() % 20 == 0) {

				if (distanceAZ(5) && world.getZombieListe().size() > 0) {

					if (action == 0 && !asec) {
						FuiteAZ(); //fuite de l'agent

						//System.out.println("je fuis !!!!");
					}

					if (energie == 0) {
						asec = true;
					}

					if (asec) { // si il n'a plus d'energie, il s'arrete pour reprendre des force
						recovery();

					}
					//System.out.println(enfuite);

					if (var) {//bug
						//System.out.println("x");

						DecideactionSurvie();
					}
					if (action == 1) {
						if (world.getBuissonListe().size() > 0) {
							//System.out.println("jv me cacher !!");
							Cache();
						}
					}

					if (action == 2) {

						//System.out.println("a l'aide !!!!!");
						chercheaide();
					}
					if (action == 3) {
						chasseA();

					}
				} else {
					if (action2 == 0 && !eat) {
						randomstep();
						energie = (energie + 1) % 10;

					}
					if (var2) {
						Decideaction();
					}
					if (action2 == 1 && !eat && world.getArbreListe().size() > 0) {
						//System.out.println("jv chercher bois");

						GoArbre();
					}

					if (faim > 25 && !nothing) {
						//System.out.println("jv chercher a manger");
						eat = true;

						eatbaie();
					}
				}
				faim = faim + 1;
			}

		} else if (stop && hide) {
			Gohorsvision();
		}
	}

	public void DecideactionSurvie() {

		if (Math.random() < 0.1) //se cacher
		{
			action = 1;
		} else if (Math.random() < 0.1)//chercher de l'aide
		{
			action = 2;
		} else if (Math.random() < 0.1)//se battre
		{
			action = 3;
		} else {
			action = 0;//fuir encore
		}
		var = false;

	}

	public void Decideaction() {

        if (Math.random() < 0) //arbre pour armes
        {
            action2 = 1;
        } else {
            action2 = 0;
        }

        var2 = false;

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

	public MAgent(int x, int y, World world) {
		super(x, y, world);
	}

	public void UdpadeAtck() {

		if (Bois >= 10) {
			attaque += 5 % 25;
			Bois = Bois - 5;
		}

	}

	///////////////////////////SURVIE////////////////////////
	public void chercheaide() {

		Zombie cible = world.getZombieListe().get(this.ZombiePlusProche());
		MAgent a = world.getMAgentListe().get(this.AgentPlusProche());
		if (distanceTo(this, a) < 50) {
			//System.out.println("x");

			if (distanceTo(this, a) <= 5) {

				this.endanger = false;
				cible.istarget = true;
				//this.var=true;
				this.action = 0;
			} else {
				this.Goto(a.getX(), a.getY());
			}
		} else {
			//System.out.println("x");
			this.action = 0;
			//this.var=true;
		}
	}

	public void Cache() {

		Zombie z = world.getZombieListe().get(ZombiePlusProche());
		buisson b = world.getBuissonListe().get(this.BuissonPlusProche());

		if (distanceTo(this, b) < 10 && distanceTo(z, b) > distanceTo(this, b)) {
			Goto(b.getX(), b.getY());

			if (this.getX() == b.getX() && this.getY() == b.getY()) {
				System.out.println("jsuis cacher");
				z.enchasse = false;
				this.hide = true;
				this.stop = true;
				this.fuite = true;
				it = world.getIteration();

			}
		} else {
			this.action = 0;
		}

	}

	public boolean nage() {
		if (world.getCellValue(this.x, this.y) == -1) {

			return true;
		}
		return false;

	}

	public boolean EnFeu() {

		int dx = world.getWidth();
		int dy = +world.getHeight();

		if (world.getCellValue((x + dx) % dx, (y - 1 + dy) % dy) == 2 && Math.random() > 0) {
			x = (x) % world.getWidth();
			y = (y + 1) % world.getHeight();
			return false;
		}
		if (world.getCellValue((x - 1 + dx) % dx, (y - 1 + dy) % dy) == 2 && Math.random() < 0) {
			x = (x + 1) % world.getWidth();
			y = (y + 1) % world.getHeight();
			return false;

		}
		if (world.getCellValue((x + 1 + dx) % dx, (y - 1 + dy) % dy) == 2 && Math.random() < 0) {
			x = (x - 1) % world.getWidth();
			y = (y + 1) % world.getHeight();
			return false;

		}

		if (world.getCellValue((x - 1 + dx) % dx, (y + 1 + dy) % dy) == 2 && Math.random() < 0) {
			x = (x + 1) % world.getWidth();
			y = (y - 1) % world.getHeight();
			return false;

		}
		if (world.getCellValue((x - 1 + dx) % dx, (y + 1 + dy) % dy) == 2 && Math.random() < 0) {
			x = (x + 1) % world.getWidth();
			y = (y - 1) % world.getHeight();
			return false;

		}
		if (world.getCellValue((x + 1 + dx) % dx, (y + dy) % dy) == 2 && Math.random() < 0) {
			x = (x - 1) % world.getWidth();
			y = (y) % world.getHeight();
			return false;

		}
		if (world.getCellValue((x + dx) % dx, (y + dy) % dy) == 2) {
			return true;
		}
		return false;

	}

	public void chasseA() {

		Zombie z = world.getZombieListe().get(this.ZombiePlusProche());
		if (distanceTo(this, z) < 5) {

			Goto(z.getX(), z.getY());

		}

	}

	public void TueZombie(Zombie z) {
		if (world.getZombieListe().size() > 0) {
			world.getZombieListe().remove(z);
		}

	}

	public void Fuite(int i, int j) {
		this.var = true;

		if (this.energie > 0) {

			energie = energie - (int) Math.random() * 3;

			if (i > this.x && j > this.y) {
				if (Math.random() < 0.4) {
					this.x = (this.x - 1) % this.world.getWidth();
				} else {
					this.y = (this.y - 1) % world.getHeight();
				}
			}
			if (i == this.x && j > this.y) {

				this.y = (this.y - 1) % world.getHeight();

				if (Math.random() < 0.4) {
					this.x = (this.x - 1) % world.getWidth();
				} else if (Math.random() < 0.2) {
					this.x = (this.x + 1) % world.getWidth();
				}
			}
			if (i > this.x && j == this.y) {

				this.x = (this.x - 1) % this.world.getWidth();
				if (Math.random() < 0.3) {
					this.y = (this.y - 1) % world.getHeight();
				} else if (Math.random() < 0.3) {
					this.y = (this.y + 1) % world.getHeight();
				}
			}
			if (i < this.x && j > this.y) {
				if (Math.random() < 0.4) {
					this.x = (this.x + 1) % this.world.getWidth();

				} else {
					this.y = (this.y - 1) % world.getHeight();
				}
			}
			if (i < this.x && j == this.y) {
				this.x = (this.x + 1) % this.world.getWidth();

				if (Math.random() < 0.3) {
					this.y = (this.y - 1) % world.getHeight();
				} else if (Math.random() < 0.3) {
					this.y = (this.y + 1) % world.getHeight();
				}

			}
			if (i < this.x && j < this.y) {
				if (Math.random() < 0.4) {
					this.x = (this.x + 1) % this.world.getWidth();

				} else {
					this.y = (this.y + 1) % world.getHeight();
				}
			}
			if (i == this.x && j < this.y) {
				this.y = (this.y + 1) % world.getHeight();
				if (Math.random() < 0.3) {
					this.x = (this.x - 1) % world.getWidth();
				} else if (Math.random() < 0.3) {
					this.x = (this.x + 1) % world.getWidth();
				}
			}
			if (i > this.x && j < this.y) {
				if (Math.random() < 0.4) {
					this.x = (this.x - 1) % this.world.getWidth();

				} else {
					this.y = (this.y + 1) % world.getHeight();
				}
			}
		} else {
			asec = true;
		}

	}

	public void FuiteAZ() {

		if (world.getZombieListe().size() > 0) {

			Fuite(world.getZombieListe().get(ZombiePlusProche()).getX(), world.getZombieListe().get(ZombiePlusProche()).getY());

		}

	}

	public void recovery() {

		if (this.energie < 60) {
			this.energie = this.energie + 20;
			//System.out.println(this.energie);
		} else {
			this.asec = false;
		}
	}

	/////////////////////////////////////////////////////////////////////
	public void GoArbre() {
		if (world.getArbreListe().size() > 0) {
			GrandArbre Arbre = world.getArbreListe().get(ArbrePlusProche());
			Goto(Arbre.getX(), Arbre.getY());
			recoltebois();
		} else {
			this.var2 = true;
		}
	}

	public void recoltebois() {
		for (GrandArbre ga : world.getArbreListe()) {
			if (x == ga.getX() && y == ga.getY()) {
				world.getArbreListe().remove(ga);
				Bois = Bois + 10;
				this.var2 = true;
			}
		}
	}

	public boolean equals(Object o) { //verifie l'egalité des arguments

		if (o == null) {
			return false;
		}

		if (getClass() != o.getClass()) {
			return false;
		}

		MAgent other = (MAgent) o;
		if (x != other.x && y != other.y--) {
			return false;
		}

		return true;
	}

	public void Goto(int i, int j) {

		if (i > this.x && j > this.y) {
			if (Math.random() < 0.4) {
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
			if (Math.random() < 0.4) {
				this.x = (this.x - 1) % this.world.getWidth();

			} else {
				this.y = (this.y + 1) % world.getHeight();
			}
		}
		if (i < this.x && j == this.y) {
			this.x = (this.x - 1) % this.world.getWidth();
		}
		if (i < this.x && j < this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x - 1) % this.world.getWidth();

			} else {
				this.y = (this.y - 1) % world.getHeight();
			}
		}
		if (i == this.x && j < this.y) {
			this.y = (this.y - 1) % world.getHeight();
		}
		if (i > this.x && j < this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x + 1) % this.world.getWidth();

			} else {
				this.y = (this.y - 1) % world.getHeight();
			}

		}

	}

	public int ArbrePlusProche() {
		int indice_arbre_proche = 0;
		double distance_min = 999999;
		for (int i = 0; i < world.getLObjects().size(); i++) {
			
			if (world.getLObjects().get(i) instanceof GrandArbre) 
			{
				
				double distance_fagent_arbre = distanceTo(this, world.getArbreListe().get(i));
				if (distance_fagent_arbre < distance_min) 
				{
					distance_min = distance_fagent_arbre;
					indice_arbre_proche = i;
				}
			}
		}
		return indice_arbre_proche;
	}

	public int BuissonPlusProche() {

		int buisson_proche = 0;
		double distance_min = 999999;
		for (int i = 0; i < world.getBuissonListe().size(); i++) {
			double distance = distanceTo(this, world.getBuissonListe().get(i));
			if (distance < distance_min) {
				distance_min = distance;
				buisson_proche = i;
			}
		}
		return buisson_proche;
	}

	public int ZombiePlusProche() {

		int zombie_proche = 0;
		double distance_min = 1;
		for (int i = 0; i < world.getZombieListe().size(); i++) {
			if (world.getZombieListe().get(i) instanceof Zombie) {

				double distance = distanceTo(this, world.getZombieListe().get(i));

				if (distance < distance_min) {
					distance_min = distance;
					zombie_proche = i;

				}
			}
		}
		return zombie_proche;

	}

	public int AgentPlusProche() {

		int agent_proche = 0;
		double distance_min = 999999;
		for (int i = 0; i < world.getMAgentListe().size(); i++) {

			if (world.getMAgentListe().get(i) != this) {

				double distance_fagent_arbre = distanceTo(this, world.getMAgentListe().get(i));

				if (distance_fagent_arbre < distance_min) {
					distance_min = distance_fagent_arbre;
					agent_proche = i;
				}
			}
		}

		return agent_proche;

	}

	public int FemmePlusProche() {

		int femme_proche = 0;
		double distance_min = 1;

		for (int i = 0; i < world.getFAgentListe().size(); i++) {

			if (world.getFAgentListe().get(i) instanceof FAgent) {

				double distance = distanceTo(this, world.getFAgentListe().get(i));

				if (distance < distance_min) {
					distance_min = distance;
					femme_proche = i;

				}
			}
		}

		return femme_proche;

	}

	public boolean distanceAZ(int dist) {
		if (world.getZombieListe().size() > 0) {
			int z = ZombiePlusProche();
			return distanceTo(this, world.getZombieListe().get(z)) <= dist;
		}
		return false;
	}

	public boolean distanceAA(int dist) {
		int a = AgentPlusProche();

		return distanceTo(this, world.getAgentListe().get(a)) <= dist;
	}

	public boolean distanceMF(int dist) {
		int f = AgentPlusProche();

		return distanceTo(this, world.getAgentListe().get(f)) <= dist;
	}

	public int BaiePlusProche() {

		int baie_proche = 0;
		double distance_min = 999999;
		for (int i = 0; i < world.getBuissonListe().size(); i++) {

			if (world.getBuissonListe().get(i) instanceof buisson && world.getBuissonListe().get(i).getnbBaie() != 0) {

				double distance = distanceTo(this, world.getBuissonListe().get(i));

				if (distance < distance_min) {
					distance_min = distance;
					baie_proche = i;

				}

			}
		}

		return baie_proche;
	}

	public void eatbaie() {

		buisson cible = world.getBuissonListe().get(BaiePlusProche());

		if (cible.getnbBaie() != 0) {

			if (distanceTo(this, cible) < 50 && cible != null) {
				Goto(cible.getX(), cible.getY());
				if (x == cible.getX() && y == cible.getY()) {
					eat = false;

					faim = faim - cible.getnbBaie();
					cible.a = true;
					cible.it = world.getIteration();
				}
			}
		} else {
			eat = false;
		}

	}

	public void Gohorsvision() {
		Zombie z = world.getZombieListe().get(this.ZombiePlusProche());

		if (distanceTo(this, z) < 3) {

		} else {
			if (distanceTo(this, z) < 7) {
				hide = false;

				FuiteAZ();
			} else {
				stop = false;
				var = true;
			}
		}

	}

	@Override
	public MAgent clone() //clone un agent
	{
		return new MAgent(x, y, world);
	}

	public double distanceTo(MAgent a, UniqueDynamicObject a2) {
		return Math.sqrt(Math.pow(a.x - a2.getX() + this.world.getWidth() % this.world.getWidth(), 2) + Math.pow(a.y - a2.getY() + this.world.getHeight() % this.world.getHeight(), 2));
	}

	public double distanceTo(MAgent a, GrandArbre grandArbre) {
		return Math.sqrt(Math.pow(a.x - grandArbre.getX() + this.world.getWidth() % this.world.getWidth(), 2) + Math.pow(a.y - grandArbre.getY() + this.world.getHeight() % this.world.getHeight(), 2));
	}

	public double distanceTo(Zombie a, UniqueDynamicObject a2) {
		return Math.sqrt(Math.pow(a.getX() - a2.getX() + this.world.getWidth() % this.world.getWidth(), 2) + Math.pow(a.getY() - a2.getY() + this.world.getHeight() % this.world.getHeight(), 2));
	}

	public int getVie() {
		return vie;
	}

	@Override
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
		if (hide) {

		} else if (swim) {
			gl.glColor3f(0.f, 0.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

			gl.glColor3f(0.f, 0.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			gl.glColor3f(0.f, 0.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			gl.glColor3f(0.f, 0.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

			gl.glColor3f(0.f, 0.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);

		} else if (burn) {
			gl.glColor3f(1.f, 0.f, 0.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

			gl.glColor3f(1.f, 0.f, 0.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			gl.glColor3f(1.f, 0.f, 0.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			gl.glColor3f(1.f, 0.f, 0.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

			gl.glColor3f(1.0f, 0.f, 0.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);

		} else {
			gl.glColor3f(1.f, 1.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

			gl.glColor3f(1.f, 1.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			gl.glColor3f(1.f, 1.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

			gl.glColor3f(1.f, 1.f, 1.f);
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
