package com.breitling.jclib.model;

import com.breitling.jclib.annotation.Fetch;
import com.breitling.jclib.util.FetchType;

public class GamePosition
{
	private long     id;
	@Fetch(type=FetchType.LAZY)
	private Game     game;
	@Fetch(type=FetchType.LAZY)
	private Position position;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
