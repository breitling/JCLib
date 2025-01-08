package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;

import org.dizitart.no2.filters.Filter;

import com.breitling.jclib.model.Move;

public interface MoveDAO 
{
	public Optional<List<Move>> findAll();
	
	public Optional<Move> findById(long id);
	
	public List<Move> find(Filter f);
	
	public Boolean save(Move n);
	
	public Boolean update(final Move n);
	
	public Boolean delete(final Move n);
}
