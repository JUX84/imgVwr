import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;
import view.*;
import controller.Controller;

public class imgVwr {

	public static void startGUI() {

		model.Image i = new model.Image();
		Path p = new Path();
		Language l = new Language();
		l.setLanguage("fr");

		Controller controller = new Controller(i,p,l);

		JFrame frame = new JFrame("imgVwr");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		ViewerView viewer = new ViewerView(i,l);
		KeywordsView keywords = new KeywordsView(l);
		ExplorerView explorer = new ExplorerView(controller, p, l);
		LangView lang = new LangView(l);
		TreeView tree = new TreeView(controller, l);
		MenuView menu = new MenuView(l);

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
