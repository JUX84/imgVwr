import java.util.*;

public class LangView extends BasePanel {
	public LangView() {
		super("Language", 360, 40);
		ResourceBundle rb;
		rb = ResourceBundle.getBundle("resources.strings", Locale.JAPANESE);
		try {
		System.out.println(new String(rb.getString("lang").getBytes(), "UTF-8"));
		} catch (Exception e) {}
	}
}
