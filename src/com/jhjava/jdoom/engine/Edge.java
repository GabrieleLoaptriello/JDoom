package com.jhjava.jdoom.engine;

public class Edge {
	private float x;
	private float xStep;
	private int yStart;
	private int yEnd;

	public Edge(Vertex start, Vertex end) {
		yStart = (int) Math.ceil(start.getY());
		yEnd = (int) Math.ceil(end.getY());

		float yDist = end.getY() - start.getY();
		float xDist = end.getX() - start.getX();

		float yPrestep = yStart - start.getY();
		xStep = xDist / yDist;
		x = start.getX() + yPrestep * xStep;
	}

	public void step() {
		x += xStep;
	}

	public int getYEnd() {
		return yEnd;
	}

	public int getYStart() {
		return yStart;
	}

	public float getX() {
		return x;
	}
}
