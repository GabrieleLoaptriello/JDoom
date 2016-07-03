package com.jhjava.jdoom.engine;

public class RenderContext extends Bitmap {
	public RenderContext(int width, int height) {
		super(width, height);
	}

	public void fillTriangle(Vertex v1, Vertex v2, Vertex v3) {
		Matrix4f screenSpaceTransform = new Matrix4f().initScreenSpaceTransform(getWidth() / 2, getHeight() / 2);
		Vertex minYVert = v1.transform(screenSpaceTransform).perspectiveDivide();
		Vertex midYVert = v2.transform(screenSpaceTransform).perspectiveDivide();
		Vertex maxYVert = v3.transform(screenSpaceTransform).perspectiveDivide();

		if(maxYVert.getY() < midYVert.getY()) {
			Vertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}
		if(midYVert.getY() < minYVert.getY()) {
			Vertex temp = midYVert;
			midYVert = minYVert;
			minYVert = temp;
		}
		if(maxYVert.getY() < midYVert.getY()) {
			Vertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}

		scanTriangle(minYVert, midYVert, maxYVert, minYVert.triangleAreaTimesTwo(maxYVert, midYVert) >= 0);
	}

	public void scanTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, boolean handedness) {
		Edge topToBottom = new Edge(minYVert, maxYVert);
		Edge topToMiddle = new Edge(minYVert, midYVert);
		Edge middleToBottom = new Edge(midYVert, maxYVert);

		scanEdges(topToBottom, topToMiddle, handedness);
		scanEdges(topToBottom, middleToBottom, handedness);
	}

	private void scanEdges(Edge a, Edge b, boolean handedness) {
		Edge left = a;
		Edge right = b;
		if(handedness) {
			Edge temp = left;
			left = right;
			right = temp;
		}

		int yStart = b.getYStart();
		int yEnd = b.getYEnd();
		for (int j = yStart; j < yEnd; j++) {
			drawScanLine(left, right, j);
			left.step();
			right.step();
		}
	}

	private void drawScanLine(Edge left, Edge right, int j) {
		int xMin = (int) Math.ceil(left.getX());
		int xMax = (int) Math.ceil(right.getX());

		for (int i = xMin; i < xMax; i++) {
			drawPixel(i, j, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff);
		}
	}
}
