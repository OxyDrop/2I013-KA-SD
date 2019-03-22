// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import graphics.Landscape;

public class MyEcosystem {
    
	public static void main(String[] args) {

		WorldOfTrees_1 myWorld = new WorldOfTrees_1();
		Landscape myLandscape = new Landscape(myWorld, "landscape_default-200.png", 0.2, 0.1);
		Landscape.run(myLandscape);
		
	/*
		// parametres:
		// 1: le "monde" (ou sont definis vos automates cellulaires et agents
		// 2: (ca d�pend de la methode : generation aleatoire ou chargement d'image)
		// 3: l'amplitude de l'altitude (plus la valeur est �lev�e, plus haute sont les montagnes)
		// 4: la quantite d'eau
		//Landscape myLandscape = new Landscape(myWorld, 128, 128, 0.1, 0.7);
		drole_de_texture-800-600.png
		Fractal1_200.png
		Fractal2_200.png
		gouffre.png
		landscape_canyon-128.png
		landscape_default-128.png
		landscape_default-200.png
		landscape_default2-128.png
		landscape_paris-200.png
		landscape_volcan-200.png
		Perlin1_200.png
		Perlin2_200.png
		Perlin3_200.png
		randomLS.png
		randomLS1.png
		randomLS2.png*/
    }
}
