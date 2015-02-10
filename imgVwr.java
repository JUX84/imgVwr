import javax.swing.*;
import java.awt.*;

public class imgVwr {

	public static void startGUI() {
		JFrame frame = new JFrame("imgVwr");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(720,490));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(720,240));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.LINE_AXIS));

		ViewerView viewer = new ViewerView();
		KeywordsView keywords = new KeywordsView();
		ExplorerView explorer = new ExplorerView();
		JButton EN = new JButton("English");
		JButton FR = new JButton("Francais");
		JButton PY = new JButton("Pycknn");

		panel3.add(EN);
		panel3.add(FR);
		panel3.add(PY);
		panel.add(panel3, BorderLayout.CENTER);
		panel.add(viewer);
		panel2.add(keywords);
		panel2.add(explorer);
		panel.add(panel2);

		frame.add(panel);
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
