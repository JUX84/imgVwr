package model;

import java.util.*;

public class Language {
	private static ResourceBundle rb;

	public static void setLanguage(String lang) {
		Locale locale;
		if(lang == "fr")
			locale = Locale.FRENCH;
		else if (lang == "ja")
			locale = Locale.JAPANESE;
		else
			locale = Locale.ENGLISH;
		rb = ResourceBundle.getBundle("resource.strings", locale);
	}

	public static String getString(String key) {
		try {
			return new String(rb.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			return "Translate server error";
		}
	}
}
