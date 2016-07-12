package com.jhjava.jdoom.engine.core;

import java.util.ArrayList;

public class Util {
	public static ArrayList arrayToArrayList(Object[] array) {
		ArrayList ret = new ArrayList();
		for (int i = 0; i < array.length; i++) {
			ret.add(array[i]);
		}
		return ret;
	}

	public static ArrayList arrayToArrayList(int[] array) {
		ArrayList ret = new ArrayList();
		for (int i = 0; i < array.length; i++) {
			ret.add(array[i]);
		}
		return ret;
	}
}
