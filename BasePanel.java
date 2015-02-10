import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {
	public BasePanel(String title, int width, int length) {
		super(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(title),
					BorderFactory.createEmptyBorder(5,5,5,5)));
		setPreferredSize(new Dimension(width, length));
	}
}
