package model;

import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Thumbnail
{
	private ImageIcon image;
	private String name;

	public Thumbnail(String path, int maxWidth, int maxHeight)
	{
		Path p = Paths.get(path);
		name = p.getFileName().toString();

		try {
			BufferedImage bi = ImageIO.read(new File(path));

			int imgWidth = bi.getWidth();
			int imgHeight = bi.getHeight();

			double maxRatio = (double)maxWidth / maxHeight;
			double imgRatio = (double)imgWidth / imgHeight;
			double scale = (imgRatio > maxRatio) ?  (double)maxWidth / imgWidth : (double)maxHeight / imgHeight;

			int newWidth = (int)(imgWidth * scale);
			int newHeight = (int)(imgHeight * scale);

			image = new ImageIcon(bi.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_FAST));
		}
		catch (Exception e) {}
	}

	public String getName()
	{
		return name;
	}

	public ImageIcon getImage()
	{
		return image;
	}
}
