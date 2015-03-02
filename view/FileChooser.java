package view;

import javax.swing.*;

import controller.Controller;

class FileChooser extends JPanel {
	public FileChooser(final Controller controller) {
		super();

		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int tmp = fc.showOpenDialog(this);
		if (tmp == JFileChooser.APPROVE_OPTION)
			controller.pathSelected(fc.getSelectedFile());
	}
}
