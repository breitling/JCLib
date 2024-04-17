package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.persistence.Position;
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
    private static int RECORDS = 1;
    
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
    	var id = dao.persistPosition(Factory.Persistence.Position.create("rnbkqbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1"));
    	
		assertEquals(RECORDS+1, id.longValue());
		
		RECORDS++;
    }
    
    @Test
    public void testPersistPositions_ListOfPositions_Records()
    {
    	List<Position> positions = new ArrayList<>();
    	
    	positions.add(Factory.Persistence.Position.create("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"));
    	positions.add(Factory.Persistence.Position.create("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2"));
    	positions.add(Factory.Persistence.Position.create("rnbqkbnr/pppp1ppp/8/4p3/2B1P3/8/PPPP1PPP/RNBQK1NR b KQkq - 1 2"));
    	positions.add(Factory.Persistence.Position.create("rnbqkb1r/pppp1ppp/5n2/4p3/2B1P3/8/PPPP1PPP/RNBQK1NR w KQkq - 2 3"));
    	positions.add(Factory.Persistence.Position.create("rnbqkb1r/pppp1ppp/5n2/4p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 3 3"));
    	positions.add(Factory.Persistence.Position.create("r1bqkb1r/pppp1ppp/2n2n2/4p2Q/2B1P3/8/PPPP1PPP/RNB1K1NR w KQkq - 4 4"));
    	positions.add(Factory.Persistence.Position.create("r1bqkb1r/pppp1Qpp/2n2n2/4p3/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 0 4"));
    	
    	var lastid = dao.persistPositions(positions);
    	var p = dao.findById(lastid);
    	
    	assertTrue(p.isPresent());
    	assertEquals(lastid, p.get().getId());
    	assertEquals("r1bqkb1r/pppp1Qpp/2n2n2/4p3/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 0 4", p.get().getFen());
    	
    	var id = lastid - 6;
    	
    	p = dao.findById(id);
    	
    	assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", p.get().getFen());
    	
    	RECORDS += 7;
    }
}
