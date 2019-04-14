package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class HeightIncreaseAction extends AbstractAction {
	Landscape land;
	public HeightIncreaseAction(Landscape land)
	{
		super("HeightIncrease");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setHeightBooster(land.getHeightBooster()+1);
	}
	
}