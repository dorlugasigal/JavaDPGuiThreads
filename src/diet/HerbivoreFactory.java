package diet;

import animals.Animal;
import animals.Elephant;
import animals.Giraffe;
import animals.Turtle;

public class HerbivoreFactory implements AbstractZooFactory{
	/**
	 * produce herbivore (turtle or giraffe or elephant)
	 */
	@Override
	public Animal produceAnimal(String type) {
		switch (type) {
		case "Turtle":
			return new Turtle();
		case "Giraffe":
			return new Giraffe();
		case "Elephant":
			return new Elephant();
		default:
			return null;
		}
	}

}
