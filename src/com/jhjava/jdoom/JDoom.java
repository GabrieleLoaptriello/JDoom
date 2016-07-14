package com.jhjava.jdoom;

import com.jhjava.jdoom.engine.components.Entity;
import com.jhjava.jdoom.engine.components.Light;
import com.jhjava.jdoom.engine.components.Scene;
import com.jhjava.jdoom.engine.core.*;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Mesh;

public class JDoom extends Scene {
	Light light;
	Player player;

	public static Level level;

	public JDoom(CoreEngine engine) {
		super(engine);
	}

	@Override
	public void init() {
		super.init();

		player = new Player(new Vector4f(0, -0.2f, 0), getCamera());

		level = new Level("map2.png", "textures.png", player);
		addEntity(level);

		light = new Light(new Vector4f(1, 0, 1), 0.8f, 0.2f);
		setLight(light);
	}

	@Override
	public void update(float delta, Input input) {
		super.update(delta, input);

		player.update(delta, input);
	}
}
