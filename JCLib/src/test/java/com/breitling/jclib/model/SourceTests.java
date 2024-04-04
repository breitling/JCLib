package com.breitling.jclib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.breitling.jclib.util.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SourceTests 
{
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testConstructor_Source_Object() throws JsonMappingException, JsonProcessingException
	{
		var s = mapper.readValue(buildJSON(), Source.class);
		
		assertNotNull(s);
		assertEquals("Test", s.getName());
	}
	
//  FACTORIES
	
	private String buildJSON() throws JsonProcessingException
	{
		return this.mapper.writeValueAsString(buildSource());
	}
	
	private Source buildSource()
	{
		var s = Factory.Model.Source.create("Test", "/Users/bobbr/Desktop/Chess/Games/Tal.pgn");
		
		return s;
	}
}
