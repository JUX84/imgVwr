package model;

import java.io.File;
import java.util.Observable;

public class Path extends Observable {
	private static String selected = null;
	private String path;

	public Path(String path) {
		this.path = path;
		selected = null;
	}

	public static boolean isSelected(String name) {
		return name.equals(selected);
	}

	public String getPath() {
		return path;
	}

	void setPath(String path) {
		this.path = path;
	}

	public void set(File f) {
		if (f == null)
			return;

		selected = null;
		if (!f.isDirectory()) {
			if (Image.isImage(f.getName()))
				selected = f.getName();
			f = f.getParentFile();
		}
		setPath(f.getAbsolutePath());

		setChanged();
		notifyObservers("path");
	}
}
