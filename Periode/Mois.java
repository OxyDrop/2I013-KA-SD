package Periode;

/**
 *
 * @author Serero
 */
public class Mois //Tableau de jour;
{
	private static final int[] janvier = new int[31];
	private static final int[] fevrier = new int[28];
	private static final int[] mars = new int[31];
	private static final int[] avril = new int[30];
	private static final int[] mai = new int[31];
	private static final int[] juin = new int[30];
	private static final int[] juillet = new int[31];
	private static final int[] aout = new int[31];
	private static final int[] septembre = new int[30];
	private static final int[] octobre = new int[31];
	private static final int[] novembre = new int[30];
	private static final int[] decembre = new int[31];
	
	public static void init()
	{
		for(int i=1;i<=31;i++)
		{
			if(i<=28)
				fevrier[i]=i;
			if(i<=30)
			{
				avril[i]=i;
				juin[i]=i;
				septembre[i]=i;
				novembre[i]=i;
			}
			if(i<=31)
			{
				janvier[i]=i;
				mars[i]=i;
				mai[i]=i;
				juillet[i]=i;
				aout[i]=i;
				octobre[i]=i;
				decembre[i]=i;
			}
		}
	}
	public static int[] getMois(String mois)
	{
		if(mois.equals("janvier"))
			return janvier;
		else if(mois.equals("fevrier"))
			return fevrier;
		else if(mois.equals("mars"))
			return mars;
		else if(mois.equals("avril"))
			return avril;
		else if(mois.equals("mai"))
			return mai;
		else if(mois.equals("juin"))
			return juin;
		else if(mois.equals("juillet"))
			return juillet;
		else if(mois.equals("aout"))
			return aout;
		else if(mois.equals("septembre"))
			return septembre;
		else if(mois.equals("octobre"))
			return octobre;
		else if(mois.equals("novembre"))
			return novembre;
		else if(mois.equals("decembre"))
			return decembre;
		else
			return null;
	}
	
	
}