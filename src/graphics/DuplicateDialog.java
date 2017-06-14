package graphics;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import animals.Animal;

public class DuplicateDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel p1, p2, p3;
	private JLabel horText,verText;
	private JSlider sl_hor, sl_ver;
	private ZooPanel zpanel;
	private JComboBox<Object> dropDownMenu;
	private JButton ok;
	private Animal cloneAnimal;
/**
 * DuplicateDialog constructor
 * @param panel
 * @param index
 */
	public DuplicateDialog(ZooPanel panel, ArrayList<Animal> index) {
		this.zpanel = panel;
		setSize(550, 300);
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		p1.setLayout(new GridLayout(7, 0, 0, 0));
		p2.setLayout(new GridLayout(4, 1,50, 0));
		p2.setSize(200, 250);
		
		p1.setBorder(BorderFactory
				.createTitledBorder("Select Animal To Clone"));
		p2.setBorder(BorderFactory
				.createTitledBorder("Speed may be Changed..."));
		
		horText= new JLabel("Horizontal Speed");
		verText= new JLabel("Vertical Speed");
		p2.add(horText);
		sl_hor = new JSlider(0, 10);
		sl_hor.setMajorTickSpacing(2);
		sl_hor.setMinorTickSpacing(1);
		sl_hor.setPaintTicks(true);
		sl_hor.setPaintLabels(true);
		p2.add(sl_hor);
		p2.add(verText);

		sl_ver = new JSlider(0, 10);
		sl_ver.setMajorTickSpacing(2);
		sl_ver.setMinorTickSpacing(1);
		sl_ver.setPaintTicks(true);
		sl_ver.setPaintLabels(true);
		p2.add(sl_ver);
		
		dropDownMenu = new JComboBox<Object>();
		dropDownMenu.addItem("No Animal");
		for (Animal an : zpanel.getAnimals())
			dropDownMenu.addItem(an);
		
		ok = new JButton("OK");
		ok.addActionListener(this);
		ok.setBackground(Color.lightGray);
		
		p1.add("North", dropDownMenu);
		
		p3.add(ok);
		add("West", p1);
		add("East", p2);
		add("South", p3);
	
		setResizable(false);
	}
	/**
	 * Action of pressing OK button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			if (dropDownMenu.getSelectedIndex() == 0)
				JOptionPane.showMessageDialog(this, "You didnt select an animal for duplication");
			else {
				
				if (zpanel.getAnimals().size() >= 10) {
					JOptionPane.showMessageDialog(this, "You cannot add more than 10"
							+ " animals, animal will move to queue");
					if (((ThreadPoolExecutor)zpanel.getPool()).getQueue().size() == 5) {
						JOptionPane.showMessageDialog(this, "the Queue is Full.");
					} else {
						cloneAnimal=(Animal) ((Animal)dropDownMenu.getSelectedItem()).clone();
						cloneAnimal.setHorSpeed(sl_hor.getValue());
						cloneAnimal.setVerSpeed(sl_ver.getValue());
						cloneAnimal.setRunning(false);
						zpanel.addAnimal(cloneAnimal,cloneAnimal.getRealSize(),cloneAnimal.getHorSpeed(),cloneAnimal.getVerSpeed(),cloneAnimal.getColor(),null,cloneAnimal.getXdir(),cloneAnimal.getYdir());
						setVisible(false);
					}
				} else {
					cloneAnimal=(Animal) ((Animal)dropDownMenu.getSelectedItem()).clone();
					cloneAnimal.setHorSpeed(sl_hor.getValue());
					cloneAnimal.setVerSpeed(sl_ver.getValue());
					zpanel.addAnimal(cloneAnimal,cloneAnimal.getRealSize(),cloneAnimal.getHorSpeed(),cloneAnimal.getVerSpeed(),cloneAnimal.getColor(),null,cloneAnimal.getXdir(),cloneAnimal.getYdir());
					setVisible(false);
				}
			}
		}
	}

	
	public Animal getCloneAnimal() {
		return cloneAnimal;
	}
}
