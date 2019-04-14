package input;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Serero
 */
public class PrintHelpAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		System.out.println(
						"Help:\n" +
						" [v] change view\n\t" +
						"[o] objects display on/off\n\t" +
						"[1] decrease altitude booster\n\t" +
						"[2] increase altitude booster\n\t" +
						"(shift+)[cursor keys] navigate in the landscape\n\t" +
						"[q/d] rotation wrt landscape\n\t" +
						"[z/s] increase/decrease height\n\t"+
						"[crtl]+[z/s] increase/decrease depth\n\t" +
						"[cursor keys] navigate\n\t,"
						+ "Souris : Scrollez et appuyez sur les differents boutons en scrollant"
						+ " Afin d'activer les fonctions decrites ci dessus"
						);
	}
	
	
}
