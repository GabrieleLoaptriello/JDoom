package com.jhjava.jdoom.engine;

public class RenderContext extends Bitmap {
	public RenderContext(int width, int height) {
		super(width, height);
	}

	public void drawMesh(Mesh mesh, Matrix4f transform, Bitmap texture) {
		for (int i = 0; i < mesh.getNumIndices(); i += 3) {
			fillTriangle(mesh.getVertex(mesh.getIndex(i)).transform(transform),
					mesh.getVertex(mesh.getIndex(i + 1)).transform(transform),
					mesh.getVertex(mesh.getIndex(i + 2)).transform(transform),
					texture);
		}
	}

	public void fillTriangle(Vertex v1, Vertex v2, Vertex v3, Bitmap texture) {
		Matrix4f screenSpaceTransform = new Matrix4f().initScreenSpaceTransform(getWidth() / 2, getHeight() / 2);
		Vertex minYVert = v1.transform(screenSpaceTransform).perspectiveDivide();
		Vertex midYVert = v2.transform(screenSpaceTransform).perspectiveDivide();
		Vertex maxYVert = v3.transform(screenSpaceTransform).perspectiveDivide();

		if(minYVert.triangleAreaTimesTwo(maxYVert, midYVert) >= 0) {
			return;
		}

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

		scanTriangle(minYVert, midYVert, maxYVert, minYVert.triangleAreaTimesTwo(maxYVert, midYVert) >= 0, texture);
	}

	public void scanTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, boolean handedness, Bitmap texture) {
		Gradients gradients = new Gradients(minYVert, midYVert, maxYVert);

		Edge topToBottom = new Edge(gradients, minYVert, maxYVert, 0);
		Edge topToMiddle = new Edge(gradients, minYVert, midYVert, 0);
		Edge middleToBottom = new Edge(gradients, midYVert, maxYVert, 1);

		scanEdges(gradients, topToBottom, topToMiddle, handedness, texture);
		scanEdges(gradients, topToBottom, middleToBottom, handedness, texture);
	}

	private void scanEdges(Gradients gradients, Edge a, Edge b, boolean handedness, Bitmap texture) {
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
			drawScanLine(gradients, left, right, j, texture);
			left.step();
			right.step();
		}
	}

	private void drawScanLine(Gradients gradients, Edge left, Edge right, int j, Bitmap texture) {
		int xMin = (int) Math.ceil(left.getX());
		int xMax = (int) Math.ceil(right.getX());
		float xPrestep = xMin - left.getX();

		float xDist = right.getX() - left.getX();
		float texCoordXXStep = (right.getTexCoordX() - left.getTexCoordX()) / xDist;
		float texCoordYXStep = (right.getTexCoordY() - left.getTexCoordY()) / xDist;
		float oneOverZXStep = (right.getOneOverZ() - left.getOneOverZ()) / xDist;

		float texCoordX = left.getTexCoordX() + texCoordXXStep * xPrestep;
		float texCoordY = left.getTexCoordY() + texCoordYXStep * xPrestep;
		float oneOverZ = left.getOneOverZ() + oneOverZXStep * xPrestep;

		for (int i = xMin; i < xMax; i++) {
			float z = 1.0f / oneOverZ;
			int srcX = (int) ((texCoordX * z) * (texture.getWidth() - 1) + 0.5f);
			int srcY = (int) ((texCoordY * z) * (texture.getHeight() - 1) + 0.5f);

			copyPixel(i, j, srcX, srcY, texture);

			texCoordX += texCoordXXStep;
			texCoordY += texCoordYXStep;
			oneOverZ += oneOverZXStep;
		}
	}
}
