package com.jhjava.jdoom.engine.render;

public class Edge {
	private float x;
	private float xStep;
	private int yStart;
	private int yEnd;

	private float texCoordX;
	private float texCoordXStep;
	private float texCoordY;
	private float texCoordYStep;
	private float oneOverZ;
	private float oneOverZStep;
	private float depth;
	private float depthStep;

	public Edge(Gradients gradients, Vertex start, Vertex end, int minYVertIndex) {
		yStart = (int) Math.ceil(start.getY());
		yEnd = (int) Math.ceil(end.getY());

		float yDist = end.getY() - start.getY();
		float xDist = end.getX() - start.getX();

		float yPrestep = yStart - start.getY();
		xStep = xDist / yDist;
		x = start.getX() + yPrestep * xStep;
		float xPrestep = x - start.getX();

		texCoordX = gradients.getTexCoordX(minYVertIndex) +
				gradients.getTexCoordXXStep() * xPrestep +
				gradients.getTexCoordXYStep() * yPrestep;
		texCoordXStep = gradients.getTexCoordXYStep() + gradients.getTexCoordXXStep() * xStep;

		texCoordY = gradients.getTexCoordY(minYVertIndex) +
				gradients.getTexCoordYXStep() * xPrestep +
				gradients.getTexCoordYYStep() * yPrestep;
		texCoordYStep = gradients.getTexCoordYYStep() + gradients.getTexCoordYXStep() * xStep;

		oneOverZ = gradients.getOneOverZ(minYVertIndex) +
				gradients.getOneOverZXStep() * xPrestep +
				gradients.getOneOverZYStep() * yPrestep;
		oneOverZStep = gradients.getOneOverZYStep() + gradients.getOneOverZXStep() * xStep;

		depth = gradients.getDepth(minYVertIndex) +
				gradients.getDepthXStep() * xPrestep +
				gradients.getDepthYStep() * yPrestep;
		depthStep = gradients.getDepthYStep() + gradients.getDepthXStep() * xStep;
	}

	public void step() {
		x += xStep;
		texCoordX += texCoordXStep;
		texCoordY += texCoordYStep;
		oneOverZ += oneOverZStep;
		depth += depthStep;
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

	public float getTexCoordX() {
		return texCoordX;
	}

	public float getTexCoordY() {
		return texCoordY;
	}

	public float getOneOverZ() {
		return oneOverZ;
	}

	public float getDepth() {
		return depth;
	}
}
