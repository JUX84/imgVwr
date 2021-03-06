package view;

import controller.Controller;
import model.Image;
import model.Language;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

public class ViewerView extends BaseView implements Observer {
	private final JLabel nameLabel;
	private final SpinnerNumberModel model;
	private final JSpinner spinner;
	private final ImageContextMenu contextMenu;
	private final MouseListener ml;
	private Image image = null;
	private Language language = null;
	private JLabel imgLabel;
	private double scale = 0.0;

	public ViewerView(final Controller controller) {
		super();
		ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					contextMenu.show(e.getComponent(), e.getX(), e.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {  // CROSSPLATFORM
				if (e.isPopupTrigger())
					contextMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		};
		contextMenu = new ImageContextMenu(controller);
		imgLabel = new JLabel();
		nameLabel = new JLabel();
		nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		nameLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					String str = image.getName();
					String ext = str.substring(str.lastIndexOf('.'), str.length());
					str = JOptionPane.showInputDialog(null, language.getString("renameImage"), image.getName());
					if(str == null)
						return;
					while(!str.substring(str.lastIndexOf('.'), str.length()).equals(ext)) {
						JOptionPane.showMessageDialog(null, language.getString("extensionError"), language.getString("menuEditRename"), JOptionPane.ERROR_MESSAGE);
						str = JOptionPane.showInputDialog(null, language.getString("renameImage"), str);
						if(str == null)
							return;
					}
					if (str.isEmpty() || str.equals(image.getName()))
						return;
					controller.imageRenamed(str);
				}
			}
		});
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(imgLabel);
		add(nameLabel);

		model = new SpinnerNumberModel(1.0d, 0.1d, 10.0d, 0.01d);
		spinner = new JSpinner(model);
		spinner.setMaximumSize(new Dimension(50, 20));
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Double d = (Double) spinner.getValue();
				setScale(d);
			}
		});
		spinner.setVisible(false);
		add(spinner);

		addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (spinner.isVisible()) {
					int notches = e.getWheelRotation();

					if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
						Double d = (Double) model.getValue();
						double amount = (double) e.getScrollAmount() * (Math.max(image.getWidth(), image.getHeight())/500000.0d);
						if(amount < 0.01d)
							amount = 0.01d;
						if (notches >= 0.0d)
							amount *= -1.0d;
						d = d + amount;
						scale = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						if(scale<0.1d)
							scale = 0.1d;
						if(scale>10.0d)
							scale = 10.0d;
						model.setValue(Double.valueOf(scale));
					}
				}
			}
		});

		controller.init(this);
	}

	public void setImage(Image image) {
		this.image = image;
		spinner.setVisible(false);
		removeMouseListener(ml);
		if (image != null) {
			nameLabel.setText(image.getName());
			if (image.isNotDamaged()) {
				imgLabel = new JLabel(new ImageIcon(image.getBufferedImage()));
				addMouseListener(ml);
				double width = getWidth();
				double height = getHeight();
				double imageWidth = image.getWidth();
				double imageHeight = image.getHeight();
				scale = Math.min(width / imageWidth * 0.80, height / imageHeight * 0.80);
				scale = new BigDecimal(scale).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				model.setValue(Double.valueOf(scale));
				spinner.setVisible(true);
				contextMenu.setImage(image);
			} else {
				imgLabel = null;
			}
		}
		new imageLoader().run();
	}

	public void setLanguage(Language language) {
		if (this.language == null)
			this.language = language;
		super.setTitle(language.getString("viewer"));
		contextMenu.setLanguage(language);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null && image.isNotDamaged()) {
			int x, y, width, height, maxWidth, maxHeight;
			maxWidth = getWidth();
			maxHeight = getHeight();
			width = image.getWidth();
			height = image.getHeight();

			width *= scale;
			height *= scale;

			if (maxHeight - height < 60)
				nameLabel.setForeground(Color.white);
			else
				nameLabel.setForeground(Color.black);

			if (maxWidth - width < 60 && maxHeight - height < 20)
				super.setTitleColor(Color.white);
			else
				super.setTitleColor(Color.black);

			x = (maxWidth - width) / 2; // On place l'image au milieu
			y = (maxHeight - height) / 2;
			g.drawImage(image.getBufferedImage(), x, y, width, height, this);
		}
	}

	void setScale(double s) {
		scale = s;
		revalidate();      // update the scroll pane
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		String tmp = (String) arg;
		if (tmp.equals("language"))
			setLanguage((Language) o);
		else if (tmp.equals("image"))
			setImage((Image) o);
	}

	private class imageLoader extends Thread {
		@Override
		public void run() {
			repaint();
		}
	}
}
