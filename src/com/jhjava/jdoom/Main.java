package com.jhjava.jdoom;

import com.jhjava.jdoom.engine.*;

public class Main {
	public static void main(String[] args) {
		Display display = new Display(800, 600, "JDoom");
		RenderContext target = display.getFrameBuffer();

		Bitmap texture = new Bitmap("test.png");
		Mesh mesh = new Mesh("cube.obj");

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
			Matrix4f rotation = new Matrix4f().initRotation(rot, rot, rot);
			Matrix4f transform = projection.mul(translation.mul(rotation));

			target.drawMesh(mesh, transform, texture);

			display.swapBuffers();
		}
	}
}
