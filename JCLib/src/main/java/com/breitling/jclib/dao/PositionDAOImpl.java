package com.breitling.jclib.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.breitling.jclib.persistence.Position;
import com.breitling.jclib.util.Factory;

public class PositionDAOImpl extends GenericDAO implements PositionDAO
{
	private static Logger LOG = LoggerFactory.getLogger(PositionDAOImpl.class);

	@Override
	public Optional<Position> findById(long id)
	{
		Optional<Position> pos = Optional.empty();
		
		try
		{
			List<Position> list = 
			    getJdbcTemplate().query(new StringBuilder("SELECT id,bitboardhash,fen,created FROM POSITIONS WHERE id = ").append(id).toString(),
			                                Factory.Persistence.Position.getRowMapper());
			if (list.size() > 0)
				pos = Optional.of(list.get(0));
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
				
		return pos;
	}

	@Override
	public List<Position> findByHash(long hash)
	{
		List<Position> list = new ArrayList<>();
		
		try
		{
			list = getJdbcTemplate().query(new StringBuilder().append("SELECT id,bitboardhash,fen,created ")
					.append("FROM POSITIONS WHERE bitboardhash=").append(hash).toString(),
				    Factory.Persistence.Position.getRowMapper());
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
			
		return list;
	}

	@Override
	public Number persistPosition(Position pos)
	{
		SimpleJdbcInsert s = new SimpleJdbcInsert(getDataSource()).withTableName("POSITIONS").usingGeneratedKeyColumns("ID");
		Map<String,Object> params = new HashMap<>();
		params.put("BITBOARDHASH", pos.getBitBoardHash());
		params.put("FEN", pos.getFen());
		params.put("CREATED", Date.valueOf(LocalDate.now()));
		
		return s.executeAndReturnKey(params);
	}
}
