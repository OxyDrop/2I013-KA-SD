package objects.Consommables;

import worlds.World;

/**
 *
 * @author Serero
 */
public class Coco extends Aliment{
	private boolean isConsommable;
	private int vie;

	public Coco(int x, int y, World world) {
		super(x, y, world);
	}
	@Override
	public boolean isConsommable() {
		return isConsommable;
	}
	
}
