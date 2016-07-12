package com.jhjava.jdoom.engine.components;

import com.jhjava.jdoom.engine.core.CoreEngine;
import com.jhjava.jdoom.engine.core.Input;
import com.jhjava.jdoom.engine.core.Matrix4f;
import com.jhjava.jdoom.engine.core.Vector4f;

import java.util.ArrayList;

public abstract class Scene {
	private Light light;
	private Camera camera;
	private ArrayList<Entity> entities;

	protected CoreEngine engine;

	public Scene(CoreEngine engine) {
		light = new Light(new Vector4f(0, 0, 0), 0, 0);
		entities = new ArrayList<>();
		camera = new Camera(new Matrix4f().initPerspective((float) Math.toRadians(70.0f), (float) engine.getRenderEngine().getTarget().getWidth() / (float) engine.getRenderEngine().getTarget().getHeight(), 0.1f, 1000.0f));
		this.engine = engine;
	}

	public void init() {

	}

	public void update(float delta, Input input) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(delta, input);
		}
	}

	protected void setLight(Light light) {
		this.light = light;
	}

	protected void setCamera(Camera camera) {
		this.camera = camera;
	}

	protected void addEntity(Entity entity) {
		entities.add(entity);
	}

	public Light getLight() {
		return light;
	}

	public Camera getCamera() {
		return camera;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
}
