package com.nec.jp.tims.server;

import java.util.HashMap;
import java.util.Map;

public class FileMapping {

	private static Map<Integer, String> mapping = new HashMap<Integer, String>();

	static {
		mapping.put(1, "dummy.dat");
		mapping.put(2, "dummy.json");
		mapping.put(3, "dummy.txt");
	}

	public static Map<Integer, String> getMapper() {
		return mapping;
	}
}
