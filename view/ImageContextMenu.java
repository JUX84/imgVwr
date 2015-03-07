package view;

import controller.Controller;
import model.Image;
import model.Language;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ImageContextMenu extends JPopupMenu {
	private final JMenuItem rename;
	private final JMenuItem delete;
	private Language language = null;
	private Image image = null;

	public ImageContextMenu(final Controller controller) {
		rename = new JMenuItem();
		rename.addActionListener(new ActionListener() {
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
		add(rename);

		delete = new JMenuItem();
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int val = JOptionPane.showConfirmDialog(null,
						language.getString("deleteImageConfirm"), language.getString("menuEditDelete"),
						JOptionPane.YES_NO_OPTION);
				if (val == JOptionPane.YES_OPTION)
					controller.imageDeleted();
			}
		});
		add(delete);
	}

	public void setLanguage(Language lang) {
		if (language == null)
			language = lang;
		rename.setText(lang.getString("menuEditRename"));
		delete.setText(lang.getString("menuEditDelete"));
	}

	public void setImage(Image image) {
		if(this.image == null)
			this.image = image;
	}
}
