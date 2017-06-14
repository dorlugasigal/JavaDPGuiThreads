package plants;

import graphics.ZooPanel;

public class Lettuce extends Plant {
	private static Lettuce instance = null;

	/**
	 * Lettuce constructor
	 * 
	 * @param pan
	 */
	public Lettuce(ZooPanel pan) {
		super(pan);
		loadImages("lettuce");
	}

	/**
	 * check if there is instance of Lettuce, if not, create new Lettuce and
	 * return it, else return Lettuce instance
	 * 
	 * @param f
	 * @return
	 */
	public static Lettuce getInstance(ZooPanel f) {
		if (instance == null)
			instance = new Lettuce(f);
		return instance;
	}
}
