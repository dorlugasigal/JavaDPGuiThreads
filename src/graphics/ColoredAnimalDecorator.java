package graphics;


public class ColoredAnimalDecorator implements ColoredAnimal {
	private ColoredAnimal changecolors;
	/**
	 * ColoredAnimalDecorator constructor
	 * @param change
	 */
	public ColoredAnimalDecorator(ColoredAnimal change){
		changecolors=change;
	}
	/**
	 * change animal's color
	 */
	@Override
	public void PaintAnimal(String color) {
		changecolors.PaintAnimal(color);
	}

}
