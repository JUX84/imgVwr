package view;

import javax.swing.*;
import java.util.*;
import model.Language;

public class KeywordsView extends BaseView
{
	private JScrollPane scroll;
	private JTextArea text;

	public KeywordsView(Language lang)
	{
		super(lang, "Keywords");
		text = new JTextArea();
		scroll = new JScrollPane(text);
		add(scroll);
	}

	public void update(Observable o, Object arg) {
	}
}
