package com.jhjava.jdoom;

import com.jhjava.jdoom.Stars3D;
import com.jhjava.jdoom.engine.Display;
import com.jhjava.jdoom.engine.RenderContext;
import com.jhjava.jdoom.engine.Vertex;

public class Main {
	public static void main(String[] args) {
		Display display = new Display(800, 600, "JDoom");
		RenderContext target = display.getFrameBuffer();
		Stars3D stars = new Stars3D(4096, 64.0f, 10.0f);

		Vertex minYVert = new Vertex(100, 100);
		Vertex midYVert = new Vertex(150, 200);
		Vertex maxYVert = new Vertex(80, 300);

		long previousTime = System.nanoTime();
		while(true) {
			long currentTime = System.nanoTime();
			float delta = (float) ((currentTime - previousTime)/1000000000.0);
			previousTime = currentTime;

//			stars.updateAndRender(target, delta);
			target.clear((byte) 0x00);

//			for (int j = 100; j < 200; j++) {
//				target.drawScanBuffer(j, 300 - j, 300 + j);
//			}
			target.scanConvertTriangle(minYVert, midYVert, maxYVert, 0);
			target.fillShape(100, 300);

			display.swapBuffers();
		}
	}
}
