package Breath;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class StartBreath extends JFrame {

	private static final long serialVersionUID = 3546365024081097528L;

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	};

	static void createAndShowGUI() {
		JFrame aFrame = new JFrame("Atmungstrainer");
		aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aFrame.setSize(new Dimension(700, 600));
		//aFrame.setContentPane(new SudokuPanel());
		BreathPanel sp = new BreathPanel();
		aFrame.setContentPane(sp);
		aFrame.setLocation(130, 30);
		aFrame.setVisible(true);
	}

	public void init() {
		setContentPane(null);
	}
}
