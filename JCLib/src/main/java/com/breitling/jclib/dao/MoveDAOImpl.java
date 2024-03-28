package com.breitling.jclib.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MoveDAOImpl extends GenericDAO implements MoveDAO
{
	private static Logger LOG = LoggerFactory.getLogger(MoveDAOImpl.class);
}
