package com.jhjava.jdoom.engine;

public class Gradients {
	private float[] texCoordX;
	private float[] texCoordY;

	private float texCoordXXStep;
	private float texCoordXYStep;
	private float texCoordYXStep;
	private float texCoordYYStep;

	public Gradients(Vertex minYVert, Vertex midYVert, Vertex maxYVert) {
		texCoordX = new float[3];
		texCoordY = new float[3];

		texCoordX[0] = minYVert.getTexCoords().getX();
		texCoordX[1] = midYVert.getTexCoords().getX();
		texCoordX[2] = maxYVert.getTexCoords().getX();

		texCoordY[0] = minYVert.getTexCoords().getY();
		texCoordY[1] = midYVert.getTexCoords().getY();
		texCoordY[2] = maxYVert.getTexCoords().getY();

		float oneOverDX = 1.0f /
				(((midYVert.getX() - maxYVert.getX()) *
				(minYVert.getY() - maxYVert.getY())) -
				((minYVert.getX() - maxYVert.getX()) *
				(midYVert.getY() - maxYVert.getY())));
		float oneOverDY = -oneOverDX;

		texCoordXXStep =
				(((texCoordX[1] - texCoordX[2]) *
				(minYVert.getY() - maxYVert.getY())) -
				((texCoordX[0] - texCoordX[2]) *
				(midYVert.getY() - maxYVert.getY()))) * oneOverDX;

		texCoordXYStep =
				(((texCoordX[1] - texCoordX[2]) *
				(minYVert.getX() - maxYVert.getX())) -
				((texCoordX[0] - texCoordX[2]) *
				(midYVert.getX() - maxYVert.getX()))) * oneOverDY;

		texCoordYXStep =
				(((texCoordY[1] - texCoordY[2]) *
				(minYVert.getY() - maxYVert.getY())) -
				((texCoordY[0] - texCoordY[2]) *
				(midYVert.getY() - maxYVert.getY()))) * oneOverDX;

		texCoordYYStep =
				(((texCoordY[1] - texCoordY[2]) *
				(minYVert.getX() - maxYVert.getX())) -
				((texCoordY[0] - texCoordY[2]) *
				(midYVert.getX() - maxYVert.getX()))) * oneOverDY;
	}

	public float getTexCoordX(int i) {
		return texCoordX[i];
	}

	public float getTexCoordY(int i) {
		return texCoordY[i];
	}

	public float getTexCoordXXStep() {
		return texCoordXXStep;
	}

	public float getTexCoordXYStep() {
		return texCoordXYStep;
	}

	public float getTexCoordYXStep() {
		return texCoordYXStep;
	}

	public float getTexCoordYYStep() {
		return texCoordYYStep;
	}
}
