package com.jhjava.jdoom.engine.render;

import com.jhjava.jdoom.engine.core.Matrix4f;
import com.jhjava.jdoom.engine.core.Vector4f;

public class Vertex {
	private Vector4f pos;
	private Vector4f texCoords;

	public Vertex(Vector4f pos, Vector4f texCoords) {
		this.pos = pos;
		this.texCoords = texCoords;
	}

	public Vertex transform(Matrix4f transform) {
		return new Vertex(transform.transform(pos), texCoords);
	}

	public Vertex perspectiveDivide() {
		return new Vertex(new Vector4f(pos.getX() / pos.getW(), pos.getY() / pos.getW(), pos.getZ() / pos.getW(), pos.getW()), texCoords);
	}

	public float triangleAreaTimesTwo(Vertex b, Vertex c) {
		float x1 = b.getX() - pos.getX();
		float y1 = b.getY() - pos.getY();
		float x2 = c.getX() - pos.getX();
		float y2 = c.getY() - pos.getY();

		return (x1 * y2 - x2 * y1);
	}

	public Vertex lerp(Vertex other, float lerpAmt) {
		return new Vertex(pos.lerp(other.getPos(), lerpAmt),
				texCoords.lerp(other.getTexCoords(), lerpAmt));
	}

	public boolean isInsideViewFrustum() {
		return Math.abs(pos.getX()) <= Math.abs(pos.getW()) &&
				Math.abs(pos.getY()) <= Math.abs(pos.getW()) &&
				Math.abs(pos.getZ()) <= Math.abs(pos.getW());
	}

	public float get(int i) {
		switch (i) {
			case 0:
				return pos.getX();
			case 1:
				return pos.getY();
			case 2:
				return pos.getZ();
			case 3:
				return pos.getW();
			default:
				System.err.println("WARNING: Index for method get in Vertex is out of bounds");
				return 0;
		}
	}

	public float getX() {
		return pos.getX();
	}

	public float getY() {
		return pos.getY();
	}

	public Vector4f getPos() {
		return pos;
	}

	public Vector4f getTexCoords() {
		return texCoords;
	}
}
