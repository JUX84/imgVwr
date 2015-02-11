import javax.swing.*;
import java.util.*;

public class LangView extends BaseView {
	public LangView() {
		super("Language", 360, 80);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		JButton engBtn, fraBtn, japBtn;
		engBtn = new JButton("English");
		fraBtn = new JButton("Français");
		japBtn = new JButton("日本人");
		add(engBtn);
		add(fraBtn);
		add(japBtn);
	}

	public void update (Observable o, Object arg) {
	}
}
