package com.breitling.jclib.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.breitling.jclib.util.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NoteTests 
{
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testConstructor_Note_Object() throws JsonMappingException, JsonProcessingException
	{
		var note = mapper.readValue(buildJSON(), Note.class);
		
		assertNotNull(note);
		assertEquals("This is s a test.", note.getNote());
	}
	
//  FACTORIES
	
	private String buildJSON() throws JsonProcessingException
	{
		return this.mapper.writeValueAsString(buildNote());
	}
	
	private Note buildNote()
	{
		var note  = Factory.Model.Note.create(null, "This is s a test.");
		
		return note;
	}
}
