package view;

import javax.swing.*;
import java.util.*;
import model.*;

public class ViewerView extends BaseView {
	private Image image;
	private JButton rename;
	private JButton hide;

	public ViewerView() {
		super("Viewer", 360, 240);
	}

	public void update (Observable o, Object arg) {
	}
}
