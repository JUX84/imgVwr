import javax.swing.*;
import java.awt.*;
import java.util.*;

public abstract class BaseView extends JPanel implements Observer {
	public BaseView(String title, int width, int length) {
		super();
		setLayout(new GridLayout(1,1));
		setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder(title),
					BorderFactory.createEmptyBorder(5,5,5,5)));
		// setPreferredSize(new Dimension(width, length));
	}

	public abstract void update (Observable o, Object arg);
}
