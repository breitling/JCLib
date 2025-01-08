package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class NoteDAOTests 
{
	@Autowired
	private NoteDAO dao;

	@Test
	public void testSave_String_Object()
	{
		var r = dao.save(Factory.Model.Note.create(1L, "This is it."));
		
		assertTrue(r);
	}
}
