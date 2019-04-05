package applications.simpleworld;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Serero
 */

/**
 *
 * @author Serero
 */
public class DialogAppli extends JDialog implements ActionListener, ItemListener
{
	private JLabel altitude, waterlevel;
	private JTextField inputalt, inputwater;
	private JButton ok, annuler;
	private JCheckBox cbFile, cbRandom;
	private static JComponent[] auto;
	
	private static boolean confirm = false;
	public DialogAppli(Object o)
	{
		setSize(400,200);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setLocation(dim.width/2, dim.height/2);
				
		altitude = new JLabel("Altitude : ");
		waterlevel = new JLabel("Eau : ");
		inputalt = new JTextField(5);
		inputwater = new JTextField(5);
		JButton ok = new JButton("Ok");
		JButton annuler = new JButton("Annuler");
		JCheckBox cbFile = new JCheckBox("Fichier");
		JCheckBox cbRandom = new JCheckBox("Random");
		auto = new JComponent[]{altitude,inputalt,waterlevel,inputwater,cbFile,cbRandom,ok,annuler};
		
		Container contenu = getContentPane();
		contenu.setLayout(new FlowLayout());
		for(JComponent jc : auto)
			contenu.add(jc);
		ok.addActionListener(this);
		annuler.addActionListener(this);
		cbFile.addItemListener(this);
		cbRandom.addItemListener(this);
		
	}
	public void LanceDialg(Transfert info)
	{
		setVisible(true);
		if(confirm)
		{
			info.altitude=(float)Double.parseDouble(inputalt.getText());
			info.waterlevel=(float)Double.parseDouble(inputwater.getText());
			info.file=cbFile.isSelected();
			info.random=cbRandom.isSelected();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton sourceb = (JButton)e.getSource();
		
		if(sourceb==ok)
			confirm = true;
		if(sourceb==annuler)
			System.exit(0);
		
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		
		JCheckBox source = (JCheckBox)ie.getItemSelectable();
		if(ie.getItemSelectable()==cbFile)
			cbRandom.setSelected(cbFile.isSelected());
		if(ie.getItemSelectable()==cbRandom)
			cbFile.setSelected(cbRandom.isSelected());
	}
}
