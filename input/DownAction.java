package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class DownAction extends AbstractAction{
	
	Landscape land;
	public DownAction(Landscape land)
	{
		super("Down");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setMovingX((land.getMovingX()-1 + land.getDxView()) % land.getDxView());
	}
	
}
