package animals;

import diet.Omnivore;
import graphics.ZooPanel;
import mobility.Point;

public class Bear extends Animal {
	/**
	 * empty constructor
	 */
	public Bear() {
	 }
	/**
	 * set Bear animal, set all the parameters
	 * @param s
	 * @param x
	 * @param y
	 * @param h
	 * @param v
	 * @param c
	 * @param p
	 * @return
	 */
	public Animal setBear(int s,int x, int y, int h, int v, String c, ZooPanel p){
		setAnimal("Bear",(int)(s*0.7),s,h,v,c, p);
		realSize=s;
		setLocation(new Point(x,y));
		 setDiet(new Omnivore());
		 loadImages("bea");
		 cor_x3 = -size/2;
		 cor_x4 = 0;
		 cor_y1 = (int) (-30-size/5);
		 cor_y3 = (int) (size*0.3);
		 cor_x5 = -size*6/7;
		 cor_y5 = cor_y6 = -size/3;
		 cor_h = (int)(size*2/3);
		return this;
	}
	
}
