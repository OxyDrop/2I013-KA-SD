package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class RotateLeftAction extends AbstractAction {
	Landscape land;
	public RotateLeftAction(Landscape land)
	{
		super("RotateLeft");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setRotationVelocity(land.getRotationVelocity()-0.1f);
	}
	
}