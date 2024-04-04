package com.breitling.jclib.util;

public abstract class Fetchable 
{
	private FetchType state;
	
	public Fetchable()
	{
		state = FetchType.UNDEFINED;
	}

//  GETTERS AND SETTERS
	
	public FetchType getState() {
		return state;
	}

	public void setFetchType(FetchType state) {
		this.state = state;
	}
}
