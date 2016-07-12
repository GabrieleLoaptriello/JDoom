package com.jhjava.jdoom.engine.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, FocusListener,
		MouseListener, MouseMotionListener {
	private JFrame frame;

	private boolean[] keys = new boolean[65536];
	private boolean[] mouseButtons = new boolean[4];
	private int mouseX = 0;
	private int mouseY = 0;
	private int prevMouseX = 0;
	private int prevMouseY = 0;

	private float mouseXDelta = 0;
	private float mouseYDelta = 0;

	private boolean mouseCaptured = true;
	private boolean captureMouse = false;

	public Input(JFrame frame) {
		this.frame = frame;
		try {
			Robot robot = new Robot();
			robot.mouseMove(frame.getX() + frame.getWidth() / 2, frame.getY() + frame.getHeight() / 2);
		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}

	public void update() {
		if(!captureMouse) {
			mouseXDelta = (mouseX - prevMouseX);
			mouseYDelta = (mouseY - prevMouseY);
			prevMouseX = mouseX;
			prevMouseY = mouseY;
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(mouseCaptured) {
			mouseX = e.getX();
			mouseY = e.getY();
			if(captureMouse) {
				try {
					Robot robot = new Robot();
					robot.mouseMove(frame.getX() + frame.getWidth() / 2, frame.getY() + frame.getHeight() / 2);
				} catch (AWTException ex) {
					ex.printStackTrace();
				}
				mouseXDelta = (mouseX - frame.getWidth() / 2);
				mouseYDelta = (mouseY - frame.getHeight() / 2);
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(mouseCaptured) {
			mouseX = e.getX();
			mouseY = e.getY();
			if(captureMouse) {
				try {
					Robot robot = new Robot();
					robot.mouseMove(frame.getX() + frame.getWidth() / 2, frame.getY() + frame.getHeight() / 2);
				} catch (AWTException ex) {
					ex.printStackTrace();
				}
				mouseXDelta = (mouseX - frame.getWidth() / 2);
				mouseYDelta = (mouseY - frame.getHeight() / 2);
				System.out.println(mouseY + ", " + frame.getHeight() / 2);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		int code = e.getButton();
		if(code > 0 && code < mouseButtons.length)
			mouseButtons[code] = true;
		if(code == 1) {
			mouseCaptured = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		int code = e.getButton();
		if(code > 0 && code < mouseButtons.length)
			mouseButtons[code] = false;
	}

	public void focusGained(FocusEvent e) {
		mouseCaptured = true;
	}

	public void focusLost(FocusEvent e) {
		for(int i = 0; i < keys.length; i++)
			keys[i] = false;
		for(int i = 0; i < mouseButtons.length; i++)
			mouseButtons[i] = false;
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code > 0 && code < keys.length)
			keys[code] = true;
		if(code == KeyEvent.VK_ESCAPE) {
			mouseCaptured = false;
		}
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code > 0 && code < keys.length)
			keys[code] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public boolean getKey(int key) {
		return keys[key];
	}

	public boolean getMouse(int button) {
		return mouseButtons[button];
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public float getMouseXDelta() {
		return mouseXDelta;
	}

	public float getMouseYDelta() {
		return mouseYDelta;
	}
}
