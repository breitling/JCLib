package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.dizitart.no2.filters.FluentFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.JCLibApplication;
import com.breitling.jclib.chess.Result;
import com.breitling.jclib.model.DataSource;
import com.breitling.jclib.model.Game;
import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {JCLibApplication.class})
@ComponentScan(basePackages = {"com.breitling.jclib"})
@ActiveProfiles("test")
public class GameDAOTests 
{
	@Autowired
    private GameDAO dao;
    
//  TEST CASES
	
	@Test
	public void testFindAll_X_Games()
	{
		var list = dao.findAll();
		
		assertTrue(list.isPresent());
//		assertEquals(5, list.get().size());
	}
	
    @Test
    public void testFindById_BadId_NotPresent()
    {
    	var game = dao.findById(123L);
    	
    	assertTrue(game.isEmpty());
    	assertFalse(game.isPresent());
    }
    
//    @Test
//    public void testFindById_GoodId_Game()
//    {
//    	var game = dao.findById(3L);
//    	
//    	var g = game.get();
//    	
//    	assertTrue(game.isPresent());
//    	
//    	assertEquals("Jo To", g.getWhite());
//    	assertEquals(Result.WHITE_WINS, Result.valueOfResult(g.getResult()));
//    	assertEquals(4, g.getMoveCount());
//    }
    
    @Test
    public void testSave_GoodGame_Game()
    {
    	var g = buildGame("Bob", "Bill", "1/2-1/2", "1. e4 e5 2. Nf3 Nc6 1/2-1/2");
    	var n = dao.save(g);
    	
    	assertTrue(n);
    	
    	var gprime = dao.findById(g.getId()).get();
    	
    	assertEquals("Bob", gprime.getWhite());
    	assertEquals(Result.DRAW, gprime.getResult());
    	assertEquals("1. e4 e5 2. Nf3 Nc6 1/2-1/2", gprime.getMoves());
    }
    
    @Test
    public void testImportGames_GoodSource_CountOf60()
    {
    	int n = dao.importGames(DataSource.create("RJF60", "/Users/bobbr/Desktop/Chess/Games/RJF60.pgn"));
    	
    	assertEquals(60, n);
    }
    
    @Test
    public void testFind_Filter_Games()
    {
    	((GameDAOImpl)dao).setStoreModule("RJF60");
    	
    	var games = dao.findAll();
    	
    	assertTrue(games.isPresent());
    	assertEquals(60, games.get().size());
    	
    	var list = dao.find(FluentFilter.where("white").eq("Robert J. Fischer"));
    	
    	assertTrue(list.size() > 0);
    	assertEquals(37, list.size());
    	
    	list = dao.find(FluentFilter.where("event").eq("Mar del Plata"));
    	
    	assertEquals(4, list.size());
    }
    
//  FACTORIES
    
    private Game buildGame(String w, String b, String r, String m)
    {
    	var g = new Game();
    	
    	g.setId(Factory.DAO.generateId());
    	
    	g.setWhite(w);
    	g.setBlack(b);
    	g.setResult(Result.valueOfResult(r));
    	g.setMoves(m);
    	g.setMoveCount(2);
    	
    	return g;
    }
}
