package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class AltitudeDecreaseAction extends AbstractAction {
	Landscape land;
	public AltitudeDecreaseAction(Landscape land)
	{
		super("AltitudeDecrease");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setModuleAltitude(land.getModuleAltitude()-3);
	}
	
}
