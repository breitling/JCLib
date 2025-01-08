package com.breitling.jclib.dao;

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

import com.breitling.jclib.model.Database;

@Component
public class DatabaseDAOImpl extends CrudNitriteRepository<Database> implements DatabaseDAO 
{
	private static Logger LOG = LoggerFactory.getLogger(DatabaseDAOImpl.class);
	
	public DatabaseDAOImpl(String databaseName)
	{
		super(databaseName);
	}

//  CONTRACT METHODS
	
	@Override
	public Optional<Database> findByName(String name) 
	{
		try (Nitrite db = Nitrite.builder().loadModule(getStoreModule()).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<Database> repo = (ObjectRepository<Database>) db.getRepository(Database.class);
			Cursor<Database> values = repo.find(FluentFilter.where("dS.name").eq(name));
			
			var list = StreamSupport.stream(values.spliterator(), false).collect(Collectors.toList());
			
			return Optional.of(list.get(0));
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return Optional.empty();
	}

	@Override
	public String getNotes(long id) 
	{
		try (Nitrite db = Nitrite.builder().loadModule(getStoreModule()).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<Database> repo = (ObjectRepository<Database>) db.getRepository(Database.class);
			Database database = repo.getById(id);
			
			if (database != null)
				return database.getNotes();
			else
				return "";
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return "";
	}

	@Override
	public void addNotes(long id, String notes) 
	{
		try (Nitrite db = Nitrite.builder().loadModule(getStoreModule()).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<Database> repo = (ObjectRepository<Database>) db.getRepository(Database.class);
			Database database = repo.getById(id);
			
			if (database!= null)
			{
				database.setNotes(notes);
				repo.update(database);
			}
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
	}
}
