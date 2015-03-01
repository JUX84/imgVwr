package view;

import javax.swing.*;
import model.*;
import controller.Controller;
import java.awt.event.*;
import java.util.Observer;
import java.util.Observable;

public class MenuView extends JMenuBar implements Observer {

	private Controller controller;
	private Language language;

	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenuItem opnImg;
	private JMenuItem exit;
	private JMenuItem rnmImg;

	public MenuView(Controller controller) {
		super();

		this.controller = controller;

		fileMenu = new JMenu();
		editMenu = new JMenu();
		opnImg = new JMenuItem();
		exit = new JMenuItem();
		rnmImg = new JMenuItem();
		rnmImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = JOptionPane.showInputDialog(language.getString("renameImage"));
				controller.imageRenamed(str);
			}
		});

		rnmImg.setEnabled(false);

		fileMenu.add(opnImg);
		fileMenu.add(exit);
		editMenu.add(rnmImg);

		add(fileMenu);
		add(editMenu);

		controller.init(this);
	}

	public void setLanguage(Language language) {
		this.language = language;
		fileMenu.setText(language.getString("menuFile"));
		editMenu.setText(language.getString("menuEdit"));
		opnImg.setText(language.getString("menuFileOpen"));
		exit.setText(language.getString("menuFileExit"));
		rnmImg.setText(language.getString("menuEditRename"));
	}

	public void update(Observable o, Object arg) {
		String tmp = (String)arg;
		if(tmp.equals("language"))
			setLanguage((Language)o);
		if(tmp.equals("image"))
			rnmImg.setEnabled(true);
	}
}
