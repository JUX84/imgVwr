package view;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.util.Observer;
import java.util.Observable;
import model.Image;
import model.Thumbnail;

public class ViewerView extends BaseView implements Observer {
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
