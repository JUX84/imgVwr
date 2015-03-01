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
	private JButton en, fr, ja;

	public LangView(Controller controller) {
		super();
		this.controller = controller;
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		en = new JButton("English");
		fr = new JButton("Français");
		ja = new JButton("日本人");
		en.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.languageSelected("en");
			}
		});
		fr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.languageSelected("fr");
			}
		});
		ja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.languageSelected("ja");
			}
		});
		add(en);
		add(fr);
		add(ja);
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
