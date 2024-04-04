package com.breitling.jclib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.breitling.jclib.chess.Result;
import com.breitling.jclib.util.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameTests
{
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testConstructor_Game_Object() throws JsonMappingException, JsonProcessingException
	{
		var g = mapper.readValue(buildJSON(), Game.class);
		
		assertNotNull(g);
		assertEquals("Jo", g.getWhite());
	}
	
//  FACTORIES
	
	private String buildJSON() throws JsonProcessingException
	{
		return this.mapper.writeValueAsString(buildGame());
	}
	
	private Game buildGame()
	{
		var g = Factory.Model.Game.create("Jo", "Fred", Result.WHITE_WINS, "1. e4 1-0");
		
		return g;
	}
}
