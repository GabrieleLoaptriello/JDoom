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

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public void move(Vector4f dir, float amt) {
		setTransform(getTransform().setPos(getTransform().getPos().add(dir.mul(amt))));
	}

	public void rotate(Vector4f axis, float angle) {
		setTransform(getTransform().rotate(new Quaternion(axis, angle)));
	}
}
