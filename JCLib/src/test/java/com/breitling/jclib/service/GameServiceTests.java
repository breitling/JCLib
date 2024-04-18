package com.breitling.jclib.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.breitling.jclib.dao.GameDAO;
import com.breitling.jclib.dao.GameDAOImpl;
import com.breitling.jclib.dao.GenericDAO;
import com.breitling.jclib.dao.PositionDAO;
import com.breitling.jclib.dao.PositionDAOImpl;
import com.breitling.jclib.dao.SourceDAO;
import com.breitling.jclib.dao.SourceDAOImpl;
import com.breitling.jclib.util.DAOUtils;
import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class GameServiceTests 
{
	@Autowired
	private GameService service;
    
    private static boolean initialized = false;
    
	@BeforeEach
	public void setupForTest() throws SQLException
	{
		var dao = (SourceDAO) Factory.DAO.createDAO(SourceDAOImpl.class, "UNIT_TESTS", Factory.DAO.INMEMORY);
		
		if (!initialized)
		{
			Connection conn = ((GenericDAO) dao).getDataSource().getConnection();
			ScriptUtils.executeSqlScript(conn, new PathResource(Paths.get("./src/test/datasets/games.schema")));
			DAOUtils.closeQuietly(conn);
			initialized = true;
		}
	}
	
//  TEST CASES
	
	@Test
	public void testSaveGamesFromSource_GoodSource_DBObjects()
	{
		service.saveGamesFromSource(Factory.Model.Source.create("UNIT_TESTS", "/Users/bobbr/Desktop/Chess/Games/RJF60.pgn"));
		
		var dao = (GameDAO) Factory.DAO.createDAO(GameDAOImpl.class, "UNIT_TESTS", Factory.DAO.INMEMORY);
		var da0 = (PositionDAO) Factory.DAO.createDAO(PositionDAOImpl.class, "UNIT_TESTS", Factory.DAO.INMEMORY);
		var positions = da0.count();
		var games = dao.findGamesBySource("UNIT_TESTS");
		
		assertEquals(60, games.size());
		assertEquals(4219, positions);
	}
	
	@Test
	public void testSaveGamesFromSource_BigSource_DBObjects()
	{
		service.saveGamesFromSource(Factory.Model.Source.create("UNIT_TESTS", "/Users/bobbr/Desktop/Chess/Games/RetiKIA.pgn"));
		
		var dao = (GameDAO) Factory.DAO.createDAO(GameDAOImpl.class, "UNIT_TESTS", Factory.DAO.INMEMORY);
		var da0 = (PositionDAO) Factory.DAO.createDAO(PositionDAOImpl.class, "UNIT_TESTS", Factory.DAO.INMEMORY);
		var positions = da0.count();
		var games = dao.findGamesBySource("UNIT_TESTS");
		
		assertEquals(22784, games.size());
		assertEquals(1822720, positions);
	}
}
