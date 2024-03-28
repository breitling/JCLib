package com.breitling.jclib.bean;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JCLDatabase 
{
	public static DataSource createDataSource(String dbname)
	{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName("com.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/" + dbname);
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
	}
}
