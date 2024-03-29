package com.breitling.jclib.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.breitling.jclib.persistence.Game;
import com.breitling.jclib.util.Factory;

public class GameDAOImpl extends GenericDAO implements GameDAO
{
	private static Logger LOG = LoggerFactory.getLogger(GameDAOImpl.class);
	

	@Override
	public Optional<Game> findById(Long id)
	{
		Optional<Game> game = Optional.empty();
		
		try
		{
			List<Game> list = getJdbcTemplate().query(new StringBuilder()
			    .append("SELECT id,source_id,white,white_elo,black,black_elo,event,site,event_date,time_control,round,game_date,result,eco,fen,move_count,moves ")
			    .append("FROM GAMES WHERE id=").append(id).toString(), Factory.Persistence.Game.getRowMapper());
			
			if (list.size() > 0)
				game = Optional.of(list.get(0));
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return game;
	}
	
	@Override
	public List<Game> findGamesByPlayerName(String name)
	{
		List<Game> games = new ArrayList<>();
		
		try
		{
			games = getJdbcTemplate().query(new StringBuilder()
    		    .append("SELECT id,source_id,white,white_elo,black,black_elo,event,site,event_date,time_control,round,game_date,result,eco,fen,move_count,moves ")
				.append("FROM GAMES WHERE white='").append(name).append("' OR black='").append(name).append("'").toString(),
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
			games = getJdbcTemplate().query(new StringBuilder()
			    .append("SELECT g.id,source_id,white,white_elo,black,black_elo,event,site,event_date,time_control,round,game_date,result,eco,fen,move_count,moves ")
				.append("FROM GAMES g, SOURCES s WHERE g.source_id=s.id AND s.name='").append(source).append("'").toString(),
			    Factory.Persistence.Game.getRowMapper());
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return games;
	}
}
