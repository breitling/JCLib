package com.breitling.jclib.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.Note;

@Repository
public interface NoteDAO extends CrudRepository<Note,Long>
{

}
