package view;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.DefaultListModel;
import javax.swing.BoxLayout;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.SwingWorker;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Component;
import java.util.Observer;
import java.util.Observable;
import java.util.List;
import java.io.File;
import javax.imageio.ImageIO;
import controller.Controller;
import model.Thumbnail;
import model.Path;

public class ExplorerView extends BaseView implements Observer
{
	private final Controller controller;

	private JScrollPane scroll;

	private DefaultListModel iconListModel;
	private JList<Thumbnail> iconList;

	private JButton browse;
	private String path = System.getProperty("user.home");

	private SwingWorker<Void, Thumbnail> loadImageWorker = null;

	public void createImages()
	{
		if (loadImageWorker != null)
			loadImageWorker.cancel(true);

		loadImageWorker = new SwingWorker<Void, Thumbnail>() {
			@Override
			protected Void doInBackground() throws Exception
			{
				iconListModel.clear();
				File folder = new File(path);
				File[] files = folder.listFiles();
				if (files == null)
					return null;

				for (File f : files) {
					if (isCancelled())
						return null;

					if(model.Image.isImage(f.getName())) {
						try {
							Thumbnail t = new Thumbnail(f.getAbsolutePath(), 100, 100);
							publish(t);
						}
						catch (Exception e)
						{}
					}
				}

				return null;
			}

			@Override
			protected void process(List<Thumbnail> chunks)
			{
				if (!isCancelled()) {
					for (Thumbnail t : chunks) {
						iconListModel.addElement(t);
						if(Path.isSelected(t.getName()))
								iconList.setSelectedValue(t, true);
					}
				}
			}
		};

		loadImageWorker.execute();
	}

	public ExplorerView(final Controller controller, Path p)
	{
		super("Explorer", 300, 300);

		this.controller = controller;

		p.addObserver(this);

		browse = new JButton("Browse");

		iconListModel = new DefaultListModel();
		iconList = new JList<Thumbnail>(iconListModel);
		iconList.setCellRenderer(new iconListCellRenderer());
		iconList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		iconList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		iconList.setVisibleRowCount(-1);
		iconList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e)
			{
				Thumbnail t = (Thumbnail)iconList.getSelectedValue();
				if (t != null)
					controller.thumbnailSelected(t);
			}
		});

		createImages();

		scroll = new JScrollPane(iconList);
		scroll.setPreferredSize(new Dimension(300, 300));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(browse);
		add(scroll);
	}

	public void update(Observable o, Object arg)
	{
		Path p = (Path)o;
		String tmp = path;
		path = p.getPath();
		if(tmp.equals(path))
			select();
		else
			createImages();
	}

	private void select() {
		for(int i = 0; i<iconListModel.getSize(); ++i) {
			if(Path.isSelected(((Thumbnail)iconListModel.getElementAt(i)).getName())) {
				iconList.setSelectedValue(iconListModel.getElementAt(i), true);
				return;
			}
		}
		iconList.clearSelection();
	}

	private class iconListCellRenderer extends JLabel implements ListCellRenderer<Thumbnail>
	{
		public iconListCellRenderer()
		{
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList<? extends Thumbnail> list, Thumbnail value, int index, boolean isSelected, boolean cellHasFocus)
		{
			ImageIcon image = value.getImage();
			if (image != null) {
				setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight() + 20));

				setText(value.getName());
				setVerticalTextPosition(JLabel.BOTTOM);
				setHorizontalTextPosition(JLabel.CENTER);

				setIcon(value.getImage());
				setHorizontalAlignment(JLabel.CENTER);

				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				}
				else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}
			}

			return this;
		}
	}
}
