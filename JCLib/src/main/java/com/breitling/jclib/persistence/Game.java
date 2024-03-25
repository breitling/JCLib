package com.breitling.jclib.persistence;

import java.sql.Date;
import java.util.Set;

import com.breitling.jclib.chess.Result;
import com.breitling.jclib.persistence.converter.ResultConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
	private Result result;
	private String eco;
	private String fEN;
	private int moveCount;
	private String moves;
	
	private Set<Position> positions;
	
	@Id
	@Column(name="ID", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="SOURCE_ID", nullable=false)
	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long source_id) {
		this.sourceId = source_id;
	}

	@Column(name="WHITE", nullable=false)
	public String getWhite() {
		return white;
	}
	
	public void setWhite(String white) {
		this.white = white;
	}
	
	@Column(name="WHITE_ELO", nullable=true)	
	public String getWhiteELO() {
		return whiteELO;
	}
	
	public void setWhiteELO(String whiteELO) {
		this.whiteELO = whiteELO;
	}
	
	@Column(name="BLACK", nullable=false)	
	public String getBlack() {
		return black;
	}
	
	public void setBlack(String black) {
		this.black = black;
	}
	
	@Column(name="BLACK_ELO", nullable=true)
	public String getBlackELO() {
		return blackELO;
	}
	
	public void setBlackELO(String blackELO) {
		this.blackELO = blackELO;
	}
	
	@Column(name="EVENT", nullable=true)	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	@Column(name="SITE", nullable=true)	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	@Column(name="EVENT_DATE", nullable=true)	
	public Date getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	@Column(name="TIME_CONTROL", nullable=true)	
	public String getTimeControl() {
		return timeControl;
	}
	
	public void setTimeControl(String timeControl) {
		this.timeControl = timeControl;
	}
	
	@Column(name="ROUND", nullable=true)	
	public Integer getRound() {
		return round;
	}
	
	public void setRound(Integer round) {
		this.round = round;
	}
	
	@Column(name="GAME_DATE", nullable=false)
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="RESULT", nullable=false)
	@Convert(converter = ResultConverter.class)
	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	@Column(name="ECO", nullable=true)	
	public String getEco() {
		return eco;
	}
	
	public void setEco(String eco) {
		this.eco = eco;
	}
	
	@Column(name="FEN", nullable=true)	
	public String getFEN() {
		return fEN;
	}
	
	public void setFEN(String fen) {
		this.fEN = fen;
	}
	
	@Column(name="MOVE_COUNT", nullable=false)	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}
	
	@Column(name="MOVES", nullable=false, length=4096)
	public String getMoves() {
		return this.moves;
	}
	
	public void setMoves(String moves) {
		this.moves = moves;
	}
	
    @OneToMany(fetch=FetchType.LAZY)
    @JoinTable(name="GAMEPOSITIONS", joinColumns=@JoinColumn(name="GAME_ID"), inverseJoinColumns=@JoinColumn(name="ID"))
	public Set<Position> getPositions() {
		return positions;
	}
	
	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}
}
