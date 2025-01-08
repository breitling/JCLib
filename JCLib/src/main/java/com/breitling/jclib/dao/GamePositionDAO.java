package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;

import org.dizitart.no2.filters.Filter;

import com.breitling.jclib.model.GamePosition;

public interface GamePositionDAO 
{
	public Optional<List<GamePosition>> findAll();
	
	public Optional<GamePosition> findById(long id);
	
	public Optional<List<GamePosition>> findByGameId(long gid);
	
	public List<GamePosition> find(Filter f);
	
	public Boolean save(GamePosition g);
	
	public Boolean update(final GamePosition g);
	
	public Boolean delete(final GamePosition g);
}
