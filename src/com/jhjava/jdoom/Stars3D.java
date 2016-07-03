package com.jhjava.jdoom;

import com.jhjava.jdoom.engine.Bitmap;

public class Stars3D {
	private final float spread;
	private final float speed;

	private final float[] starX;
	private final float[] starY;
	private final float[] starZ;

	public Stars3D(int numStars, float spread, float speed) {
		this.spread = spread;
		this.speed = speed;

		starX = new float[numStars];
		starY = new float[numStars];
		starZ = new float[numStars];

		for(int i = 0; i < starX.length; i++) {
			initStar(i);
		}
	}

	private void initStar(int i) {
		starX[i] = ((float)Math.random() * 2 - 1) * spread;
		starY[i] = ((float)Math.random() * 2 - 1) * spread;
		starZ[i] = ((float)Math.random() + 0.00001f) * spread;
	}

	public void updateAndRender(Bitmap target, float delta) {
		final float tanHalfFOV = (float) Math.tan(Math.toRadians(70.0 / 2.0));

		target.clear((byte) 0x00);

		float halfWidth = target.getWidth() / 2.0f;
		float halfHeight = target.getHeight() / 2.0f;

		for(int i = 0; i < starX.length; i++) {
			starZ[i] -= speed * delta;

			if(starZ[i] <= 0) {
				initStar(i);
			}

			int x = (int) ((starX[i] / (starZ[i] * tanHalfFOV)) * halfWidth + halfWidth);
			int y = (int) ((starY[i] / (starZ[i] * tanHalfFOV)) * halfHeight + halfHeight);

			if(x < 0 || x >= target.getWidth() || y < 0 || y >= target.getHeight()) {
				initStar(i);
			} else {
				target.drawPixel(x, y, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff);
			}
		}
	}
}
