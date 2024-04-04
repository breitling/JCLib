package com.breitling.jclib.service;

public interface GameService 
{
	public void saveGameFromMoves(String moves);
	
	public void saveGamesFromSource(String path);
	
	public void saveGamesFromSource(String name, String path);
}
