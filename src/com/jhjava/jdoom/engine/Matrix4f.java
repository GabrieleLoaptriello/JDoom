package com.jhjava.jdoom.engine;

public class Matrix4f {
	private float[][] m;
	
	public Matrix4f() {
		m = new float[4][4];
	}
	
	public Matrix4f initIdentity() {
		m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
		return this;
	}

	public Matrix4f initScreenSpaceTransform(float halfWidth, float halfHeight) {
		m[0][0] = halfWidth; m[0][1] = 0; m[0][2] = 0; m[0][3] = halfWidth;
		m[1][0] = 0; m[1][1] = -halfHeight; m[1][2] = 0; m[1][3] = halfHeight;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
		return this;
	}
	
	public Matrix4f initTranslation(float x, float y, float z) {
		m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = x;
		m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = y;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = z;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
		return this;
	}
	
	public Matrix4f initRotation(float x, float y, float z) {
		Matrix4f mx = new Matrix4f();
		Matrix4f my = new Matrix4f();
		Matrix4f mz = new Matrix4f();
		
		x = (float)Math.toRadians(x);
		y = (float)Math.toRadians(y);
		z = (float)Math.toRadians(z);
		
		mx.m[0][0] = 1;	mx.m[0][1] = 0;					mx.m[0][2] = 0;				   	 mx.m[0][3] = 0;
		mx.m[1][0] = 0;	mx.m[1][1] = (float)Math.cos(x);mx.m[1][2] = -(float)Math.sin(x);mx.m[1][3] = 0;
		mx.m[2][0] = 0;	mx.m[2][1] = (float)Math.sin(x);mx.m[2][2] = (float)Math.cos(x); mx.m[2][3] = 0;
		mx.m[3][0] = 0;	mx.m[3][1] = 0;					mx.m[3][2] = 0;					 mx.m[3][3] = 1;
		
		my.m[0][0] = (float)Math.cos(y);my.m[0][1] = 0;	my.m[0][2] = -(float)Math.sin(y);my.m[0][3] = 0;
		my.m[1][0] = 0;					my.m[1][1] = 1;	my.m[1][2] = 0;					 my.m[1][3] = 0;
		my.m[2][0] = (float)Math.sin(y);my.m[2][1] = 0;	my.m[2][2] = (float)Math.cos(y); my.m[2][3] = 0;
		my.m[3][0] = 0;					my.m[3][1] = 0;	my.m[3][2] = 0;					 my.m[3][3] = 1;
		
		mz.m[0][0] = (float)Math.cos(z);mz.m[0][1] = -(float)Math.sin(z);mz.m[0][2] = 0;	mz.m[0][3] = 0;
		mz.m[1][0] = (float)Math.sin(z);mz.m[1][1] = (float)Math.cos(z); mz.m[1][2] = 0;	mz.m[1][3] = 0;
		mz.m[2][0] = 0;					mz.m[2][1] = 0;					 mz.m[2][2] = 1;	mz.m[2][3] = 0;
		mz.m[3][0] = 0;					mz.m[3][1] = 0;					 mz.m[3][2] = 0;	mz.m[3][3] = 1;
		
		m = mz.mul(my.mul(mx)).getM();
		return this;
	}
	
	public Matrix4f initScale(float x, float y, float z) {
		m[0][0] = x; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = y; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = z; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
		return this;
	}
	
	public Matrix4f initPerspective(float fov, float aspectRatio, float zNear, float zFar) {
		float tanHalfFov = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;
		
		m[0][0] = 1.0f / (tanHalfFov * aspectRatio); m[0][1] = 0;				 m[0][2] = 0; 						 m[0][3] = 0;
		m[1][0] = 0; 						m[1][1] = 1.0f / tanHalfFov; m[1][2] = 0; 						 m[1][3] = 0;
		m[2][0] = 0; 						m[2][1] = 0;				 m[2][2] = (-zNear - zFar) / zRange; m[2][3] = 2 * zFar * zNear / zRange;
		m[3][0] = 0; 						m[3][1] = 0;				 m[3][2] = 1; 						 m[3][3] = 0;
		return this;
	}

	public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;

		m[0][0] = 2/width; m[0][1] = 0; 	m[0][2] = 0; 	m[0][3] = -(right + left)/width;
		m[1][0] = 0; 	m[1][1] = 2/height; m[1][2] = 0; 	m[1][3] = -(top + bottom)/height;
		m[2][0] = 0; 	m[2][1] = 0; 		m[2][2] = -2/depth; m[2][3] = -(far + near)/depth;
		m[3][0] = 0; 	m[3][1] = 0; 		m[3][2] = 0; 	m[3][3] = 1;
		return this;
	}
	
	public Matrix4f initRotation(Vector4f forward, Vector4f up) {
		Vector4f f = forward.normalized();

		Vector4f r = up.normalized();
		r = r.cross(f);

		Vector4f u = f.cross(r);

		return initRotation(f, u, r);
	}

	public Matrix4f initRotation(Vector4f forward, Vector4f up, Vector4f right) {
		Vector4f f = forward;
		Vector4f r = right;
		Vector4f u = up;

		m[0][0] = r.getX(); m[0][1] = r.getY(); m[0][2] = r.getZ(); m[0][3] = 0;
		m[1][0] = u.getX();	m[1][1] = u.getY(); m[1][2] = u.getZ(); m[1][3] = 0;
		m[2][0] = f.getX(); m[2][1] = f.getY(); m[2][2] = f.getZ(); m[2][3] = 0;
		m[3][0] = 0; 		m[3][1] = 0; 		m[3][2] = 0; 		m[3][3] = 1;
		return this;
	}

	public Vector4f transform(Vector4f v) {
		return new Vector4f(m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3] * v.getW(),
							m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3] * v.getW(),
							m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3] * v.getW(),
							m[3][0] * v.getX() + m[3][1] * v.getY() + m[3][2] * v.getZ() + m[3][3] * v.getW());
	}

	public Matrix4f mul(Matrix4f n) {
		Matrix4f res = new Matrix4f();
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				res.set(i, j, m[i][0] * n.get(0, j) +
							  m[i][1] * n.get(1, j) +
							  m[i][2] * n.get(2, j) +
							  m[i][3] * n.get(3, j));
			}
		}
		
		return res;
	}

	public float[][] getM() {
		float[][] res = new float[4][4];
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				res[i][j] = m[i][j];
		
		return res;
	}
	
	public float get(int x, int y) {
		return m[x][y];
	}

	public void setM(float[][] m) {
		this.m = m;
	}
	
	public void set(int x, int y, float value) {
		m[x][y] = value;
	}
}
