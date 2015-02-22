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

	public String getSelected() {
		return selected;
	}

	public void set(File f) {
		if(!f.isDirectory()) {
			if(Image.isImage(f.getName()))
				selected = f.getName();
			else
				selected = null;
			f = f.getParentFile();
		} else {
			selected = null;
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
