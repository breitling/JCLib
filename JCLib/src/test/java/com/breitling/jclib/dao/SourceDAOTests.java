package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts={"/sources.schema","/sources.sql"},executionPhase=ExecutionPhase.BEFORE_TEST_CLASS)
@ActiveProfiles("test")
public class SourceDAOTests 
{
    @Autowired
    private SourceDAO dao;
    
    @Test
    public void testFindById_GoodId_Source()
    {
    	var source = dao.findById(1L);
    	
    	assertNotNull(source);
    	assertEquals("TestPositions", source.get().getName());
    }
    
	@Test
	public void testAddSource_GoodSource_Success() throws SQLException
	{
		int rows = dao.addSource(Factory.Persistence.Source.create("RJF60", "rjf60.pgn"));
		
		assertEquals(1, rows);
	}
}
