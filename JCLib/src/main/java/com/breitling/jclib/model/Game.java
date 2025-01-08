package com.breitling.jclib.model;

import java.sql.Date;

import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;

import com.breitling.jclib.chess.Result;

@Entity(value = "game")
public class Game extends BaseModel
{
	@Id
	private long    id;
	
	private long	sourceId;
	private String  white;
	private String  whiteELO;
	private String  black;
	private String  blackELO;
	private String  event;
	private String  site;
	private Date    eventDate;
	private String  timeControl;
	private Integer round;
	private Date    date;
	private Result  result;
	private String  eCO;
	private String  fEN;
	private int     moveCount;
	private String  moves;
	
//  GETTERS AND SETTERS	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSourceId() {
		return sourceId;
	}

	public void setSource(long sourceId) {
		this.sourceId = sourceId;
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
	
	public String getECO() {
		return eCO;
	}
	
	public void setECO(String eco) {
		this.eCO = eco;
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
}
