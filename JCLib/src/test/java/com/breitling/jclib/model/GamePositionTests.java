package com.breitling.jclib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GamePositionTests 
{
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testConstructor_GamePosition_Object() throws JsonMappingException, JsonProcessingException
	{
		var gp = mapper.readValue(buildJSON(), GamePosition.class);
		
		assertNotNull(gp);
		assertEquals(gp.getPositionId(), 12L);
	}
	
//  FACTORIES
	
	private String buildJSON() throws JsonProcessingException
	{
		return this.mapper.writeValueAsString(buildGamePosition());
	}
	
	private GamePosition buildGamePosition()
	{
		var gp  = new GamePosition();
		
		gp.setGameId(12L);
		gp.setPositionId(12L);
		
		return gp;
	}
}
