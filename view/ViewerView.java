package view;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import java.util.Observer;
import java.util.Observable;
import java.awt.Graphics;
import java.awt.Component;
import model.Image;
import model.Language;
import controller.Controller;

public class ViewerView extends BaseView implements Observer {
    private Image image;
    private JLabel imgLabel;
	private final JLabel nameLabel;

	public ViewerView(final Controller controller) {
		super();
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
        super.setTitle(language.getString("viewer"));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x, y, width, height, maxWidth, maxHeight;
		maxWidth = getWidth();
		maxHeight = getHeight();
		width = image.getWidth();
		height = image.getHeight();

        double maxRatio = (double)maxWidth / maxHeight;
        double imgRatio = (double)width / height;
        double scale = (imgRatio > maxRatio) ?  (double)maxWidth*0.8 / width : (double)maxHeight*0.8 / height;

        width = (int)(width * scale);
        height = (int)(height * scale);

		x = (maxWidth-width)/2; // On place l'image au milieu
		y = (maxHeight-height)/2;
		g.drawImage(image.getBufferedImage(), x, y, width, height, this);
	}

	@Override
	public void update (Observable o, Object arg) {
		String tmp = (String)arg;
		if(tmp.equals("language"))
			setLanguage((Language)o);
		else if(tmp.equals("image"))
			setImage((Image)o);
	}
}
