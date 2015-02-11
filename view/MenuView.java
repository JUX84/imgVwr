package view;

import javax.swing.*;
import model.*;

public class MenuView extends JMenuBar {
	public MenuView() {
		super();

		JMenu fileMenu = new JMenu(Language.getString("menuFile"));
		JMenuItem opnImg = new JMenuItem(Language.getString("menuFileOpen"));
		JMenuItem exit = new JMenuItem(Language.getString("menuFileExit"));


		fileMenu.add(opnImg);
		fileMenu.add(exit);

		add(fileMenu);
	}
}
