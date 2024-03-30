package com.breitling.jclib.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
    @Column(value="ID")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Position getPosition() {
		return this.position;
	}
	
	public void setPosition(Position p) {
		this.position = p;
	}
	
	@Column(value="MOVE")
	public String getMove() {
		return move;
	}
	
	public void setMove(String move) {
		this.move = move;
	}
	
	@Column(value="GAMES")	
	public int getGames() {
		return games;
	}
	
	public void setGames(int games) {
		this.games = games;
	}
	
	@Column(value="AVERAGE_ELO")	
	public int getAverageELO() {
		return averageELO;
	}
	
	public void setAverageELO(int averageELO) {
		this.averageELO = averageELO;
	}
	
	@Column(value="WHITE_WIN_PECENTAGE")	
	public int getWhiteWinPercent() {
		return whiteWinPercent;
	}
	
	public void setWhiteWinPercent(int whiteWinPercent) {
		this.whiteWinPercent = whiteWinPercent;
	}
	
	@Column(value="BLACK_WIN_PERCENTAGE")	
	public int getBlackWinPercent() {
		return blackWinPercent;
	}
	
	public void setBlackWinPercent(int blackWinPercent) {
		this.blackWinPercent = blackWinPercent;
	}
	
	@Column(value="DRAW_PERCENTAGE")	
	public int getDrawPercent() {
		return drawPercent;
	}
	
	public void setDrawPercent(int drawPercent) {
		this.drawPercent = drawPercent;
	}
}
