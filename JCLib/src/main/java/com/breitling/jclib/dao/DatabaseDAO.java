package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;

import org.dizitart.no2.filters.Filter;

import com.breitling.jclib.model.Database;

public interface DatabaseDAO
{
	public Optional<List<Database>> findAll();
	
	public Optional<Database> findById(long id);
	
	public Optional<Database> findByName(String name);
	
	public List<Database> find(Filter f);
	
	public Boolean save(Database db);
	
	public Boolean update(final Database db);
	
	public Boolean delete(final Database db);
	
	public String getNotes(long id);
	
	public void addNotes(long id, String notes);
}
