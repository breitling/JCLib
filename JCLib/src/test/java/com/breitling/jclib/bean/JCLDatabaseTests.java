package com.breitling.jclib.bean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Paths;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.breitling.jclib.dao.SourceDAO;
import com.breitling.jclib.dao.SourceDAOImpl;
import com.breitling.jclib.util.Factory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class JCLDatabaseTests 
{
	private static DataSource datasource;
	
	private SourceDAO dao;
	
	@BeforeAll
	public static void setupForTests() throws ScriptException, SQLException
	{
		datasource = JCLDatabase.createDataSource("test");
		ScriptUtils.executeSqlScript(datasource.getConnection(), new PathResource(Paths.get("./src/test/datasets/sources.schema")));
		ScriptUtils.executeSqlScript(datasource.getConnection(), new PathResource(Paths.get("./src/test/datasets/sources.sql")));
	}
	
	@BeforeEach
	public void setupForTest()
	{
		dao = (SourceDAO) Factory.Persistence.DAO.createDAO(SourceDAOImpl.class, "test");
	}
	
	@Test
	public void testCreateDataSource_GoodDB_Object() throws SQLException
	{
		assertNotNull(datasource);
		assertNotNull(dao);
		
		var source = dao.findById(1L);
    	
    	assertNotNull(source.get());
	}
	
	@Test
	public void testDummy_Nothing_Null()
	{
		var source = dao.findById(1L);
    	
    	assertNotNull(source.get());
	}
	
	@AfterAll
	public static void cleanup()
	{
		assert(Paths.get("./test.mv.db").toFile().delete() == true);
	}
}
