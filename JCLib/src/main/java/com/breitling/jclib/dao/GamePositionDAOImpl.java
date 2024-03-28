package com.breitling.jclib.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GamePositionDAOImpl extends GenericDAO implements GamePositionDAO
{
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(GamePositionDAOImpl.class);
}
