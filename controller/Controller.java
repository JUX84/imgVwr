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
		image.set(t);
	}
}
