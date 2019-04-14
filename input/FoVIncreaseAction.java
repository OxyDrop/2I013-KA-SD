package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class FoVIncreaseAction extends AbstractAction {
	Landscape land;
	public FoVIncreaseAction(Landscape land)
	{
		super("FovIncrease");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setModuleDepth(land.getModuleDepth()-10);
	}
	
}