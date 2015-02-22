package model;

import java.util.Observable;
import java.io.File;

public class Path extends Observable {
	private String path;
	private String selected = null;

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
			selected = f.getAbsolutePath();
			f = f.getParentFile();
		} else {
			selected = null;
		}
		setPath(f.getAbsolutePath());
		setChanged();
		notifyObservers();
	}
}
