package Periode;

public class Annee //tableau de 12 mois
{
	private final int[][] annee; 
	public Annee()
	{
		annee = new int[][]
		{
			Mois.getMois("janvier"),Mois.getMois("fevrier"),Mois.getMois("mars"),Mois.getMois("avril"),
			Mois.getMois("mai"),Mois.getMois("juin"),Mois.getMois("juillet"),Mois.getMois("aout"),
			Mois.getMois("septembre"),Mois.getMois("octobre"),Mois.getMois("novembre"),Mois.getMois("decembre")
		};
	}

	public int[][] getAnnee() {
		return annee;
	}
	
}
