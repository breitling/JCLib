package com.breitling.jclib.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.common.mapper.JacksonMapperModule;
import org.dizitart.no2.filters.Filter;
import org.dizitart.no2.filters.FluentFilter;
import org.dizitart.no2.mvstore.MVStoreModule;
import org.dizitart.no2.repository.Cursor;
import org.dizitart.no2.repository.ObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.GenericTypeResolver;

import com.breitling.jclib.model.BaseModel;
import com.breitling.jclib.util.Factory;

import jakarta.annotation.PostConstruct;

public abstract class CrudNitriteRepository<T extends BaseModel>
{
	private static Logger LOG = LoggerFactory.getLogger(CrudNitriteRepository.class);
	
	@Value("${databases.home}")
	private String home;
	private String path;
	private String name;
	private MVStoreModule storeModule;
	private Class<T> klass;
	
	public CrudNitriteRepository(String name)
	{
		this.name = name;
	}
	
	@PostConstruct
	@SuppressWarnings("unchecked")
	private void initialize()
	{
		this.path =  new StringBuilder(home).append("/").append(name).toString();
		this.storeModule = MVStoreModule.withConfig().filePath(path).compress(true).build();
		this.klass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), CrudNitriteRepository.class);
	}
	
//  GETTRS AND SETTERS
	
	public MVStoreModule getStoreModule() {
		return storeModule;
	}
	
	public void setStoreModule(String name) {
		this.path =  new StringBuilder(home).append("/").append(name).toString();
		this.storeModule = MVStoreModule.withConfig().filePath(path).compress(true).build();
	}
	
//  CONTRACT METHODS
	
	public Optional<List<T>> findAll()
	{
		try (Nitrite db = Nitrite.builder().loadModule(storeModule).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<T> repo = (ObjectRepository<T>) db.getRepository(klass);
			Cursor<T> values = repo.find();
			
			var list = StreamSupport.stream(values.spliterator(), false).collect(Collectors.toList());
			
			return Optional.of(list);
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return Optional.empty();
	}
	
	public Optional<T> findById(long id)
	{
		try (Nitrite db = Nitrite.builder().loadModule(storeModule).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<T> repo = (ObjectRepository<T>) db.getRepository(klass);
			T object = repo.getById(id);
			
			if (object != null)
				return Optional.of(object);
			else
				return Optional.empty();
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return Optional.empty();
	}
	
	public List<T> find(Filter f)
	{
		List<T> list = new ArrayList<>();
		
		try (Nitrite db = Nitrite.builder().loadModule(storeModule).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<T> repo = (ObjectRepository<T>) db.getRepository(klass);
			Cursor<T> objects = repo.find(f);
			
			for (T o : objects)
				list.add(o);
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Boolean save(T object)
	{
		try (Nitrite db = Nitrite.builder().loadModule(storeModule).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<T> repo = (ObjectRepository<T>) db.getRepository(klass);
			
			if (object.getId() == 0)
				object.setId(Factory.DAO.generateId());
			
			repo.insert(object);
			
			return Boolean.TRUE;
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean update(final T object)
	{
		try (Nitrite db = Nitrite.builder().loadModule(storeModule).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<T> repo = (ObjectRepository<T>) db.getRepository(klass);
			
			var r = repo.update(FluentFilter.where("id").eq(object.getId()), object);
			var list = StreamSupport.stream(r.spliterator(), false).collect(Collectors.toList());
			
			return Long.getLong(list.get(0).getIdValue()) == object.getId();
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean delete(final T object)
	{
		try (Nitrite db = Nitrite.builder().loadModule(storeModule).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
		{
			ObjectRepository<T> repo = (ObjectRepository<T>) db.getRepository(klass);
			
			var r = repo.remove(FluentFilter.where("id").eq(object.getId()));
			var list = StreamSupport.stream(r.spliterator(), false).collect(Collectors.toList());
			
			return Long.getLong(list.get(0).getIdValue()) == object.getId();
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return Boolean.FALSE;
	}
}
