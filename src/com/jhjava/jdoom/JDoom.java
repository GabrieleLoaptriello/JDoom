package com.jhjava.jdoom;

import com.jhjava.jdoom.engine.components.Entity;
import com.jhjava.jdoom.engine.components.Light;
import com.jhjava.jdoom.engine.components.Scene;
import com.jhjava.jdoom.engine.core.*;
import com.jhjava.jdoom.engine.map.Map;
import com.jhjava.jdoom.engine.map.MapMesh;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Mesh;

public class JDoom extends Scene {
	Light light;

	Entity monkey;
	Entity map;

	public JDoom(CoreEngine engine) {
		super(engine);
	}

	@Override
	public void init() {
		super.init();

		monkey = new Entity(new Mesh("smoothMonkey0.obj"), new Bitmap("bricks2.jpg"), new Transform(new Vector4f(20, 0, 22), new Quaternion(new Vector4f(0, 0, 0), 0), new Vector4f(0.5f, 0.5f, 0.5f)));
		addEntity(monkey);

		map = new Map("map.png");
		addEntity(map);

		light = new Light(new Vector4f(1, 0, 1), 0.8f, 0.2f);
		setLight(light);

		getCamera().setTransform(getCamera().getTransform().setPos(new Vector4f(2, 0, 2)));
	}

	@Override
	public void update(float delta, Input input) {
		super.update(delta, input);

		getCamera().update(input, delta);

		monkey.setTransform(monkey.getTransform().rotate(new Quaternion(new Vector4f(0, 1, 0), delta)));
	}
}
