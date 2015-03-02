package view;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.util.Observable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import model.Language;
import controller.Controller;

public class LangView extends BaseView {

    public LangView(final Controller controller) {
		super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        JButton en = new JButton("English");
        JButton fr = new JButton("Français");
        JButton ja = new JButton("日本人");
		en.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                controller.languageSelected("en");
            }
        });
		fr.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                controller.languageSelected("fr");
            }
        });
		ja.addActionListener(new ActionListener() {
			@Override
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
        super.setTitle(language.getString("language"));
	}

	@Override
	public void update (Observable o, Object arg) {
		String tmp = (String)arg;
		if(tmp.equals("language"))
			setLanguage((Language)o);
	}
}
