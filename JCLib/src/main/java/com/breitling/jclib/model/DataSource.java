package com.breitling.jclib.model;

import java.util.Objects;

public class DataSource 
{
	private String name;
	private String path;
	
//  FACTORIES
	
	public static DataSource create(String name, String path)
	{
		DataSource ds = new DataSource();
		
		ds.name = name;
		ds.path = path;
		
		return ds;
	}
	
//  GETTERS AND SETTERS
	
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
	
	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		
		if (o == null || o instanceof DataSource == false)
			return false;
		
		DataSource ds = (DataSource) o;
		
		return (this.name.equals(ds.getName()));
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(this.name);
	}
}
