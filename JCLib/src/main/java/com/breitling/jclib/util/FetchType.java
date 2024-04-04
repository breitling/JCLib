package com.breitling.jclib.util;

public enum FetchType 
{
	UNDEFINED(0), LOADED(1), LAZY(2);
	
	private int type;
	
	FetchType(int type)
	{
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
