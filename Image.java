import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class Image extends JPanel
{
	private String name;
	private String path;
	private java.awt.BufferedImage image;

	public Image(String path)
	{
		this.path = path;
		image = new ImageIcon(path).getImage();
	}

	public void paint(Graphics g)
	{
		g.drawImage(image, 0, 0, null);
	}
}
