import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {
	public BasePanel(String title, int width, int length) {
		super();
		setLayout(new GridLayout(1,1));
		JScrollPane scroll = new JScrollPane();
		add(scroll);
		setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(title),
					BorderFactory.createEmptyBorder(5,5,5,5)));
		setPreferredSize(new Dimension(width, length));
	}
}
