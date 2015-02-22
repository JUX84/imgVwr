package model;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Image extends Observable
{
	private String name;
	private String path;
	// private BufferedImage image = null;
	private java.awt.Image image = null;

	public Image(String path)
	{
		this.path = path;
		try {
			BufferedImage bi = ImageIO.read(new File(path));
			image = bi.getScaledInstance(300, -1, java.awt.Image.SCALE_FAST);
		}
		catch (Exception e) {}
	}

	public void paint(Graphics g)
	{
		g.drawImage(image, 0, 0, null);
	}
}
