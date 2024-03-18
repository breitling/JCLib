package com.breitling.jclib.chess;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ResultTests 
{
	@Test
	public void testGetValue_GoodResult_String()
	{
		var r = Result.WHITE_WINS.getValue();
		
		assertEquals("1-0", r);
	}
	
	@Test
	public void testValueOf_GoodResult_Result()
	{
		assertSame(Result.WHITE_WINS, Result.valueOfResult("1-0"));
		
		var r2 = Result.valueOfResult("0-1");
		var r3 = Result.valueOfResult("1/2-1/2");
		var r4 = Result.valueOfResult("*");
		var r1 = Result.valueOfResult("1-0");
		
		assertEquals(Result.NORESULT, r4);
		assertEquals(Result.DRAW, r3);
		assertEquals(Result.BLACK_WINS, r2);
		assertEquals(Result.WHITE_WINS, r1);
	}
}
