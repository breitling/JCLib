package com.breitling.jclib.chess;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BitBoardTests 
{
	@Test
	public void testOfSquare_IndexOfA1_Bit()
	{
		var i = BitBoard.OfSquare(Board.A1);
		
		assertEquals(i, 0x0000000000000001);
	}
	
	@Test
	public void testOfSquare_IndexOfH8_Bit()
	{
		var i = BitBoard.OfSquare(Board.H8);
		
		assertEquals(i, 0x8000000000000000L);
	}
	
	@Test
	public void testOfSquare_IndexOfF2_Bit()
	{
		var i = BitBoard.OfSquare(Board.F2);
		
		assertEquals(i, 0x0000000000002000L);
	}
	
	@Test
	public void testDeltaFile_B2andD6_2()
	{
		var d = BitBoard.deltaFile(Board.B2, Board.D6);
		
		assertEquals(d, 2);
	}
	
	@Test
	public void testDeltaRank_F1andG7_6()
	{
		var d = BitBoard.deltaRank(Board.F1, Board.G7);
		
		assertEquals(d, 6);
	}
	
	@Test
	public void testDoesPieceAttack_ASquare_Boolean()
	{
		assertTrue(BitBoard.doesPieceAttack(Piece.KNIGHT, Board.A1, Board.C2));
		assertTrue(BitBoard.doesPieceAttack(Piece.KNIGHT, Board.A1, Board.B3));
		assertFalse(BitBoard.doesPieceAttack(Piece.KNIGHT, Board.A1, Board.D7));
		
		assertTrue(BitBoard.doesPieceAttack(Piece.QUEEN, Board.A1, Board.H8));
		assertTrue(BitBoard.doesPieceAttack(Piece.QUEEN, Board.A1, Board.A8));
		assertTrue(BitBoard.doesPieceAttack(Piece.QUEEN, Board.A1, Board.H1));
		
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.F5));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.G6));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.H7));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.D3));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.C2));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.B1));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.F3));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.G2));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.H1));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.D5));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.C6));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.B7));
		assertTrue(BitBoard.doesPieceAttack(Piece.BISHOP, Board.E4, Board.A8));
	}
	
	@Test
	public void  testDoesColorPieceAttack_ASquare_Boolean()
	{
		assertTrue(BitBoard.doesColorPieceAttack(Color.BLACK, Piece.KNIGHT, Board.A1, Board.C2));
		assertTrue(BitBoard.doesColorPieceAttack(Color.BLACK, Piece.PAWN, Board.D5, Board.C4));
		assertTrue(BitBoard.doesColorPieceAttack(Color.WHITE, Piece.PAWN, Board.C6, Board.B7));
	}
	
	@Test
	public void testGenerateBitBoardHash_GoodPosition_HashValue()
	{
		var hash = BitBoard.generateBitBoardHash(FEN.STARTING_POSITION);
		
		assertEquals(FEN.STARTING_POSITION_HASH, hash);
	}
}
