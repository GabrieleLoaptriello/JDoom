package com.jhjava.jdoom.engine.components;

import com.jhjava.jdoom.engine.core.*;

import java.awt.event.KeyEvent;

public class Camera {
	private static final Vector4f Y_AXIS = new Vector4f(0,1,0);

	private Transform transform;
	private Matrix4f projection;

	public Camera(Matrix4f projection) {
		this.projection = projection;
		this.transform = new Transform();
	}

	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		Vector4f cameraPos = getTransform().getTransformedPos().mul(-1);

		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

		return projection.mul(cameraRotation.mul(cameraTranslation));
	}

	public void update(Input input, float delta) {
		// Speed and rotation amounts are hardcoded here.
		// In a more general system, you might want to have them as variables.
		final float sensitivityX = 2.66f * delta;
		final float sensitivityY = 2.0f * delta;
		final float movAmt = 5.0f * delta;

		// Similarly, input keys are hardcoded here.
		// As before, in a more general system, you might want to have these as variables.
		if(input.getKey(KeyEvent.VK_W))
			move(getTransform().getRot().getForward(), movAmt);
		if(input.getKey(KeyEvent.VK_S))
			move(getTransform().getRot().getForward(), -movAmt);
		if(input.getKey(KeyEvent.VK_A))
			move(getTransform().getRot().getLeft(), movAmt);
		if(input.getKey(KeyEvent.VK_D))
			move(getTransform().getRot().getRight(), movAmt);

		if(input.getKey(KeyEvent.VK_RIGHT))
			rotate(Y_AXIS, sensitivityX);
		if(input.getKey(KeyEvent.VK_LEFT))
			rotate(Y_AXIS, -sensitivityX);
		if(input.getKey(KeyEvent.VK_DOWN))
			rotate(getTransform().getRot().getRight(), sensitivityY);
		if(input.getKey(KeyEvent.VK_UP))
			rotate(getTransform().getRot().getRight(), -sensitivityY);
	}

	private void move(Vector4f dir, float amt) {
		transform = getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

	private void rotate(Vector4f axis, float angle) {
		transform = getTransform().rotate(new Quaternion(axis, angle));
	}

	private Transform getTransform() {
		return transform;
	}
}
