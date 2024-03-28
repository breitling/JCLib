package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts={"/games.schema"},executionPhase=ExecutionPhase.BEFORE_TEST_CLASS)
@ActiveProfiles("test")
public class SourceDAOTests 
{
    @Autowired
    private SourceDAO dao;
    
    @Test
    @Sql("/games.sql")
    public void testFindById_GoodId_Source()
    {
    	var source = dao.findById(1L);
    	
    	assertNotNull(source);
    	assertEquals("TestPositions", source.get().getName());
    }
}
