package view;

import controller.Controller;
import model.Language;
import model.Path;
import model.SearchResults;
import model.Thumbnail;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public final class ExplorerView extends BaseView implements Observer
{
	private final DefaultListModel<Thumbnail> iconListModel;
	private final JList<Thumbnail> iconList;
	private final JButton browse;
	private final JButton search;
	private final ImageContextMenu contextMenu;
	private SearchResults results = null;
	private String path = null;
	private JTextField searchField;
	private SwingWorker<Void, Thumbnail> loadImageWorker = null;
	private model.Image image = null;

	private boolean searchDisplay = false;

	private Language language = null;

	public ExplorerView(final Controller controller)
	{
		super();

		contextMenu = new ImageContextMenu(controller);

		browse = new JButton();

		browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FileChooser(controller);
			}
		});

		search = new JButton();
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = searchField.getText();
				if (s.isEmpty())
					createImages();
				else
					controller.searchByKeyword(searchField.getText());
			}
		});

		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(200, 20));
		searchField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = searchField.getText();
				if (s.isEmpty())
					createImages();
				else
					controller.searchByKeyword(searchField.getText());
			}
		});

		iconListModel = new DefaultListModel<Thumbnail>();
		iconList = new JList<Thumbnail>(iconListModel);
		iconList.setCellRenderer(new iconListCellRenderer());
		iconList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		iconList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		iconList.setVisibleRowCount(-1);
		iconList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Thumbnail t = iconList.getSelectedValue();
				if (t != null)
					controller.thumbnailSelected(t);
			}
		});
		iconList.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					Point p = e.getPoint();
					int index = iconList.locationToIndex(p);

					if (index != -1
							&& iconList.getCellBounds(index, index).contains(p)) {
						iconList.setSelectedIndex(index);
						contextMenu.setImage(image);
						contextMenu.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) // CROSSPLATFORM
			{
				if (e.isPopupTrigger()) {
					Point p = e.getPoint();
					int index = iconList.locationToIndex(p);

					if (index != -1
							&& iconList.getCellBounds(index, index).contains(p)) {
						iconList.setSelectedIndex(index);
						contextMenu.setImage(image);
						contextMenu.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
		});

		createImages();

		JScrollPane scroll = new JScrollPane(iconList);
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

	void createImages() {
		if (path == null)
			return;

		new imageLoader().execute();
	}

	public void setLanguage(Language language) {
		if (this.language == null)
			this.language = language;

		super.setTitle(language.getString("explorer"));
		browse.setText(language.getString("browse"));
		search.setText(language.getString("search"));
		contextMenu.setLanguage(language);
	}

	public void setPath(Path p) {
		if (path != null && path.equals(p.getPath()) && !searchDisplay) {
			select();
		} else {
			path = p.getPath();
			createImages();
		}
	}

	void setImage(model.Image image) {
		if(this.image == null)
			this.image = image;

		iconList.getSelectedValue().setName(image.getName());
		iconList.getSelectedValue().setPath(image.getPath());
	}

	public void setSearchResults(SearchResults sr) {
		results = sr;
	}

	@Override
	public void update(Observable o, Object arg) {
		String tmp = (String) arg;
		if (tmp.equals("path"))
			setPath((Path) o);
		else if (tmp.equals("language"))
			setLanguage((Language) o);
		else if (tmp.equals("image"))
			setImage((model.Image) o);
		else if (tmp.equals("searchResults"))
			new imageLoader(results.getResults()).execute();
	}

	private void select() {
		for (int i = 0; i < iconListModel.getSize(); ++i) {
			if (Path.isSelected((iconListModel.getElementAt(i)).getName())) {
				iconList.setSelectedValue(iconListModel.getElementAt(i), true);
				return;
			}
		}
		iconList.clearSelection();
	}

	private class imageLoader extends SwingWorker<Void, Thumbnail> {
		private final File[] files;

		public imageLoader() {
			searchDisplay = false;

			if (loadImageWorker != null)
				loadImageWorker.cancel(true);

			loadImageWorker = this;

			File folder = new File(path);
			files = folder.listFiles();
		}

		public imageLoader(List<String> paths) {
			searchDisplay = true;

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
		protected Void doInBackground() throws Exception {
			iconListModel.clear();
			if (files == null)
				return null;

			for (File f : files) {
				if (isCancelled())
					return null;

				if (model.Image.isImage(f.getName())) {
					try {
						Thumbnail t = new Thumbnail(f.getAbsolutePath());
						publish(t);
					} catch (Exception e) {
						System.err.println(e.getMessage());
						try {
							publish(Thumbnail.getDamagedIcon(f.getName()));
						} catch (Exception ex) {
							System.err.println(ex.getMessage());
						}
					}
				}
			}

			return null;
		}

		@Override
		protected void process(List<Thumbnail> chunks) {
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

class iconListCellRenderer extends JLabel implements ListCellRenderer<Thumbnail> {
	public iconListCellRenderer() {
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList<? extends Thumbnail> list, Thumbnail value, int index, boolean isSelected, boolean cellHasFocus) {
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
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
		}

		return this;
	}
}
