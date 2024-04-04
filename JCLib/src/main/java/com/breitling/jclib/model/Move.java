package com.breitling.jclib.model;

import com.breitling.jclib.annotation.Fetch;
import com.breitling.jclib.util.FetchType;
import com.breitling.jclib.util.Fetchable;

public class Move extends Fetchable
{
	private long     id;
	@Fetch(type=FetchType.LAZY)
	private Position position;
	private String   move;
	private int      games;
	private int      averageELO;
	private int      whiteWinPercent;
	private int      blackWinPercent;
	private int      drawPercent;
	
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
