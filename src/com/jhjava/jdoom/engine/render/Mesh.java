package com.jhjava.jdoom.engine.render;

import com.jhjava.jdoom.engine.core.Matrix4f;

import java.util.ArrayList;

public class Mesh {
	private ArrayList<Vertex> vertices;
	private ArrayList<Integer> indices;

	public Mesh(String name) {
		IndexedModel model = new OBJModel(name).toIndexedModel();

		vertices = new ArrayList<>();
		for (int i = 0; i < model.getPositions().size(); i++) {
			vertices.add(new Vertex(model.getPositions().get(i), model.getTexCoords().get(i)));
		}

		indices = model.getIndices();
	}

	public void draw(RenderContext r, Matrix4f transform, Bitmap texture) {
		for (int i = 0; i < indices.size(); i += 3) {
			r.drawTriangle(vertices.get(indices.get(i)).transform(transform),
					vertices.get(indices.get(i + 1)).transform(transform),
					vertices.get(indices.get(i + 2)).transform(transform),
					texture);
		}
	}

	public Vertex getVertex(int i) {
		return vertices.get(i);
	}

	public int getIndex(int i) {
		return indices.get(i);
	}

	public int getNumIndices() {
		return indices.size();
	}
}
