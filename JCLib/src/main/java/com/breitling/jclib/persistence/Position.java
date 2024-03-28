package com.breitling.jclib.persistence;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="POSITIONS")
public class Position 
{
	private long id;
	private long bitBoardHash;
	private String fen;
	private Date created;
	private Set<Move> moves;
	
	@Id
	@Column(value="ID")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(value="BITBOARDHASH")
	public long getBitBoardHash() {
		return bitBoardHash;
	}
	
	public void setBitBoardHash(long bitBoardHash) {
		this.bitBoardHash = bitBoardHash;
	}
	
	@Column(value="FEN")	
	public String getFen() {
		return fen;
	}
	
	public void setFen(String fen) {
		this.fen = fen;
	}

	@Column(value="CREATED")	
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
