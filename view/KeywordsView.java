package view;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import model.Language;
import model.Image;
import model.Keywords;
import controller.Controller;

public class KeywordsView extends BaseView implements Observer
{
	private Controller controller;
	private Language language;
	private JScrollPane scroll;
	private JTextArea text;
	private JButton save;

	public KeywordsView(Controller controller)
	{
		super();

		this.controller = controller;

		text = new JTextArea();
		scroll = new JScrollPane(text);

		save = new JButton();
		save.setAlignmentX(Component.RIGHT_ALIGNMENT);
		save.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				controller.keywordsSaved(text.getText());
			}
		});

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(scroll);
		add(save);

		controller.init(this);
	}

	public void setLanguage(Language language) {
		this.language = language;
		super.setTitle(language.getString("keywords"));
		save.setText(language.getString("save"));
	}

	public void update(Observable o, Object arg) {
		String tmp = (String)arg;
		if (tmp.equals("language"))
			setLanguage((Language)o);
		else if (tmp.equals("image"))
			loadImageKeywords(((Image)o).getPath());
	}

	public void loadImageKeywords(String p)
	{
		if (p != null) {
			String t = Keywords.getkeywords(p);
			if (t != null)
				text.setText(t);
		}
	}
}
