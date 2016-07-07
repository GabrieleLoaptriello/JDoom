package com.jhjava.jdoom.engine.map;

import com.jhjava.jdoom.engine.core.Matrix4f;
import com.jhjava.jdoom.engine.core.Quaternion;
import com.jhjava.jdoom.engine.core.Vector4f;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Mesh;
import com.jhjava.jdoom.engine.render.Vertex;
import java.lang.ArrayIndexOutOfBoundsException;

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
				createTile(x, y, map);
			}
		}
	}

	private void createTile(int x, int y, Bitmap map) {
		boolean center = (map.getComponent((x + y * map.getWidth()) * 4 + 1) & 0xff) == 255;

		if(center) {
			boolean top = false;
			try {
				top = (map.getComponent((x + (y + 1) * map.getWidth()) * 4 + 1) & 0xff) == 255;
			} catch (ArrayIndexOutOfBoundsException e) {
				top = false;
			}
			boolean right = false;
			try {
				right = (map.getComponent(((x + 1) + y * map.getWidth()) * 4 + 1) & 0xff) == 255;
			} catch (ArrayIndexOutOfBoundsException e) {
				right = false;
			}
			boolean bottom = false;
			try {
				bottom = (map.getComponent((x + (y - 1) * map.getWidth()) * 4 + 1) & 0xff) == 255;
			} catch (ArrayIndexOutOfBoundsException e) {
				bottom = false;
			}
			boolean left = false;
			try {
				left = (map.getComponent(((x - 1) + y * map.getWidth()) * 4 + 1) & 0xff) == 255;
			} catch (ArrayIndexOutOfBoundsException e) {
				left = false;
			}
			boolean topright = false;
			try {
				topright = (map.getComponent(((x + 1) + (y + 1) * map.getWidth()) * 4 + 1) & 0xff) == 255;
			} catch (ArrayIndexOutOfBoundsException e) {
				topright = false;
			}
			boolean bottomright = false;
			try {
				bottomright = (map.getComponent(((x + 1) + (y - 1) * map.getWidth()) * 4 + 1) & 0xff) == 255;
			} catch (ArrayIndexOutOfBoundsException e) {
				bottomright = false;
			}
			boolean bottomleft = false;
			try {
				bottomleft = (map.getComponent(((x - 1) + (y - 1) * map.getWidth()) * 4 + 1) & 0xff) == 255;
			} catch (ArrayIndexOutOfBoundsException e) {
				bottomleft = false;
			}
			boolean topleft = false;
			try {
				topleft = (map.getComponent(((x - 1) + (y + 1) * map.getWidth()) * 4 + 1) & 0xff) == 255;
			} catch (ArrayIndexOutOfBoundsException e) {
				topleft = false;
			}

			for (int i = 0; i < planeVertices.length; i++) {
				Matrix4f translation = new Matrix4f().initTranslation(x * 2, - 1, y * 2);
				Matrix4f rotation = new Quaternion(new Vector4f(1, 0, 0), (float) Math.toRadians(90)).toRotationMatrix();
				Matrix4f m = translation.mul(rotation);

				vertices.add(planeVertices[i].transform(m, rotation));
			}
			for (int i = 0; i < planeIndices.length; i++) {
				indices.add(planeIndices[i] + indicesIndex);
			}
			indicesIndex += 4;
			for (int i = 0; i < planeVertices.length; i++) {
				Matrix4f translation = new Matrix4f().initTranslation(x * 2, + 1, y * 2);
				Matrix4f rotation = new Quaternion(new Vector4f(1, 0, 0), (float) Math.toRadians(-90)).toRotationMatrix();
				Matrix4f m = translation.mul(rotation);

				vertices.add(planeVertices[i].transform(m, rotation));
			}
			for (int i = 0; i < planeIndices.length; i++) {
				indices.add(planeIndices[i] + indicesIndex);
			}
			indicesIndex += 4;

			if(!top) {
				for (int i = 0; i < planeVertices.length; i++) {
					Matrix4f translation = new Matrix4f().initTranslation(x * 2, 0, y * 2 + 1);
					Matrix4f rotation = new Quaternion(new Vector4f(0, 1, 0), (float) Math.toRadians(0)).toRotationMatrix();
					Matrix4f m = translation.mul(rotation);

					vertices.add(planeVertices[i].transform(m, rotation));
				}
				for (int i = 0; i < planeIndices.length; i++) {
					indices.add(planeIndices[i] + indicesIndex);
				}
				indicesIndex += 4;
			}
			if(!right) {
				for (int i = 0; i < planeVertices.length; i++) {
					Matrix4f translation = new Matrix4f().initTranslation(x * 2 + 1, 0, y * 2);
					Matrix4f rotation = new Quaternion(new Vector4f(0, 1, 0), (float) Math.toRadians(90)).toRotationMatrix();
					Matrix4f m = translation.mul(rotation);

					vertices.add(planeVertices[i].transform(m, rotation));
				}
				for (int i = 0; i < planeIndices.length; i++) {
					indices.add(planeIndices[i] + indicesIndex);
				}
				indicesIndex += 4;
			}
			if(!bottom) {
				for (int i = 0; i < planeVertices.length; i++) {
					Matrix4f translation = new Matrix4f().initTranslation(x * 2, 0, y * 2 - 1);
					Matrix4f rotation = new Quaternion(new Vector4f(0, 1, 0), (float) Math.toRadians(180)).toRotationMatrix();
					Matrix4f m = translation.mul(rotation);

					vertices.add(planeVertices[i].transform(m, rotation));
				}
				for (int i = 0; i < planeIndices.length; i++) {
					indices.add(planeIndices[i] + indicesIndex);
				}
				indicesIndex += 4;
			}
			if(!left) {
				for (int i = 0; i < planeVertices.length; i++) {
					Matrix4f translation = new Matrix4f().initTranslation(x * 2 - 1, 0, y * 2);
					Matrix4f rotation = new Quaternion(new Vector4f(0, 1, 0), (float) Math.toRadians(-90)).toRotationMatrix();
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
}
