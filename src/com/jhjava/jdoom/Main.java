package com.jhjava.jdoom;

public class Main {
	public static void main(String[] args) {
		Display display = new Display(800, 600, "JDoom");

		while(true) {
			display.swapBuffers();
		}
	}
}
