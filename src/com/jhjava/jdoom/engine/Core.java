package com.jhjava.jdoom.engine;

public class Core {
	private Renderer renderer;

	private boolean isRunning;

	public Core(Renderer renderer) {
		this.renderer = renderer;
	}

	public void start() {
		if(!isRunning) {
			isRunning = true;
			run();
		}
	}

	public void run() {
		while(isRunning) {
			renderer.render();
		}
	}

	public void stop() {
		if(isRunning) {
			isRunning = false;
		}
	}
}
