import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ExplorerView extends BaseView
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
		super("Explorer", 360, 240);
		imagesPanel = new JPanel();
		scroll = new JScrollPane();
		add(scroll);
	}

	public void update(Observable o, Object arg)
	{}
}
