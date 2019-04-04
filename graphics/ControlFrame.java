package graphics;
import javax.swing.*; 
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
/**
 *
 * @author Serero
 */
public class ControlFrame extends JFrame implements ActionListener
{
	private JButton slower, faster, skip; //va plus ou moins vite sur les jours
	private JPanel ControlPanel;
	private JTextField preda,proies;
	private JLabel predateur, proie;
	private JTextArea infoUtiles;
	public ControlFrame()
	{
		setSize(200,600);
		setLocation(new Point(1200,800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Panneau de controle");
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
	}
	
}

class Info //Classe pour le transfert des données
{
	
}


