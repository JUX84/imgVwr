package view;

import javax.swing.*;
import model.*;

public class MenuView extends JMenuBar {
	private Language lang;

	public MenuView(Language lang) {
		super();

		this.lang = lang;

		JMenu fileMenu = new JMenu(lang.getString("menuFile"));
		JMenuItem opnImg = new JMenuItem(lang.getString("menuFileOpen"));
		JMenuItem exit = new JMenuItem(lang.getString("menuFileExit"));


		fileMenu.add(opnImg);
		fileMenu.add(exit);

		add(fileMenu);
	}
}
