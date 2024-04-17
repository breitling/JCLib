package com.breitling.jclib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.breitling.jclib.persistence.Position;
import com.breitling.jclib.util.DAOUtils;
import com.breitling.jclib.util.Factory;

public class PositionDAOImpl extends GenericDAO implements PositionDAO
{
	private static Logger LOG = LoggerFactory.getLogger(PositionDAOImpl.class);

	public PositionDAOImpl() {
	}
	
	public PositionDAOImpl(DataSource source) {
		super(source);
	}

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
		params.put("CREATED", pos.getCreated());
		
		return s.executeAndReturnKey(params);
	}
	
	@Override
	public long persistPositions(List<Position> positions)
	{
		long lastId = 0;
		PreparedStatement batch = null;
        ResultSet keys = null;
        Connection conn = null;
        
        try
        {
        	conn = getDataSource().getConnection();
        	batch = conn.prepareStatement("INSERT INTO POSITIONS (BITBOARDHASH, FEN, CREATED) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        	
        	for (Position p : positions)
        	{
        		batch.setLong(1, p.getBitBoardHash());
        		batch.setString(2, p.getFen());
        		batch.setDate(3, p.getCreated());
        		batch.addBatch();
        	}
        	
        	batch.executeLargeBatch();
        	
        	keys = batch.getGeneratedKeys();
        	
        	for (Position p : positions)
        	{
        		keys.next();
        		lastId = keys.getLong("ID");
        		p.setId(lastId);
        	}
        }
        catch (Exception e)
        {
        	LOG.error(e.getMessage());
        }
        finally
        {
        	DAOUtils.closeQuietly(keys);
        	DAOUtils.closeQuietly(batch);
        	DAOUtils.closeQuietly(conn);
        }
        
        return lastId;
	}

	@Override
	public int count() 
	{
		int count = 0;
		
		try
		{
			count = getJdbcTemplate().query(new StringBuilder("SELECT count(*) AS cnt FROM POSITIONS").toString(),
				        new ResultSetExtractor<Integer>() {
							@Override
							public Integer extractData(ResultSet rs) throws SQLException, DataAccessException 
							{
								if (rs.next())
									return rs.getInt(1);
								else
									return 0;
							}
						});
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return count;
	}
}
