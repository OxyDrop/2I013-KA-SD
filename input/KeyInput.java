package input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

/**
 *
 * @author Serero
 */
public class KeyInput implements KeyListener {

	private static boolean[] keys = new boolean[256];

	@Override
	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
	}
	
	public static boolean getKey(int keyCode)
	{
		return keys[keyCode];
	}

}
