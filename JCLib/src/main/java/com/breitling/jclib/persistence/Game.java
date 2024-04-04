package com.breitling.jclib.persistence;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="GAMES")
public class Game 
{
	private long id;
	private long sourceId;
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
	private String result;
	private String eCO;
	private String fEN;
	private int moveCount;
	private String moves;
	
	@Id
	@Column(value="ID")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(value="SOURCE_ID")
	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long source_id) {
		this.sourceId = source_id;
	}

	@Column(value="WHITE")
	public String getWhite() {
		return white;
	}
	
	public void setWhite(String white) {
		this.white = white;
	}
	
	@Column(value="WHITE_ELO")
	public String getWhiteELO() {
		return whiteELO;
	}
	
	public void setWhiteELO(String whiteELO) {
		this.whiteELO = whiteELO;
	}
	
	@Column(value="BLACK")	
	public String getBlack() {
		return black;
	}
	
	public void setBlack(String black) {
		this.black = black;
	}
	
	@Column(value="BLACK_ELO")
	public String getBlackELO() {
		return blackELO;
	}
	
	public void setBlackELO(String blackELO) {
		this.blackELO = blackELO;
	}
	
	@Column(value="EVENT")	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	@Column(value="SITE")	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	@Column(value="EVENT_DATE")	
	public Date getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	@Column(value="TIME_CONTROL")	
	public String getTimeControl() {
		return timeControl;
	}
	
	public void setTimeControl(String timeControl) {
		this.timeControl = timeControl;
	}
	
	@Column(value="ROUND")	
	public Integer getRound() {
		return round;
	}
	
	public void setRound(Integer round) {
		this.round = round;
	}
	
	@Column(value="GAME_DATE")
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(value="RESULT")
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	@Column(value="ECO")	
	public String getECO() {
		return eCO;
	}
	
	public void setECO(String eco) {
		this.eCO = eco;
	}
	
	@Column(value="FEN")	
	public String getFEN() {
		return fEN;
	}
	
	public void setFEN(String fen) {
		this.fEN = fen;
	}
	
	@Column(value="MOVE_COUNT")	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
	
	@Column(value="MOVES")
	public String getMoves() {
		return this.moves;
	}
	
	public void setMoves(String moves) {
		this.moves = moves;
	}
}
