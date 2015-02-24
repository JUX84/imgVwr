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

		Controller controller = new Controller(i, p, l);

		JFrame frame = new JFrame("imgVwr");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ViewerView viewer = new ViewerView(i, l);
		KeywordsView keywords = new KeywordsView(l);
		ExplorerView explorer = new ExplorerView(controller, p, l);
		LangView lang = new LangView(l);
		TreeView tree = new TreeView(controller, l);
		MenuView menu = new MenuView(l);

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
