package view;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.util.Observer;
import java.util.Observable;

public abstract class BaseView extends JPanel implements Observer {
	public BaseView(String title, int width, int length) {
		super();
		setLayout(new GridLayout(1,1));
		setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(title),
					BorderFactory.createEmptyBorder(5,5,5,5)));
		// setPreferredSize(new Dimension(width, length));
	}

	public abstract void update (Observable o, Object arg);
}
