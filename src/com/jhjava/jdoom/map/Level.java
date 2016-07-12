package com.jhjava.jdoom.map;

import com.jhjava.jdoom.engine.components.Entity;
import com.jhjava.jdoom.engine.core.Quaternion;
import com.jhjava.jdoom.engine.core.Transform;
import com.jhjava.jdoom.engine.core.Util;
import com.jhjava.jdoom.engine.core.Vector4f;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Mesh;
import com.jhjava.jdoom.engine.render.Vertex;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Level extends Entity {
	private ArrayList<Vertex> vertices = new ArrayList<>();
	private ArrayList<Integer> indices = new ArrayList<>();

	public Level(String levelName, String textureName) {
		super();
		setTexture(new Bitmap(textureName));

		Bitmap level = new Bitmap(levelName).flipY();
		createMesh(level);
	}

	private void createMesh(Bitmap level) {
		for (int x = 0; x < level.getWidth(); x++) {
			for (int y = 0; y < level.getHeight(); y++) {
				if((level.getComponent((x + y * level.getWidth()) * 4 + 1) & 0xff) == 0) {
					continue;
				}

				int texX = (level.getComponent((x + y * level.getWidth()) * 4 + 2) & 0xff) / 16;
				int texY = texX % 4;
				texX /= 4;
				Vector4f texCoords = new Vector4f((1f - (float) texX / 4f) - 1f / 4f, (1f - (float) texY / 4f) - 1f / 4f, 1f - (float) texX / 4f, 1f - (float) texY / 4f);

				addPlane(new Transform(new Vector4f(x * 2, -1, y * 2), new Quaternion(new Vector4f(1, 0, 0), (float) Math.toRadians(90)), new Vector4f(1, 1, 1)), texCoords);
				addPlane(new Transform(new Vector4f(x * 2, 1, y * 2), new Quaternion(new Vector4f(1, 0, 0), (float) Math.toRadians(-90)), new Vector4f(1, 1, 1)), texCoords);

				if((level.getComponent((x + (y + 1) * level.getWidth()) * 4 + 1) & 0xff) == 0) {
					addPlane(new Transform(new Vector4f(x * 2, 0, y * 2 + 1), new Quaternion(new Vector4f(0, 1, 0), (float) Math.toRadians(0)), new Vector4f(1, 1, 1)), texCoords);
				}
				if((level.getComponent((x + (y - 1) * level.getWidth()) * 4 + 1) & 0xff) == 0) {
					addPlane(new Transform(new Vector4f(x * 2, 0, y * 2 - 1), new Quaternion(new Vector4f(0, 1, 0), (float) Math.toRadians(180)), new Vector4f(1, 1, 1)), texCoords);
				}
				if((level.getComponent(((x + 1) + y * level.getWidth()) * 4 + 1) & 0xff) == 0) {
					addPlane(new Transform(new Vector4f(x * 2 + 1, 0, y * 2), new Quaternion(new Vector4f(0, 1, 0), (float) Math.toRadians(90)), new Vector4f(1, 1, 1)), texCoords);
				}
				if((level.getComponent(((x - 1) + y * level.getWidth()) * 4 + 1) & 0xff) == 0) {
					addPlane(new Transform(new Vector4f(x * 2 - 1, 0, y * 2), new Quaternion(new Vector4f(0, 1, 0), (float) Math.toRadians(-90)), new Vector4f(1, 1, 1)), texCoords);
				}
			}
		}

		setMesh(new Mesh(vertices, indices));
	}

	private void addPlane(Transform transform, Vector4f texCoords) {
		indices.add(vertices.size());
		indices.add(vertices.size() + 1);
		indices.add(vertices.size() + 2);
		indices.add(vertices.size());
		indices.add(vertices.size() + 2);
		indices.add(vertices.size() + 3);

		vertices.add(new Vertex(new Vector4f(-1, 1, 0), new Vector4f(texCoords.getX(), texCoords.getW(), 0), new Vector4f(0, 0, -1))
				.transform(transform.getTransformation(), new Transform(new Vector4f(0, 0, 0), transform.getRot(), new Vector4f(1, 1, 1)).getTransformation()));
		vertices.add(new Vertex(new Vector4f(1, 1, 0), new Vector4f(texCoords.getZ(), texCoords.getW(), 0), new Vector4f(0, 0, -1))
				.transform(transform.getTransformation(), new Transform(new Vector4f(0, 0, 0), transform.getRot(), new Vector4f(1, 1, 1)).getTransformation()));
		vertices.add(new Vertex(new Vector4f(1, -1, 0), new Vector4f(texCoords.getZ(), texCoords.getY(), 0), new Vector4f(0, 0, -1))
				.transform(transform.getTransformation(), new Transform(new Vector4f(0, 0, 0), transform.getRot(), new Vector4f(1, 1, 1)).getTransformation()));
		vertices.add(new Vertex(new Vector4f(-1, -1, 0), new Vector4f(texCoords.getX(), texCoords.getY(), 0), new Vector4f(0, 0, -1))
				.transform(transform.getTransformation(), new Transform(new Vector4f(0, 0, 0), transform.getRot(), new Vector4f(1, 1, 1)).getTransformation()));
	}
}

/*
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
 */
