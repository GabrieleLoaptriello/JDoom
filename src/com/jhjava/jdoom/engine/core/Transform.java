package com.jhjava.jdoom.engine.core;

public class Transform {
	private Vector4f pos;
	private Quaternion rot;
	private Vector4f scale;

	public Transform() {
		this(new Vector4f(0,0,0,0));
	}

	public Transform(Vector4f pos) {
		this(pos, new Quaternion(0,0,0,1), new Vector4f(1,1,1,1));
	}

	public Transform(Vector4f pos, Quaternion rot, Vector4f scale) {
		this.pos = pos;
		this.rot = rot;
		this.scale = scale;
	}

	public Transform setPos(Vector4f pos) {
		return new Transform(pos, rot, scale);
	}

	public Transform rotate(Quaternion rotation) {
		return new Transform(pos, rotation.mul(rot).normalized(), scale);
	}

	public Transform lookAt(Vector4f point, Vector4f up) {
		return rotate(getLookAtRotation(point, up));
	}

	public Quaternion getLookAtRotation(Vector4f point, Vector4f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}

	public Vector4f getTransformedPos() {
		return pos;
	}

	public Quaternion getTransformedRot() {
		return rot;
	}

	public Vector4f getPos() {
		return pos;
	}

	public Quaternion getRot() {
		return rot;
	}

	public Vector4f getScale() {
		return scale;
	}
}
