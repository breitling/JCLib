package com.breitling.jclib.bean;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JCLDatabase 
{
	private static Logger LOG = LoggerFactory.getLogger(JCLDatabase.class);
	
	public static DataSource createDataSource(String dbname)
	{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(new StringBuilder("jdbc:h2:./").append(dbname).toString());
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        
        LOG.info("JCLDatabase: created data source for {} at {}", dbname, dataSource.getUrl());

        return dataSource;
	}
	
	public static DataSource createInMemoryDataSource(String dbname)
	{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(new StringBuilder("jdbc:h2:mem:").append(dbname).toString());
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        
        LOG.info("JCLDatabase: created in-memory data source for {} at {}", dbname, dataSource.getUrl());

        return dataSource;
	}
}
