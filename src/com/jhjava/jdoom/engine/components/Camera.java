package com.jhjava.jdoom.engine.components;

import com.jhjava.jdoom.engine.core.*;

import java.awt.event.KeyEvent;

public class Camera {
	private static final Vector4f Y_AXIS = new Vector4f(0,1,0);

	private Transform transform;
	private Matrix4f projection;

	private float pitch = 0.0f;

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
		final float rotX = input.getMouseXDelta() * 4 * delta;
		final float rotY = input.getMouseYDelta() * 3 * delta;
		final float movAmt = 5.0f * delta;

		if(input.getKey(KeyEvent.VK_W))
			move(new Vector4f(getTransform().getRot().getForward().getX(), 0, getTransform().getRot().getForward().getZ()), movAmt);
		if(input.getKey(KeyEvent.VK_S))
			move(new Vector4f(getTransform().getRot().getForward().getX(), 0, getTransform().getRot().getForward().getZ()), -movAmt);
		if(input.getKey(KeyEvent.VK_A))
			move(new Vector4f(getTransform().getRot().getLeft().getX(), 0, getTransform().getRot().getLeft().getZ()), movAmt);
		if(input.getKey(KeyEvent.VK_D))
			move(new Vector4f(getTransform().getRot().getRight().getX(), 0, getTransform().getRot().getRight().getZ()), movAmt);

		rotate(Y_AXIS, (float) Math.toRadians(rotX));
		rotate(getTransform().getRot().getRight(), (float) Math.toRadians(rotY));
	}

	private void move(Vector4f dir, float amt) {
		transform = getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

	private void rotate(Vector4f axis, float angle) {
		transform = getTransform().rotate(new Quaternion(axis, angle));
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}
}
