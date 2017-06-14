package graphics;

import java.util.Observable;
import java.util.Observer;

public class ZooObserver implements Observer{
	/**
	 * update while there is a change with animal array
	 */
	@Override
	public void update(Observable o, Object msg) {
		ZooPanel p =(ZooPanel)msg;
	      p.eatanimal();
	      p.repaint(); 
	}
}
