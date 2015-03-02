package view;

import javax.swing.*;
import model.*;
import controller.Controller;
import java.awt.event.*;
import java.util.Observer;
import java.util.Observable;

public class MenuView extends JMenuBar implements Observer {

    private Language language;

	private final JMenu fileMenu;
	private final JMenu editMenu;
	private final JMenuItem opnImg;
	private final JMenuItem exit;
	private final JMenuItem rnmImg;

	public MenuView(final Controller controller) {
		super();

        fileMenu = new JMenu();
		editMenu = new JMenu();
		opnImg = new JMenuItem();
		exit = new JMenuItem();
		rnmImg = new JMenuItem();

		opnImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FileChooser(controller);
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = ((JFrame)getParent().getParent().getParent());
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		rnmImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = JOptionPane.showInputDialog(language.getString("renameImage"));
				if(str == null || str.isEmpty())
					return;
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

	@Override
	public void update(Observable o, Object arg) {
		String tmp = (String)arg;
		if(tmp.equals("language"))
			setLanguage((Language)o);
		if(tmp.equals("image"))
			rnmImg.setEnabled(true);
	}
}
