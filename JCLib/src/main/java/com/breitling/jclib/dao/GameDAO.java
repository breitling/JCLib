package com.breitling.jclib.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.Game;

@Repository
public interface GameDAO extends CrudRepository<Game,Long> 
{
	@Query("FROM Game WHERE white = :name OR black = :name")
	public List<Game> findGamesByPlayerName(@Param("name") String name);
	
	@Query("SELECT g FROM Game g, Source s WHERE g.sourceId = s.id AND s.name = :source")
	public List<Game> findGamesBySource(@Param("source") String source);
}
