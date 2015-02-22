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
	private BufferedImage bi = null;

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public BufferedImage getBufferedImage() {
		return bi;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setBufferedImage(BufferedImage bi) {
		this.bi = bi;
	}
}
