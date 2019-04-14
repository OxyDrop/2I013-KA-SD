package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class HeightDecreaseAction extends AbstractAction {
	Landscape land;
	public HeightDecreaseAction(Landscape land)
	{
		super("HeightDecrease");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if ( land.getHeightBooster() > 0 )
					land.setHeightBooster(land.getHeightBooster()-1);
	}
	
}