package model;

import java.util.List;
import java.util.Observable;

public class SearchResults extends Observable {
	private List<String> results = null;

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
		setChanged();
		notifyObservers("searchResults");
	}

	public void clear() {
		results = null;
	}
}
