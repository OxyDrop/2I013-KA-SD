// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package cellularautomata;

public class CellularAutomataDouble extends CellularAutomata {

	protected double Buffer0[][];
	protected double Buffer1[][];
	//initialise toutes les cases des deux buffers à 0
	public CellularAutomataDouble ( int dx , int dy, boolean buffering )
	{
		super(dx,dy,buffering);

		Buffer0 = new double[dx][dy];
		Buffer1 = new double[dx][dy];
		
	    for ( int x = 0 ; x != dx ; x++ )
	    	for ( int y = 0 ; y != dy ; y++ )
	    	{
    			Buffer0[x][y]=0;
    			Buffer1[x][y]=0;
	    	}
	}
        //retourne la case, choisis entre les deux buffers selon buffering et activeIndex
	public double getCellState ( int x, int y )
	{
		checkBounds (x,y);
		
		double value;

		if ( buffering == false )
	
			value = Buffer0[x][y];
		else
		{
			if ( activeIndex == 1 ) // read old buffer
				value = Buffer0[x][y];
			else
				value = Buffer1[x][y];
		}
		
		return value;
	}
	
	public void setCellState ( int x, int y, double value )
	{
		checkBounds (x,y);
		
		if ( buffering == false )
			Buffer0[x][y] = value;
		else
		{
			if ( activeIndex == 0 ) // write new buffer
				Buffer0[x][y] = value;
			else
				Buffer1[x][y] = value;
		}
	}
	
	public double[][] getCurrentBuffer()
	{
		if ( activeIndex == 0 || buffering == false ) 
			return Buffer0;
		else
			return Buffer1;		
	}
	
}
