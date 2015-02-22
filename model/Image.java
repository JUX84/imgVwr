package model;

import java.awt.image.BufferedImage;
import java.util.Observable;

public class Image extends Observable
{
	private String name;
	private String path;
	private BufferedImage bi = null;

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public BufferedImage getBufferedImage() {
		return bi;
	}

	public void set(Thumbnail t)
	{
		name = t.getName();
		path = t.getPath();
		bi = t.getOriginalImage();

		setChanged();
		notifyObservers();
	}
}
