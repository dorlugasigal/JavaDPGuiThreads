package diet;

import animals.Animal;


public interface AbstractZooFactory {
/**
 * factoery interface 
 * @param type
 * @return
 */
	/**
	 * produce animal method 
	 * @param type
	 * @return animal
	 */
	public Animal produceAnimal(String type);
}
