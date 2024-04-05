package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class PositionDAOTests
{
	private PositionDAO dao;
	
	@Autowired
    @SuppressWarnings("unused")
    private DatabaseDAO dbDao;
    
    private static boolean initialized = false;
    
	@BeforeEach
	public void setupForTest() throws SQLException
	{
		dao = (PositionDAO) Factory.DAO.createDAO(PositionDAOImpl.class, "positions", Factory.DAO.INMEMORY);
		
		if (!initialized)
		{
			Connection conn = ((GenericDAO) dao).getDataSource().getConnection();
			ScriptUtils.executeSqlScript(conn, new PathResource(Paths.get("./src/test/datasets/positions.sql")));
			initialized = true;
		}
	}
    
    @Test
    public void testFindById_GoodId_Position()
    {
    	var p = dao.findById(1L);
    	
    	assertTrue(p.isPresent());
    	assertEquals("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", p.get().getFen());
    }
    
    @Test
    public void testFindById_BadId_Empty()
    {
    	var p = dao.findById(112312423L);
    	
    	assertTrue(p.isEmpty());
    }
    
    @Test
    public void testFindByHash_GoodHash_List()
    {
    	var list = dao.findByHash(-281474976645121L);
    	
    	assertNotNull(list);
 //   	assertEquals(1, list.size());
    	assertEquals("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", list.get(0).getFen());
    }
    
    @Test
    public void testFindByHash_BadHash_EmptyList()
    {
    	var list = dao.findByHash(343531L);
    	
    	assertNotNull(list);
    	assertEquals(0, list.size());
    }
    
    @Test
    public void testAddPosition_GoodPosition_OneRow()
    {
    	var id = dao.addPosition(Factory.Persistence.Position.create("rnbkqbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1"));
    	
		assertEquals(2, id.longValue());
    }
}
