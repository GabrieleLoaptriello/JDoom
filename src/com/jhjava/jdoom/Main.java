package com.jhjava.jdoom;

import com.jhjava.jdoom.Stars3D;
import com.jhjava.jdoom.engine.Display;
import com.jhjava.jdoom.engine.Matrix4f;
import com.jhjava.jdoom.engine.RenderContext;
import com.jhjava.jdoom.engine.Vertex;

public class Main {
	public static void main(String[] args) {
		Display display = new Display(800, 600, "JDoom");
		RenderContext target = display.getFrameBuffer();
		Stars3D stars = new Stars3D(4096, 64.0f, 10.0f);

		Vertex v1 = new Vertex(-1, -1, 0);
		Vertex v2 = new Vertex(0, 1, 0);
		Vertex v3 = new Vertex(1, -1, 0);

		Matrix4f projection = new Matrix4f().initPerspective((float) Math.toRadians(70.0f), (float) target.getWidth() / (float) target.getHeight(), 0.1f, 1000.0f);

		float rot = 0.0f;

		long previousTime = System.nanoTime();
		while(true) {
			long currentTime = System.nanoTime();
			float delta = (float) ((currentTime - previousTime)/1000000000.0);
			previousTime = currentTime;

			target.clear((byte) 0x00);

			rot += 50 * delta;
			Matrix4f translation = new Matrix4f().initTranslation(0.0f, 0.0f, 3.0f);
			Matrix4f rotation = new Matrix4f().initRotation(0.0f, rot, 0.0f);
			Matrix4f transform = projection.mul(translation.mul(rotation));

			target.fillTriangle(v1.transform(transform), v2.transform(transform), v3.transform(transform));

			display.swapBuffers();
		}
	}
}
