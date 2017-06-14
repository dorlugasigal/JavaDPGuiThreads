package mobility;

/**
 * @author baroh
 *
 */
public class Point {

	private int x; // the x value
	private int y; // the y value
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * return X
	 * @return x
	 */
	public int getX() {
		return x;
	}
	/**
	 * return y
	 * @return y
	 */
	public int getY() {
		return y;
	}
	/**
	 * set x
	 * @param x
	 * @return true
	 */
	public boolean setX(int x) {
		this.x = x;
		return true;
	}
	/**
	 * set y
	 * @param y
	 * @return true
	 */
	public boolean setY(int y) {
		this.y = y;
		return true;
	}
}
