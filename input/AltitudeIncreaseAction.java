package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class AltitudeIncreaseAction extends AbstractAction {
	Landscape land;
	public AltitudeIncreaseAction(Landscape land)
	{
		super("AltitudeIncrease");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setModuleAltitude(land.getModuleAltitude()+3);
	}
	
}
