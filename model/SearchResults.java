package model;

import java.util.List;
import java.util.Observable;

public class SearchResults extends Observable
{
	List<String> results = null;

	public void setResults(List<String> results)
	{
		this.results = results;
		setChanged();
		notifyObservers("searchResults");
	}

	public List<String> getResults()
	{
		return results;
	}

	public void clear()
	{
		results = null;
	}
}
