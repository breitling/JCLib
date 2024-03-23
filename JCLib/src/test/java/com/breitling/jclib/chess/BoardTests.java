package com.breitling.jclib.chess;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BoardTests 
{
	@Test
	public void testBoard_StartingPosition_Board()
	{
		var b = Board.create();
		
		assertTrue(b.pieceAt(Color.WHITE, Piece.KING, Board.E1));
		
		assertTrue(b.pieceAt(Color.WHITE, Piece.QUEEN, Board.D1));
		
		assertTrue(b.pieceAt(Color.WHITE, Piece.KNIGHT, Board.B1));
		assertTrue(b.pieceAt(Color.WHITE, Piece.KNIGHT, Board.G1));

		assertTrue(b.pieceAt(Color.WHITE, Piece.BISHOP, Board.C1));
		assertTrue(b.pieceAt(Color.WHITE, Piece.BISHOP, Board.F1));
		
		assertTrue(b.pieceAt(Color.WHITE, Piece.ROOK, Board.A1));
		assertTrue(b.pieceAt(Color.WHITE, Piece.ROOK, Board.H1));
		
		assertTrue(b.pieceAt(Color.WHITE, Piece.PAWN, Board.A2));
		assertTrue(b.pieceAt(Color.WHITE, Piece.PAWN, Board.B2));
		assertTrue(b.pieceAt(Color.WHITE, Piece.PAWN, Board.C2));
		assertTrue(b.pieceAt(Color.WHITE, Piece.PAWN, Board.D2));
		assertTrue(b.pieceAt(Color.WHITE, Piece.PAWN, Board.E2));
		assertTrue(b.pieceAt(Color.WHITE, Piece.PAWN, Board.F2));
		assertTrue(b.pieceAt(Color.WHITE, Piece.PAWN, Board.G2));
		assertTrue(b.pieceAt(Color.WHITE, Piece.PAWN, Board.H2));
		
		assertTrue(b.pieceAt(Color.BLACK, Piece.KING, Board.E8));
		
		assertTrue(b.pieceAt(Color.BLACK, Piece.QUEEN, Board.D8));
		
		assertTrue(b.pieceAt(Color.BLACK, Piece.KNIGHT, Board.B8));
		assertTrue(b.pieceAt(Color.BLACK, Piece.KNIGHT, Board.G8));

		assertTrue(b.pieceAt(Color.BLACK, Piece.BISHOP, Board.C8));
		assertTrue(b.pieceAt(Color.BLACK, Piece.BISHOP, Board.F8));
		
		assertTrue(b.pieceAt(Color.BLACK, Piece.ROOK, Board.A8));
		assertTrue(b.pieceAt(Color.BLACK, Piece.ROOK, Board.H8));
		
		assertTrue(b.pieceAt(Color.BLACK, Piece.PAWN, Board.A7));
		assertTrue(b.pieceAt(Color.BLACK, Piece.PAWN, Board.B7));
		assertTrue(b.pieceAt(Color.BLACK, Piece.PAWN, Board.C7));
		assertTrue(b.pieceAt(Color.BLACK, Piece.PAWN, Board.D7));
		assertTrue(b.pieceAt(Color.BLACK, Piece.PAWN, Board.E7));
		assertTrue(b.pieceAt(Color.BLACK, Piece.PAWN, Board.F7));
		assertTrue(b.pieceAt(Color.BLACK, Piece.PAWN, Board.G7));
		assertTrue(b.pieceAt(Color.BLACK, Piece.PAWN, Board.H7));
		
		assertFalse(b.pieceAt(Color.BLACK, Piece.PAWN, Board.E5));
		assertFalse(b.pieceAt(Color.WHITE, Piece.ROOK, Board.C7));
	}
	
	@Test
	public void testMovePiece_GoodMove_Moved()
	{
		var b = Board.create();
		
		b.movePiece(Color.WHITE, Piece.PAWN, Board.E2, Board.E4);
		b.movePiece(Color.BLACK, Piece.KNIGHT, Board.B8, Board.C6);		
		
		assertTrue(b.pieceAt(Color.WHITE, Piece.PAWN, Board.E4));
		assertFalse(b.pieceAt(Color.WHITE, Piece.PAWN, Board.E2));
		
		assertTrue(b.pieceAt(Color.BLACK, Piece.KNIGHT, Board.C6));
		assertFalse(b.pieceAt(Color.BLACK, Piece.KNIGHT, Board.B8));
	}
	
	@Test
	public void testMovePiece_BadMove_Exception()
	{
		var b = Board.create();
				
		Exception exception = assertThrows(RuntimeException.class, () -> {
			b.movePiece(Color.WHITE, Piece.QUEEN, Board.E3, Board.E8);
		});
		
		assertEquals("Error: no Queen at e3", exception.getMessage());
	}
	
	@Test
	public void testToFEN_StartingPosition_FEN()
	{
		var b = Board.create();
		
		assertEquals(FEN.STARTING_POSITION, b.toFEN());
	}
	
	@Test
	public void testToFEN_PositionWithMoves_FEN()
	{
		var b = Board.create();
		
		b.movePiece(Color.WHITE, Piece.PAWN, Board.E2, Board.E4);
		b.movePiece(Color.WHITE, Piece.PAWN, Board.F2, Board.F4);
		b.movePiece(Color.WHITE, Piece.KNIGHT, Board.G1, Board.F3);
		b.movePiece(Color.BLACK, Piece.PAWN, Board.E7, Board.E5);
		b.movePiece(Color.BLACK, Piece.KNIGHT, Board.B8, Board.C6);
		
		assertEquals("r1bqkbnr/pppp1ppp/2n5/4p3/4PP2/5N2/PPPP2PP/RNBQKB1R w KQkq - 0 1", b.toFEN());
	}
	
	@Test
	public void testBoard_FromFEN_GoodPosition()
	{
		var b = Board.create("8/6p1/7k/8/1K6/8/1P6/8");
		
		assertEquals("8/6p1/7k/8/1K6/8/1P6/8", b.toFEN().split(" ")[0]);
	}
	
	@Test
	public void testResetToStaringPosition_FEN_Board()
	{
		var b = Board.create("8/6p1/7k/8/1K6/8/1P6/8");

		b.resetToStartingPosition();
		
		assertEquals(FEN.STARTING_POSITION, b.toFEN());
	}
}
