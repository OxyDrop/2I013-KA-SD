// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import graphics.Landscape;
import javax.swing.JOptionPane;
import worlds.*;

public class MyEcosystem {
    
	public static void main(String[] args) {
		
		int choix = Integer.parseInt(JOptionPane.showInputDialog(null,
					"\tMode de lancement\n\tChoississez une option :\n1>Fenetre\n2>Console\n3>Normal\n4>Graphique"),
					JOptionPane.INFORMATION_MESSAGE);
		switch(choix)
		{
			case 1: 	
				WorldOfTrees wTree = new WorldOfTrees();
				WorldOfSand wSand = new WorldOfSand();
				WorldOfSnow wSnow = new WorldOfSnow();
				
				String[] path = {"Perlin1.png", "Perlin2.png", "Perlin3.png", "Perlin4.png", "Perlin5.png", "Perlin6.png",
								"Fractal1.png", "Fractal2.png", "fun1.png", "fun2.png", "canyon.png", "gouffre.png", 
								"defaultM.png", "defaultS", "default2S.png", "paris.png", "random1.png", "random2.png",
								"random3.png", "volcano1.png", "volcano2.png"};
	
				int choixW =	Integer.parseInt(JOptionPane.showInputDialog(null,
								"Choisir Monde\n\t1>Tree\n2>Sand\n3>Snow"),
								JOptionPane.INFORMATION_MESSAGE);
				
				int choixRF =	Integer.parseInt(JOptionPane.showInputDialog(null,
								"Landscape en : \n\t1>Fichier\n2>Bruit de Perlin"),
								JOptionPane.INFORMATION_MESSAGE);
				
				switch(choixRF)
				{
					case 1: 
						break;
					case 2: break;
					default:
						JOptionPane.showMessageDialog(null, "Entrée incorrecte, réessayez");
						System.exit(1);
				}
				
				Landscape myLandscapeGen = new Landscape(wTree,200,200, 0.8, 0.2);
				Landscape.run(myLandscapeGen);
				break;
			case 2: break;
			case 3: break;
			default :
				JOptionPane.showMessageDialog(null, "Entrée incorrecte, réessayez");
				System.exit(1);
		}
	
		DialogAppli dialog = new DialogAppli();
		Transfert info = new Transfert();
		dialog.LanceDialog(info);
		if(info.file)
		{ 
			System.out.println("okfile");
			Landscape myLandscape = new Landscape(info.choosen, "landscape_default-200.png", info.altitude, info.waterlevel);
			Landscape.run(myLandscape);
		}
				
		else if(info.random)
		{
			System.out.println("okrand");
			Landscape myLandscapeGen = new Landscape(info.choosen,200,200, info.altitude, info.waterlevel);
			Landscape.run(myLandscapeGen);
		}
		
		//
		
		
		
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
