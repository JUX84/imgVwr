package view;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import java.util.Observable;
import controller.Controller;
import view.TreeView;

public class FileChooser extends JPanel {
	public FileChooser(final Controller controller) {
		super();

		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int tmp = fc.showOpenDialog(this);
		if (tmp == JFileChooser.APPROVE_OPTION)
			controller.pathSelected(fc.getSelectedFile());
	}
}
