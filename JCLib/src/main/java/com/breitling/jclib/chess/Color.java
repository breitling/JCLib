package com.breitling.jclib.chess;

import java.util.HashMap;
import java.util.Map;

public enum Color {
	WHITE("White"), BLACK("Black");
	
	private String color;
	private static final Map<String,Color> map = new HashMap<>();
	
	static {
		for (Color c : values())
			map.put(c.color, c);
	}
	
	Color(String c)
	{
		this.color = c;
	}
	
	public String getColor()
	{
		return this.color;
	}
	
	public static Color valueOfColor(String c) {
		return map.get(c);
	}
}
