package com.jhjava.jdoom.engine;

public class Vertex {
	private Vector4f pos;

	public Vertex(float x, float y, float z) {
		pos = new Vector4f(x, y, z, 1);
	}

	public Vertex(float x, float y) {
		this(x, y, 1);
	}

	public Vertex(Vector4f pos) {
		this.pos = pos;
	}

	public Vertex transform(Matrix4f transform) {
		return new Vertex(transform.transform(pos));
	}

	public Vertex perspectiveDivide() {
		return new Vertex(new Vector4f(pos.getX() / pos.getW(), pos.getY() / pos.getW(), pos.getZ() / pos.getW(), pos.getW()));
	}

	public float triangleAreaTimesTwo(Vertex b, Vertex c) {
		float x1 = b.getX() - pos.getX();
		float y1 = b.getY() - pos.getY();
		float x2 = c.getX() - pos.getX();
		float y2 = c.getY() - pos.getY();

		return (x1 * y2 - x2 * y1);
	}

	public float getX() {
		return pos.getX();
	}

	public float getY() {
		return pos.getY();
	}
}
