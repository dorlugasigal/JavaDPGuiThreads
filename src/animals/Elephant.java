package animals;

import diet.Herbivore;
import graphics.ZooPanel;
import mobility.Point;

public class Elephant extends Animal {
	/**
	 * empty constructor
	 */
	public Elephant() {
	}
	/**
	 * set Elephant animal, set all the parameters
	 * @param s
	 * @param x
	 * @param y
	 * @param h
	 * @param v
	 * @param c
	 * @param p
	 * @return
	 */
	public Animal setElphant(int s, int x, int y, int h, int v, String c, ZooPanel p){
		setAnimal("Elephant", (int) (s * 1.4), (s - 45), h, v, c, p);
		realSize=s;
		setLocation(new Point(x, y));
		setDiet(new Herbivore());
		loadImages("elf");
		cor_x3 = (int) (-size * 0.3);
		cor_y1 = (int) (-30 - size * 0.45);
		cor_y3 = (int) (size * 0.25);
		cor_x5 = -size * 3 / 4;
		cor_x6 = -size * 1 / 5;
		cor_y5 = cor_y6 = -size / 4;
		cor_h = (int) (size * 0.7);
		return this;
	}

}
