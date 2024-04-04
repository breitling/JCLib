package com.breitling.jclib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MoveTests
{
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testConstructor_Move_Object() throws JsonMappingException, JsonProcessingException
	{
		var m = mapper.readValue(buildJSON(), Move.class);
		
		assertNotNull(m);
		assertEquals("e4", m.getMove());
	}
	
//  FACTORIES
	
	private String buildJSON() throws JsonProcessingException
	{
		return this.mapper.writeValueAsString(buildMove());
	}
	
	private Move buildMove()
	{
		var move = new Move();
		
		move.setMove("e4");
		
		return move;
	}
}
