package com.breitling.jclib.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class GameServiceTests 
{
	@Autowired
	private GameService service;
	
//  TEST CASES
	
	@Test
	public void testSaveGamesFromSource_OneGame_DBObjects()
	{
	}
	
	@Test
	public void testSaveGamesFromSource_GoodSource_DBObjects()
	{
	}
	
	@Test
	@Disabled
	public void testSaveGamesFromSource_BigSource_DBObjects()
	{
	}
	
	@Test
	@Disabled
	public void testSaveGamesFromSource_Tal_DBObjects()
	{
	}
}
