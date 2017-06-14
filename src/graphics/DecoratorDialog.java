package graphics;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import animals.Animal;

public class DecoratorDialog extends JDialog implements ItemListener,
		ActionListener {
	private JPanel p1, p2, p3;
	private ButtonGroup rbg;
	private JRadioButton[] rb;
	private String[] colors = { "Red", "Blue" };
	private ZooPanel zpanel;
	private JComboBox<Object> dropDownMenu;
	private ArrayList<Integer> indexes;
	private JButton ok;
	private String color;
/**
 * DecoratorDialog constructor
 * @param panel
 * @param index
 */
	public DecoratorDialog(ZooPanel panel, ArrayList<Integer> index) {
		this.zpanel = panel;
		this.indexes = index;
		setSize(550, 300);
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p1.setLayout(new GridLayout(7, 0, 0, 0));
		p2.setLayout(new GridLayout(1, 2, 110, 110));
		p2.setSize(200, 250);
		p1.setBorder(BorderFactory
				.createTitledBorder("Select Animal To Decorate"));
		p2.setBorder(BorderFactory
				.createTitledBorder("Choose Decoration color"));
		rbg = new ButtonGroup();
		rb = new JRadioButton[colors.length];
		for (int i = 0; i < colors.length; i++) {
			rb[i] = new JRadioButton(colors[i], false);
			rb[i].addItemListener(this);
			rbg.add(rb[i]);
			p2.add(rb[i]);
		}
		dropDownMenu = new JComboBox<Object>();
		dropDownMenu.addItem("No Animal");
		for (Animal an : zpanel.getAnimals())
			if (indexes.contains(panel.getAnimals().indexOf(an)))
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
				JOptionPane.showMessageDialog(this, "You didnt select an animal for decoration");
			else if(!rb[0].isSelected()&&!rb[1].isSelected())
				JOptionPane.showMessageDialog(this, "You must choose a color");
			else {
				ColoredAnimal change=new ColoredAnimalDecorator(((Animal)dropDownMenu.getSelectedItem())) ;
				change.PaintAnimal(color);			
			}
		}
	}
/**
 * change color parameter when user press on radio button
 */
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		for (int i = 0; i < rb.length; i++)
			if (rb[i].isSelected()) {
				color = colors[i];
				break;
			}
	}
}
