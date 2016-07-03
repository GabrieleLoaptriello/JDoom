package com.jhjava.jdoom;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Display extends Canvas {
	private final JFrame frame;
	private final Bitmap frameBuffer;
	private final BufferedImage displayImage;
	private final byte[] displayComponents;
	private final BufferStrategy bufferStrategy;
	private final Graphics graphics;

	public Display(int width, int height, String title) {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		frameBuffer = new Bitmap(width, height);
		displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		displayComponents = ((DataBufferByte) displayImage.getRaster().getDataBuffer()).getData();

		frameBuffer.clear((byte) 0x80);
		frameBuffer.drawPixel(100, 100, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xff);
		frameBuffer.drawPixel(101, 100, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xff);
		frameBuffer.drawPixel(100, 101, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xff);
		frameBuffer.drawPixel(101, 101, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xff);

		frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle(title);
		frame.setVisible(true);

		createBufferStrategy(1);
		bufferStrategy = getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
	}

	public void swapBuffers() {
		frameBuffer.copyToByteArray(displayComponents);
		graphics.drawImage(displayImage, 0, 0, frameBuffer.getWidth(), frameBuffer.getHeight(), null);
		bufferStrategy.show();
	}
}
