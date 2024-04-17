package com.breitling.jclib.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class GenericDAO extends JdbcDaoSupport
{
	private DataSource datasource;
	
	public GenericDAO()	{
	}
	
	public GenericDAO(DataSource source) {
		this.setDataSource(datasource);
	}
	
	public JdbcTemplate getTemplate()
	{
		return new JdbcTemplate(datasource);
	}
}
