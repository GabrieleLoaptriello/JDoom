package com.jhjava.jdoom;

public class Main {
	public static void main(String[] args) {
		Display display = new Display(800, 600, "JDoom");
		Bitmap target = display.getFrameBuffer();
		Stars3D stars = new Stars3D(4096, 64.0f, 20.0f);

		long previousTime = System.nanoTime();
		while(true) {
			long currentTime = System.nanoTime();
			float delta = (float) ((currentTime - previousTime)/1000000000.0);
			previousTime = currentTime;

			stars.updateAndRender(target, delta);
			display.swapBuffers();
		}
	}
}
