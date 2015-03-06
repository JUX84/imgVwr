package view;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Observer;
import java.util.Observable;

abstract class BaseView extends JPanel implements Observer {
	BaseView() {
		super();
		setLayout(new GridLayout(1,1));
	}

	void setTitle(String str) {
		setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(str),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}

    void setTitleColor(Color c) {
        CompoundBorder cb = (CompoundBorder) getBorder();
        TitledBorder tb = (TitledBorder) cb.getOutsideBorder();
        tb.setTitleColor(c);
    }

	@Override
	public abstract void update (Observable o, Object arg);
}
