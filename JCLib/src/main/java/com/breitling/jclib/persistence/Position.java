package com.breitling.jclib.persistence;

import java.sql.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="POSITIONS")
public class Position 
{
	private long id;
	private long bitBoardHash;
	private String fen;
	private Date created;
	private Set<Move> moves;
	
	@Id
	@Column(name="ID", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="BITBOARDHASH", nullable=false)
	public long getBitBoardHash() {
		return bitBoardHash;
	}
	
	public void setBitBoardHash(long bitBoardHash) {
		this.bitBoardHash = bitBoardHash;
	}
	
	@Column(name="FEN", nullable=false)	
	public String getFen() {
		return fen;
	}
	
	public void setFen(String fen) {
		this.fen = fen;
	}

	@Column(name="CREATED", nullable=false)	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinTable(name="POSITIONMOVES", joinColumns=@JoinColumn(name="ID"), inverseJoinColumns=@JoinColumn(name="POS_ID"))
	public Set<Move> getMoves() {
		return this.moves;
	}
	
	public void setMoves(Set<Move> moves) {
		this.moves = moves;
	}
}
