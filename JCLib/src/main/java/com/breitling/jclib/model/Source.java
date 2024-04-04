package com.breitling.jclib.model;

import com.breitling.jclib.annotation.Fetch;
import com.breitling.jclib.util.FetchType;
import com.breitling.jclib.util.Fetchable;

@Fetch(type=FetchType.LAZY)
public class Source extends Fetchable
{
	private long id;
	private String name;
	private String path;
	
//  GETTERS AND SETTERS
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
