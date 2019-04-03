package input;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

/**
 *
 * @author Serero
 */
public class MouseInput implements MouseListener {
	
	private int x;
	private int y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public  static float getWorldX(){
		//retourne (unitsWide / windowWidth * x - unitsWide / 2) + cameraX
		return 0;
	}
	public static float getWorldY(){
		//float unitsTall = (float) unitsWide * ((float) WindowHeight() / (float) WindowWidth());
		//return (unitsTall / windowHeight * y - unitsTall / 2)  + cameraY
		return 0;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseEvent e) {
	}
	
}
