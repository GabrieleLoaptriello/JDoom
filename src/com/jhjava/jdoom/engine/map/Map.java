package com.jhjava.jdoom.engine.map;

import com.jhjava.jdoom.engine.components.Entity;
import com.jhjava.jdoom.engine.core.Transform;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Mesh;

public class Map extends Entity {
	private MapMesh mesh;

	public Map(String name) {
		super();

		mesh = new MapMesh(name);

		setMesh(mesh);
		setTexture(new Bitmap("bricks.jpg"));
		setTransform(new Transform());
	}
}
