package com.breitling.jclib.model;

import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;

@Entity(value = "note")
public class Note extends BaseModel
{
	@Id
	private long     id;
	
	private long	 positionId;
	private String   note;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getPositionId() {
		return positionId;
	}
	
	public void setPositionId(long position) {
		this.positionId = position;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
