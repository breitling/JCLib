package com.breitling.jclib.bean;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JCLDatabase 
{
	private static Logger LOG = LoggerFactory.getLogger(JCLDatabase.class);
	
	private static Map<String,DriverManagerDataSource> cache = new HashMap<>();
	
	public static DataSource createDataSource(String dbname)
	{		
        DriverManagerDataSource dataSource = cache.get(dbname);
        
        if (dataSource == null)
        {
        	dataSource = new DriverManagerDataSource();
        
	        dataSource.setDriverClassName("org.h2.Driver");
	        dataSource.setUrl(new StringBuilder("jdbc:h2:./").append(dbname).toString());
	        dataSource.setUsername("sa");
	        dataSource.setPassword("");
	        
	        LOG.info("JCLDatabase: created data source for {} at {}", dbname, dataSource.getUrl());
	        
	        cache.put(dbname, dataSource);
        }

        return dataSource;
	}
	
	public static DataSource createInMemoryDataSource(String dbname)
	{
        DriverManagerDataSource dataSource = cache.get(dbname);
        
        if (dataSource == null)
        {
	        dataSource = new DriverManagerDataSource();
	        
	        dataSource.setDriverClassName("org.h2.Driver");
	        dataSource.setUrl(new StringBuilder("jdbc:h2:mem:").append(dbname).append(";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE").toString());
	        dataSource.setUsername("sa");
	        dataSource.setPassword("");
	        
	        LOG.info("JCLDatabase: created in-memory data source for {} at {}", dbname, dataSource.getUrl());
	        
	        cache.put(dbname, dataSource);
        }
        
        return dataSource;
	}
}
