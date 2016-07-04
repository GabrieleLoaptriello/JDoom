package com.jhjava.jdoom.engine;

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
