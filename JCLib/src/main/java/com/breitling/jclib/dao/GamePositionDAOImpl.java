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

import com.breitling.jclib.model.GamePosition;

@Component
public class GamePositionDAOImpl extends CrudNitriteRepository<GamePosition> implements GamePositionDAO
{
	private static Logger LOG = LoggerFactory.getLogger(GamePositionDAOImpl.class);
	
	public GamePositionDAOImpl(String name) {
		super(name);
	}

//  CONTRACT METHODS
	
	@Override
	public Optional<List<GamePosition>> findByGameId(long gid)
	{
		try (Nitrite db = Nitrite.builder().loadModule(getStoreModule()).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<GamePosition> repo = (ObjectRepository<GamePosition>) db.getRepository(GamePosition.class);
			Cursor<GamePosition> cursor = repo.find(FluentFilter.where("gameId").eq(gid));
			var list = StreamSupport.stream(cursor.spliterator(), false).collect(Collectors.toList());
			
			if (list.size() > 0)
				return Optional.of(list);
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
