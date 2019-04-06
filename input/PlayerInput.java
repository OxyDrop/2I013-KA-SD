package input;

import graphics.Landscape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class PlayerInput implements KeyListener, MouseListener { //Ajouter les actions de landscape ici

		private Landscape land;
		public PlayerInput(Landscape land)
		{
			this.land=land;
		}
		@Override
		public void keyPressed(java.awt.event.KeyEvent key) {
			switch (key.getKeyCode()) {
			case java.awt.event.KeyEvent.VK_ESCAPE:
				new Thread()
				{
					public void run() { Landscape.getAnimator().stop();}
				}.start();
				System.exit(0);
				break;
				
			case java.awt.event.KeyEvent.VK_V:
				Landscape.setVIEW_FROM_ABOVE(!Landscape.isVIEW_FROM_ABOVE()) ;
				break;
				
			case java.awt.event.KeyEvent.VK_R:
				//MY_LIGHT_RENDERING = !MY_LIGHT_RENDERING;
				break;
				
			case java.awt.event.KeyEvent.VK_O:
				Landscape.setDISPLAY_OBJECTS(Landscape.isDISPLAY_OBJECTS());
				break;
				
			case java.awt.event.KeyEvent.VK_2:
				land.setHeightBooster(land.getHeightBooster()+1);
				break;
				
			case java.awt.event.KeyEvent.VK_1:
				if ( land.getHeightBooster() > 0 )
					land.setHeightBooster(land.getHeightBooster()-1);
				break;
				
			case java.awt.event.KeyEvent.VK_UP:
				if(key.isShiftDown())
					land.setMovingX((land.getMovingX() + 5 ) % (land.getDxView()-1));
				else
					land.setMovingX((land.getMovingX() + 1 ) % (land.getDxView()-1));
				break;
				
			case java.awt.event.KeyEvent.VK_DOWN:
				if(key.isShiftDown())
					land.setMovingX((land.getMovingX()-5 + land.getDxView()) % land.getDxView());
				else
					land.setMovingX((land.getMovingX()-1 + land.getDxView()) % land.getDxView());
				break;
				
			case java.awt.event.KeyEvent.VK_RIGHT:
				if(key.isShiftDown())
					land.setMovingY((land.getMovingY()-5 + land.getDyView()) % land.getDyView());
				else
					land.setMovingY((land.getMovingY()-1 + land.getDyView()) % land.getDyView());
				break;
				
			case java.awt.event.KeyEvent.VK_LEFT:
				if(key.isShiftDown())
					land.setMovingY((land.getMovingY() + 5 ) % (land.getDyView()-1));
				else
					land.setMovingY((land.getMovingY() + 1 ) % (land.getDyView()-1));
				break; 
				
			case java.awt.event.KeyEvent.VK_Q:
				land.setRotationVelocity(land.getRotationVelocity()-0.1f);
				break;
				
			case java.awt.event.KeyEvent.VK_D:
				land.setRotationVelocity(land.getRotationVelocity()+0.1f);			
				break;
				
			case java.awt.event.KeyEvent.VK_Z:
				if(key.isControlDown())
					land.setModuleDepth(land.getModuleDepth()-10);
				else
					land.setModuleAltitude(land.getModuleAltitude()-3);
				break;
				
			case java.awt.event.KeyEvent.VK_S:
				if(key.isControlDown())
					land.setModuleDepth(land.getModuleDepth()+10);
				else
					land.setModuleAltitude(land.getModuleAltitude()+3);
				break; 
			case java.awt.event.KeyEvent.VK_H:
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
						"[cursor keys] navigate\n\t"
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
		public void keyTyped(java.awt.event.KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseClicked(java.awt.event.MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseEntered(java.awt.event.MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseExited(java.awt.event.MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		  public void mousePressed(java.awt.event.MouseEvent mouse)
		  {
			//example from doublebuf.java
		    switch (mouse.getButton()) 
			{
		      case MouseEvent.BUTTON1:
		        land.setRotationVelocity(land.getRotationVelocity()+0.1f);
		        break;
		      case MouseEvent.BUTTON2:
		      case MouseEvent.BUTTON3:
				land.setRotationVelocity(0f);
		        break;
		    }
		    /**/
		  }

		@Override
		public void mouseReleased(java.awt.event.MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
}
