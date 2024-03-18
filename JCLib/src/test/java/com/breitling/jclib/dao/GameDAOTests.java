package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import com.breitling.jclib.chess.Result;
import com.breitling.jclib.persistence.Game;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(scripts={"/games.schema"},executionPhase=ExecutionPhase.BEFORE_TEST_CLASS)
@ActiveProfiles("test")
public class GameDAOTests 
{
	@Autowired
    @SuppressWarnings("unused")
    private TestEntityManager entityManager;

    @Autowired
    private GameDAO dao;
    
    @Test
    public void testFindGamesByPlayerName_BadName_EmptyList()
    {
    	var games = dao.findGamesByPlayerName("Jo To");
    	
    	assertNotNull(games);
    	assertEquals(0, games.size());
    }
    
    @Test
    @Sql("/games.sql")
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
}
