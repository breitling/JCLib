package com.breitling.jclib.model;

import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;

@Entity(value = "move")
public class Move extends BaseModel
{
	@Id
	private long	id;
	
	private long	positionId;
	private String	move;
	private int    games;
	private int    averageELO;
	private int    whiteWinPercent;
	private int    blackWinPercent;
	private int    drawPercent;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getPositionId() {
		return this.positionId;
	}
	
	public void setPositionId(long p) {
		this.positionId = p;
	}
	
	public String getMove() {
		return move;
	}
	
	public void setMove(String move) {
		this.move = move;
	}
	
	public int getGames() {
		return games;
	}
	
	public void setGames(int games) {
		this.games = games;
	}
	
	public int getAverageELO() {
		return averageELO;
	}
	
	public void setAverageELO(int averageELO) {
		this.averageELO = averageELO;
	}
	
	public int getWhiteWinPercent() {
		return whiteWinPercent;
	}
	
	public void setWhiteWinPercent(int whiteWinPercent) {
		this.whiteWinPercent = whiteWinPercent;
	}
	
	public int getBlackWinPercent() {
		return blackWinPercent;
	}
	
	public void setBlackWinPercent(int blackWinPercent) {
		this.blackWinPercent = blackWinPercent;
	}
	
	public int getDrawPercent() {
		return drawPercent;
	}
	
	public void setDrawPercent(int drawPercent) {
		this.drawPercent = drawPercent;
	}
}
