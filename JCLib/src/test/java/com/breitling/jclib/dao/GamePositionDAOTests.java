package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.breitling.jclib.util.Factory;

public class GamePositionDAOTests 
{
	private GamePositionDAO dao;
    
    private static boolean initialized = false;
    
	@BeforeEach
	public void setupForTest() throws SQLException
	{
		dao = (GamePositionDAO) Factory.DAO.createDAO(GamePositionDAOImpl.class, "positions", Factory.DAO.INMEMORY);
		
		if (!initialized)
		{
			Connection conn = ((GenericDAO) dao).getDataSource().getConnection();
			ScriptUtils.executeSqlScript(conn, new PathResource(Paths.get("./src/test/datasets/positions.sql")));
			initialized = true;
		}
	}

    @Test
    public void testFindByGameId_GoodId_List()
    {
    	var list = dao.findByGameId(1L);
    	
    	assertNotNull(list);
    	assertEquals(1, list.size());
    	assertEquals("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", list.get(0).getFen());
    }
    
    @Test
    public void testAddPosition_GoodPosition_OneRow()
    {
    	int rows = dao.addRecord(1,2);
    	
		assertEquals(1, rows);
    }
}
