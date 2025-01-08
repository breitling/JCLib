package com.breitling.jclib.model;

import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;

@Entity(value="database")
public class Database extends BaseModel
{
	@Id
	private long id;
	
	private DataSource dS;
	private String notes;
	
//  FACTORIES
	
	public static Database create(String name, String path)
	{
		Database d = new Database();
		
		d.setId(0);
		d.setDS(DataSource.create(name, path));
		
		return d;
	}
	
//  GETTERS AND SETTERS	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DataSource getDS() {
		return dS;
	}

	public void setDS(DataSource ds) {
		this.dS = ds;
	}

	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}	
}
