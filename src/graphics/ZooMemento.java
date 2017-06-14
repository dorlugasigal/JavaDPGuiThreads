package graphics;

import java.util.ArrayList;

import animals.Animal;
import food.EFoodType;
import mobility.Point;
import plants.Plant;

public class ZooMemento {
	private String state;
	private EFoodType food;
	private ArrayList<Animal> animals;
	private Plant forFood;
	private ZooPanel panel;
	public ZooMemento(String state,ZooPanel panel){
		this.panel=panel;
		animals = new ArrayList<Animal>();
		this.food=null;
		this.state = state;
		
	}
/**
 * set new state
 * @param animal
 * @param food
 * @param forFood
 */
	public void setState(ArrayList<Animal> animal,EFoodType food,Plant forFood) {
		clearState();
		for (Animal an : animal) {
			Animal cloneAnimal=(Animal) an.clone();
			//cloneAnimal.setAnimal(an.getName(),an.getSize(),(int) an.getWeight(),an.getHorSpeed(),an.getVerSpeed(),an.getColor(),panel);
			Point newLocation=new Point(an.getLocation().getX(),an.getLocation().getY());
			cloneAnimal.setLocation(newLocation);
			int xDir,yDir;
			xDir=new Integer((Integer)an.getXdir());
			yDir=new Integer((Integer)an.getYdir());
			cloneAnimal.setXdir(xDir);
			cloneAnimal.setYdir(yDir);
			animals.add(cloneAnimal);
		}
		this.forFood=forFood;
		this.food=food;
	}

	/**
	 * clear a state 
	 */
	public void clearState(){
		animals.clear();
		this.food=null;
	}
	/**
	 * return state
	 * @param panel
	 * @return
	 */
	public String getState(ZooPanel panel){
		panel.clear();
		for (Animal an : animals) {
			panel.addAnimal(an, an.getRealSize(), an.getHorSpeed(), an.getVerSpeed(), an.getColor(),an.getLocation(),an.getXdir(),an.getYdir());
		}
		panel.setFood(food,forFood);
		return state;
	}
	/**
	 * check if state is empty
	 * @return
	 */
	public boolean isEmpty(){
		return (animals.isEmpty()&&food==null);
	}
	
}
