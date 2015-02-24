package controller;

import view.*;
import model.*;
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
		explorer.setLanguage(language);
		explorer.setPath(path);
	}

	public void init(KeywordsView keywords) {
		language.addObserver(keywords);
		keywords.setLanguage(language);
	}

	public void init(LangView lang) {
		language.addObserver(lang);
		lang.setLanguage(language);
	}

	public void init(MenuView menu) {
		language.addObserver(menu);
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
}
