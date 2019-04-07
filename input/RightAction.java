package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class RightAction extends AbstractAction{
	
	Landscape land;
	public RightAction(Landscape land)
	{
		super("Right");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
			land.setMovingY((land.getMovingY()-1 + land.getDyView()) % land.getDyView());
	}
	
}
