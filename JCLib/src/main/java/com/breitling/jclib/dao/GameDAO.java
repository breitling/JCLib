package com.breitling.jclib.dao;

import java.util.List;

import com.breitling.jclib.persistence.Game;

public interface GameDAO
{
	public List<Game> findGamesByPlayerName(String name);
	
	public List<Game> findGamesBySource(String source);
}
