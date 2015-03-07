package view;

import controller.Controller;
import model.Image;
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
	private final JMenuItem delImg;
	private final JMenuItem about;
	private Language language;
	private Image image;

	public MenuView(final Controller controller) {
		super();

		fileMenu = new JMenu();
		editMenu = new JMenu();
		helpMenu = new JMenu();
		opnImg = new JMenuItem();
		exit = new JMenuItem();
		rnmImg = new JMenuItem();
		delImg = new JMenuItem();
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
		delImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int val = JOptionPane.showConfirmDialog(null,
						language.getString("deleteImageConfirm"), language.getString("menuEditDelete"),
						JOptionPane.YES_NO_OPTION);
				if (val == JOptionPane.YES_OPTION)
					controller.imageDeleted();
			}
		});
		rnmImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = image.getName();
				String ext = str.substring(str.lastIndexOf('.'), str.length());
				str = JOptionPane.showInputDialog(null, language.getString("renameImage"), image.getName());
				if(str == null)
					return;
				while(!str.substring(str.lastIndexOf('.'), str.length()).equals(ext)) {
					JOptionPane.showMessageDialog(null, language.getString("extensionError"), language.getString("menuEditRename"), JOptionPane.ERROR_MESSAGE);
					str = JOptionPane.showInputDialog(null, language.getString("renameImage"), str);
					if(str == null)
						return;
				}
				if (str.isEmpty() || str.equals(image.getName()))
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
		delImg.setEnabled(false);

		fileMenu.add(opnImg);
		fileMenu.add(exit);
		editMenu.add(rnmImg);
		editMenu.add(delImg);
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
		delImg.setText(language.getString("menuEditDelete"));
		about.setText(language.getString("about"));
	}

	public void setImage(Image image) {
		if(this.image == null)
			this.image = image;
		rnmImg.setEnabled(true);
		delImg.setEnabled(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		String tmp = (String) arg;
		if (tmp.equals("language"))
			setLanguage((Language) o);
		if (tmp.equals("image"))
			setImage((Image) o);
	}
}
