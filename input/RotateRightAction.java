package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class RotateRightAction extends AbstractAction {
	Landscape land;
	public RotateRightAction(Landscape land)
	{
		super("RotateRight");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setRotationVelocity(land.getRotationVelocity()+0.1f);	
	}
	
}
