package com.jhjava.jdoom;

import com.jhjava.jdoom.engine.Core;
import com.jhjava.jdoom.engine.Renderer;

public class Main {
	public static void main(String[] args) {
		Renderer renderer = new Renderer("JDoom Test", 600, 400);
		Core core = new Core(renderer);

		core.start();
	}
}
