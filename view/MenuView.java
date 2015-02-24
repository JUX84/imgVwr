package view;

import javax.swing.*;
import model.*;
import controller.Controller;
import java.util.Observer;
import java.util.Observable;

public class MenuView extends JMenuBar implements Observer {

	private Controller controller;
	private Language language;

	private JMenu fileMenu;
	private JMenuItem opnImg;
	private JMenuItem exit;

	public MenuView(Controller controller) {
		super();

		this.controller = controller;

		fileMenu = new JMenu();
		opnImg = new JMenuItem();
		exit = new JMenuItem();

		fileMenu.add(opnImg);
		fileMenu.add(exit);

		add(fileMenu);

		controller.init(this);
	}

	public void setLanguage(Language language) {
		this.language = language;
		fileMenu.setText(language.getString("menuFile"));
		opnImg.setText(language.getString("menuFileOpen"));
		exit.setText(language.getString("menuFileExit"));
	}

	public void update(Observable o, Object arg) {
		String tmp = (String)arg;
		if(tmp.equals("language"))
			setLanguage((Language)o);
	}
}
