package com.jhjava.jdoom.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OBJModel {
	private class OBJIndex {
		private int vertexIndex;
		private int texCoordIndex;
		private int normalIndex;

		@Override
		public boolean equals(Object obj) {
			OBJIndex index = (OBJIndex)obj;

			return vertexIndex == index.vertexIndex
					&& texCoordIndex == index.texCoordIndex
					&& normalIndex == index.normalIndex;
		}

		@Override
		public int hashCode() {
			final int BASE = 17;
			final int MULTIPLIER = 31;

			int result = BASE;

			result = MULTIPLIER * result + vertexIndex;
			result = MULTIPLIER * result + texCoordIndex;
			result = MULTIPLIER * result + normalIndex;

			return result;
		}

		public int getVertexIndex() {
			return vertexIndex;
		}

		public void setVertexIndex(int vertexIndex) {
			this.vertexIndex = vertexIndex;
		}

		public int getTexCoordIndex() {
			return texCoordIndex;
		}

		public void setTexCoordIndex(int texCoordIndex) {
			this.texCoordIndex = texCoordIndex;
		}

		public int getNormalIndex() {
			return normalIndex;
		}

		public void setNormalIndex(int normalIndex) {
			this.normalIndex = normalIndex;
		}
	}

	private ArrayList<Vector4f> positions;
	private ArrayList<Vector4f> texCoords;
	private ArrayList<Vector4f> normals;
	private ArrayList<OBJIndex> indices;

	private boolean hasTexCoords;
	private boolean hasNormals;

	private static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();

		for(int i = 0; i < data.length; i++) {
			if (!data[i].equals("")) {
				result.add(data[i]);
			}
		}

		String[] res = new String[result.size()];
		result.toArray(res);

		return res;
	}

	public OBJModel(String name) {
		positions = new ArrayList<>();
		texCoords = new ArrayList<>();
		normals = new ArrayList<>();
		indices = new ArrayList<>();
		hasTexCoords = false;
		hasNormals = false;

		BufferedReader meshReader = null;
		try {
			meshReader = new BufferedReader(new InputStreamReader(OBJModel.class.getResourceAsStream("/com/jhjava/jdoom/res/models/" + name)));
		} catch (Exception e) {
			System.err.println("ERROR: Could not find obj file " + name + " in res/models/");
			e.printStackTrace();
			System.exit(1);
		}

		String line;

		try {
			while ((line = meshReader.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens = removeEmptyStrings(tokens);

				if (tokens.length == 0 || tokens[0].equals("#")) {
					continue;
				} else if (tokens[0].equals("v")) {
					positions.add(new Vector4f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3]), 1));
				} else if (tokens[0].equals("vt")) {
					texCoords.add(new Vector4f(Float.valueOf(tokens[1]),
							1.0f - Float.valueOf(tokens[2]), 0, 0));
				} else if (tokens[0].equals("vn")) {
					normals.add(new Vector4f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3]), 0));
				} else if (tokens[0].equals("f")) {
					for (int i = 0; i < tokens.length - 3; i++) {
						indices.add(parseOBJIndex(tokens[1]));
						indices.add(parseOBJIndex(tokens[2 + i]));
						indices.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}

			meshReader.close();
		} catch (IOException e) {
			System.err.println("ERROR: Could not read obj file " + name + " in res/models/");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public IndexedModel toIndexedModel() {
		IndexedModel result = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<>();
		HashMap<Integer, Integer> normalIndexMap = new HashMap<>();
		HashMap<Integer, Integer> indexMap = new HashMap<>();

		for(int i = 0; i < indices.size(); i++) {
			OBJIndex currentIndex = indices.get(i);

			Vector4f currentPosition = positions.get(currentIndex.getVertexIndex());
			Vector4f currentTexCoord;
			Vector4f currentNormal;

			if (hasTexCoords) {
				currentTexCoord = texCoords.get(currentIndex.getTexCoordIndex());
			} else {
				currentTexCoord = new Vector4f(0, 0, 0, 0);
			}

			if (hasNormals) {
				currentNormal = normals.get(currentIndex.getNormalIndex());
			} else {
				currentNormal = new Vector4f(0, 0, 0, 0);
			}

			Integer modelVertexIndex = resultIndexMap.get(currentIndex);

			if(modelVertexIndex == null) {
				modelVertexIndex = result.getPositions().size();
				resultIndexMap.put(currentIndex, modelVertexIndex);

				result.getPositions().add(currentPosition);
				result.getTexCoords().add(currentTexCoord);
				if(hasNormals)
					result.getNormals().add(currentNormal);
			}

			Integer normalModelIndex = normalIndexMap.get(currentIndex.getVertexIndex());

			if(normalModelIndex == null) {
				normalModelIndex = normalModel.getPositions().size();
				normalIndexMap.put(currentIndex.getVertexIndex(), normalModelIndex);

				normalModel.getPositions().add(currentPosition);
				normalModel.getTexCoords().add(currentTexCoord);
				normalModel.getNormals().add(currentNormal);
				normalModel.getTangents().add(new Vector4f(0,0,0,0));
			}

			result.getIndices().add(modelVertexIndex);
			normalModel.getIndices().add(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);
		}

		if(!hasNormals) {
			normalModel.CalcNormals();

			for(int i = 0; i < result.getPositions().size(); i++) {
				result.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
			}
		}

		normalModel.calcTangents();

		for(int i = 0; i < result.getPositions().size(); i++) {
			result.getTangents().add(normalModel.getTangents().get(indexMap.get(i)));
		}

		return result;
	}

	private OBJIndex parseOBJIndex(String token) {
		String[] values = token.split("/");

		OBJIndex result = new OBJIndex();
		result.setVertexIndex(Integer.parseInt(values[0]) - 1);

		if(values.length > 1) {
			if(!values[1].isEmpty()) {
				hasTexCoords = true;
				result.setTexCoordIndex(Integer.parseInt(values[1]) - 1);
			}

			if(values.length > 2) {
				hasNormals = true;
				result.setNormalIndex(Integer.parseInt(values[2]) - 1);
			}
		}

		return result;
	}
}
