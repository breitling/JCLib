package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;

import org.dizitart.no2.filters.Filter;

import com.breitling.jclib.model.Position;

public interface PositionDAO 
{
	public Optional<List<Position>> findAll();
	
	public Optional<Position> findById(long id);
	
	public Optional<List<Position>> findByHash(long hash);
	
	public List<Position> find(Filter f);
	
	public Boolean save(Position p);
	
	public Boolean update(final Position p);
	
	public Boolean delete(final Position p);
}
