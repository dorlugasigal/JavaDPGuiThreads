package animals;

import diet.Herbivore;
import graphics.ZooPanel;
import mobility.Point;

public class Giraffe extends Animal {
	/**
	 * empty constructor
	 */
	public Giraffe() {
	}
	/**
	 * set Giraffe animal, set all the parameters
	 * @param s
	 * @param x
	 * @param y
	 * @param h
	 * @param v
	 * @param c
	 * @param p
	 * @return
	 */
	public Animal setGiraffe(int s, int x, int y, int h, int v, String c, ZooPanel p){
		setAnimal("Giraffe", s * 4 / 3, s * 2, h, v, c, p);
		realSize=s;
		setLocation(new Point(0, 0));
		setDiet(new Herbivore());
		loadImages("grf");
		cor_x1 = size / 4;
		cor_x2 = (-size / 4);
		cor_x3 = (int) (-size * 0.25);
		cor_x4 = (int) (size * 0.25);
		cor_y1 = (int) (-30 - size * 9 / 10);
		cor_y3 = size / 10;
		cor_x5 = -size / 2;
		cor_y5 = cor_y6 = -size / 10;
		cor_w = (int) (size * 0.7);
		return this;
	}

}
