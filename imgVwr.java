import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;
import view.*;
import controller.Controller;

public class imgVwr {

	public static void startGUI() {
		Controller controller = new Controller();

		JFrame frame = new JFrame("imgVwr");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Language.setLanguage("fr");

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(720,480));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(720,240));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.LINE_AXIS));
		JPanel panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.LINE_AXIS));

		ViewerView viewer = new ViewerView();
		KeywordsView keywords = new KeywordsView();
		ExplorerView explorer = new ExplorerView(controller);
		LangView lang = new LangView();
		TreeView tree = new TreeView();
		MenuView menu = new MenuView();

		frame.setJMenuBar(menu);

		panel3.add(lang);
		panel.add(panel3, BorderLayout.CENTER);
		panel4.add(tree);
		panel4.add(viewer);
		panel.add(panel4);
		panel2.add(keywords);
		panel2.add(explorer);
		panel.add(panel2);

		frame.add(panel);
		// frame.setResizable(false);
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
