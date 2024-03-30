package com.breitling.jclib.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="NOTES")
public class Note 
{
	private long id;
	private long positionId;
	private String note;
	
	@Id
	@Column(value="ID")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getPositionId() {
		return positionId;
	}
	
	public void setPositionId(long id) {
		this.positionId = id;
	}
	
	@Column(value="NOTE")
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
