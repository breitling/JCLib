package com.breitling.jclib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataSourceTests 
{
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testConstructor_Source_Object() throws JsonMappingException, JsonProcessingException
	{
		var s = mapper.readValue(buildJSON(), DataSource.class);
		
		assertNotNull(s);
		assertEquals("Test", s.getName());
	}
	
//  FACTORIES
	
	private String buildJSON() throws JsonProcessingException
	{
		return this.mapper.writeValueAsString(buildSource());
	}
	
	private DataSource buildSource()
	{
		var s = DataSource.create("Test", "/Users/bobbr/Desktop/Chess/Games/Tal.pgn");
		
		return s;
	}
}
