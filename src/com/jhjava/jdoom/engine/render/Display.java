package com.jhjava.jdoom.engine.render;

import com.jhjava.jdoom.engine.core.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Display extends Canvas {
	private final JFrame frame;
	private final RenderContext frameBuffer;
	private final BufferedImage displayImage;
	private final byte[] displayComponents;
	private final BufferStrategy bufferStrategy;
	private final Graphics graphics;
	private final Input input;

	public Display(int width, int height, String title) {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		frameBuffer = new RenderContext(width, height);
		displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		displayComponents = ((DataBufferByte) displayImage.getRaster().getDataBuffer()).getData();

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

		input = new Input();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

		setFocusable(true);
		requestFocus();
	}

	public void swapBuffers() {
		frameBuffer.copyToByteArray(displayComponents);
		graphics.drawImage(displayImage, 0, 0, frameBuffer.getWidth(), frameBuffer.getHeight(), null);
		bufferStrategy.show();
	}

	public RenderContext getFrameBuffer() {
		return frameBuffer;
	}

	public Input getInput() {
		return input;
	}
}
