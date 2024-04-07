package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.breitling.jclib.chess.Result;
import com.breitling.jclib.persistence.Game;
import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class GameDAOTests 
{
    private GameDAO dao;
    
	@Autowired
    @SuppressWarnings("unused")
    private DatabaseDAO dbDao;
    
    private static boolean initialized = false;
	
	@BeforeEach
	public void setupForTest() throws SQLException
	{
		dao = (GameDAO) Factory.DAO.createDAO(GameDAOImpl.class, "games", Factory.DAO.INMEMORY);
		
		if (!initialized)
		{
			Connection conn = ((GenericDAO) dao).getDataSource().getConnection();
			ScriptUtils.executeSqlScript(conn, new PathResource(Paths.get("./src/test/datasets/games.schema")));
			ScriptUtils.executeSqlScript(conn, new PathResource(Paths.get("./src/test/datasets/games.sql")));
			initialized = true;
		}
	}
    
//  TEST CASES
	
    @Test
    public void testFindGamesByPlayerName_BadName_EmptyList()
    {
    	var games = dao.findGamesByPlayerName("Chris Evert");
    	
    	assertNotNull(games);
    	assertEquals(0, games.size());
    }
    
    @Test
    public void testFindGamesByPlayerName_GoodName_List()
    {
    	var games = dao.findGamesByPlayerName("Bob Breitling");
    	
    	assertNotNull(games);
    	assertEquals(1, games.size());
    	
    	Game g = games.get(0);
    	
    	assertEquals("Bob Breitling", g.getWhite());
    	assertEquals(Result.WHITE_WINS, Result.valueOfResult(g.getResult()));
    	assertEquals(4, g.getMoveCount());
    }
    
    @Test
    public void testFindGamesBySource_GoodSource_List()
    {
    	var games = dao.findGamesBySource("JoToGames");
    	
    	assertNotNull(games);
    	assertEquals(2, games.size());
    	
    	Game g = games.get(0);
    	
    	assertEquals("Jo To", g.getWhite());
    	assertEquals(Result.WHITE_WINS, Result.valueOfResult(g.getResult()));
    	assertEquals(4, g.getMoveCount());
    }
    
    @Test
    public void testFindById_BadId_NotPresent()
    {
    	var game = dao.findById(123L);
    	
    	assertTrue(game.isEmpty());
    	assertFalse(game.isPresent());
    }
    
    @Test
    public void testFindById_GoodId_Game()
    {
    	var game = dao.findById(3L);
    	
    	var g = game.get();
    	
    	assertTrue(game.isPresent());
    	
    	assertEquals("Jo To", g.getWhite());
    	assertEquals(Result.WHITE_WINS, Result.valueOfResult(g.getResult()));
    	assertEquals(4, g.getMoveCount());
    }
    
    @Test
    public void testPersistGame_GoodGame_1()
    {
    	var n = dao.persistGame(buildGame(new String[]{"Bob", "Bill", "1/2-1/2", "1. e4 e5 2. Nf3 Nc6 1/2-1/2"}));
    	
    	assertEquals(4, n.longValue());
    	
    	var list = dao.findGamesByPlayerName("Bill");
    	
    	assertEquals(1, list.size());
    	assertEquals("Bob", list.get(0).getWhite());
    	assertEquals("1/2-1/2", list.get(0).getResult());
    	assertEquals("1. e4 e5 2. Nf3 Nc6 1/2-1/2", list.get(0).getMoves());
    }
    
//  FACTORIES
    
    private Game buildGame(String... params)
    {
    	return Factory.Persistence.Game.create(params[0], params[1], params[2], params[3]);
    }
}
