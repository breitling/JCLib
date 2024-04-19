package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class GamePositionDAOTests 
{
	private GamePositionDAO dao;
	
	@Autowired
    @SuppressWarnings("unused")
    private DatabaseDAO dbDao;
    
    private static boolean initialized = false;
    
	@BeforeEach
	public void setupForTest() throws SQLException
	{
		dao = (GamePositionDAO) Factory.DAO.createDAO(GamePositionDAOImpl.class, "gamepositions", Factory.DAO.INMEMORY);
		
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
    	Number id = dao.persistRecord(1L, 2L);
    	
		assertEquals(2, id.longValue());
    }
}
