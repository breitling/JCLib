package com.breitling.jclib.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="GAMEPOSITIONS")
public class GamePosition 
{
	private long id;
	private Game game;
	private Position position;
	
	@Id
	@Column(value="ID")
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
