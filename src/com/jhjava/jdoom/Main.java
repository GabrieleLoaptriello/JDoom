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
		CoreEngine coreEngine = new CoreEngine(1080, 720, "JDoom");
		Scene scene = new JDoom(coreEngine);
		coreEngine.setScene(scene);
		coreEngine.start();
	}
}
