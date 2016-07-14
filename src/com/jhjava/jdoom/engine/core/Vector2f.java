package com.jhjava.jdoom.engine.core;

public class Vector2f {
	private float x;
	private float y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float length() {
		return (float)Math.sqrt(x * x + y * y);
	}

	public float max() {
		return Math.max(x, y);
	}

	public float dot(Vector2f v) {
		return x * v.getX() + y * v.getY();
	}
	
	public Vector2f normalized() {
		float length = length();
		
		return new Vector2f(x / length, y / length);
	}

	public float cross(Vector2f v) {
		return x * v.getY() - y * v.getX();
	}

	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		
		return new Vector2f((float)(x * cos - y * sin), (float)(x * sin + y * cos));
	}

	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}
	
	public Vector2f add(Vector2f v) {
		return new Vector2f(x + v.getX(), y + v.getY());
	}
	
	public Vector2f add(float v) {
		return new Vector2f(x + v, y + v);
	}
	
	public Vector2f sub(Vector2f v) {
		return new Vector2f(x - v.getX(), y - v.getY());
	}
	
	public Vector2f sub(float v) {
		return new Vector2f(x - v, y - v);
	}
	
	public Vector2f mul(Vector2f v) {
		return new Vector2f(x * v.getX(), y * v.getY());
	}
	
	public Vector2f mul(float v) {
		return new Vector2f(x * v, y * v);
	}
	
	public Vector2f div(Vector2f v) {
		return new Vector2f(x / v.getX(), y / v.getY());
	}
	
	public Vector2f div(float v) {
		return new Vector2f(x / v, y / v);
	}
	
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}
	
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2f set(Vector2f v) {
		this.set(v.getX(), v.getY());
		return this;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean equals(Vector2f v) {
		return x == v.getX() && y == v.getY();
	}
}
