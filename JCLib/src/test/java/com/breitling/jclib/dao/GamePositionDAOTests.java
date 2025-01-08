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

import com.breitling.jclib.model.GamePosition;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class GamePositionDAOTests 
{
	@Autowired
	private GamePositionDAO dao;

	@Test
	public void testFindAll_Good_List()
	{
		var list = dao.findAll();
		
		assertTrue(list.isPresent());
	}
	
    @Test
    public void testFindByGameId_GoodId_List()
    {
    	var opt = dao.findByGameId(1L);
    	
    	assertNotNull(opt);
    	
    	var list = opt.get();
    	
//    	assertEquals(1, list.size());
    	assertEquals(2L, list.get(0).getPositionId());
    }
    
    @Test
    public void testAddPosition_GoodPosition_OneRow()
    {
    	var r = dao.save(GamePosition.create(1L, 2L));
    	
		assertTrue(r);
    }
}
