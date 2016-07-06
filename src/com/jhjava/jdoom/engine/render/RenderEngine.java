package com.jhjava.jdoom.engine.render;

import com.jhjava.jdoom.engine.components.Camera;
import com.jhjava.jdoom.engine.components.Entity;
import com.jhjava.jdoom.engine.components.Light;
import com.jhjava.jdoom.engine.core.Matrix4f;

public class RenderEngine {
	private Display display;
	private RenderContext target;

	private Camera camera;

	private byte clearShade = 0x00;

	public RenderEngine(int width, int height, String title) {
		display = new Display(width, height, title);
		target = display.getFrameBuffer();
		camera = new Camera(new Matrix4f().initPerspective((float) Math.toRadians(70.0f), (float) width / (float) height, 0.1f, 1000.0f));
	}

	public void render(Entity entity, Light light) {
		entity.getMesh().draw(target, camera.getViewProjection(), entity.getTransform().getTransformation(), entity.getTexture(), light);
	}

	public void swapBuffers() {
		display.swapBuffers();
	}

	public void clear(boolean color, boolean depth) {
		if(color) {
			target.clear(clearShade);
		}
		if(depth) {
			target.clearDepthBuffer();
		}
	}

	public void updateCamera(float delta) {
		camera.update(display.getInput(), delta);
	}

	public RenderContext getTarget() {
		return target;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setClearShade(byte clearShade) {
		this.clearShade = clearShade;
	}
}
