package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.common.mapper.JacksonMapperModule;
import org.dizitart.no2.filters.FluentFilter;
import org.dizitart.no2.repository.Cursor;
import org.dizitart.no2.repository.ObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.breitling.jclib.model.Position;

@Component
public class PositionDAOImpl extends CrudNitriteRepository<Position> implements PositionDAO
{
	private static Logger LOG = LoggerFactory.getLogger(PositionDAOImpl.class);
	
	public PositionDAOImpl(String name) {
		super(name);
	}

//  CONTRACT METHODS
	
	@Override
	public Optional<List<Position>> findByHash(long hash) 
	{
		try (Nitrite db = Nitrite.builder().loadModule(getStoreModule()).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<Position> prepo = db.getRepository(Position.class);
			
			Cursor<Position> cursor = prepo.find(FluentFilter.where("bitBoardHash").eq(hash));
			var positions = StreamSupport.stream(cursor.spliterator(), false).collect(Collectors.toList());
			
			if (positions.size() > 0)
				return Optional.of(positions);
			else
				return Optional.empty();
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return Optional.empty();
	}
}
