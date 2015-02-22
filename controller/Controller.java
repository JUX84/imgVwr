package controller;

import model.*;
import java.io.File;

public class Controller
{
	private Image image;
	private Path path;

	public Controller(Image image, Path path) {
		this.image = image;
		this.path = path;
	}

	public void thumbnailSelected(Thumbnail t)
	{
		image.set(t);
	}

	public void pathSelected(File f) {
		if(!f.isDirectory()) {
			Thumbnail t = new Thumbnail(f.getAbsolutePath(), 0, 0);
			image.set(t);
			f = f.getParentFile();
		}
		path.set(f);
	}
}
