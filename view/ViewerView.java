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

	public ViewerView(Image image) {
		super("Viewer", 360, 240);
		this.image = image;
		image.addObserver(this);
	}

	public void update (Observable o, Object arg) {
		if(getComponentCount() == 3)
			remove(label);
		label = new JLabel(new ImageIcon(image.getBufferedImage()));
		add(label);
		System.out.println("test");
	}
}
