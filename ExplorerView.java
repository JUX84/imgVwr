import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class ExplorerView extends BasePanel implements Observer
{
	private JScrollPane scroll;

	private JPanel imagesPanel;
	private JButton browse;
	private String path = "/";

	public void createImages()
	{
		imagesPanel.removeAll();
		FlowLayout layout = new FlowLayout(FlowLayout.LEADING, 10, 10);
		imagesPanel.setLayout(layout);

		File folder = new File(path);
		File[] files = folder.listFiles();
		for (File f : files) {
			String name = f.getName();
			String extension = null;
			int pos = name.lastIndexOf('.');
			if (pos > 0 &&  pos < name.length() - 1) {
				extension = name.substring(pos + 1).toLowerCase();
			}

			if (extension != null) {
				if (extension.equals("jpeg")
						|| extension.equals("jpg")
						|| extension.equals("gif")
						|| extension.equals("png")) {
					Image img = new Image(f.getAbsolutePath());
					img.setPreferredSize(new Dimension(300, 300));
					imagesPanel.add(img);
				}
			}
		}
	}

	public ExplorerView()
	{
		super("Explorer", 300, 300);

		browse = new JButton("Browse");

		imagesPanel = new JPanel();
		createImages();
		scroll = new JScrollPane(imagesPanel);
		scroll.setPreferredSize(new Dimension(300, 300));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(browse);
		add(scroll);
	}

	public void update(Observable o, Object arg)
	{}
}
