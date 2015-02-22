package model;

import java.util.Observable;
import java.io.File;

public class Path extends Observable {
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void set(File f) {
		setPath(f.getName());
		setChanged();
		notifyObservers();
	}		
}
