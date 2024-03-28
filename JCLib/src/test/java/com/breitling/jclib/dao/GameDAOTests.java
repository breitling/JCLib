package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.chess.Result;
import com.breitling.jclib.persistence.Game;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts={"/games.schema", "/games.sql"},executionPhase=ExecutionPhase.BEFORE_TEST_CLASS)
@ActiveProfiles("test")
public class GameDAOTests 
{
    @Autowired
    private GameDAO dao;
    
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
    	assertEquals(Result.WHITE_WINS, g.getResult());
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
    	assertEquals(Result.WHITE_WINS, g.getResult());
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
    	assertEquals(Result.WHITE_WINS, g.getResult());
    	assertEquals(4, g.getMoveCount());
    }
}
