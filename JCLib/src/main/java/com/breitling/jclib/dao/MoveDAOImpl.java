package com.breitling.jclib.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveDAOImpl extends GenericDAO implements MoveDAO
{
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(MoveDAOImpl.class);

	public MoveDAOImpl() {
	}
	
	public MoveDAOImpl(DataSource source) {
		super(source);
	}
}
