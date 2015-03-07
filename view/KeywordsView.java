package view;

import controller.Controller;
import model.Image;
import model.Keywords;
import model.Language;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class KeywordsView extends BaseView implements Observer {
	private final JTextArea text;
	private final JButton save;

	public KeywordsView(final Controller controller) {
		super();

		text = new JTextArea();
		JScrollPane scroll = new JScrollPane(text);

		save = new JButton();
		save.setAlignmentX(Component.RIGHT_ALIGNMENT);
		save.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.keywordsSaved(text.getText());
			}
		});

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(scroll);
		add(save);

		controller.init(this);
	}

	public void setLanguage(Language language) {
		super.setTitle(language.getString("keywords"));
		save.setText(language.getString("save"));
	}

	@Override
	public void update(Observable o, Object arg) {
		String tmp = (String) arg;
		if (tmp.equals("language"))
			setLanguage((Language) o);
		else if (tmp.equals("image"))
			loadImageKeywords(((Image) o).getPath());
	}

	private void loadImageKeywords(String p) {
		if (p != null) {
			String t = Keywords.getkeywords(p);
			if (t != null)
				text.setText(t);
		}
	}
}
