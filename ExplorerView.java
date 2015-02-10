import javax.swing.*;
import java.util.*;
import java.awt.*;

public class ExplorerView extends BasePanel implements Observer
{
	private JScrollPane scroll;

	private JPanel imagesPanel;
	private ImageIcon[] images;
	private JButton browse;
	private String path;

	public void createImages()
	{
		imagesPanel.removeAll();
		imagesPanel.setLayout(new GridLayout(5, 10));
	}

	public ExplorerView()
	{
		super("Explorer", 300, 300);
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				imagesPanel = new JPanel();
				scroll = new JScrollPane();
				add(scroll);
			}
		});
	}

	public void update(Observable o, Object arg)
	{}
}
