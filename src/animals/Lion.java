package animals;

import diet.Carnivore;
import graphics.ZooPanel;
import mobility.Point;

public class Lion extends Animal {
	/**
	 * empty constructor
	 */
	public Lion() {
	}
	/**
	 * set Lion animal, set all the parameters
	 * @param s
	 * @param x
	 * @param y
	 * @param h
	 * @param v
	 * @param c
	 * @param p
	 * @return
	 */
	public Animal setLion(int s, int x, int y, int h, int v, String c, ZooPanel p){
		setAnimal("Lion", (int) (s * 0.745), (int) (s * 0.8), h, v, c, p);
		realSize=s;
		setLocation(new Point(x, y));
		setDiet(new Carnivore());
		loadImages("lio");
		cor_x4 = 0;
		cor_y1 = (int) (-30 - size / 3);
		cor_y3 = (int) (size * 0.25);
		cor_x5 = cor_x6 = -size / 2;
		cor_y5 = cor_y6 = -size / 3;
		cor_h = (int) (size * 0.73);
		return this;
	}
	
	
}
