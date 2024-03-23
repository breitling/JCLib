package com.breitling.jclib.chess;

import java.util.HashMap;
import java.util.Map;

public enum Piece 
{
	PAWN(' '), KNIGHT('N'), BISHOP('B'), ROOK('R'), QUEEN('Q'), KING('K');
	
	private Character piece;
	private static final Map<Character,Piece> map = new HashMap<>();
	private static final Map<Piece,String> nameMap = new HashMap<>();
	
	static {
		for (Piece p : values())
			map.put(p.piece, p);

		nameMap.put(PAWN, "Pawn");
		nameMap.put(KNIGHT, "Knight");
		nameMap.put(BISHOP, "Bishop");
		nameMap.put(ROOK, "Rook");
		nameMap.put(QUEEN, "Queen");
		nameMap.put(KING, "King");
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
	
	public static String getName(Piece p) {
		return nameMap.get(p);
	}
}
