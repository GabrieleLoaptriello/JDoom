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

	public CoreEngine(int width, int height, String title) {
		renderEngine = new RenderEngine(width, height, title);
	}

	public void start() {
		if(!running) {
			if(scene == null) {
				System.err.println("ERROR: No scene in CoreEngine");
				System.exit(1);
			}
			running = true;
			scene.init();
			run();
		}
	}

	private void run() {
		long previousTime = System.nanoTime();
		while(running) {
			long currentTime = System.nanoTime();
			float delta = (float) ((currentTime - previousTime)/1000000000.0);
			previousTime = currentTime;

			scene.update(delta, renderEngine.getDisplay().getInput());

			renderEngine.clear(true, true);

			for (int i = 0; i < scene.getEntities().size(); i++) {
				renderEngine.render(scene.getEntities().get(i), scene.getLight(), scene.getCamera());
			}

			renderEngine.swapBuffers();
		}
	}

	public void stop() {
		if(running) {
			running = false;
		}
	}

	public RenderEngine getRenderEngine() {
		return renderEngine;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
