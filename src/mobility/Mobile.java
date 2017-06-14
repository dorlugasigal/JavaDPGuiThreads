package mobility;

/**
 * @author baroh
 *
 */
public abstract class Mobile implements ILocatable {
	protected Point location;

	/**
	 * Mobile constructor
	 * 
	 * @param location
	 */
	public Mobile(Point location) {
		this.setLocation(location);
	}

	/**
	 * return location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * set location
	 */
	public boolean setLocation(Point newLocation) {
		this.location = newLocation;
		return true;

	}
}
