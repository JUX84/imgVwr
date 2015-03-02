package view;

import controller.Controller;

import javax.swing.JPanel;
import javax.swing.JFileChooser;

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
