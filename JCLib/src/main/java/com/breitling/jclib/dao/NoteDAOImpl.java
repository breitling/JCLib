package com.breitling.jclib.dao;

import org.springframework.stereotype.Component;

import com.breitling.jclib.model.Note;

@Component
public class NoteDAOImpl extends CrudNitriteRepository<Note> implements NoteDAO
{
	public NoteDAOImpl(String name) {
		super(name);
	}
}
