package com.breitling.jclib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.breitling.jclib.util.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PositionTests
{
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testConstructor_Position_Object() throws JsonMappingException, JsonProcessingException
	{
		var p = mapper.readValue(buildJSON(), Position.class);
		
		assertNotNull(p);
		assertEquals("6k1/6p1/2pR1p1p/2P4P/1K3PP1/pP6/8/r7 b - - 2 37", p.getFen());
	}
	
//  FACTORIES
	
	private String buildJSON() throws JsonProcessingException
	{
		return this.mapper.writeValueAsString(buildPosition());
	}
	
	private Position buildPosition()
	{
		var g = Factory.Model.Position.create("6k1/6p1/2pR1p1p/2P4P/1K3PP1/pP6/8/r7 b - - 2 37");
		
		return g;
	}
}
