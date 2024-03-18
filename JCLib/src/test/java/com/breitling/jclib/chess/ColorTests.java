package com.breitling.jclib.chess;

import static org.junit.Assert.assertSame;

import org.junit.jupiter.api.Test;

public class ColorTests 
{
	@Test
	public void testValueOf_GoodResult_Result()
	{
		assertSame(Color.WHITE, Color.valueOfColor("White"));
	}
}
