package plants;

import graphics.ZooPanel;

public class Cabbage extends Plant {
	private static Cabbage instance = null;
/**
 * Cabbage constructor
 * @param pan
 */
	public Cabbage(ZooPanel pan) {
		super(pan);
		loadImages("cabbage");
	}
/**
 * check if there is instance of cabbage, if not, create new Cabbage and return it, else return Cabbage instance
 * @param f
 * @return
 */
	public static Cabbage getInstance(ZooPanel f) {
		if (instance == null)
			instance = new Cabbage(f);
		return instance;
	}
}
