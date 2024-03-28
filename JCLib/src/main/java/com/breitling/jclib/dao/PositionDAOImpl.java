package com.breitling.jclib.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDAOImpl extends GenericDAO implements PositionDAO
{
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(PositionDAOImpl.class);
}
