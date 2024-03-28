package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.Source;
import com.breitling.jclib.util.Factory;

@Repository
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
			    getJdbcTemplate().query("SELECT id,name,path FROM SOURCES WHERE id = " + id,
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
}