package com.jhjava.jdoom;

import com.jhjava.jdoom.engine.components.Entity;
import com.jhjava.jdoom.engine.components.Scene;
import com.jhjava.jdoom.engine.core.Quaternion;
import com.jhjava.jdoom.engine.core.Transform;
import com.jhjava.jdoom.engine.core.Vector2f;
import com.jhjava.jdoom.engine.core.Vector4f;
import com.jhjava.jdoom.engine.render.Bitmap;
import com.jhjava.jdoom.engine.render.Mesh;
import com.jhjava.jdoom.engine.render.Vertex;

import java.util.ArrayList;

public class Level extends Entity {
	private ArrayList<Vertex> vertices = new ArrayList<>();
	private ArrayList<Integer> indices = new ArrayList<>();
	private Player player;

	private Bitmap level;

	public Level(String levelName, String textureName, Player player) {
		super();
		setTexture(new Bitmap(textureName));
		this.player = player;

		level = new Bitmap(levelName).flipY();
		createLevel(level);
		level = level.flipX();
	}

	public Vector4f checkCollision(Vector4f oldPos, Vector4f newPos, float objectWidth, float objectLength) {
		Vector2f collisionVector = new Vector2f(1, 1);
		Vector4f moveVector = newPos.sub(oldPos);

		if(moveVector.length() > 0) {
			Vector2f blockSize = new Vector2f(2, 2);
			Vector2f objectSize = new Vector2f(objectWidth, objectLength);

			Vector2f oldPos2 = new Vector2f(oldPos.getX(), oldPos.getZ());
			Vector2f newPos2 = new Vector2f(newPos.getX(), newPos.getZ());

			for (int x = 0; x < level.getWidth(); x++) {
				for (int y = 0; y < level.getHeight(); y++) {
					if((level.getComponent((x + y * level.getWidth()) * 4 + 1) & 0xff) == 0) {
						collisionVector = collisionVector.mul(rectCollide(oldPos2, newPos2, objectSize, blockSize, blockSize.mul(new Vector2f(x, y))));
					}
				}
			}
		}

		return new Vector4f(collisionVector.getX(), 1, collisionVector.getY(), 1);
	}

	private Vector2f rectCollide(Vector2f oldPos, Vector2f newPos, Vector2f size1, Vector2f size2, Vector2f pos2) {
		Vector2f result = new Vector2f(0,0);

		if(newPos.getX() + size1.getX() < pos2.getX() ||
				newPos.getX() - size1.getX() > pos2.getX() + size2.getX() * size2.getX() ||
				oldPos.getY() + size1.getY() < pos2.getY() ||
				oldPos.getY() - size1.getY() > pos2.getY() + size2.getY() * size2.getY())
			result.setX(1);

		if(oldPos.getX() + size1.getX() < pos2.getX() ||
				oldPos.getX() - size1.getX() > pos2.getX() + size2.getX() * size2.getX() ||
				newPos.getY() + size1.getY() < pos2.getY() ||
				newPos.getY() - size1.getY() > pos2.getY() + size2.getY() * size2.getY())
			result.setY(1);

		return result;
	}

	private void createLevel(Bitmap level) {
		for (int x = 0; x < level.getWidth(); x++) {
			for (int y = 0; y < level.getHeight(); y++) {
				if((level.getComponent((x + y * level.getWidth()) * 4 + 1) & 0xff) == 0) {
					continue;
				}

				if((level.getComponent((x + y * level.getWidth()) * 4 + 1) & 0xff) == 100) {
					player.setPos(new Vector4f(x * 2, player.getPos().getY(), y * 2));
				}

				int texX = (level.getComponent((x + y * level.getWidth()) * 4 + 2) & 0xff) / 16;
				int texY = texX % 4;
				texX /= 4;
				Vector4f texCoords = new Vector4f((1f - (float) texX / 4f) - 1f / 4f, 1f - (float) texY / 4f, 1f - (float) texX / 4f, (1f - (float) texY / 4f) - 1f / 4f);

				addPlane(new Transform(new Vector4f(x * 2, -1, y * 2), new Quaternion(new Vector4f(1, 0, 0), (float) Math.toRadians(90)), new Vector4f(1, 1, 1)), texCoords);
				addPlane(new Transform(new Vector4f(x * 2, 1, y * 2), new Quaternion(new Vector4f(1, 0, 0), (float) Math.toRadians(-90)), new Vector4f(1, 1, 1)), texCoords);

				texX = (level.getComponent((x + y * level.getWidth()) * 4 + 3) & 0xff) / 16;
				texY = texX % 4;
				texX /= 4;
				texCoords = new Vector4f((1f - (float) texX / 4f) - 1f / 4f, 1f - (float) texY / 4f, 1f - (float) texX / 4f, (1f - (float) texY / 4f) - 1f / 4f);

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
