package input;

import graphics.Landscape;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class ViewFromAboveAction extends AbstractAction {

	public ViewFromAboveAction() {
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
			Landscape.setVIEW_FROM_ABOVE(!Landscape.isVIEW_FROM_ABOVE()) ;
	}
	
}
