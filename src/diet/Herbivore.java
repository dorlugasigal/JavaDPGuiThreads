package diet;

import animals.Animal;
import food.EFoodType;
import food.IEdible;

public class Herbivore implements IDiet {
/**
 * can eat function return boolean
 */
	@Override
	public boolean canEat(IEdible food) {
		return (food.getFoodtype() == EFoodType.VEGETABLE);
	}
	/**
	 * can eat function return boolean
	 */
	public boolean canEat(EFoodType food_type) {
		return food_type == EFoodType.VEGETABLE;
	}
	/**
	 * eat function updates weight 
	 */
	@Override
	public boolean eat(Animal animal, IEdible food) {
		boolean isSuccess = canEat(food);
		if (isSuccess) {
			animal.setWeight(animal.getWeight() * 1.07);
		}
		return isSuccess;
	}
/**
 * to string 
 */
	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + "]";
	}
}
