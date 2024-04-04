package com.breitling.jclib.model;

import com.breitling.jclib.util.Fetchable;

public class Note extends Fetchable
{
	private long     id;
	private Position position;
	private String   note;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
