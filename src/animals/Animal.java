package animals;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import mobility.Point;
import diet.IDiet;
import food.EFoodType;
import food.IEdible;
import graphics.ColoredAnimal;
import graphics.IAnimalBehavior;
import graphics.IDrawable;
import graphics.ZooPanel;
/**
 * @author Dor Lugasi and Daniel Saka
 *
 */
public abstract class Animal extends Observable implements IEdible, IDrawable,
		IAnimalBehavior, Runnable, ColoredAnimal, Cloneable {

	protected final int EAT_DISTANCE = 5;
	private IDiet diet;
	protected Point location;
	protected String name;
	private double weight;
	protected int size;
	protected int realSize;
	protected String col;
	protected int horSpeed;
	protected int verSpeed;
	protected boolean coordChanged;
	// protected Thread thread;
	protected int x_dir;
	protected int y_dir;
	protected int eatCount;
	protected ZooPanel pan;
	protected boolean threadSuspended;
	protected BufferedImage img1, img2;
	protected int cor_x1, cor_x2, cor_x3, cor_x4, cor_x5, cor_x6;
	protected int cor_y1, cor_y2, cor_y3, cor_y4, cor_y5, cor_y6;
	protected int cor_w, cor_h;
	private Future<?> task;
	private boolean isRunning = false;
	
	/**
	 * constructor for animal class
	 */
	public Animal() {
	}
/**
 * get food type
 */
	public EFoodType getFoodtype() {
		return EFoodType.MEAT;
	}
/**
 * get diet
 * @return diet
 */
	public IDiet getDiet() {
		return diet;
	}
/**
 * get name
 */
	public String getName() {
		return this.name;
	}
/**
 * get weigth 
 * @return weight 
 */
	public double getWeight() {
		return this.weight;
	}
/**
 * set weight
 * @param w
 */
	public void setWeight(double w) {
		weight = w;
	}
/**
 * set diet
 * @param diet
 */
	protected void setDiet(IDiet diet) {
		this.diet = diet;
	}
/**
 * get size
 */
	public int getSize() {
		return size;
	}
/**
 * get hor speed
 * @return
 */
	public int getHorSpeed() {
		return horSpeed;
	}
/**
 * set hor speed
 * @param hor
 */
	public void setHorSpeed(int hor) {
		horSpeed = hor;
	}
/**
 * get ver speed
 * @return
 */
	public int getVerSpeed() {
		return verSpeed;
	}
/**
 * set vertical speed
 * @param ver
 */
	public void setVerSpeed(int ver) {
		verSpeed = ver;
	}
/**
 * increase eat count with 1
 */
	public void eatInc() {
		eatCount++;
	}
/**
 * get eat count
 */
	public int getEatCount() {
		return eatCount;
	}
/**
 * set thread suspended to true
 */
	synchronized public void setSuspend() {
		threadSuspended = true;
	}
/**
 * set resume and notify
 */
	synchronized public void setResume() {
		threadSuspended = false;
		notify();
	}
/**
 * get coordination changes
 */
	synchronized public boolean getChanges() {
		return coordChanged;
	}
/**
 * set coord changes to state param
 */
	synchronized public void setChanges(boolean state) {
		coordChanged = state;
	}
/**
 * get color
 */
	public String getColor() {
		return col;
	}

	// public void start() { Thread.start(); }
	// public void interrupt() { Thread.interrupt(); }
	/**
	 * set task to task
	 * @param task
	 */
	public void setTask(Future<?> task) {
		this.task = task;
	}
/**
 * get task
 * @return
 */
	public Future<?> getTask() {
		return this.task;
	}
/**
 * get isRunning
 * @return
 */
	public boolean isRunning() {
		return isRunning;
	}
/**
 * function that load the images
 */
	public void loadImages(String nm) {
		switch (getColor()) {
		case "Red":
			try {
				img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_1.png"));
				img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_r_2.png"));
			} catch (IOException e) {
				System.out.println("Cannot load picture");
			}
			break;
		case "Blue":
			try {
				img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_1.png"));
				img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_b_2.png"));
			} catch (IOException e) {
				System.out.println("Cannot load picture");
			}
			break;
		default:
			try {
				img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_n_1.png"));
				img2 = ImageIO.read(new File(PICTURE_PATH + nm + "_n_2.png"));
			} catch (IOException e) {
				System.out.println("Cannot load picture");
			}
		}
	}
/**
 * animal run method, the movement of the animal 
 */
	public void run() {
		isRunning = true;
		while (true) {
			try {
				Thread.sleep(25);
				synchronized (this) {
					while (threadSuspended)
						wait();
				}
			} catch (InterruptedException e) {
				System.out.println(getName() + " dead...");
				return;
			}

			if (this.getDiet().canEat(pan.checkFood())) {
				double oldSpead = Math.sqrt(horSpeed * horSpeed + verSpeed
						* verSpeed);
				double newHorSpeed = oldSpead
						* (location.getX() - pan.getWidth() / 2)
						/ (Math.sqrt(Math.pow(location.getX() - pan.getWidth()
								/ 2, 2)
								+ Math.pow(location.getY() - pan.getHeight()
										/ 2, 2)));
				double newVerSpeed = oldSpead
						* (location.getY() - pan.getHeight() / 2)
						/ (Math.sqrt(Math.pow(location.getX() - pan.getWidth()
								/ 2, 2)
								+ Math.pow(location.getY() - pan.getHeight()
										/ 2, 2)));
				int v = 1;
				if (newVerSpeed < 0) {
					v = -1;
					newVerSpeed = -newVerSpeed;
				}
				if (newVerSpeed > 10)
					newVerSpeed = 10;
				else if (newVerSpeed < 1) {
					if (location.getY() != pan.getHeight() / 2)
						newVerSpeed = 1;
					else
						newVerSpeed = 0;
				}
				int h = 1;
				if (newHorSpeed < 0) {
					h = -1;
					newHorSpeed = -newHorSpeed;
				}
				if (newHorSpeed > 10)
					newHorSpeed = 10;
				else if (newHorSpeed < 1) {
					if (location.getX() != pan.getWidth() / 2)
						newHorSpeed = 1;
					else
						newHorSpeed = 0;
				}
				location.setX((int) (location.getX() - newHorSpeed * h));
				location.setY((int) (location.getY() - newVerSpeed * v));
				if (location.getX() < pan.getWidth() / 2)
					x_dir = 1;
				else
					x_dir = -1;
				if ((Math.abs(location.getX() - pan.getWidth() / 2) < EAT_DISTANCE)
						&& (Math.abs(location.getY() - pan.getHeight() / 2) < EAT_DISTANCE)) {
					pan.eatFood(this);
				}
			} else {
				location.setX(location.getX() + horSpeed * x_dir);
				location.setY(location.getY() + verSpeed * y_dir);
			}

			if (location.getX() > pan.getWidth() + cor_x1) {
				x_dir = -1;
				if (cor_x2 != -1)
					location.setX(location.getX() + cor_x2);
			} else if (location.getX() < cor_x3) {
				x_dir = 1;
				if (cor_x4 != -1)
					location.setX(cor_x4);
			}

			if (location.getY() > (pan.getHeight() + cor_y1)) {
				y_dir = -1;
				if (cor_y2 != -1)
					location.setY(location.getY() + cor_y2);
			} else if (location.getY() < cor_y3) {
				y_dir = 1;
				if (cor_y4 != -1)
					location.setY(cor_y4);
			}
			this.setChanged();
			this.notifyObservers(pan);
		}
	}
/**
 * draw the object method
 */
	public void drawObject(Graphics g) {
		if (x_dir == 1) // an animal goes to right side
		{
			g.drawImage(img1, location.getX() + cor_x5, location.getY()
					+ cor_y5, cor_w, cor_h, pan);
		} else // an animal goes to left side
		{
			g.drawImage(img2, location.getX() + cor_x6, location.getY()
					+ cor_y6, cor_w, cor_h, pan);
		}

	}
/**
 * to string override
 */
	public String toString() {
		return "[" + getName() + ": running=" + isRunning + ", weight="
				+ weight + ", color=" + col + "]";
	}
/**
 * set animal is equivalent to the constructor. set animal details 
 * @param nm
 * @param sz
 * @param w
 * @param hor
 * @param ver
 * @param c
 * @param p
 */
	public void setAnimal(String nm, int sz, int w, int hor, int ver, String c,
			ZooPanel p) {
		name = new String(nm);
		size = sz;
		weight = w;
		horSpeed = hor;
		verSpeed = ver;
		col = c;
		pan = p;
		x_dir = 1;
		y_dir = 1;
		cor_x1 = cor_x3 = cor_x5 = cor_x6 = 0;
		cor_y1 = cor_y3 = cor_y5 = cor_y6 = 0;
		cor_x2 = cor_y2 = cor_x4 = cor_y4 = -1;
		cor_w = cor_h = size;
		coordChanged = false;
		// thread = new Thread(this);
	}
/**
 * paint animal method change the color, load different images
 */
	@Override
	public void PaintAnimal(String color) {
		this.col = color;
		loadImages(animlsChangeColor());
	}
/**
 * return the name of the animal for the function above
 * @return
 */
	public String animlsChangeColor() {
		switch (name) {
		case "Lion":
			return "lio";
		case "Elephant":
			return "elf";
		case "Giraffe":
			return "grf";
		case "Bear":
			return "bea";
		default:
			return "trt";
		}
	}
/**
 * get location 
 * @return
 */
	public Point getLocation() {
		return location;
	}
/**
 * set location
 * @param location
 */
	public void setLocation(Point location) {
		this.location = location;
	}
/**
 * clone method 
 * return a copy of the object
 */
	public Object clone() {
		Object clone = null;

		try {
			clone = super.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		((Animal)clone).setLocation(location);
		return clone;
	}
	/**
	 * get real size of animal
	 * @return
	 */
	public int getRealSize() {
		return realSize;
	}
	/**
	 * set isRunning to isRunning
	 * @param isRunning
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public int getXdir(){
		return x_dir;
	}
	public int getYdir(){
		return y_dir;
	}
	public void setXdir(int a){
		 x_dir=a;
	}
	public void setYdir(int a){
		 y_dir=a;
	}
	
	
	
}
