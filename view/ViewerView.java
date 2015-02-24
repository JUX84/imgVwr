package view;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.util.Observer;
import java.util.Observable;
import java.awt.Graphics;
import model.Image;
import model.Thumbnail;
import model.Language;

public class ViewerView extends BaseView implements Observer {
	private Image image;
	private JLabel label;
	private JButton rename;
	private JButton hide;

	public ViewerView(Image image, Language lang) {
		super(lang, "Viewer");
		this.image = image;
		label = new JLabel();
		add(label);
		image.addObserver(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getBufferedImage(), 0, 0, this);
	}

	public void update (Observable o, Object arg) {
		label = new JLabel(new ImageIcon(image.getBufferedImage()));
		repaint();
	}
}
