package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Thumbnail {
	private static Thumbnail damaged = null;
	private String path;
	private BufferedImage original;
	private ImageIcon image;
	private String name;

	public Thumbnail(String path) throws Exception {
		this.path = path;
		Path p = Paths.get(path);
		name = p.getFileName().toString();

		original = ImageIO.read(new File(path));
		if (original == null)
			throw new Exception(path + ": this file seems to be damaged.");

		int imgWidth = original.getWidth();
		int imgHeight = original.getHeight();

		double maxRatio = 1;
		double imgRatio = (double) imgWidth / imgHeight;
		double scale = (imgRatio > maxRatio) ? (100.0 / imgWidth) : (100.0 / imgHeight);

		int newWidth = (int) (imgWidth * scale);
		int newHeight = (int) (imgHeight * scale);

		image = new ImageIcon(original.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_FAST));
	}

	public static Thumbnail getDamagedIcon(String name) throws Exception {
		if (damaged == null)
			damaged = new Thumbnail(new File("damaged.png").getAbsolutePath());

		damaged.setName(name);
		return damaged;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ImageIcon getImage() {
		return image;
	}

	public BufferedImage getOriginalImage() {
		return original;
	}
}
