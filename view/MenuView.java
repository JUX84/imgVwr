package view;

import controller.Controller;
import model.Language;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

public class MenuView extends JMenuBar implements Observer {

	private final JMenu fileMenu;
	private final JMenu editMenu;
	private final JMenu helpMenu;
	private final JMenuItem opnImg;
	private final JMenuItem exit;
	private final JMenuItem rnmImg;
	private final JMenuItem about;
	private Language language;

	public MenuView(final Controller controller) {
		super();

		fileMenu = new JMenu();
		editMenu = new JMenu();
		helpMenu = new JMenu();
		opnImg = new JMenuItem();
		exit = new JMenuItem();
		rnmImg = new JMenuItem();
		about = new JMenuItem();

		opnImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FileChooser(controller);
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = ((JFrame) getParent().getParent().getParent());
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		rnmImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = JOptionPane.showInputDialog(language.getString("renameImage"));
				if (str == null || str.isEmpty())
					return;
				controller.imageRenamed(str);
			}
		});
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, language.getString("aboutContent"), language.getString("about"), JOptionPane.INFORMATION_MESSAGE);
			}
		});

		rnmImg.setEnabled(false);

		fileMenu.add(opnImg);
		fileMenu.add(exit);
		editMenu.add(rnmImg);
		helpMenu.add(about);

		add(fileMenu);
		add(editMenu);
		add(helpMenu);

		controller.init(this);
	}

	public void setLanguage(Language language) {
		if (this.language == null)
			this.language = language;
		fileMenu.setText(language.getString("menuFile"));
		editMenu.setText(language.getString("menuEdit"));
		helpMenu.setText(language.getString("menuHelp"));
		opnImg.setText(language.getString("menuFileOpen"));
		exit.setText(language.getString("menuFileExit"));
		rnmImg.setText(language.getString("menuEditRename"));
		about.setText(language.getString("about"));
	}

	@Override
	public void update(Observable o, Object arg) {
		String tmp = (String) arg;
		if (tmp.equals("language"))
			setLanguage((Language) o);
		if (tmp.equals("image"))
			rnmImg.setEnabled(true);
	}
}
