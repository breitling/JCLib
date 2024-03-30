package com.breitling.jclib.model;

import java.sql.Date;
import java.util.Set;

import com.breitling.jclib.chess.Result;

public class Game 
{
	private long id;
	private Source source;
	private String white;
	private String whiteELO;
	private String black;
	private String blackELO;
	private String event;
	private String site;
	private Date eventDate;
	private String timeControl;
	private Integer round;
	private Date date;
	private Result result;
	private String eco;
	private String fEN;
	private int moveCount;
	private String moves;
	
	private Set<Position> positions;
	
//  GETTERS AND SETTERS	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Source getSourceId() {
		return source;
	}

	public void setSourceId(Source source) {
		this.source = source;
	}

	public String getWhite() {
		return white;
	}
	
	public void setWhite(String white) {
		this.white = white;
	}
	
	public String getWhiteELO() {
		return whiteELO;
	}
	
	public void setWhiteELO(String whiteELO) {
		this.whiteELO = whiteELO;
	}
	
	public String getBlack() {
		return black;
	}
	
	public void setBlack(String black) {
		this.black = black;
	}
	
	public String getBlackELO() {
		return blackELO;
	}
	
	public void setBlackELO(String blackELO) {
		this.blackELO = blackELO;
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	public String getTimeControl() {
		return timeControl;
	}
	
	public void setTimeControl(String timeControl) {
		this.timeControl = timeControl;
	}
	
	public Integer getRound() {
		return round;
	}
	
	public void setRound(Integer round) {
		this.round = round;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	public String getEco() {
		return eco;
	}
	
	public void setECO(String eco) {
		this.eco = eco;
	}
	
	public String getFEN() {
		return fEN;
	}
	
	public void setFEN(String fen) {
		this.fEN = fen;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
	
	public String getMoves() {
		return this.moves;
	}
	
	public void setMoves(String moves) {
		this.moves = moves;
	}
	
	public Set<Position> getPositions() {
		return positions;
	}
	
	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}
}
