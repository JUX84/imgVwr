package model;

import java.util.Observable;
import java.io.File;

public class Path extends Observable {
	private String path;
	private static String selected = null;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
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
		notifyObservers();
	}

	public static boolean isSelected(String name) {
		if(name.equals(selected))
			return true;
		return false;
	}
}
