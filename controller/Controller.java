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
		System.out.println("test");
		path.set(f);
	}
}
