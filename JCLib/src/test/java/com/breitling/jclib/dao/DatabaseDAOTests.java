package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.JCLibApplication;
import com.breitling.jclib.model.Database;
import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {JCLibApplication.class})
@ComponentScan(basePackages = {"com.breitling.jclib"})
@ActiveProfiles("test")
public class DatabaseDAOTests 
{
	@Autowired
	private DatabaseDAO dao;

	@Test
	public void testSave_GoodDatabase_Object()
	{
		var r = dao.save(Database.create("RJF60", "/Users/bobbr/Desktop/Chess/Games/RJF60.pgn"));
		
		assertTrue(r);
		
		Optional<List<Database>> opt = dao.findAll();
		
		assertTrue(opt.isPresent());
		assertEquals("RJF60", opt.get().get(0).getDS().getName());
	}
	
	@Test
	public void testFindById_GoodId_Object()
	{
		var db = Database.create("RJF60", "/Users/bobbr/Desktop/Chess/Games/RJF60.pgn");
		db.setId(Factory.DAO.generateId());
		
		dao.save(db);
		Optional<Database> opt = dao.findById(db.getId());
		
		assertTrue(opt.isPresent());
		assertEquals("RJF60", opt.get().getDS().getName());
	}
	
	@Test
	public void testFindById_BadId_Empty()
	{
		Optional<Database> opt = dao.findById(7L);
		
		assertTrue(opt.isEmpty());
	}
	
	@Test
	public void testFindByName_GoodName_Object()
	{
		Optional<Database> opt = dao.findByName("RJF60");
		
		assertTrue(opt.isPresent());
		assertEquals("RJF60", opt.get().getDS().getName());
		assertEquals("/Users/bobbr/Desktop/Chess/Games/RJF60.pgn", opt.get().getDS().getPath());
	}
	
	@Test
	public void testFindByName_BadName_Empty()
	{
		Optional<Database> opt = dao.findByName("X");
		
		assertTrue(opt.isEmpty());
	}
	
	@Test
	public void testAddNotes_GoodId_String()
	{
		Optional<Database> opt = dao.findByName("RJF60");
		
		if (opt.isPresent())
		{
			var d = opt.get();
			dao.addNotes(d.getId(), "These are the notes.");
			
			var e = dao.getNotes(d.getId());
			
			assertEquals("These are the notes.", e);
		}
	}
}
