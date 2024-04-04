package com.breitling.jclib.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.breitling.jclib.persistence.Source;
import com.breitling.jclib.util.Factory;

public class SourceDAOImpl extends GenericDAO implements SourceDAO
{
	private static Logger LOG = LoggerFactory.getLogger(GameDAOImpl.class);

	@Override
	public Optional<Source> findById(long id)
	{
		Optional<Source> source = Optional.empty();
		
		try
		{
			List<Source> list = 
			    getJdbcTemplate().query(new StringBuilder("SELECT id,name,path FROM SOURCES WHERE id = ").append(id).toString(),
			                                Factory.Persistence.Source.getRowMapper());
			if (list.size() > 0)
				source = Optional.of(list.get(0));
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
				
		return source;
	}

	@Override
	public int persistSource(Source source) 
	{
		SimpleJdbcInsert s = new SimpleJdbcInsert(getDataSource()).withTableName("SOURCES").usingGeneratedKeyColumns("ID");
		Map<String,Object> params = new HashMap<>();
		params.put("NAME", source.getName());
		params.put("PATH", source.getPath());
		
		return s.execute(params);
	}
}