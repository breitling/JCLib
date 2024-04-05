package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;

import com.breitling.jclib.persistence.Game;

public interface GameDAO
{
	public Optional<Game> findById(Long id);
	
	public List<Game> findGamesByPlayerName(String name);
	
	public List<Game> findGamesBySource(String source);
	
	public Number persistGame(Game g);
}
