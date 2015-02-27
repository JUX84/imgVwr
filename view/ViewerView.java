package view;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import java.util.Observer;
import java.util.Observable;
import java.awt.Graphics;
import java.awt.Component;
import model.Image;
import model.Thumbnail;
import model.Language;
import controller.Controller;

public class ViewerView extends BaseView implements Observer {
	private Language language;
	private Image image;
	private Controller controller;
	private JLabel imgLabel;
	private JLabel nameLabel;
	private JButton rename;
	private JButton hide;

	public ViewerView(final Controller controller) {
		super();
		this.controller = controller;
		imgLabel = new JLabel();
		nameLabel = new JLabel();
		nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(imgLabel);
		add(nameLabel);

		controller.init(this);
	}

	public void setImage(Image image) {
		this.image = image;
		if(image.getBufferedImage() != null) {
			nameLabel.setText(image.getName());
			imgLabel = new JLabel(new ImageIcon(image.getBufferedImage()));
			repaint();
		}
	}

	public void setLanguage(Language language) {
		this.language = language;
		super.setTitle(language.getString("viewer"));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x, y, width, height, maxWidth, maxHeight;
		maxWidth = getWidth();
		maxHeight = getHeight();
		width = image.getWidth();
		height = image.getHeight();
		if(width>maxWidth*0.80) {  // Double condition pour conserver les ratios
			width = (int)(maxWidth*0.80);
			height = (int)(maxHeight*0.80);
		}
		if(height>maxHeight*0.80) {
			width = (int)(maxWidth*0.80);
			height = (int)(maxHeight*0.80);
		}
		x = (maxWidth-width)/2; // On place l'image au milieu
		y = (maxHeight-height)/2;
		g.drawImage(image.getBufferedImage(), x, y, width, height, this);
	}

	public void update (Observable o, Object arg) {
		String tmp = (String)arg;
		if(tmp.equals("language"))
			setLanguage((Language)o);
		else if(tmp.equals("image"))
			setImage((Image)o);
	}
}
