package controller;

import model.*;
import view.*;

import javax.swing.*;
import java.io.File;

public class Controller
{
	private final Image image;
	private final Path path;
	private final Language language;
	private final SearchResults results;

	public Controller(Image image, Path path, Language language, SearchResults results) {
		this.image = image;
		this.path = path;
		this.language = language;
		this.results = results;
	}

	public void init(ExplorerView explorer) {
		language.addObserver(explorer);
		path.addObserver(explorer);
		image.addObserver(explorer);
		results.addObserver(explorer);
		explorer.setLanguage(language);
		explorer.setPath(path);
		explorer.setSearchResults(results);
	}

	public void init(KeywordsView keywords) {
		language.addObserver(keywords);
		image.addObserver(keywords);
		keywords.setLanguage(language);
	}

	public void init(LangView lang) {
		language.addObserver(lang);
		lang.setLanguage(language);
	}

	public void init(MenuView menu) {
		language.addObserver(menu);
		image.addObserver(menu);
		menu.setLanguage(language);
	}

	public void init(TreeView tree) {
		language.addObserver(tree);
		path.addObserver(tree);
		tree.setLanguage(language);
	}

	public void init(ViewerView viewer) {
		language.addObserver(viewer);
		image.addObserver(viewer);
		viewer.setLanguage(language);
		viewer.setImage(image);
	}

	public void languageSelected(String str) {
		language.setLanguage(str);
	}

	public void thumbnailSelected(Thumbnail t)
	{
		image.set(t);
	}

	public void pathSelected(File f) {
		path.set(f);
	}

	public void keywordsSaved(String kws)
	{
		String p = image.getPath();
		if (p != null)
			Keywords.setKeywords(p, kws);
	}

	public void imageRenamed(String name) {
		if (image != null) {
			File f = new File(image.getPath());
			File tmp = new File(f.getParent() + "/" + name);
			if(f.getAbsolutePath().equals(tmp.getAbsolutePath()))
				return;
			if(tmp.exists()) {
				JOptionPane.showMessageDialog(null, language.getString("errorDuplicate"), language.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			boolean b = f.renameTo(tmp);
			if(b)
				image.setName(name);
		}
	}

	public void searchByKeyword(String searchText)
	{
		if (!searchText.isEmpty())
			results.setResults(Keywords.search(searchText));
	}

	public void imageDeleted()
	{
		if (image != null) {
			File f = new File(image.getPath());
			if(!f.delete())
                JOptionPane.showMessageDialog(null, language.getString("errorDelete"), language.getString("error"), JOptionPane.ERROR_MESSAGE);
			path.set(new File(path.getPath())); // get path observers to update
		}
	}
}
