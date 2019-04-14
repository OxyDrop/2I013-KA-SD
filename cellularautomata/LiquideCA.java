package cellularautomata;

/**
 *
 * @author Serero
 */
public class LiquideCA extends CellularAutomataInteger{
	
	private DarkCA lava;
	public LiquideCA(int dx, int dy, boolean buffering, DarkCA lava) 
	{
		super(dx, dy, buffering);
		this.lava=lava;
	}
	@Override
	public void init()
	{
		for ( int x = 0 ; x != dx ; x++)
    		for ( int y = 0 ; y != dy ; y++)
			{
				if(lava.getHeightVal().getCellState(x, y)>lava.getLavatitude())
					this.setCellState(x, y, 1);
				else
					this.setCellState(x, y, 0);
			}		
	}
}
