package com.breitling.jclib.chess;

import java.util.HashMap;
import java.util.Map;

public enum Result 
{
	WHITE_WINS("1-0"), BLACK_WINS("0-1"), DRAW("1/2-1/2"), NORESULT("*");
	
	private String result;
	private static final Map<String,Result> map = new HashMap<>();
	
	static {
		for (Result r : values())
			map.put(r.result, r);
	}
	
	Result(String r) {
		result = r;
	}
	
	public String getResult() {
		return result;
	}
	
	public String getValue() {
		return result;
	}
	
	public static Result valueOfResult(String r) {
		return map.get(r);
	}
}
