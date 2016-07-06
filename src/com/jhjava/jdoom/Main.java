package com.jhjava.jdoom;

import com.jhjava.jdoom.engine.components.Entity;
import com.jhjava.jdoom.engine.components.Light;
import com.jhjava.jdoom.engine.components.Scene;
import com.jhjava.jdoom.engine.core.CoreEngine;
import com.jhjava.jdoom.engine.core.Transform;
import com.jhjava.jdoom.engine.core.Vector4f;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Mesh;

public class Main {
	public static void main(String[] args) {
		Entity monkey = new Entity(new Mesh("smoothMonkey0.obj"), new Bitmap("bricks2.jpg"), new Transform(new Vector4f(0.0f, 0.0f, 3.0f)));
		Entity terrain = new Entity(new Mesh("terrain2.obj"), new Bitmap("bricks.jpg"), new Transform(new Vector4f(0.0f, -1.0f, 0.0f)));

		Light light = new Light(new Vector4f(1, 0, 0), 0.8f, 0.2f);

		Scene scene = new Scene(light);
		scene.addEntity(monkey);
		scene.addEntity(terrain);

		CoreEngine coreEngine = new CoreEngine(800, 600, "JDoom", scene);
		coreEngine.start();
	}
}
