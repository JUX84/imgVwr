package model;

import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

public class Language extends Observable {
	private ResourceBundle rb;

	public Language(String lang) {
		setLanguage(lang);
	}

	public void setLanguage(String lang) {
		Locale locale;
		if (lang.equals("fr"))
			locale = Locale.FRENCH;
		else if (lang.equals("ja"))
			locale = Locale.JAPANESE;
		else
			locale = Locale.ENGLISH;
		rb = ResourceBundle.getBundle("resource.strings", locale);

		setChanged();
		notifyObservers("language");
	}

	public String getString(String key) {
		try {
			return new String(rb.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			return "Translate server error";
		}
	}
}
