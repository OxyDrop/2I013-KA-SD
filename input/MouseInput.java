package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


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
	public void mouseClicked(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}
}
