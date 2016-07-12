package com.jhjava.jdoom.engine.components;

import com.jhjava.jdoom.engine.core.Input;
import com.jhjava.jdoom.engine.core.Transform;
import com.jhjava.jdoom.engine.core.Vector4f;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Mesh;

public class Entity {
	private Mesh mesh;
	private Bitmap texture;
	private Transform transform;

	public Entity(Mesh mesh, Bitmap texture, Transform transform) {
		this.mesh = mesh;
		this.texture = texture;
		this.transform = transform;
	}

	public Entity() {
		this(new Mesh(), new Bitmap(2, 2), new Transform());
	}

	public void update(float delta, Input input) {

	}

	public Mesh getMesh() {
		return mesh;
	}

	public Bitmap getTexture() {
		return texture;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	protected void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	protected void setTexture(Bitmap texture) {
		this.texture = texture;
	}
}
