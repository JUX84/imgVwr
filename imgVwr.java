import javax.swing.*;
import java.awt.*;

public class imgVwr {

	public static void startGUI() {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ViewerView viewer = new ViewerView();
		frame.add(viewer);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main (String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startGUI();
			}
		});
	}
}
