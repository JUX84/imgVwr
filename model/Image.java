package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.regex.Pattern;

public class Image extends Observable {
	private String name = null;
	private String path = null;
	private BufferedImage bi = null;

	public static boolean isImage(String path) {
		String extension = null;
		int pos = path.lastIndexOf('.');
		if (pos > 0 && pos < path.length() - 1) {
			extension = path.substring(pos + 1).toLowerCase();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

		String[] paths = path.split(Pattern.quote(System.getProperty("file.separator")));
		paths[paths.length-1] = name;
		path = "";
		for ( int i = 1; i < paths.length; i++ )
			path += "/" + paths[i];

		setChanged();
		notifyObservers("image");
	}

	public String getPath() {
		return path;
	}

	public BufferedImage getBufferedImage() {
		return bi;
	}

	public void set(Thumbnail t) {
		name = t.getName();
		path = t.getPath();
		bi = t.getOriginalImage();

		setChanged();
		notifyObservers("image");
	}

	public int getWidth() {
		return (bi != null ? bi.getWidth() : 0);
	}

	public int getHeight() {
		return (bi != null ? bi.getHeight() : 0);
	}

	public boolean isNotDamaged() {
		if (path != null) {
			File f = new File(path);
			return (!f.getName().equals("damaged.png") || f.getParentFile().getAbsolutePath().equals(path));
		}
		return false;
	}
}
