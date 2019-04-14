package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class FoVDecreaseAction extends AbstractAction {
	Landscape land;
	public FoVDecreaseAction(Landscape land)
	{
		super("FoVDecrease");
		this.land=land;
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setModuleDepth(land.getModuleDepth()-10);
	}
	
}