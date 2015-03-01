package controller;

import view.ExplorerView;
import view.LangView;
import view.KeywordsView;
import view.ViewerView;
import view.MenuView;
import view.TreeView;
import model.Language;
import model.Image;
import model.Path;
import model.Thumbnail;
import model.Keywords;
import java.io.File;

public class Controller
{
	private Image image;
	private Path path;
	private Language language;

	public Controller(Image image, Path path, Language language) {
		this.image = image;
		this.path = path;
		this.language = language;
	}

	public void init(ExplorerView explorer) {
		language.addObserver(explorer);
		path.addObserver(explorer);
		image.addObserver(explorer);
		explorer.setLanguage(language);
		explorer.setPath(path);
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
			System.out.println(tmp.getAbsolutePath());
			if(tmp.exists())
				return;
			f.renameTo(tmp);
			image.setName(name);
		}
	}
}
