package com.breitling.jclib.chess;

import java.util.HashMap;
import java.util.Map;

public enum Piece 
{
	PAWN(' '), KNIGHT('N'), BISHOP('B'), ROOK('R'), QUEEN('Q'), KING('K');
	
	private Character piece;
	private static final Map<Character,Piece> map = new HashMap<>();
	
	static {
		for (Piece p : values())
			map.put(p.piece, p);
	}
	
	Piece(char p) 
	{
		this.piece = p;
	}
	
	public Character getPiece()
	{
		return piece;
	}
	
	public static Piece valueOfPiece(Character p) {
		return map.get(p);
	}
}
