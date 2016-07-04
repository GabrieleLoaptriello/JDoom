package com.jhjava.jdoom;

import com.jhjava.jdoom.engine.*;
import com.jhjava.jdoom.engine.core.Matrix4f;
import com.jhjava.jdoom.engine.core.Quaternion;
import com.jhjava.jdoom.engine.core.Transform;
import com.jhjava.jdoom.engine.core.Vector4f;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Display;
import com.jhjava.jdoom.engine.render.Mesh;
import com.jhjava.jdoom.engine.render.RenderContext;

public class Main {
	public static void main(String[] args) {
		Display display = new Display(800, 600, "JDoom");
		RenderContext target = display.getFrameBuffer();

		Bitmap texture = new Bitmap("bricks.jpg");
		Bitmap texture2 = new Bitmap("bricks2.jpg");
		Mesh monkeyMesh = new Mesh("smoothMonkey0.obj");
		Mesh terrainMesh = new Mesh("terrain2.obj");

		Transform monkeyTransform = new Transform(new Vector4f(0.0f, 0.0f, 3.0f));
		Transform terrainTransform = new Transform(new Vector4f(0.0f, -1.0f, 0.0f));

		Camera camera = new Camera(new Matrix4f().initPerspective((float) Math.toRadians(70.0f), (float) target.getWidth() / (float) target.getHeight(), 0.1f, 1000.0f));

		float rot = 0.0f;

		long previousTime = System.nanoTime();
		while(true) {
			long currentTime = System.nanoTime();
			float delta = (float) ((currentTime - previousTime)/1000000000.0);
			previousTime = currentTime;

			camera.update(display.getInput(), delta);
			Matrix4f vp = camera.getViewProjection();

			monkeyTransform = monkeyTransform.rotate(new Quaternion(new Vector4f(0, 1, 0), delta));

			target.clear((byte) 0x00);
			target.clearDepthBuffer();

			monkeyMesh.draw(target, vp, monkeyTransform.getTransformation(), texture2);
			terrainMesh.draw(target, vp, terrainTransform.getTransformation(), texture);

			display.swapBuffers();
		}
	}
}
