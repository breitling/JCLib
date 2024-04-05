package com.breitling.jclib.dao;

import java.util.List;

import com.breitling.jclib.persistence.Position;

public interface GamePositionDAO
{
	public List<Position> findByGameId(long id);
	
	public Number persistRecord(long gameId, long posId);
}
