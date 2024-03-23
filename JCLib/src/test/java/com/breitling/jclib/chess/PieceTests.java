package com.breitling.jclib.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PieceTests 
{
	@Test
	public void testPieceOrdinal_Ordinals_Ints()
	{
		assertEquals(0, Piece.PAWN.ordinal());
		assertEquals(1, Piece.KNIGHT.ordinal());
		assertEquals(2, Piece.BISHOP.ordinal());
		assertEquals(3, Piece.ROOK.ordinal());
		assertEquals(4, Piece.QUEEN.ordinal());
		assertEquals(5, Piece.KING.ordinal());		
	}
}
