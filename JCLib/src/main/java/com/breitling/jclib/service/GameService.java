package com.breitling.jclib.service;

import com.breitling.jclib.model.DataSource;

public interface GameService 
{
	public void saveGameFromMoves(String moves);
	
	public void saveGamesFromPath(String path);
	
	public void saveGamesFromSource(DataSource source);
}
