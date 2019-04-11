package input;

import graphics.Landscape;
import java.awt.event.*;

public class PlayerInput implements KeyListener, MouseListener, MouseWheelListener { //Ajouter les actions de landscape ici

		private final Landscape land;
		public PlayerInput(Landscape land)
		{
			this.land=land;
		}
		@Override
		public void keyPressed(KeyEvent key) {
			switch (key.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				new Thread()
				{
					public void run() { Landscape.getAnimator().stop();}
				}.start();
				System.exit(0);
				break;
				
			case KeyEvent.VK_V:
				Landscape.setVIEW_FROM_ABOVE(!Landscape.isVIEW_FROM_ABOVE()) ;
				break;
				
			case KeyEvent.VK_R:
				//MY_LIGHT_RENDERING = !MY_LIGHT_RENDERING;
				break;
				
			case KeyEvent.VK_O:
				Landscape.setDISPLAY_OBJECTS(Landscape.isDISPLAY_OBJECTS());
				break;
				
			case KeyEvent.VK_2:
				land.setHeightBooster(land.getHeightBooster()+1);
				break;
				
			case KeyEvent.VK_1:
				if ( land.getHeightBooster() > 0 )
					land.setHeightBooster(land.getHeightBooster()-1);
				break;
				
			case KeyEvent.VK_UP:
				if(key.isShiftDown())
					land.setMovingX((land.getMovingX() + 5 ) % (land.getDxView()-1));
				else
					land.setMovingX((land.getMovingX() + 1 ) % (land.getDxView()-1));
				break;
				
			case KeyEvent.VK_DOWN:
				if(key.isShiftDown())
					land.setMovingX((land.getMovingX()-5 + land.getDxView()) % land.getDxView());
				else
					land.setMovingX((land.getMovingX()-1 + land.getDxView()) % land.getDxView());
				break;
				
			case KeyEvent.VK_RIGHT:
				if(key.isShiftDown())
					land.setMovingY((land.getMovingY()-5 + land.getDyView()) % land.getDyView());
				else
					land.setMovingY((land.getMovingY()-1 + land.getDyView()) % land.getDyView());
				break;
				
			case KeyEvent.VK_LEFT:
				if(key.isShiftDown())
					land.setMovingY((land.getMovingY() + 5 ) % (land.getDyView()-1));
				else
					land.setMovingY((land.getMovingY() + 1 ) % (land.getDyView()-1));
				break; 
				
			case KeyEvent.VK_Q:
				land.setRotationVelocity(land.getRotationVelocity()-0.1f);
				break;
				
			case KeyEvent.VK_D:
				land.setRotationVelocity(land.getRotationVelocity()+0.1f);			
				break;
				
			case KeyEvent.VK_Z:
				if(key.isControlDown())
					land.setModuleDepth(land.getModuleDepth()-10);
				else
					land.setModuleAltitude(land.getModuleAltitude()-3);
				break;
				
			case KeyEvent.VK_S:
				if(key.isControlDown())
					land.setModuleDepth(land.getModuleDepth()+10);
				else
					land.setModuleAltitude(land.getModuleAltitude()+3);
				break; 
			case KeyEvent.VK_H:
				System.out.println(
						"Help:\n" +
						" [v] change view\n\t" +
						"[o] objects display on/off\n\t" +
						"[1] decrease altitude booster\n\t" +
						"[2] increase altitude booster\n\t" +
						"(shift+)[cursor keys] navigate in the landscape\n\t" +
						"[q/d] rotation wrt landscape\n\t" +
						"[z/s] increase/decrease height\n\t"+
						"[crtl]+[z/s] increase/decrease depth\n\t" +
						"[cursor keys] navigate\n\t,"
						+ "Souris : Scrollez et appuyez sur les differents boutons en scrollant"
						+ " Afin d'activer les fonctions decrites ci dessus"
						);
				break;
			default:
				break;
			}
		}
		@Override
		public void keyReleased(KeyEvent ke) {
		}
		
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		  public void mousePressed(MouseEvent mouse)
		  {
			//example from doublebuf.java
		    switch (mouse.getButton()) 
			{
		      case MouseEvent.BUTTON1:
				  if (mouse.getClickCount() == 2 && !mouse.isConsumed()) 
				{
					land.setRotationVelocity(0f);
					mouse.consume();
				}
		        if(!mouse.isConsumed())
					land.setRotationVelocity(land.getRotationVelocity()+0.2f);
		        break;
				
		      case MouseEvent.BUTTON2:break;
			  
		      case MouseEvent.BUTTON3:
				if (mouse.getClickCount() == 2 && !mouse.isConsumed()) 
				{
					Landscape.setVIEW_FROM_ABOVE(!Landscape.isVIEW_FROM_ABOVE()) ;
					mouse.consume();
				}
				 if(!mouse.isConsumed())
					 land.setRotationVelocity(land.getRotationVelocity()-0.2f);
		        break;
		    }
		    /**/
		  }

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe)
	{
		int source = mwe.getModifiers();
		int notches = mwe.getWheelRotation();
		
		if ((source & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK)  //Avance
			  land.setMovingX((land.getMovingX() + 5 ) % (land.getDxView()-1));
		
		else if ((source & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK){  //Modifie le relief
			  land.setHeightBooster(land.getHeightBooster()+notches);
			  if(land.getHeightBooster()<=0)
				  land.setHeightBooster(0);
		}
		else if ((source & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) //Modifie le champ de profondeur
			 land.setModuleDepth(land.getModuleDepth()+10*notches);
		
		else
			land.setModuleAltitude(land.getModuleAltitude()+5*notches); //Modifie l'altitude

	}
}
