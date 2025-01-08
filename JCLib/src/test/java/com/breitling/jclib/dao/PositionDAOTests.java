package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PositionDAOTests
{
	@Autowired
	private PositionDAO dao;
    
//  TEST CASES
	
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
    	var opt = dao.findByHash(-281474976645121L);
    	
    	assertTrue(opt.isPresent());
    	
    	var list = opt.get();
    	
    	assertNotNull(list);
    	assertEquals("rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", list.get(0).getFen());
    }
    
    @Test
    public void testFindByHash_BadHash_EmptyList()
    {
    	var opt = dao.findByHash(343531L);
    	
    	assertTrue(opt.isEmpty());
    }
    
    @Test
    public void testSave_Position_True()
    {
    	var r = dao.save(Factory.Model.Position.create("rnbkqbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1"));
    	
		assertTrue(r);
    }
}
