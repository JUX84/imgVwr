package model;

import java.awt.image.BufferedImage;
import java.util.Observable;

public class Image extends Observable
{
	private String name = null;
	private String path = null;
	private BufferedImage bi = null;

	public void setName(String name) {
		this.name = name;

		setChanged();
		notifyObservers("image");
	}

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
		notifyObservers("image");
	}

	public static boolean isImage(String path) {
		String extension = null;
		int pos = path.lastIndexOf('.');
		if(pos>0 && pos<path.length()-1) {
			extension = path.substring(pos+1).toLowerCase();
		}
		if (extension != null) {
			if (extension.equals("jpeg")
					|| extension.equals("jpg")
					|| extension.equals("gif")
					|| extension.equals("png")) {
				return true;
			}
		}
		return false;
	}

	public int getWidth() {
		return (bi != null ? bi.getWidth() : 0);
	}

	public int getHeight() {
		return (bi != null ? bi.getHeight() : 0);
	}
}
