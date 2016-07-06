package com.jhjava.jdoom.engine.components;

import java.util.ArrayList;

public class Scene {
	private Light light;
	private ArrayList<Entity> entities;

	public Scene(Light light) {
		this.light = light;
		entities = new ArrayList<>();
	}

	public void update() {
		
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public Light getLight() {
		return light;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
}
