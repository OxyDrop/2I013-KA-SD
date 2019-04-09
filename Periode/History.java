package Periode;

/**
 *
 * @author Serero
 */
public class History //Etendue de temps
{
	private final static int INTERVAL = 100;
	private final Annee[] history; 
	private int i=0;
	
	public History()
	{
		history = new Annee[INTERVAL];
		for(int i=0;i<=INTERVAL;i++)
		{
			history[i]=new Annee();
			
		}
	}

	public int getHistory() 
	{
		return i++;
	}
	
}
