package diet;

import animals.Animal;
import animals.Lion;

public class CarnivoreFactory implements AbstractZooFactory {
/**
 * produce lion function 
 */
	@Override
	public Animal produceAnimal(String type) {
		switch (type) {
		case "Lion":
			return new Lion();
		default:
			return null;
		}
	}

}
