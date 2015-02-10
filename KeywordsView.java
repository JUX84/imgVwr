import javax.swing.*;

public class KeywordsView extends BasePanel
{
	private JScrollPane scroll;
	private JTextArea text;

	public KeywordsView()
	{
		super("Keywords", 300, 300);
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				text = new JTextArea();
				scroll = new JScrollPane(text);
				add(scroll);
			}
		});
	}
}
