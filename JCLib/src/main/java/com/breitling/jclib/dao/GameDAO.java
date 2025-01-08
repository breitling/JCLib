package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;

import org.dizitart.no2.filters.Filter;

import com.breitling.jclib.model.DataSource;
import com.breitling.jclib.model.Game;

public interface GameDAO 
{
	public Optional<List<Game>> findAll();
	
	public Optional<Game> findById(long id);
	
	public List<Game> find(Filter f);
	
	public int importGames(DataSource d);
	
	public Boolean save(Game g);
	
	public Boolean update(final Game g);
	
	public Boolean delete(final Game g);
}
