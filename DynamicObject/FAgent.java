// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12
package DynamicObject;

import java.util.ArrayList;
import java.util.Iterator;
import javax.media.opengl.GL2;
import objects.Arbres.GrandArbre;
import worlds.World;

public class FAgent extends UniqueDynamicObject {

	public int vie = 100;

	public int Bois = 0;
	public int roche = 0;
	public int fec = 1;
	public int faim = -50;
	public boolean nothing = false;
	public boolean endanger = false;
	public boolean enceinte = false;
	public boolean eat = false;

	public boolean stop = false;
	public int action2 = 0;
	public boolean var = true;
	public boolean var2 = true;
	public boolean asec = false;
	public boolean swim = false;
	public boolean burn = false;

	public int getVie() {
		return vie;
	}

	public FAgent(int x, int y, World world) {
		super(x, y, world);
	}

	public void step() {

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

				if (distanceAZ(4)) {
					FuiteFZ();
				} else {
					if (var2) {
						Decideaction();
					}
					//System.out.println(action2);

					if (action2 == 0 && !eat) {
						randomstep();
					}

					if (fec > 0 && !enceinte && action2 == 1 && world.getMAgentListe().size() > 0 && !eat) {
						GORepro();

					} else {
						if (enceinte && action2 == 1 && !eat) {
							GOnaissance();
						}
					}
					if (action2 == 2 && world.getArbreListe().size() > 0 && !eat) {
						GoArbre();
						//System.out.println(this.getBois());
					}
					if (faim > 25 && world.getBuissonListe().size() > 0) {
						//System.out.println("jv chercher a manger");
						eat = true;

						eatbaie();
					}

				}
				//System.out.println(faim);
				faim = faim + 1;

			}
		}

	}

	public void Decideaction() {

		if (Math.random() < 0.2) // reproduction
		{
			action2 = 1;
		} else if (Math.random() < 0) // recolte arbre
		{
			action2 = 2;
		} else {
			action2 = 0; //sinon, marche alÃ©atoire
		}
		var2 = false;

	}

	public int getBois() {

		return this.Bois;
	}

	public boolean nage() {
		if (world.getCellValue(this.x, this.y) == -1) {

			return true;
		}
		return false;

	}

	public boolean EnFeu() {

		int dx = world.getWidth() - 1;
		int dy = world.getHeight() - 1;

		if (world.getCellValue((x + dx) % dx, (y - 1 + dy) % dy) == 2 && Math.random() > 0) {
			x = (x) % dx;
			y = (y + 1) % dy;
			return false;
		}
		if (world.getCellValue((x - 1 + dx) % dx, (y - 1 + dy) % dy) == 2 && Math.random() < 0) {
			x = (x + 1) % dx;
			y = (y + 1) % dy;
			return false;

		}
		if (world.getCellValue((x + 1 + dx) % dx, (y - 1 + dy) % dy) == 2 && Math.random() < 0) {
			x = (x - 1) % dx;
			y = (y + 1) % dy;
			return false;

		}

		if (world.getCellValue((x - 1 + dx) % dx, (y + 1 + dy) % dy) == 2 && Math.random() < 0) {
			x = (x + 1) % dx;
			y = (y - 1) % dy;
			return false;

		}
		if (world.getCellValue((x - 1 + dx) % dx, (y + 1 + dy) % dy) == 2 && Math.random() < 0) {
			x = (x + 1) % dx;
			y = (y - 1) % dy;
			return false;

		}
		if (world.getCellValue((x + 1 + dx) % dx, (y + dy) % dy) == 2 && Math.random() < 0) {
			x = (x - 1) % dx;
			y = (y) % dy;
			return false;

		}
		if (world.getCellValue((x + dx) % dx, (y + dy) % dy) == 2) {
			return true;
		}
		return false;

	}

	public void GoArbre() {

		GrandArbre Arbre = world.getArbreListe().get(ArbrePlusProche());

		Goto(Arbre.getX(), Arbre.getY());

		recoltebois();
	}

	public void recoltebois() {

		for (Iterator<GrandArbre> it = world.getArbreList().iterator(); it.hasNext();) {
			GrandArbre ga = it.next();
			if (x == ga.getX() && y == ga.getY()) {

				it.remove();
				Bois = Bois + 10;
				var2 = true;
			}
		}

	}

	public boolean equals(Object o) { //verifie l'egalitÃ© des arguments

		if (o == null) {
			return false;
		}

		if (getClass() != o.getClass()) {
			return false;
		}

		FAgent other = (FAgent) o;
		if (x != other.x && y != other.y--) {
			return false;
		}

		return true;
	}

	public void Fuite(int i, int j) {

		int dx = world.getWidth() - 1;
		int dy = world.getHeight() - 1;

		if (i > this.x && j > this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x - 1) % dx;
			} else {
				this.y = (this.y - 1) % dy;
			}
		}
		if (i == this.x && j > this.y) {

			this.y = (this.y - 1) % dy;

			if (Math.random() < 0.4) {
				this.x = (this.x - 1) % dx;
			} else if (Math.random() < 0.2) {
				this.x = (this.x + 1) % dx;
			}
		}
		if (i > this.x && j == this.y) {

			this.x = (this.x - 1) % dx;
			if (Math.random() < 0.3) {
				this.y = (this.y - 1) % dy;
			} else if (Math.random() < 0.3) {
				this.y = (this.y + 1) % dy;
			}
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

			if (Math.random() < 0.3) {
				this.y = (this.y - 1) % dx;
			} else if (Math.random() < 0.3) {
				this.y = (this.y + 1) % dy;
			}

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
			if (Math.random() < 0.3) {
				this.x = (this.x - 1) % dx;
			} else if (Math.random() < 0.3) {
				this.x = (this.x + 1) % dx;
			}
		}
		if (i > this.x && j < this.y) {
			if (Math.random() < 0.4) {
				this.x = (this.x - 1) % dx;

			} else {
				this.y = (this.y + 1) % dy;
			}
		}

	}

	public void FuiteFZ() {
		ArrayList<Zombie> zl = world.getZombieListe();
		if (zl.size() > 0) {
			Fuite(zl.get(ZombiePlusProche()).getX(), zl.get(ZombiePlusProche()).getY());

		}
	}

	public void Goto(int i, int j) {

		int dx = world.getWidth() - 1;
		int dy = world.getHeight() - 1;
		if (i > this.x && j > this.y) {
			if (Math.random() < 0.5) {
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
			if (Math.random() < 0.5) {
				this.x = (this.x - 1) % dx;

			} else {
				this.y = (this.y + 1) % dy;
			}

		}
		if (i < this.x && j == this.y) {
			this.x = (this.x - 1) % dx;
		}
		if (i < this.x && j < this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x - 1) % dx;

			} else {
				this.y = (this.y - 1) % dy;
			}
		}
		if (i == this.x && j < this.y) {
			this.y = (this.y - 1) % dy;
		}
		if (i > this.x && j < this.y) {
			if (Math.random() < 0.5) {
				this.x = (this.x + 1) % dx;

			} else {
				this.y = (this.y - 1) % dy;
			}
		}

	}

	public void GOnaissance() {
		if (enceinte) 
		{

			Goto(120, 120);
			if (x == 120 && y == 120) {
				world.getbebeAgentListe().add(new BebeAgent(x, y, world));
				this.enceinte = false;
				//this.var=true;
				this.action2 = 0;
				this.fec = 1;

			}

		}
	}

	public void reproduce() //reproduit tous les agents selon les conditions de reproduction
	{

		for (FAgent f : world.getFAgentListe()) {
			for (MAgent m : world.getMAgentListe()) {
				if (m.getX() == f.getX() && m.getY() == f.getY() && this == f && f.fec > 0) {
					f.enceinte = true;
					f.fec--;
					m.stop = false;
				}
			}
		}

	}

	// retrouver l'arbre le plus proche de l'fagent
	public int ArbrePlusProche() {

		// l'indice de l'arbre le plus proche 
		int indice_arbre_proche = 0;
		double distance_min = 999999; // une grande distance dans un premier temps 
		// parcourir tous les arbres
		for (int i = 0; i < world.getArbreListe().size(); i++) {

			// verifier que c'est un arbre 
			// calculer la distance euclidienne entre l'fagent et cette arbre //
			double distance_fagent_arbre = distanceTo(this, world.getArbreListe().get(i));
			// si la distance avec cet arbre est moins que la distance minimale actuelle
			// on prend cet arbre comme la plus proche pour le moment
			if (distance_fagent_arbre < distance_min) {
				distance_min = distance_fagent_arbre;
				indice_arbre_proche = i;

			}
		}
		return indice_arbre_proche;
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

		int indice_arbre_proche = 0;
		double distance_min = 999999;
		for (int i = 0; i < world.getMAgentListe().size(); i++) {

			if (world.getMAgentListe().get(i) instanceof MAgent) {

				double distance_fagent_arbre = distanceTo(this, world.getMAgentListe().get(i));

				if (distance_fagent_arbre < distance_min) {
					distance_min = distance_fagent_arbre;
					indice_arbre_proche = i;
				}
			}
		}

		return indice_arbre_proche;

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

					faim = faim - 50;//cible.getnbBaie();
					cible.a = true;
					cible.it = world.getIteration();
				}
			}
		} else {
			eat = false;
		}

	}

	public void GORepro() {

		MAgent Agent = world.getMAgentListe().get(AgentPlusProche());
		Goto(Agent.getX(), Agent.getY());

		if (distanceTo(this, Agent) <= 20) {
			Agent.stop = true;
		}
		reproduce();

	}

	public void randomstep() {
		var2 = true;
		double dice = Math.random();
		int dx = world.getWidth() - 1;
		int dy = world.getHeight() - 1;
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

	public boolean distanceAZ(int dist) {
		if (world.getZombieListe().size() > 0) {
			int z = ZombiePlusProche();
			return distanceTo(this, world.getZombieListe().get(z)) <= dist;
		}
		return false;
	}

	@Override
	public FAgent clone() //clone un agent
	{
		return new FAgent(x, y, world);
	}

	public static double distanceTo(FAgent a, GrandArbre grandArbre) //verifie la distance relative entre deux agents
	{
		return Math.sqrt(Math.pow(a.x - grandArbre.getX(), 2) + Math.pow(a.y - grandArbre.getY(), 2));
	}

	public static double distanceTo(FAgent a, MAgent mAgent) //verifie la distance relative entre deux agents
	{
		return Math.sqrt(Math.pow(a.x - mAgent.getX(), 2) + Math.pow(a.y - mAgent.getY(), 2));
	}

	public static double distanceTo(FAgent a, Zombie b) //verifie la distance relative entre deux agents
	{
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
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
		float altitude = (float) height * normalizeHeight; // test, a enlever apres

		if (swim) {
			gl.glColor3f(0.f, 0.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

		
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

		
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

		
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

		
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

	
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

		
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

	
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);


			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);

		} else {
			gl.glColor3f(1.f, 0.f, 1.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

	
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);


			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight);

		
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 4.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight);

		
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX - lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY + lenY, height * normalizeHeight + 5.f);
			gl.glVertex3f(offset + x2 * stepX + lenX, offset + y2 * stepY - lenY, height * normalizeHeight + 5.f);

		}
	}
}
