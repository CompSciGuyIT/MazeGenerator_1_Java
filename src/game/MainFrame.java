package game;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1794412472541954352L;
	private FormPanel formPanel;
	
	public MainFrame(int size, String[] filenames) {
		super("Maze");
		
		formPanel = new FormPanel(size, filenames);
		add(formPanel);
		
		setSize(size * 50, size * 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);		
	}

}
