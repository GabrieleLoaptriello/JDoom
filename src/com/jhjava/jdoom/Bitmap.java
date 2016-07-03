package com.jhjava.jdoom;

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

	public void clear(byte shade) {
		Arrays.fill(components, shade);
	}
	
	public void drawPixel(int x, int y, byte a, byte b, byte g, byte r) {
		int index = (y * width + x) * 4;
		components[index] = a;
		components[index + 1] = b;
		components[index + 2] = g;
		components[index + 3] = r;
	}

	public void copyToByteArray(byte[] dest) {
		for(int i = 0; i < width * height; i++) {
			dest[i * 3] = components[i * 4 + 1];
			dest[i * 3 + 1] = components[i * 4 + 2];
			dest[i * 3 + 2] = components[i * 4 + 3];
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
