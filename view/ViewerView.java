package view;

import javax.swing.*;
import java.util.*;
import model.*;

public class ViewerView extends BaseView {
	private Image image;
	private JLabel label;
	private JButton rename;
	private JButton hide;

	public ViewerView() {
		super("Viewer", 360, 240);
	}

	public void update (Observable o, Object arg) {
		image = ((Thumbnail)o).getOriginalImage();
		label = new JLabel(new ImageIcon(image));
		add(label);
	}
}
