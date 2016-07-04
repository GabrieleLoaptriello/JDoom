package com.jhjava.jdoom.engine.render;

public class Gradients {
	private float[] texCoordX;
	private float[] texCoordY;
	private float[] oneOverZ;
	private float[] depth;

	private float texCoordXXStep;
	private float texCoordXYStep;
	private float texCoordYXStep;
	private float texCoordYYStep;
	private float oneOverZXStep;
	private float oneOverZYStep;
	private float depthXStep;
	private float depthYStep;

	public Gradients(Vertex minYVert, Vertex midYVert, Vertex maxYVert) {
		texCoordX = new float[3];
		texCoordY = new float[3];
		oneOverZ = new float[3];
		depth = new float[3];

		oneOverZ[0] = 1.0f / minYVert.getPos().getW();
		oneOverZ[1] = 1.0f / midYVert.getPos().getW();
		oneOverZ[2] = 1.0f / maxYVert.getPos().getW();
		texCoordX[0] = minYVert.getTexCoords().getX() * oneOverZ[0];
		texCoordX[1] = midYVert.getTexCoords().getX() * oneOverZ[1];
		texCoordX[2] = maxYVert.getTexCoords().getX() * oneOverZ[2];
		texCoordY[0] = minYVert.getTexCoords().getY() * oneOverZ[0];
		texCoordY[1] = midYVert.getTexCoords().getY() * oneOverZ[1];
		texCoordY[2] = maxYVert.getTexCoords().getY() * oneOverZ[2];
		depth[0] = minYVert.getPos().getZ();
		depth[1] = midYVert.getPos().getZ();
		depth[2] = maxYVert.getPos().getZ();

		float oneOverDX = 1.0f /
				(((midYVert.getX() - maxYVert.getX()) *
				(minYVert.getY() - maxYVert.getY())) -
				((minYVert.getX() - maxYVert.getX()) *
				(midYVert.getY() - maxYVert.getY())));
		float oneOverDY = -oneOverDX;

		texCoordXXStep = calcXStep(texCoordX, minYVert, midYVert, maxYVert, oneOverDX);
		texCoordXYStep = calcYStep(texCoordX, minYVert, midYVert, maxYVert, oneOverDY);
		texCoordYXStep = calcXStep(texCoordY, minYVert, midYVert, maxYVert, oneOverDX);
		texCoordYYStep = calcYStep(texCoordY, minYVert, midYVert, maxYVert, oneOverDY);
		oneOverZXStep = calcXStep(oneOverZ, minYVert, midYVert, maxYVert, oneOverDX);
		oneOverZYStep = calcYStep(oneOverZ, minYVert, midYVert, maxYVert, oneOverDY);
		depthXStep =calcXStep(depth, minYVert, midYVert, maxYVert, oneOverDX);
		depthYStep = calcYStep(depth, minYVert, midYVert, maxYVert, oneOverDY);
	}

	private float calcXStep(float[] values, Vertex minYVert, Vertex midYVert, Vertex maxYVert, float oneOverDX) {
		return (((values[1] - values[2]) *
				(minYVert.getY() - maxYVert.getY())) -
				((values[0] - values[2]) *
				(midYVert.getY() - maxYVert.getY()))) * oneOverDX;
	}

	private float calcYStep(float[] values, Vertex minYVert, Vertex midYVert, Vertex maxYVert, float oneOverDY) {
		return (((values[1] - values[2]) *
				(minYVert.getX() - maxYVert.getX())) -
				((values[0] - values[2]) *
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

	public float getOneOverZ(int i) {
		return oneOverZ[i];
	}

	public float getOneOverZXStep() {
		return oneOverZXStep;
	}

	public float getOneOverZYStep() {
		return oneOverZYStep;
	}

	public float getDepth(int i) {
		return depth[i];
	}

	public float getDepthXStep() {
		return depthXStep;
	}

	public float getDepthYStep() {
		return depthYStep;
	}
}
