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
		Thumbnail thumb = (Thumbnail)o;
		image.setName(thumb.getName());
		image.setPath(thumb.getPath());
		image.setBufferedImage(thumb.getOriginalImage());
		label = new JLabel(new ImageIcon(image.getBufferedImage()));
		add(label);
	}
}
