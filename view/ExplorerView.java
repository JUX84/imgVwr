package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.DefaultListModel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.SwingWorker;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Observer;
import java.util.Observable;
import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import controller.Controller;
import model.Language;
import model.Path;
import model.SearchResults;
import model.Thumbnail;

public class ExplorerView extends BaseView implements Observer
{
	private final Controller controller;
	private Language language;
	private SearchResults results;
	private String path;

	private JScrollPane scroll;

	private DefaultListModel iconListModel;
	private JList<Thumbnail> iconList;

	private JButton browse;
	private JButton search;
	private JTextField searchField;

	private SwingWorker<Void, Thumbnail> loadImageWorker = null;

	public void createImages()
	{
		if (path == null)
			return;

		new imageLoader().execute();
	}

	public ExplorerView(final Controller controller)
	{
		super();

		this.controller = controller;

		browse = new JButton();
		search = new JButton();
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				controller.searchByKeyword(searchField.getText());
			}
		});
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(200, 20));

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

		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add(browse, BorderLayout.LINE_START);
		JPanel topRight = new JPanel();
		topRight.setLayout(new FlowLayout());
		topRight.add(searchField);
		topRight.add(search);
		top.add(topRight, BorderLayout.LINE_END);

		setLayout(new BorderLayout());
		add(top, BorderLayout.PAGE_START);
		add(scroll, BorderLayout.CENTER);
		controller.init(this);
	}

	public void setLanguage(Language language) {
		this.language = language;
		super.setTitle(language.getString("explorer"));
		browse.setText(language.getString("browse"));
		search.setText(language.getString("search"));
	}

	public void setPath(Path p)
	{
		if (path != null && path.equals(p.getPath())) {
			select();
		}
		else {
			path = p.getPath();
			createImages();
		}
	}

	public void setSearchResults(SearchResults sr)
	{
		results = sr;
	}

	public void setSelectedName(model.Image img) {
		iconList.getSelectedValue().setName(img.getName());
	}

	public void update(Observable o, Object arg)
	{
		String tmp = (String)arg;
		if (tmp.equals("path"))
			setPath((Path)o);
		else if (tmp.equals("language"))
			setLanguage((Language)o);
		else if (tmp.equals("image"))
			setSelectedName((model.Image)o);
		else if (tmp.equals("searchResults"))
			new imageLoader(results.getResults()).execute();
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

	private class imageLoader extends SwingWorker<Void, Thumbnail>
	{
		private File[] files;

		public imageLoader()
		{
			if (loadImageWorker != null)
				loadImageWorker.cancel(true);

			loadImageWorker = this;

			File folder = new File(path);
			files = folder.listFiles();
		}

		public imageLoader(List<String> paths)
		{
			if (loadImageWorker != null)
				loadImageWorker.cancel(true);

			loadImageWorker = this;

			int size = paths.size();
			files = new File[size];
			for (int i = 0; i < size; i++)
				files[i] = new File(paths.get(i));

			results.clear();
		}

		@Override
		protected Void doInBackground() throws Exception
		{
			iconListModel.clear();
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
					catch (Exception e) {
						System.err.println(e);
						// publish damaged image icon?
					}
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
					if (Path.isSelected(t.getName()))
						iconList.setSelectedValue(t, true);
				}
			}
		}
	}
}
