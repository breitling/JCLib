package com.breitling.jclib.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.breitling.jclib.util.Factory;

public class NoteDAOTests 
{
	private NoteDAO dao;
	
	@Autowired
    @SuppressWarnings("unused")
    private DatabaseDAO dbDao;
    
    private static boolean initialized = false;
    
	@BeforeEach
	public void setupForTest() throws SQLException
	{
		dao = (NoteDAO) Factory.DAO.createDAO(NoteDAOImpl.class, "notes", Factory.DAO.INMEMORY);
		
		if (!initialized)
		{
			Connection conn = ((GenericDAO) dao).getDataSource().getConnection();
			ScriptUtils.executeSqlScript(conn, new PathResource(Paths.get("./src/test/datasets/notes.sql")));
			initialized = true;
		}
	}
	@Test
	public void testPersistNote_String_Object()
	{
		var id = dao.persistNote(0, "This is it.");
		
		assertEquals(1, id.longValue());
	}
}
