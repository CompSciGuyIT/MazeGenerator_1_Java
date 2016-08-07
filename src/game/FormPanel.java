package game;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormPanel extends JPanel {

	private static final long serialVersionUID = -7571734280730605252L;

	public FormPanel(int size, String[] filenames) {
		
		setLayout(new GridLayout(size, size, 0, 0));
		
		for (int i = 0; i < size * size; i++) {
			JLabel label = new JLabel("");
			Image img = new ImageIcon(this.getClass().getResource(filenames[i])).getImage();
			label.setIcon(new ImageIcon(img));
			add(label);
			System.out.println(i + ": " + filenames[i]);
		}
		
		
	}

}
