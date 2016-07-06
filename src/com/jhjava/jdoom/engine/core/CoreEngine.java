package com.jhjava.jdoom.engine.core;

import com.jhjava.jdoom.engine.components.Camera;
import com.jhjava.jdoom.engine.components.Entity;
import com.jhjava.jdoom.engine.components.Light;
import com.jhjava.jdoom.engine.components.Scene;
import com.jhjava.jdoom.engine.render.*;

public class CoreEngine {
	private RenderEngine renderEngine;

	private Scene scene;

	private boolean running = false;

	public CoreEngine(int width, int height, String title, Scene scene) {
		renderEngine = new RenderEngine(width, height, title);
		this.scene = scene;
	}

	public void start() {
		if(!running) {
			running = true;
			run();
		}
	}

	private void run() {
		long previousTime = System.nanoTime();
		while(running) {
			long currentTime = System.nanoTime();
			float delta = (float) ((currentTime - previousTime)/1000000000.0);
			previousTime = currentTime;

			renderEngine.updateCamera(delta);

			renderEngine.clear(true, true);

			for (int i = 0; i < scene.getEntities().size(); i++) {
				renderEngine.render(scene.getEntities().get(i), scene.getLight());
			}

			renderEngine.swapBuffers();
		}
	}

	public void stop() {
		if(running) {
			running = false;
		}
	}
}
