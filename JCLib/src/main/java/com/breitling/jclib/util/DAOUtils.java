package com.breitling.jclib.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOUtils 
{
	private static Logger LOG = LoggerFactory.getLogger(DAOUtils.class);
	
	public static void closeQuietly(ResultSet rs)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (Throwable e)
            {
                LOG.error("Error while closing resultset: " + e.toString());
            }
        }
    }
    
    public static void closeQuietly(PreparedStatement ps)
    {
        if (ps != null)
        {
            try
            {
                ps.close();
            }
            catch (Throwable e)
            {
                LOG.error("Error while closing prepared statement: " + e.toString());
            }
        }
    }
    
    public static void closeQuietly(Statement ps)
    {
        if (ps != null)
        {
            try
            {
                ps.close();
            }
            catch (Throwable e)
            {
                LOG.error("Error while closing statement: " + e.toString());                
            }
        }
    }
	
	public static void closeQuietly(CallableStatement cs)
    {
        if (cs != null)
        {
            try
            {
                cs.close();
            }
            catch (Throwable e)
            {
                LOG.error("Error while closing classable statment: " + e.toString());
            }
        }
    }
}
