package cellularautomata;

/**
 *
 * @author Serero
 */
public class LiquideCA extends CellularAutomataInteger{
	
	private DarkCA lava;
	private int liqLim;
	
	public LiquideCA(int dx, int dy, boolean buffering, DarkCA lava, int liqLim) 
	{
		super(dx, dy, buffering);
		this.lava=lava;
		this.liqLim=liqLim;
	}
	@Override
	public void init()
	{
		for ( int x = 0 ; x != dx ; x++)
    		for ( int y = 0 ; y != dy ; y++)
			{
				if(lava.getHeightVal().getCellState(x, y)>lava.getLavatitude())
					this.setCellState(x, y, liqLim);
				else 
					this.setCellState(x, y, 0);
			}		
	}
	
	@Override
	public void step()
	{
		
	}

	public DarkCA getLava() {
		return lava;
	}

	public void setLava(DarkCA lava) {
		this.lava = lava;
	}

	public int getLiqLim() {
		return liqLim;
	}

	public void setLiqLim(int liqLim) {
		this.liqLim = liqLim;
	}
	
}
