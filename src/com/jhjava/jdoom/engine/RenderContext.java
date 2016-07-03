package com.jhjava.jdoom.engine;

public class RenderContext extends Bitmap {
	private final int[] scanBuffer;

	public RenderContext(int width, int height) {
		super(width, height);
		scanBuffer= new int[height * 2];
	}

	public void drawScanBuffer(int yCoord, int xMin, int xMax) {
		scanBuffer[yCoord * 2] = xMin;
		scanBuffer[yCoord * 2 + 1] = xMax;
	}

	public void fillShape(int yMin, int yMax) {
		for (int j = yMin; j < yMax; j++) {
			int xMin = scanBuffer[j * 2];
			int xMax = scanBuffer[j * 2 + 1];

			for (int i = xMin; i < xMax; i++) {
				drawPixel(i, j, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff);
			}
		}
	}

	public void scanConvertTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, int handedness) {
		scanConvertLine(minYVert, maxYVert, handedness);
		scanConvertLine(minYVert, midYVert, 1 - handedness);
		scanConvertLine(midYVert, maxYVert, 1 - handedness);
	}

	private void scanConvertLine(Vertex minYVert, Vertex maxYVert, int whichSide) {
		int yStart = (int) minYVert.getY();
		int yEnd = (int) maxYVert.getY();
		int xStart = (int) minYVert.getX();
		int xEnd = (int) maxYVert.getX();

		int yDist = yEnd - yStart;
		int xDist = xEnd - xStart;

		if(yDist <= 0) {
			return;
		}

		float xStep = (float) xDist / (float) yDist;
		float curX = (float) xStart;

		for(int j = yStart; j < yEnd; j++) {
			scanBuffer[j * 2 + whichSide] = (int) curX;
			curX += xStep;
		}
	}
}
