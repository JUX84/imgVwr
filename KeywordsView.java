import javax.swing.*;

public class KeywordsView extends BasePanel
{
	private JScrollPane scroll;
	private JTextArea text;

	public KeywordsView()
	{
		super("Keywords", 300, 300);
		text = new JTextArea();
		scroll = new JScrollPane(text);
		add(scroll);
	}
}
