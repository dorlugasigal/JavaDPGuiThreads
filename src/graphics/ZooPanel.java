package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.tools.DocumentationTool.Location;

import mobility.Point;
import plants.Cabbage;
import plants.Lettuce;
import plants.Plant;
import animals.Animal;
import animals.Bear;
import animals.Elephant;
import animals.Giraffe;
import animals.Lion;
import animals.Turtle;
import food.EFoodType;

public class ZooPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int MAX_ANIMAL_NUMBER = 10;
	private static final int MAX_QUEUE_NUMBER = 5;
	private final String BACKGROUND_PATH = Animal.PICTURE_PATH + "savanna.jpg";
	private final String MEAT_PATH = Animal.PICTURE_PATH + "meat.gif";
	private ZooFrame frame;
	private EFoodType Food;
	private JPanel p1;
	private JButton[] b_num1;
	private String[] names = { "Add Animal", "Sleep", "Wake up", "Clear",
			"Food", "Info", "Decorate", "Duplicate", "Save state",
			"Restore state", "Exit" };
	private ArrayList<Animal> animals;
	private Plant forFood = null;
	private JScrollPane scrollPane;
	private boolean isTableVisible;
	private int totalCount;
	private BufferedImage img, img_m, bgImage;
	private boolean bgr;
	private ZooObserver controller;
	private ExecutorService pool;
	private static ZooPanel instance = null;
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	private ZooMemento state1, state2, state3;
	private int chooseState;

	public ZooPanel(ZooFrame f)

	{
		pool = Executors.newFixedThreadPool(10);
		frame = f;
		Food = EFoodType.NOTFOOD;
		totalCount = 0;
		isTableVisible = false;

		state1 = new ZooMemento("state1",this);
		state2 = new ZooMemento("state2",this);
		state3 = new ZooMemento("state3",this);
		animals = new ArrayList<Animal>();

		controller = new ZooObserver();

		setBackground(new Color(255, 255, 255));

		p1 = new JPanel();
		p1.setLayout(new GridLayout(2, 0, 0, 0));
		p1.setBackground(Color.lightGray);

		b_num1 = new JButton[names.length];
		for (int i = 0; i < names.length; i++) {
			b_num1[i] = new JButton(names[i]);
			b_num1[i].addActionListener(this);
			b_num1[i].setBackground(Color.lightGray);
			p1.add(b_num1[i]);
		}

		setLayout(new BorderLayout());
		add("South", p1);

		img = img_m = null;
		bgr = false;
		try {
			img = ImageIO.read(new File(BACKGROUND_PATH));
		} catch (IOException e) {
			System.out.println("Cannot load background");
		}
		try {
			img_m = ImageIO.read(new File(MEAT_PATH));
		} catch (IOException e) {
			System.out.println("Cannot load meat");
		}
	}

	/**
	 * draw objects to screen
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (bgr && (img != null))
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

		if (bgImage != null)
			g.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), this);

		if (Food == EFoodType.MEAT)
			g.drawImage(img_m, getWidth() / 2 - 20, getHeight() / 2 - 20, 40,
					40, this);

		if ((Food == EFoodType.VEGETABLE) && (forFood != null))
			forFood.drawObject(g);

		synchronized (this) {
			for (Animal an : animals) {
				if (an.isRunning())
					an.drawObject(g);
			}
		}
	}

	/**
	 * set new background to screen
	 * 
	 * @param num
	 */
	public void setBackgr(int num) {
		switch (num) {
		case 0:
			setBackground(new Color(255, 255, 255));
			bgr = false;
			bgImage = null;
			break;
		case 1:
			setBackground(new Color(0, 155, 0));
			bgr = false;
			bgImage = null;
			break;
		case 2:
			try {
				bgImage = ImageIO.read(new File("src\\pictures\\savanna.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			bgr = false;
			break;
		default:
			bgr = true;
		}
		repaint();
	}

	/**
	 * @return food
	 */
	synchronized public EFoodType checkFood() {
		return Food;
	}

	/**
	 * CallBack function
	 * 
	 * @param f
	 */
	synchronized public void eatFood(Animal an) {
		if (Food != EFoodType.NOTFOOD) {
			if (Food == EFoodType.VEGETABLE)
				forFood = null;
			Food = EFoodType.NOTFOOD;
			an.eatInc();
			totalCount++;
			System.out.println("The " + an.getName() + " with " + an.getColor()
					+ " color and size " + an.getSize() + " ate food.");
		} else {
			System.out.println("The " + an.getName() + " with " + an.getColor()
					+ " color and size " + an.getSize() + " missed food.");
		}
	}

	/**
	 * add animal dialog
	 */
	public void addDialog() {
		if (animals.size() >= MAX_ANIMAL_NUMBER) {
			JOptionPane
					.showMessageDialog(this, "You cannot add more than "
							+ MAX_ANIMAL_NUMBER
							+ " animals, animal will move to queue");
			if (((ThreadPoolExecutor) pool).getQueue().size() == MAX_QUEUE_NUMBER) {
				JOptionPane.showMessageDialog(this, "the Queue is Full.");
			} else {
				AddAnimalDialog dial = new AddAnimalDialog(this,
						"Add an animal to aquarium");
				dial.setVisible(true);
			}

		} else {
			AddAnimalDialog dial = new AddAnimalDialog(this,
					"Add an animal to aquarium");
			dial.setVisible(true);
		}
	}

	/**
	 * check if there is instance of zoo panel and return zoo panel
	 * 
	 * @param f
	 * @return instance
	 */
	public static ZooPanel getInstance(ZooFrame f) {
		if (instance == null)
			instance = new ZooPanel(f);
		return instance;
	}

	/**
	 * add new animal to screen
	 * 
	 * @param animal
	 * @param sz
	 * @param hor
	 * @param ver
	 * @param c
	 * @param location
	 */
	public void addAnimal(Animal animal, int sz, int hor, int ver, String c,Point location,int xDir,int yDir) {
		if (executor == null)
			executor = Executors.newFixedThreadPool(10);
		if (animal instanceof Elephant)
			((Elephant) animal).setElphant(sz, 0, 0, hor, ver, c, this);
		else if (animal instanceof Lion)
			((Lion) animal).setLion(sz, 0, 0, hor, ver, c, this);
		else if (animal instanceof Turtle)
			((Turtle) animal).setTurtle(sz, 0, 0, hor, ver, c, this);
		else if (animal instanceof Bear)
			((Bear) animal).setBear(sz, 0, 0, hor, ver, c, this);
		else
			((Giraffe) animal).setGiraffe(sz, 0, 0, hor, ver, c, this);
		if (location != null)
			animal.setLocation(location);
		if (xDir!=5){
			animal.setXdir(xDir);
			animal.setYdir(yDir);		
		}

		animal.addObserver(controller);
		animal.setTask(((ExecutorService) pool).submit(animal));
		animals.add(animal);
	}

	/**
	 * start animal run()
	 */
	public void start() {
		for (Animal an : animals)
			if (an.isRunning())
				an.setResume();
	}

	/**
	 * stop animal's run
	 */
	public void stop() {
		for (Animal an : animals)
			if (an.isRunning())
				an.setSuspend();
	}

	/**
	 * clear all animals from screen
	 */
	synchronized public void clear() {
		ArrayList<Animal> temp = new ArrayList<Animal>();
		for (Animal an : animals) {
			if (an.isRunning())
				an.getTask().cancel(true);
			else {
				temp.add(an);
			}
		}
		animals = temp;
		Food = EFoodType.NOTFOOD;
		forFood = null;
		totalCount = 0;
		repaint();
	}

	/**
	 * increase eat count to animal
	 * 
	 * @param predator
	 * @param prey
	 */
	synchronized public void preyEating(Animal predator, Animal prey) {
		predator.eatInc();
		totalCount -= (prey.getEatCount() - 1);
	}

	/**
	 * add new food
	 */
	synchronized public void addFood() {
		if (Food == EFoodType.NOTFOOD) {
			Object[] options = { "Meat", "Cabbage", "Lettuce" };
			int n = JOptionPane.showOptionDialog(frame, "Please choose food",
					"Food for animals", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
			switch (n) {
			case 0: // Meat
				Food = EFoodType.MEAT;
				break;
			case 1: // Cabbage
				Food = EFoodType.VEGETABLE;
				forFood = Cabbage.getInstance(this);
				break;
			default: // Lettuce
				Food = EFoodType.VEGETABLE;
				forFood = Lettuce.getInstance(this);
				break;
			}
		} else {
			Food = EFoodType.NOTFOOD;
			forFood = null;
		}
		repaint();
	}

	/**
	 * show info table
	 */
	public void info() {
		if (isTableVisible == false) {
			int i = 0;
			int sz = animals.size();

			String[] columnNames = { "Animal", "Color", "Weight", "Hor. speed",
					"Ver. speed", "Eat counter" };
			String[][] data = new String[sz + 1][columnNames.length];
			for (Animal an : animals) {
				data[i][0] = an.getName();
				data[i][1] = an.getColor();
				data[i][2] = new Integer((int) (an.getWeight())).toString();
				data[i][3] = new Integer(an.getHorSpeed()).toString();
				data[i][4] = new Integer(an.getVerSpeed()).toString();
				data[i][5] = new Integer(an.getEatCount()).toString();
				i++;
			}
			data[i][0] = "Total";
			data[i][5] = new Integer(totalCount).toString();

			JTable table = new JTable(data, columnNames);
			scrollPane = new JScrollPane(table);
			scrollPane.setSize(450, table.getRowHeight() * (sz + 1) + 24);
			add(scrollPane, BorderLayout.CENTER);
			isTableVisible = true;
		} else {
			isTableVisible = false;
		}
		scrollPane.setVisible(isTableVisible);
		repaint();
	}

	/**
	 * exit from program
	 */
	public void destroy() {
		// for(Animal an : animals)
		// an.interrupt();
		executor = null;
		// controller.interrupt();
		System.exit(0);
	}

	/**
	 * Action while pressing on some button from panel
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b_num1[0]) // "Add Animal"
			addDialog();
		else if (e.getSource() == b_num1[1]) // "Sleep"
			stop();
		else if (e.getSource() == b_num1[2]) // "Wake up"
			start();
		else if (e.getSource() == b_num1[3]) // "Clear"
			clear();
		else if (e.getSource() == b_num1[4]) // "Food"
			addFood();
		else if (e.getSource() == b_num1[5]) // "Info"
			info();
		else if (e.getSource() == b_num1[6])// "Decorate"
			decorateDialog();
		else if (e.getSource() == b_num1[7]) // "Duplicate"
			duplicateDialog();
		else if (e.getSource() == b_num1[8]) // "Save state"
			saveState();
		else if (e.getSource() == b_num1[9]) // "Restore state"
			restoreState();
		else if (e.getSource() == b_num1[10]) // "Exit"
			destroy();
	}

	/**
	 * save states
	 */
	private void saveState() {
		Object[] options = { "State 1", "State 2", "State 3", "Cancel" };
		chooseState = JOptionPane.showOptionDialog(this, "Please choose State",
				"Please choose State to save",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[2]);
		switch (chooseState) {
		case 0:
			state1.setState(animals, Food, forFood);
			break;
		case 1:
			state2.setState(animals, Food, forFood);
			break;
		case 2:
			state3.setState(animals, Food, forFood);
			break;
		default:
			break;
		}
	}

	/**
	 * restore saved state
	 */
	private void restoreState() {
		if (state1.isEmpty() && state2.isEmpty() && state3.isEmpty()) {
			JOptionPane.showMessageDialog(this, "You Have Not Saved States");
		} else {
			Object[] options = { "State 1", "State 2", "State 3", "Cancel" };
			chooseState = JOptionPane.showOptionDialog(this,
					"Please choose State", "Please choose State to restore",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
			switch (chooseState) {
			case 0:
				if (state1.isEmpty()) {
					JOptionPane.showMessageDialog(this, "This state is Empty");
				} else {
					state1.getState(this);
					state1.clearState();
				}
				break;
			case 1:
				if (state2.isEmpty()) {
					JOptionPane.showMessageDialog(this, "This state is Empty");
				} else {
					state2.getState(this);
					state2.clearState();
				}
				break;
			case 2:
				if (state3.isEmpty()) {
					JOptionPane.showMessageDialog(this, "This state is Empty");
				} else {
					state3.getState(this);
					state3.clearState();
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * show decorate dialog
	 */
	private void decorateDialog() {
		boolean naturalAnimals = false;
		ArrayList<Integer> natuAn = new ArrayList<Integer>();
		for (Animal an : animals) {
			if (an.getColor().toString() == "Natural") {
				naturalAnimals = true;
				natuAn.add(animals.indexOf(an));
			}
		}
		if (!naturalAnimals) {
			JOptionPane.showMessageDialog(this,
					"You have no Animals for decoration");
		} else {
			DecoratorDialog dialog = new DecoratorDialog(this, natuAn);
			dialog.setVisible(true);
		}
	}

	/**
	 * show duplicate dialog
	 */
	private void duplicateDialog() {
		DuplicateDialog dialog = new DuplicateDialog(this, animals);
		dialog.setVisible(true);
	}

	/**
	 * return threadpool
	 * 
	 * @return threadpool
	 */
	public ExecutorService getPool() {
		return pool;
	}

	/**
	 * eat animal
	 */
	public void eatanimal() {
		boolean prey_eaten = false;
		synchronized (this) {
			for (Animal predator : animals) {
				for (Animal prey : animals) {
					if (prey.isRunning()
							&& predator != prey
							&& predator.getDiet().canEat(prey)
							&& predator.getWeight() / prey.getWeight() >= 2
							&& (Math.abs(predator.getLocation().getX()
									- prey.getLocation().getX()) < prey
										.getSize())
							&& (Math.abs(predator.getLocation().getY()
									- prey.getLocation().getY()) < prey
										.getSize())) {
						preyEating(predator, prey);
						System.out.print("The " + predator + " cought up the "
								+ prey + " ==> ");
						/* prey.interrupt(); */
						prey.deleteObserver(controller);
						prey.getTask().cancel(true);
						animals.remove(prey);
						repaint();
						// JOptionPane.showMessageDialog(frame,
						// ""+prey+" killed by "+predator);
						prey_eaten = true;
						break;
					}
				}
				if (prey_eaten)
					break;
			}
			if (prey_eaten)
				for (Animal an : animals)
					if (an.isRunning() == false) {
						an.addObserver(controller);
						animals.add(an);
						break;
					}
		}
	}

	/**
	 * check if there is changes with animals
	 * 
	 * @return
	 */
	public boolean isChange() {
		boolean rc = false;
		for (Animal an : animals) {
			if (an.getChanges()) {
				rc = true;
				an.setChanges(false);
			}
		}
		return rc;
	}

	/**
	 * return array's animal
	 * 
	 * @return
	 */
	public ArrayList<Animal> getAnimals() {
		return animals;
	}

	/**
	 * set new food
	 * @param food
	 * @param forFood
	 */
	public void setFood(EFoodType food, Plant forFood) {
		if (food == EFoodType.MEAT)
			Food = food;
		else {
			Food = food;
			this.forFood = forFood;
		}

	}

}