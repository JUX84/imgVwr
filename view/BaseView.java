package view;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.util.Observer;
import java.util.Observable;
import model.Language;

public abstract class BaseView extends JPanel implements Observer {
	protected Language lang;

	public BaseView(Language lang, String title)
	{
		super();
		this.lang = lang;
		setLayout(new GridLayout(1,1));
		setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(lang.getString(title)),
					BorderFactory.createEmptyBorder(5,5,5,5)));
	}

	public abstract void update (Observable o, Object arg);
}
