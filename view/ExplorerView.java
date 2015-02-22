package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.event.*;
import controller.Controller;

public class ExplorerView extends BaseView
{
	private Controller controller;

	private JScrollPane scroll;

	private DefaultListModel iconListModel;
	private JList<Thumbnail> iconList;

	private JButton browse;
	private String path = System.getProperty("user.home");

	public void createImages()
	{
		iconListModel.clear();

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
					try {
						iconListModel.addElement(new Thumbnail(f.getAbsolutePath(), 100, 100));
					}
					catch (Exception e)
					{}
				}
			}
		}
	}

	public ExplorerView(Controller controller)
	{
		super("Explorer", 300, 300);

		this.controller = controller;

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
	{}


	private class iconListCellRenderer extends JLabel implements ListCellRenderer<Thumbnail>
	{
		public iconListCellRenderer()
		{
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList<? extends Thumbnail> list, Thumbnail value, int index, boolean isSelected, boolean cellHasFocus)
		{
			ImageIcon image = value.getImage();
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

			return this;
		}
	}
}
