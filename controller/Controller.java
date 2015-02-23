package controller;

import model.*;
import java.io.File;

public class Controller
{
	private Image image;
	private Path path;
	private Language lang;

	public Controller(Image image, Path path, Language lang) {
		this.image = image;
		this.path = path;
		this.lang = lang;
	}

	public void languageSelected(String str) {
		lang.setLanguage(str);
	}

	public void thumbnailSelected(Thumbnail t)
	{
		image.set(t);
	}

	public void pathSelected(File f) {
		path.set(f);
	}
}
