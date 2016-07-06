package com.jhjava.jdoom.engine.map;

import com.jhjava.jdoom.engine.core.Matrix4f;
import com.jhjava.jdoom.engine.core.Quaternion;
import com.jhjava.jdoom.engine.core.Vector4f;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Mesh;
import com.jhjava.jdoom.engine.render.Vertex;

import java.util.ArrayList;

public class MapMesh extends Mesh {
	private static final Vertex[] planeVertices = new Vertex[]{new Vertex(new Vector4f(-1, 1, 0), new Vector4f(0, 1, 0), new Vector4f(0, 0, -1)),
			new Vertex(new Vector4f(1, 1, 0), new Vector4f(1, 1, 0), new Vector4f(0, 0, -1)),
			new Vertex(new Vector4f(1, -1, 0), new Vector4f(1, 0, 0), new Vector4f(0, 0, -1)),
			new Vertex(new Vector4f(-1, -1, 0), new Vector4f(0, 0, 0), new Vector4f(0, 0, -1))};
	private static final int[] planeIndices = new int[]{0, 1, 2, 0, 2, 3};

	private int indicesIndex = 0;

	public MapMesh(String name) {
		super();

		Bitmap map = new Bitmap(name);

		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int mapIndex = (x + y * map.getWidth()) * 4;
				createTile(x, y, mapIndex, map);
			}
		}
	}

	private void createTile(int x, int y, int mapIndex, Bitmap map) {
		if((map.getComponent(mapIndex + 1) & 0xff) == 255) {
			for (int i = 0; i < planeVertices.length; i++) {
				Matrix4f translation = new Matrix4f().initTranslation(x * 2, 0, y * 2);
				Matrix4f rotation = new Quaternion(new Vector4f(1, 0, 0), (float) Math.toRadians(90)).toRotationMatrix();
				Matrix4f m = translation.mul(rotation);

				vertices.add(planeVertices[i].transform(m, rotation));
			}
			for (int i = 0; i < planeIndices.length; i++) {
				indices.add(planeIndices[i] + indicesIndex);
			}
			indicesIndex += 4;
		}
	}
}
