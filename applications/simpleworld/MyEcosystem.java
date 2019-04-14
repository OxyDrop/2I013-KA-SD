// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import graphics.Landscape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import worlds.*;

public class MyEcosystem {
	
	static ImageIcon imIc;
	private final static float ALTITUDE= 0.5f;
	private final static float WATERLVL = 0.3f;
	private final static int DX = 200, DY=200;
	
	public static void main(String[] args) {
		
		WorldOfTrees wTree = new WorldOfTrees();
		WorldOfSand wSand = new WorldOfSand();
		WorldOfSnow wSnow = new WorldOfSnow();
		DarkWorld wDark = new DarkWorld();
		
		try {
			BufferedImage bi = ImageIO.read(new File("earthmap1k.png"));
			 imIc = new ImageIcon(bi);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		int choix = Integer.parseInt(JOptionPane.showInputDialog(null,
					"\tMode de lancement\n\tChoississez une option :\n1>Un seul monde\n2>Console\n3>Direct\n"
					+ "4>Fenetre de dialogue\n5>Multiple à boite d'option\n6>Multiple à Onglets",
					JOptionPane.INFORMATION_MESSAGE));
		switch(choix)
		{
			case 1: 	
				
				String[] path = {"Perlin1.png", "Perlin2.png", "Perlin3.png", "Perlin4.png", "Perlin5.png", "Perlin6.png",
								"Fractal1.png", "Fractal2.png", "fun1.png", "fun2.png", "canyon.png", "gouffre.png", 
								"defaultM.png", "defaultS", "default2S.png", "paris.png", "random1.png", "random2.png",
								"random3.png", "volcano1.png", "volcano2.png"};
	
				int choixW =	Integer.parseInt(JOptionPane.showInputDialog(null,
								"Choisir Monde\n\t1>Tree\n2>Sand\n3>Snow\n4>Dark",
								JOptionPane.INFORMATION_MESSAGE));
				
				int choixRF =	Integer.parseInt(JOptionPane.showInputDialog(null,
								"Landscape en : \n\t1>Fichier\n2>Bruit de Perlin",
								JOptionPane.INFORMATION_MESSAGE));
				
				
				switch(choixRF)
				{
					case 1: 
						String file = (String)JOptionPane.showInputDialog(null,"Choississez un fichier", 
						"Choix fichier", 0, imIc ,path, path[0]);					
						switch(choixW)
						{
							case 1: Landscape.run(new Landscape(wTree,file,ALTITUDE,WATERLVL)); break;
							case 2: Landscape.run(new Landscape(wSand,file,ALTITUDE,WATERLVL/3)); break;
							case 3: Landscape.run(new Landscape(wSnow,file,ALTITUDE,WATERLVL/1.5)); break;
							case 4: Landscape.run(new Landscape(wDark,file,ALTITUDE,WATERLVL)); break;
							default:
								JOptionPane.showMessageDialog(null, "Entrée incorrecte, réessayez");
								System.exit(1);
						}
						break;
			
					case 2: 
						switch(choixW)
						{
							case 1 : Landscape.run(new Landscape(wTree,DX,DY,ALTITUDE,WATERLVL)); break;
							case 2 : Landscape.run(new Landscape(wSand,DX,DY,ALTITUDE,WATERLVL/3));break;
							case 3 : Landscape.run(new Landscape(wSnow,DX,DY,ALTITUDE,WATERLVL/1.5)); break;
							case 4: Landscape.run(new Landscape(wDark,DX,DY,ALTITUDE,WATERLVL)); break;
							default:
								JOptionPane.showMessageDialog(null, "Entrée incorrecte, réessayez");
								System.exit(1);
						}
						break;
						
					default:
						JOptionPane.showMessageDialog(null, "Entrée incorrecte, réessayez");
						System.exit(1);
				}
				break;
				
			case 2:
				Scanner sc = new Scanner(System.in);
				System.out.print("Initialisation\n\tfalse:Fichier\n\ttrue:Random\n>");
				boolean choixfile = sc.nextBoolean();
		
				System.out.print("\nUtiliser la virgule\nAltitude : ");
				double altitude = sc.nextDouble();
				
				System.out.print("\nHauteur de l'eau : ");
				double waterLevel = sc.nextDouble();
				
				if(choixfile)
					Landscape.run(new Landscape(new WorldOfTrees(),DX,DY,altitude,waterLevel));
				else
				{
					System.out.println("Entrez le nom d'un fichier\n"
							+ "\"Perlin1.png\", \"Perlin2.png\", \"Perlin3.png\", \"Perlin4.png\", \"Perlin5.png\", \"Perlin6.png\",\n" +
"								\"Fractal1.png\", \"Fractal2.png\", \"fun1.png\", \"fun2.png\", \"canyon.png\", \"gouffre.png\", \n" +
"								\"defaultM.png\", \"defaultS\", \"default2S.png\", \"paris.png\", \"random1.png\", \"random2.png\",\n" +
"								\"random3.png\", \"volcano1.png\", \"volcano2.png\"");
					
					Landscape.run(new Landscape(new WorldOfTrees(),sc.next(),altitude,waterLevel));
				}
				break;
				
			case 3: 
				Landscape.run(new Landscape(wTree,DX,DY,ALTITUDE,WATERLVL)); 
				break;
				
			case 4: //Works only in debug mode
				DialogAppli dialog = new DialogAppli();
				Transfert info = new Transfert();
				dialog.LanceDialog(info);

				if (info.file) {
					System.out.println("okfile");
					Landscape myLandscape = new Landscape(info.choosen, "landscape_default-200.png", info.altitude, info.waterlevel);
					Landscape.run(myLandscape);
					
				} else if (info.random) {
					System.out.println("okrand");
					Landscape.run(new Landscape(info.choosen, DX, DY, info.altitude, info.waterlevel));
				}
				
				break;
				
			case 5:
				wTree.setW1(wSand);
				wTree.setW2(wSnow);
				wTree.setW3(wDark);
				
				wSand.setW1(wTree);
				wSand.setW2(wSand);
				wSand.setW3(wDark);
				
				wSnow.setW1(wTree);
				wSnow.setW2(wSand);
				wSnow.setW3(wDark);
				
				wDark.setW1(wTree);
				wDark.setW2(wSand);
				wDark.setW3(wSnow);
				
				int choixMultipleRF =	Integer.parseInt(JOptionPane.showInputDialog(null,
										"Landscape en : \n\t1>Fichier\n2>Bruit de Perlin",
										JOptionPane.INFORMATION_MESSAGE));
				
				switch (choixMultipleRF)
				{
					case 1:
						String[] pathMultipleFile = {"Perlin1.png", "Perlin2.png", "Perlin3.png", "Perlin4.png", "Perlin5.png", "Perlin6.png",
													"Fractal1.png", "Fractal2.png", "fun1.png", "fun2.png", "canyon.png", "gouffre.png", 
													"defaultM.png", "defaultS", "default2S.png", "paris.png", "random1.png", "random2.png",
													"random3.png", "volcano1.png", "volcano2.png"};
						
						String fileMultiple = (String)JOptionPane.showInputDialog(null,"Choississez un fichier", 
						"Choix fichier", 0, imIc ,pathMultipleFile, pathMultipleFile[0]);
						
						Landscape[] landListeTabFile = {new Landscape(wTree,fileMultiple,ALTITUDE,WATERLVL),
														new Landscape(wSand,fileMultiple,ALTITUDE,WATERLVL/3),
														new Landscape(wSnow,fileMultiple,ALTITUDE,WATERLVL/2),
														new Landscape(wDark,fileMultiple,ALTITUDE*1.2,WATERLVL)};
						
						Landscape.runAll(landListeTabFile);
						break;
						
					case 2:
						Landscape[] landListeTab = {new Landscape(wTree,DX,DY,ALTITUDE,WATERLVL),
													new Landscape(wSand,DX,DY,ALTITUDE,WATERLVL/3),
													new Landscape(wSnow,DX,DY,ALTITUDE,WATERLVL/2),
													new Landscape(wDark,DX,DY,ALTITUDE*1.2,WATERLVL)};
						
						Landscape.runAll(landListeTab);
						break;
						
					default:
						JOptionPane.showMessageDialog(null, "Entrée incorrecte, réessayez");
						System.exit(1);
				}
				break;
				
			case 6 :
				wTree.setW1(wSand);
				wTree.setW2(wSnow);
				wTree.setW3(wDark);
				
				wSand.setW1(wTree);
				wSand.setW2(wSand);
				wSand.setW3(wDark);
				
				wSnow.setW1(wTree);
				wSnow.setW2(wSand);
				wSnow.setW3(wDark);
				
				wDark.setW1(wTree);
				wDark.setW2(wSand);
				wDark.setW3(wSnow);
				
				int choixMultipleRFTab =	Integer.parseInt(JOptionPane.showInputDialog(null,
											"Landscape en : \n\t1>Fichier\n2>Bruit de Perlin",
											JOptionPane.INFORMATION_MESSAGE));
				
				switch (choixMultipleRFTab)
				{
					case 1:
						String[] pathMultipleFileTab = {"Perlin1.png", "Perlin2.png", "Perlin3.png", "Perlin4.png", "Perlin5.png", "Perlin6.png",
														"Fractal1.png", "Fractal2.png", "fun1.png", "fun2.png", "canyon.png", "gouffre.png", 
														"defaultM.png", "defaultS", "default2S.png", "paris.png", "random1.png", "random2.png",
														"random3.png", "volcano1.png", "volcano2.png"};
						
						String fileMultipleTab = (String)JOptionPane.showInputDialog(null,"Choississez un fichier", 
						"Choix fichier", 0, imIc ,pathMultipleFileTab, pathMultipleFileTab[0]);
						
						Landscape[] landListeTabFile = {new Landscape(wTree,fileMultipleTab,ALTITUDE,WATERLVL),
														new Landscape(wSand,fileMultipleTab,ALTITUDE,WATERLVL/3),
														new Landscape(wSnow,fileMultipleTab,ALTITUDE,WATERLVL/2),
														new Landscape(wDark,fileMultipleTab,ALTITUDE*1.2,WATERLVL)};
						
						Landscape.runAllTabbedPane(landListeTabFile);
						break;
						
					case 2:
						Landscape[] landListeTab = {new Landscape(wTree,DX,DY,ALTITUDE,WATERLVL),
													new Landscape(wSand,DX,DY,ALTITUDE,WATERLVL/3),
													new Landscape(wSnow,DX,DY,ALTITUDE,WATERLVL/2),
													new Landscape(wDark,DX,DY,ALTITUDE*1.2,WATERLVL)};
						
						Landscape.runAllTabbedPane(landListeTab);
						break;
						
					default:
						JOptionPane.showMessageDialog(null, "Entrée incorrecte, réessayez");
						System.exit(1);
				}
				break;
				
			default :
				JOptionPane.showMessageDialog(null, "Entrée incorrecte, réessayez");
				System.exit(1);
		}
	}
}
