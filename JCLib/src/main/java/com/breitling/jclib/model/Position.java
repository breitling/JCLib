package com.breitling.jclib.model;

import java.sql.Date;
import java.util.Set;

import com.breitling.jclib.annotation.Fetch;
import com.breitling.jclib.util.FetchType;
import com.breitling.jclib.util.Fetchable;

@Fetch(type = FetchType.LAZY)
public class Position extends Fetchable
{
	private long id;
	private long bitBoardHash;
	private String fen;
	private Date created;
	
	private Set<Move> moves;
	
//  GETTERS AND SETTERS
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getBitBoardHash() {
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
	
	public Set<Move> getMoves() {
		return this.moves;
	}
	
	public void setMoves(Set<Move> moves) {
		this.moves = moves;
	}
}
