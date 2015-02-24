package view;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.util.Observer;
import java.util.Observable;
import model.Language;

public abstract class BaseView extends JPanel implements Observer {
	public BaseView() {
		super();
		setLayout(new GridLayout(1,1));
	}

	public void setTitle(String str) {
		setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(str),
					BorderFactory.createEmptyBorder(5,5,5,5)));
	}

	public abstract void update (Observable o, Object arg);
}
