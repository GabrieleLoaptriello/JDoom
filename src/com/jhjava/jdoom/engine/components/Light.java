package com.jhjava.jdoom.engine.components;

import com.jhjava.jdoom.engine.core.Vector4f;

public class Light {
    private Vector4f direction;
    private float intensity;
	private float ambient;

	public Light(Vector4f direction, float intensity, float ambient) {
		this.direction = direction;
		this.intensity = intensity;
		this.ambient = ambient;
	}

    public Light(Vector4f direction, float intensity) {
        this(direction, intensity, 0.0f);
    }

	public Vector4f getDirection() {
		return direction;
	}

	public void setDirection(Vector4f direction) {
		this.direction = direction;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public float getAmbient() {
		return ambient;
	}

	public void setAmbient(float ambient) {
		this.ambient = ambient;
	}
}
