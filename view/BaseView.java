package view;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.util.Observer;
import java.util.Observable;

abstract class BaseView extends JPanel implements Observer {
	BaseView() {
		super();
		setLayout(new GridLayout(1,1));
	}

	void setTitle(String str) {
		setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(str),
					BorderFactory.createEmptyBorder(5,5,5,5)));
	}

	@Override
	public abstract void update (Observable o, Object arg);
}
