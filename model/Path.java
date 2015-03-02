package model;

import java.util.Observable;
import java.io.File;

public class Path extends Observable {
	private String path;
	private static String selected;

	public Path(String path) {
		this.path = path;
		selected = null;
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
		if(!f.isDirectory()) {
			if(Image.isImage(f.getName()))
				selected = f.getName();
			f = f.getParentFile();
		}
		setPath(f.getAbsolutePath());

		setChanged();
		notifyObservers("path");
	}

	public static boolean isSelected(String name) {
        return name.equals(selected);
    }
}
