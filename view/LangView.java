package view;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.util.Observable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import model.Language;
import controller.Controller;

public class LangView extends BaseView {
	private Controller controller;
	private Language language;

	public LangView(Controller controller) {
		super();
		this.controller = controller;
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		LangButton engBtn, fraBtn, japBtn;
		engBtn = new LangButton("English", controller);
		fraBtn = new LangButton("Français", controller);
		japBtn = new LangButton("日本人", controller);
		add(engBtn);
		add(fraBtn);
		add(japBtn);
		controller.init(this);
	}

	public void setLanguage(Language language) {
		this.language = language;
		super.setTitle(language.getString("language"));
	}

	public void update (Observable o, Object arg) {
		String tmp = (String)arg;
		if(tmp.equals("language"))
			setLanguage((Language)o);
	}
}

class LangButton extends JButton implements ActionListener {
	private String lang;
	private Controller controller;

	public LangButton(String str, Controller controller) {
		super(str);
		lang = "fr";
		if(str.equals("English"))
			lang = "en";
		if(str.equals("日本人"))
			lang = "ja";
		this.controller = controller;
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		controller.languageSelected(lang);
	}
}
