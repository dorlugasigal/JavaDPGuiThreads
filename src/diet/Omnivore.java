package diet;

import animals.Animal;
import food.EFoodType;
import food.IEdible;

/**
 * @author Dor  Lugasi and Daniel Saka
 *
 */
public class Omnivore implements IDiet {

	private IDiet canrivore;
	private IDiet herbivore;

	public Omnivore() {
		this.canrivore = new Carnivore();
		this.herbivore = new Herbivore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see diet.IDiet#canEat(food.IFood)
	 */
	/**
	 * return boolean can eat function
	 */
	@Override
	public boolean canEat(IEdible food) {

		return this.canrivore.canEat(food) || this.herbivore.canEat(food);

	}
	/**
	 * return boolean can eat function
	 */
	public boolean canEat(EFoodType food_type) {
		return food_type != EFoodType.NOTFOOD;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see diet.IDiet#eat(food.IFood)
	 */
	/**
	 * eat function return boolean if ate
	 */
	@Override
	public boolean eat(Animal animal, IEdible food) {
		EFoodType ft = food.getFoodtype();

		if (ft == EFoodType.MEAT)
			return this.canrivore.eat(animal, food);
		if (ft == EFoodType.VEGETABLE)
			return this.canrivore.eat(animal, food);
		return false;

	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "]";
	}
}
