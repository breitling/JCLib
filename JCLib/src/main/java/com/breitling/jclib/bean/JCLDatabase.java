package com.breitling.jclib.bean;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JCLDatabase 
{
	public static DataSource createDataSource(String dbname)
	{
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(new StringBuilder("jdbc:h2:./").append(dbname).toString());
        dataSource.setUsername("");
        dataSource.setPassword("");

        return dataSource;
	}
}
