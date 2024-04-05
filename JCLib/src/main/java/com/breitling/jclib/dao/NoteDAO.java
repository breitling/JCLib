package com.breitling.jclib.dao;

import java.util.List;

import com.breitling.jclib.persistence.Note;

public interface NoteDAO 
{
	public List<Note> findByPositionId(long posId);
	
	public Number persistNote(long posId, String note);
}
