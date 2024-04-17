package com.breitling.jclib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.breitling.jclib.persistence.Position;
import com.breitling.jclib.util.DAOUtils;
import com.breitling.jclib.util.Factory;

public class GamePositionDAOImpl extends GenericDAO implements GamePositionDAO
{
	private static Logger LOG = LoggerFactory.getLogger(GamePositionDAOImpl.class);
	
	public GamePositionDAOImpl() {
	}
	
	public GamePositionDAOImpl(DataSource source) {
		super(source);
	}

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
	public Number persistRecord(Long gameId, Long posId) 
	{
		SimpleJdbcInsert s = new SimpleJdbcInsert(getDataSource()).withTableName("GAMEPOSITIONS").usingGeneratedKeyColumns("ID");
		Map<String,Long> params = new HashMap<>();
		params.put("GAME_ID", gameId);
		params.put("POS_ID", posId);
		
		return s.executeAndReturnKey(params);
	}

	@Override
	public long persistRecords(Long gameId, List<Position> positions) 
	{
		long count = 0;
		PreparedStatement batch = null;
		Connection conn = null;
		
        try
        {
        	conn = getDataSource().getConnection();
        	batch = conn.prepareStatement("INSERT INTO GAMEPOSITIONS (GAME_ID, POS_ID) VALUES(?, ?)");
        	
        	for (Position p : positions)
        	{
        		batch.setLong(1, gameId);
        		batch.setLong(2, p.getId());
        		batch.addBatch();
        	}
        
        	var r = batch.executeLargeBatch();
        	
        	count = r.length;
        }
        catch (Exception e)
        {
        	LOG.error(e.getMessage());
        }
        finally
        {
        	DAOUtils.closeQuietly(batch);
        	DAOUtils.closeQuietly(conn);
        }
        
        return count;
	}
}
