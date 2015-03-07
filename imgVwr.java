import controller.Controller;
import model.DBConnection;
import model.Language;
import model.Path;
import model.SearchResults;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;
import java.util.*;

class imgVwr {

	private static void startGUI() {

		model.Image i = new model.Image();
		Path p = new Path(System.getProperty("user.home"));
		Language l = new Language(System.getProperty("user.language"));
		SearchResults sr = new SearchResults();

		Controller controller = new Controller(i, p, l, sr);

		JFrame frame = new JFrame("imgVwr");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DBConnection.closeConnection();
				System.exit(0);
			}
		});

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(720, 480));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(720, 240));
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

		HashMap<KeyStroke, Action> actionMap = new HashMap<KeyStroke, Action>();

		KeyStroke ctrlr = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(ctrlr, new AbstractAction("ctrlr") {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (i != null) {
					String str = i.getName();
					if (str != null && !str.isEmpty()) {
						String ext = str.substring(str.lastIndexOf('.'), str.length());
						str = JOptionPane.showInputDialog(null, l.getString("renamei"), i.getName());
						while(!str.substring(str.lastIndexOf('.'), str.length()).equals(ext)) {
							JOptionPane.showMessageDialog(null, l.getString("extensionError"), l.getString("menuEditRename"), JOptionPane.ERROR_MESSAGE);
							str = JOptionPane.showInputDialog(null, l.getString("renamei"), str);
						}
						if (str.isEmpty() || str.equals(i.getName()))
							return;
						controller.imageRenamed(str);
					}
				}
			}
		});
		KeyStroke ctrld = KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(ctrld, new AbstractAction("ctrld") {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = i.getPath();
				if (i != null && str != null && !str.isEmpty()) {
					int val = JOptionPane.showConfirmDialog(null,
							l.getString("deleteImageConfirm"), "",
							JOptionPane.YES_NO_OPTION);
					if (val == JOptionPane.YES_OPTION)
						controller.imageDeleted();
				}
			}
		});

		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher( new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);

				if (actionMap.containsKey(keyStroke)) {
					final Action a = actionMap.get(keyStroke);
					final ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), null);
					a.actionPerformed(ae);

					return true;
				}

				return false;
			}
		});

		frame.setJMenuBar(menu);
		frame.add(mainPanel);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				startGUI();
			}
		});
	}
}
