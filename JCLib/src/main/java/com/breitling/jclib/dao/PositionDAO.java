package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;

import com.breitling.jclib.persistence.Position;

public interface PositionDAO
{
	public Optional<Position> findById(long id);
	
	public List<Position> findByHash(long hash);
	
	public Number persistPosition(Position pos);
}
