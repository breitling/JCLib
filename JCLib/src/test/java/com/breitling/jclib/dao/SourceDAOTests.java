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
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class SourceDAOTests 
{
    private SourceDAO dao;
    
	@Autowired
    @SuppressWarnings("unused")
    private DatabaseDAO dbDao;
    
    private static boolean initialized = false;
    
	@BeforeEach
	public void setupForTest() throws ScriptException, SQLException
	{
		dao = (SourceDAO) Factory.DAO.createDAO(SourceDAOImpl.class, "sources", Factory.DAO.INMEMORY);
		
		if (!initialized)
		{
			Connection conn = ((GenericDAO) dao).getDataSource().getConnection();
			ScriptUtils.executeSqlScript(conn, new PathResource(Paths.get("./src/test/datasets/sources.sql")));
			initialized = true;
		}
	}
    
    @Test
    public void testFindById_GoodId_Source()
    {
    	var source = dao.findById(1L);
    	
    	assertNotNull(source);
    	assertEquals("TestPositions", source.get().getName());
    }
    
	@Test
	public void testAddSource_GoodSource_OneRow() throws SQLException
	{
		Number id = dao.persistSource(Factory.Persistence.Source.create("RJF60", "rjf60.pgn"));
		
		assertEquals(2, id.longValue());
	}
}
