package view;

import javax.swing.*;
import java.util.*;

public class KeywordsView extends BaseView
{
	private JScrollPane scroll;
	private JTextArea text;

	public KeywordsView()
	{
		super("Keywords", 100, 240);
		text = new JTextArea();
		scroll = new JScrollPane(text);
		add(scroll);
	}

	public void update(Observable o, Object arg) {
	}
}
