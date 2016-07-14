package com.jhjava.jdoom;

import com.jhjava.jdoom.engine.components.Camera;
import com.jhjava.jdoom.engine.core.Input;
import com.jhjava.jdoom.engine.core.Quaternion;
import com.jhjava.jdoom.engine.core.Transform;
import com.jhjava.jdoom.engine.core.Vector4f;

import java.awt.event.KeyEvent;

public class Player {
	private static final float SPEED = 4.0f;
	private static final float RUN_SPEED = 7.0f;
	private static final float SENSITIVITY = 5.0f;
	private static final float PLAYER_SIZE = 0.3f;

	private static final Vector4f ZERO_VECTOR = new Vector4f(0, 0, 0, 0);

	private Camera camera;
	private Vector4f moveVector;

	public Player(Vector4f pos, Camera mainCamera) {
		this.camera = mainCamera;
		camera.setTransform(new Transform(pos));
	}

	public void update(float delta, Input input) {
		final float rotX = input.getMouseXDelta() * SENSITIVITY * delta;
		final float rotY = input.getMouseYDelta() * SENSITIVITY * delta;
		final float moveAmt = (input.getKey(KeyEvent.VK_SHIFT) ? RUN_SPEED : SPEED) * delta;

		moveVector = ZERO_VECTOR;

		if (input.getKey(KeyEvent.VK_W)) {
			moveVector = moveVector.add(camera.getTransform().getRot().getForward());
		}
		if (input.getKey(KeyEvent.VK_S)) {
			moveVector = moveVector.sub(camera.getTransform().getRot().getForward());
		}
		if(input.getKey(KeyEvent.VK_A)) {
			moveVector = moveVector.add(camera.getTransform().getRot().getLeft());
		}
		if(input.getKey(KeyEvent.VK_D)) {
			moveVector = moveVector.add(camera.getTransform().getRot().getRight());
		}

		camera.rotate(new Vector4f(0, 1, 0), (float) Math.toRadians(rotX));
		camera.rotate(camera.getTransform().getRot().getRight(), (float) Math.toRadians(rotY));
//		if(camera.getTransform().getRot().getForward().getY() > 0.5f) {
//			camera.rotate(camera.getTransform().getRot().getRight(), (float) Math.toRadians(10 * delta));
//		} else if(camera.getTransform().getRot().getForward().getY() < -0.5f) {
//			camera.rotate(camera.getTransform().getRot().getRight(), (float) Math.toRadians(-10 * delta));
//		} else {
//			camera.rotate(camera.getTransform().getRot().getRight(), (float) Math.toRadians(rotY));
//		}

		moveVector.setY(0);
		if(moveVector.length() > 0) {
			moveVector = moveVector.normalized();
		}

		Vector4f oldPos = camera.getTransform().getPos();
		Vector4f newPos = oldPos.add(moveVector.mul(moveAmt));

		Vector4f collisionVector = JDoom.level.checkCollision(oldPos, newPos, PLAYER_SIZE, PLAYER_SIZE);
//		moveVector = moveVector.mul(collisionVector);

		camera.move(moveVector, moveAmt);
	}

	public void setPos(Vector4f pos) {
		camera.setTransform(new Transform(pos));
	}

	public Vector4f getPos() {
		return camera.getTransform().getPos();
	}
}
