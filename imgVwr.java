import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;
import view.*;
import controller.Controller;

public class imgVwr {

	public static void startGUI() {

		model.Image i = new model.Image();
		Path p = new Path(System.getProperty("user.home"));
		Language l = new Language("fr");

		Controller controller = new Controller(i, p, l);

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

		ViewerView viewer = new ViewerView(controller);
		KeywordsView keywords = new KeywordsView(controller);
		ExplorerView explorer = new ExplorerView(controller);
		LangView lang = new LangView(controller);
		TreeView tree = new TreeView(controller);
		MenuView menu = new MenuView(controller);

		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(720, 480));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		JPanel langPanel = new JPanel();
		langPanel.setLayout(new BoxLayout(langPanel, BoxLayout.LINE_AXIS));

		JSplitPane treeViewerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tree, viewer);
		treeViewerSplit.setResizeWeight(0.3);
		JPanel treeViewerSplitContainer = new JPanel();
		treeViewerSplitContainer.setLayout(new BoxLayout(treeViewerSplitContainer, BoxLayout.LINE_AXIS));
		treeViewerSplitContainer.add(treeViewerSplit);

		JSplitPane keywordsExplorerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, keywords, explorer);
		keywordsExplorerSplit.setResizeWeight(0.5);
		JPanel keywordsExplorerSplitContainer = new JPanel();
		keywordsExplorerSplitContainer.setPreferredSize(new Dimension(720, 240));
		keywordsExplorerSplitContainer.setLayout(new BoxLayout(keywordsExplorerSplitContainer, BoxLayout.LINE_AXIS));
		keywordsExplorerSplitContainer.add(keywordsExplorerSplit);

		JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treeViewerSplitContainer, keywordsExplorerSplitContainer);
		verticalSplit.setResizeWeight(0.6);
		JPanel verticalSplitContainer = new JPanel();
		verticalSplitContainer.setLayout(new BoxLayout(verticalSplitContainer, BoxLayout.LINE_AXIS));
		verticalSplitContainer.add(verticalSplit);

		mainPanel.add(lang);
		mainPanel.add(verticalSplitContainer);

		frame.setJMenuBar(menu);
		frame.add(mainPanel);
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
