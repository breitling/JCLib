package com.breitling.jclib.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.breitling.jclib.persistence.Game;
import com.breitling.jclib.util.Factory;

public class GameDAOImpl extends GenericDAO implements GameDAO
{
	private static Logger LOG = LoggerFactory.getLogger(GameDAOImpl.class);
	
	public GameDAOImpl() {
	}
	
	public GameDAOImpl(DataSource source) {
		super(source);
	}

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

	@Override
	public Number persistGame(Game g) 
	{
		SimpleJdbcInsert s = new SimpleJdbcInsert(getDataSource()).withTableName("GAMES").usingGeneratedKeyColumns("ID");
		Map<String,Object> params = new HashMap<>();
		params.put("SOURCE_ID", g.getSourceId());
		params.put("WHITE", g.getWhite());
		params.put("WHITE_ELO", g.getWhiteELO());
		params.put("BLACK", g.getBlack());
		params.put("BLACK_ELO", g.getBlackELO());
		params.put("EVENT", g.getEvent());
		params.put("SITE", g.getSite());
		params.put("EVENT_DATE", g.getEventDate());
		params.put("TIME_CONTROL", g.getTimeControl());
		params.put("ROUND", g.getRound());
		params.put("GAME_DATE", g.getDate());
		params.put("RESULT", g.getResult());
		params.put("ECO", g.getECO());
		params.put("FEN", g.getFEN());
		params.put("MOVE_COUNT", g.getMoveCount());
		params.put("MOVES", g.getMoves());
		
		return s.executeAndReturnKey(params);
	}
}
