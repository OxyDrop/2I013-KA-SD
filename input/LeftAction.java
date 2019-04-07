package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class LeftAction extends AbstractAction {
	Landscape land;
	public LeftAction(Landscape land)
	{
		super("Left");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setMovingY((land.getMovingY() + 1 ) % (land.getDyView()-1));
	}
	
}
