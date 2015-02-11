import javax.swing.*;
import java.util.*;

public class LangView extends BaseView {
	public LangView() {
		super("Language", 360, 80);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		ResourceBundle rb;
		rb = ResourceBundle.getBundle("resources.strings", Locale.JAPANESE);
		try {
			System.out.println(new String(rb.getString("lang").getBytes("ISO-8859-1"), "UTF-8"));
		} catch (Exception e) {}
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
