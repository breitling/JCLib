package com.breitling.jclib.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.Position;
import com.breitling.jclib.util.Factory;

@Repository
public class GamePositionDAOImpl extends GenericDAO implements GamePositionDAO
{
	private static Logger LOG = LoggerFactory.getLogger(GamePositionDAOImpl.class);
	
	@Override
	public List<Position> findByGameId(long id) 
	{
		List<Position> list = new ArrayList<>();
		
		try
		{
			list = getJdbcTemplate().query(new StringBuilder().append("SELECT p.id,bitboardhash,fen,created ")
					.append("FROM POSITIONS p, GAMEPOSITIONS gp WHERE p.id = gp.pos_id AND gp.game_id = ").append(id).toString(),
				    Factory.Persistence.Position.getRowMapper());
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
			
		return list;
	}

	@Override
	public int addRecord(long gameId, long posId) 
	{
		SimpleJdbcInsert s = new SimpleJdbcInsert(getDataSource()).withTableName("GAMEPOSITIONS").usingGeneratedKeyColumns("ID");
		Map<String,Object> params = new HashMap<>();
		params.put("GAME_ID", gameId);
		params.put("POS_ID", posId);
		
		return s.execute(params);
	}
}
