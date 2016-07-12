package com.jhjava.jdoom.engine.render;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Bitmap {
	private final int width;
	private final int height;
	private final byte[] components;

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		components = new byte[width * height * 4];
	}

	public Bitmap(String name) {
		int width = 0;
		int height = 0;
		byte[] components = null;

		BufferedImage image = null;

		try {
			image = ImageIO.read(Bitmap.class.getResourceAsStream("/com/jhjava/jdoom/res/textures/" + name));
		} catch (Exception e) {
			System.err.println("ERROR: Could not read image " + name + " in res/textures/");
			e.printStackTrace();
			System.exit(1);
		}

		width = image.getWidth();
		height = image.getHeight();

		int imgPixels[] = new int[width * height];
		image.getRGB(0, 0, width, height, imgPixels, 0, width);
		components = new byte[width * height * 4];

		for(int i = 0; i < width * height; i++) {
			int pixel = imgPixels[i];

			components[i * 4] = (byte)((pixel >> 24) & 0xFF); // A
			components[i * 4 + 1] = (byte)((pixel) & 0xFF); // B
			components[i * 4 + 2] = (byte)((pixel >> 8 ) & 0xFF); // G
			components[i * 4 + 3] = (byte)((pixel >> 16) & 0xFF); // R
		}

		this.width = width;
		this.height = height;
		this.components = components;
	}

	public void clear(byte shade) {
		Arrays.fill(components, shade);
	}
	
	public void drawPixel(int x, int y, byte a, byte b, byte g, byte r) {
		int index = (x + y * width) * 4;
		components[index] = a;
		components[index + 1] = b;
		components[index + 2] = g;
		components[index + 3] = r;
	}

	public void copyPixel(int destX, int destY, int srcX, int srcY, Bitmap src, float lightAmt) {
		int destIndex = (destX + destY * width) * 4;
		int srcIndex = (srcX + srcY * src.getWidth()) * 4;
		components[destIndex] = (byte) ((src.getComponent(srcIndex) & 0xff) * lightAmt);
		components[destIndex + 1] = (byte) ((src.getComponent(srcIndex + 1) & 0xff) * lightAmt);
		components[destIndex + 2] = (byte) ((src.getComponent(srcIndex + 2) & 0xff) * lightAmt);
		components[destIndex + 3] = (byte) ((src.getComponent(srcIndex + 3) & 0xff) * lightAmt);
	}

	public void copyToByteArray(byte[] dest) {
		for(int i = 0; i < width * height; i++) {
			dest[i * 3] = components[i * 4 + 1];
			dest[i * 3 + 1] = components[i * 4 + 2];
			dest[i * 3 + 2] = components[i * 4 + 3];
		}
	}

	public Bitmap flipX() {
		Bitmap ret = new Bitmap(width, height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int componentIndex = ((width - x - 1) + y * width) * 4;
				ret.drawPixel(x, y, components[componentIndex], components[componentIndex + 1], components[componentIndex + 2], components[componentIndex + 3]);
			}
		}

		return ret;
	}

	public Bitmap flipY() {
		Bitmap ret = new Bitmap(width, height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int componentIndex = (x + (height - y - 1) * width) * 4;
				ret.drawPixel(x, y, components[componentIndex], components[componentIndex + 1], components[componentIndex + 2], components[componentIndex + 3]);
			}
		}

		return ret;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public byte getComponent(int i) {
		return components[i];
	}
}
