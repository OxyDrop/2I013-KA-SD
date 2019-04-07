package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class UpAction extends AbstractAction{
	
	Landscape land;
	public UpAction(Landscape land)
	{
		super("Up");
		this.land=land;
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		land.setMovingX((land.getMovingX() + 1 ) % (land.getDxView()-1));
	}
	
}
