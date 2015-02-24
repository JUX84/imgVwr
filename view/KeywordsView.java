package view;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.util.Observable;
import model.Language;
import controller.Controller;

public class KeywordsView extends BaseView
{
	private Controller controller;
	private Language language;
	private JScrollPane scroll;
	private JTextArea text;

	public KeywordsView(Controller controller)
	{
		super();
		this.controller = controller;
		text = new JTextArea();
		scroll = new JScrollPane(text);
		add(scroll);
		controller.init(this);
	}

	public void setLanguage(Language language) {
		this.language = language;
		super.setTitle(language.getString("keywords"));
	}

	public void update(Observable o, Object arg) {
		String tmp = (String)arg;
		if(tmp.equals("language"))
			setLanguage((Language)o);
	}
}
