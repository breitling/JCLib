package com.breitling.jclib.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="POSITIONMOVES")
public class Move 
{
	private long id;
	private Position position;
	private String move;
	private int games;
	private int averageELO;
	private int whiteWinPercent;
	private int blackWinPercent;
	private int drawPercent;
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", unique=true, nullable=false)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="POS_ID")
	public Position getPosition() {
		return this.position;
	}
	
	public void setPosition(Position p) {
		this.position = p;
	}
	
	@Column(name="", nullable=false)
	public String getMove() {
		return move;
	}
	
	public void setMove(String move) {
		this.move = move;
	}
	
	@Column(name="GAMES", nullable=false)	
	public int getGames() {
		return games;
	}
	
	public void setGames(int games) {
		this.games = games;
	}
	
	@Column(name="AVERAGE_ELO", nullable=false)	
	public int getAverageELO() {
		return averageELO;
	}
	
	public void setAverageELO(int averageELO) {
		this.averageELO = averageELO;
	}
	
	@Column(name="WHITE_WIN_PECENTAGE", nullable=false)	
	public int getWhiteWinPercent() {
		return whiteWinPercent;
	}
	
	public void setWhiteWinPercent(int whiteWinPercent) {
		this.whiteWinPercent = whiteWinPercent;
	}
	
	@Column(name="BLACK_WIN_PERCENTAGE", nullable=false)	
	public int getBlackWinPercent() {
		return blackWinPercent;
	}
	
	public void setBlackWinPercent(int blackWinPercent) {
		this.blackWinPercent = blackWinPercent;
	}
	
	@Column(name="DRAW_PERCENTAGE", nullable=false)	
	public int getDrawPercent() {
		return drawPercent;
	}
	
	public void setDrawPercent(int drawPercent) {
		this.drawPercent = drawPercent;
	}
}
