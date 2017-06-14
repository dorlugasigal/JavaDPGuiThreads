package diet;

import animals.Animal;
import animals.Bear;

public class OmnivoreFactory implements AbstractZooFactory{

	/**
	 *produce Omnivore (BEAR) 
	 */
	@Override
	public Animal produceAnimal(String type) {
		switch (type) {
		case "Bear":
			return new Bear();
		default:
			return null;
		}
	}

}
