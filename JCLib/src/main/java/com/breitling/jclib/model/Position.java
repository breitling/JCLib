package com.breitling.jclib.model;

import java.sql.Date;

import org.dizitart.no2.index.IndexType;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;

@Entity(value = "position", indices = {@Index (fields = "bitBoardHash", type = IndexType.NON_UNIQUE)})
public class Position extends BaseModel
{
	@Id
	private long id;
	
	private Long bitBoardHash;
	private String fen;
	private Date created;
	
//  GETTERS AND SETTERS
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Long getBitBoardHash() {
		return bitBoardHash;
	}
	
	public void setBitBoardHash(long bitBoardHash) {
		this.bitBoardHash = bitBoardHash;
	}
	
	public String getFen() {
		return fen;
	}
	
	public void setFen(String fen) {
		this.fen = fen;
	}

	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
}
