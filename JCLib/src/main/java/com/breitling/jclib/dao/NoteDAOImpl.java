package com.breitling.jclib.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class NoteDAOImpl extends GenericDAO implements NoteDAO
{
	private static Logger LOG = LoggerFactory.getLogger(NoteDAOImpl.class);
}
