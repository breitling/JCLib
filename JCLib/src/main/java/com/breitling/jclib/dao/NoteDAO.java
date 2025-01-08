package com.breitling.jclib.dao;

import java.util.List;
import java.util.Optional;

import org.dizitart.no2.filters.Filter;

import com.breitling.jclib.model.Note;

public interface NoteDAO 
{
	public Optional<List<Note>> findAll();
	
	public Optional<Note> findById(long id);
	
	public List<Note> find(Filter f);
	
	public Boolean save(Note n);
	
	public Boolean update(final Note n);
	
	public Boolean delete(final Note n);
}
