package com.breitling.jclib.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import jakarta.annotation.PostConstruct;

public abstract class GenericDAO extends JdbcDaoSupport
{
	@Autowired
	private DataSource datasource;
	
	@PostConstruct
	public void init()
	{
		this.setDataSource(datasource);
	}
}
