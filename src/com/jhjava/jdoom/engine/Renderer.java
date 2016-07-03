package com.jhjava.jdoom.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Renderer extends JFrame implements KeyListener {
	private Graphics2D graphics;

	public Renderer(String title, int width, int height) {
		super(title);

		setSize(width, height);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);

		setVisible(true);

		graphics = (Graphics2D) getGraphics();
		graphics.scale(4, 4);
	}

	public Renderer(String title) {
		this(title, 600, 400);
	}

	public Renderer() {
		this("");
	}

	float playerX = 50;
	float playerY = 50;
	float playerAngle = 0;

	public void render() {
		drawWall(70, 70, 70, 20, Color.BLUE, playerX, playerY, playerAngle);
		drawWall(70, 20, 50, 30, Color.GREEN, playerX, playerY, playerAngle);
		drawWall(50, 30, 20, 50, Color.YELLOW, playerX, playerY, playerAngle);
		drawWall(20, 50, 20, 70, Color.CYAN, playerX, playerY, playerAngle);
		drawWall(20, 70, 70, 70, Color.RED, playerX, playerY, playerAngle);

		System.out.println(playerX + " " + playerY);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.err.println("ERROR: Could not sleep in main game loop");
		}
	}

	private void drawWall(float vx1, float vy1, float vx2, float vy2, Color color, float px, float py, float angle) {
		float tx1 = vx1 - px;
		float ty1 = vy1 - py;
		float tx2 = vx2 - px;
		float ty2 = vy2 - py;

		float tz1 = (float)(tx1 * Math.cos(angle) + ty1 * Math.sin(angle));
		float tz2 = (float)(tx2 * Math.cos(angle) + ty2 * Math.sin(angle));
		tx1 = (float)(tx1 * Math.sin(angle) - ty1 * Math.cos(angle));
		tx2 = (float)(tx2 * Math.sin(angle) - ty2 * Math.cos(angle));

		if(tz1 > 0 || tz2 > 0) {
			Vector2f ret = intersect(tx1, tz1, tx2, tz2, -0.0001f, 0.0001f, -20, 5);
			float ix1 = ret.x;
			float iz1 = ret.y;
			ret = intersect(tx1, tz1, tx2, tz2, 0.0001f, 0.0001f, 20, 5);
			float ix2 = ret.x;
			float iz2 = ret.y;

			if(tz1 <= 0) {
				if(iz1 > 0) {
					tx1 = ix1;
					tz1 = iz1;
				} else {
					tx1 = ix2;
					tz1 = iz2;
				}
			}
			if(tz2 <= 0) {
				if(iz1 > 0) {
					tx2 = ix1;
					tz2 = iz1;
				} else {
					tx2 = ix2;
					tz2 = iz2;
				}
			}

			float x1 = -tx1 * 16 / tz1;
			float y1a = -50 / tz1;
			float y1b = 50 / tz1;
			float x2 = -tx2 * 16 / tz2;
			float y2a = -50 / tz2;
			float y2b = 50 / tz2;

			if(x1 < x2) {
				for (float x = x1; x < x2; x++) {
					float ya = y1a + (x - x1) * (long) (y2a - y1a) / (x2 - x1);
					float yb = y1b + (x - x1) * (long) (y2b - y1b) / (x2 - x1);

					graphics.setColor(Color.GRAY);
					graphics.drawLine((int)(50 + x), (int)(0), (int)(50 + x), (int)(50 + -ya));
					graphics.setColor(Color.WHITE);
					graphics.drawLine((int)(50 + x), (int)(50 + yb), (int)(50 + x), (int)(140));

					graphics.setColor(color);
					graphics.drawLine((int) (50 + x), (int) (50 + ya), (int) (50 + x), (int) (50 + yb));
				}
			} else {
				for (float x = x2; x < x1; x++) {
					float ya = y1a + (x - x1) * (long) (y2a - y1a) / (x2 - x1);
					float yb = y1b + (x - x1) * (long) (y2b - y1b) / (x2 - x1);

					graphics.setColor(Color.GRAY);
					graphics.drawLine((int)(50 + x), (int)(0), (int)(50 + x), (int)(50 + -ya));
					graphics.setColor(Color.WHITE);
					graphics.drawLine((int)(50 + x), (int)(50 + yb), (int)(50 + x), (int)(140));

					graphics.setColor(color);
					graphics.drawLine((int) (50 + x), (int) (50 + ya), (int) (50 + x), (int) (50 + yb));
				}
			}

			graphics.setColor(color);
			graphics.drawLine((int)(50 + x1), (int)(50 + y1a), (int)(50 + x1), (int)(50 + y1b));
			graphics.drawLine((int)(50 + x2), (int)(50 + y2a), (int)(50 + x2), (int)(50 + y2b));
		}
	}

	private float cross(float x1, float y1, float x2, float y2) {
		return x1 * y2 - y1 * x2;
	}

	private Vector2f intersect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		float x = cross(x1, y1, x2, y2);
		float y = cross(x3, y3, x4, y4);
		float det = cross(x1 - x2, y1 - y2, x3 - x4, y3 - y4);
		x = cross(x, x1-x2, y, x3 - x4) / det;
		y = cross(x, y1-y2, y, y3 - y4) / det;
		return new Vector2f(x, y);
	}

	private void clear() {
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				playerX += Math.cos(playerAngle);
				playerY += Math.sin(playerAngle);
				break;
			case KeyEvent.VK_DOWN:
				playerX -= Math.cos(playerAngle);
				playerY -= Math.sin(playerAngle);
				break;
			case KeyEvent.VK_RIGHT:
				playerAngle += 0.1f;
				break;
			case KeyEvent.VK_LEFT:
				playerAngle -= 0.1f;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
