package applications.simpleworld;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import worlds.World;
import worlds.WorldOfSand;
import worlds.WorldOfSnow;
import worlds.WorldOfTrees;
/**
 *
 * @author Serero
 */

/**
 *
 * @author Serero
 */
public class DialogAppli extends JDialog implements ActionListener
{

	private final JLabel altitude;
	private JLabel waterlevel, explication;
	private final JTextField inputalt;
	private JTextField inputwater;
	private final JButton ok;
	private JButton annuler;
	private final JCheckBox cbFile;
	private JCheckBox cbRandom;
	private final JList chooseWorld;
	
	private static final World[] WORLD = {new WorldOfTrees(),new WorldOfSand(),new WorldOfSnow()};
	private static JComponent[] auto;
	
	private boolean confirm = false;
	public DialogAppli()
	{
		setSize(700,200);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		toFront();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setLocation(dim.width/3, dim.height/3);
				
		explication = new JLabel("1:Tree|2:Sand|3:Snow");
		altitude = new JLabel("Altitude : ");
		waterlevel = new JLabel("Eau : ");
		inputalt = new JTextField(5);
		inputwater = new JTextField(5);
		ok = new JButton("Ok");
		annuler = new JButton("Annuler");
		cbFile = new JCheckBox("Fichier");
		cbRandom = new JCheckBox("Random");
		chooseWorld = new JList(WORLD);
		auto = new JComponent[]{explication,chooseWorld,altitude,inputalt,waterlevel,inputwater,cbFile,cbRandom,ok,annuler};
		confirm = false;
		
		Container contenu = getContentPane();
		contenu.setLayout(new FlowLayout());
		for(JComponent jc : auto)
			contenu.add(jc);
		ok.addActionListener(this);
		annuler.addActionListener(this);
		cbFile.addActionListener(this);
		cbRandom.addActionListener(this);
		
	}
		
	public void LanceDialog(Transfert info)
	{
		setVisible(true);
		requestFocus();
		while(!confirm){}
		if(confirm)
		{
			info.altitude=Double.parseDouble(inputalt.getText());
			info.waterlevel=Double.parseDouble(inputwater.getText());
			info.file=cbFile.isSelected();
			info.random=cbRandom.isSelected();
			info.choosen=(World)chooseWorld.getSelectedValue();
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==ok){
			confirm = true;
			setVisible(false);
		}
		if(e.getSource()==annuler)
			System.exit(0);
	}
}

