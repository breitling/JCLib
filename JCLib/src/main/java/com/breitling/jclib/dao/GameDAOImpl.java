package com.breitling.jclib.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.Game;
import com.breitling.jclib.util.Factory;

@Repository
public class GameDAOImpl extends GenericDAO implements GameDAO
{
	private static Logger LOG = LoggerFactory.getLogger(GameDAOImpl.class);
	
	@Override
	public List<Game> findGamesByPlayerName(String name)
	{
		List<Game> games = new ArrayList<>();
		
		try
		{
			games = getJdbcTemplate().query("SELECT id,white,black,result,move_count,moves FROM GAMES WHERE white='" + name + "' OR black='" + name + "'", 
			           Factory.Persistence.Game.getRowMapper());
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return games;
	}

	@Override
	public List<Game> findGamesBySource(String source)
	{
		List<Game> games = new ArrayList<>();
		
		try
		{
			games = getJdbcTemplate().query("SELECT g.id,white,black,result,move_count,moves FROM GAMES g, SOURCES s WHERE g.source_id=s.id AND s.name='" + source + "'",
			           Factory.Persistence.Game.getRowMapper());
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return games;
	}
}
