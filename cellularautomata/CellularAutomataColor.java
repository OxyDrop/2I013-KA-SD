// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package cellularautomata;

public class CellularAutomataColor extends CellularAutomata {

	protected float Buffer0[][][];
	protected float Buffer1[][][];
	
	//Initialise toutes les cases à blanc
	public CellularAutomataColor ( int dx , int dy, boolean buffering )
	{
		super(dx,dy,buffering);

		Buffer0 = new float[dx][dy][3];
		Buffer1 = new float[dx][dy][3];
		
	    for ( int x = 0 ; x != dx ; x++ )
	    	for ( int y = 0 ; y != dy ; y++ )
	    	{
    			Buffer0[x][y][0]=255;
    			Buffer0[x][y][1]=255;
    			Buffer0[x][y][2]=255;
    			Buffer1[x][y][0]=255;
    			Buffer1[x][y][1]=255;
    			Buffer1[x][y][2]=255;
	    	}
	}
	
	public float[] getCellState ( int x, int y )
	{
		checkBounds (x,y);
		
		float color[] = new float[3];

		if ( buffering == false )
		{
			color[0] = Buffer0[x][y][0];
			color[1] = Buffer0[x][y][1];
			color[2] = Buffer0[x][y][2];
		}
		else
		{
			if ( activeIndex == 1 ) // read old buffer
			{
				color[0] = Buffer0[x][y][0];
				color[1] = Buffer0[x][y][1];
				color[2] = Buffer0[x][y][2];
			}
			else
			{
				color[0] = Buffer1[x][y][0];
				color[1] = Buffer1[x][y][1];
				color[2] = Buffer1[x][y][2];
			}
		}
		
		return color;
	}
	
	public void setCellState ( int x, int y, float r, float g, float b ) //modifie couleur case
	{
		checkBounds (x,y);
		//RGB 0-255 to 0.0-0.1f >> x/255f
		if ( r > 1.0f || g > 1.0f || b > 1.0f )
		{
			System.err.println("[WARNING] CellularAutomataColor - value must be in [0.0,1.0[ ( was: " + r + "," + g + "," + b + " ) -- THRESHOLDING.");
			if ( r > 1.0f ) r = 1.0f;
			if ( g > 1.0f ) g = 1.0f;
			if ( b > 1.0f ) b = 1.0f;
		}
		
		if ( buffering == false )
		{
			Buffer0[x][y][0] = r;
			Buffer0[x][y][1] = g;
			Buffer0[x][y][2] = b;
		}
		else
		{
			if ( activeIndex == 0 ) // write new buffer
			{
				Buffer0[x][y][0] = r;
				Buffer0[x][y][1] = g;
				Buffer0[x][y][2] = b;
			}
			else
			{
				Buffer1[x][y][0] = r;
				Buffer1[x][y][1] = g;
				Buffer1[x][y][2] = b;
			}
		}
	}
	
	public void setCellState ( int x, int y, float color[] ) //modifie couleur case
	{
		checkBounds (x,y);
			
		if ( color[0] > 1.0 || color[1] > 1.0 || color[2] > 1.0 )
		{
			System.err.println("[WARNING] CellularAutomataColor - value must be in [0.0,1.0[ ( was: " + color[0] + "," + color[1] + "," + color[2] + " ) -- THRESHOLDING.");
			if ( color[0] > 1.0f ) color[0] = 1.0f;
			if ( color[1] > 1.0f ) color[1] = 1.0f;
			if ( color[2] > 1.0f ) color[2] = 1.0f;
		}
		
		if ( buffering == false )
		{
			Buffer0[x][y][0] = color[0];
			Buffer0[x][y][1] = color[1];
			Buffer0[x][y][2] = color[2];
		}
		else
		{
			if ( activeIndex == 0 )
			{
				Buffer0[x][y][0] = color[0];
				Buffer0[x][y][1] = color[1];
				Buffer0[x][y][2] = color[2];
			}
			else
			{
				Buffer1[x][y][0] = color[0];
				Buffer1[x][y][1] = color[1];
				Buffer1[x][y][2] = color[2];
			}	
		}
	}
	
	public float[][][] getCurrentBuffer()
	{
		if ( activeIndex == 0 || buffering == false ) 
			return Buffer0;
		else
			return Buffer1;		
	}
	
}
