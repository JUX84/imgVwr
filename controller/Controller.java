package controller;

import model.*;

public class Controller
{
	private Image image;

	public Controller(Image image) {
		this.image = image;
	}

	public void thumbnailSelected(Thumbnail t)
	{
		image.setName(t.getName());
		image.setPath(t.getPath());
		image.setBufferedImage(t.getOriginalImage());
		t.selected();
	}
}
