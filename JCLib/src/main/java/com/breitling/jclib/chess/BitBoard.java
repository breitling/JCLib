package com.breitling.jclib.chess;

import java.util.BitSet;

public class BitBoard 
{
	private static final int NUM_OF_RANKS = 8;
	private static final int NUM_OF_FILES = 8;
	private static final int NUM_OF_SQUARES = NUM_OF_RANKS * NUM_OF_FILES;
	
	private static final long [] bitOfSquare;
	
//  PRECOMPUTED BIT BOARDS
    private static final long[] KNIGHT_ATTACKS       = new long[NUM_OF_SQUARES];
    private static final long[] BISHOP_ATTACKS       = new long[NUM_OF_SQUARES];
    private static final long[] ROOK_ATTACKS         = new long[NUM_OF_SQUARES];
    private static final long[] QUEEN_ATTACKS        = new long[NUM_OF_SQUARES];
    private static final long[] KING_ATTACKS         = new long[NUM_OF_SQUARES];
    private static final long[] ALL_ATTACKS          = new long[NUM_OF_SQUARES];
    private static final long[] WHITE_PAWN_MOVES     = new long[NUM_OF_SQUARES];
    private static final long[] BLACK_PAWN_MOVES     = new long[NUM_OF_SQUARES];
    private static final long[] WHITE_PAWN_ATTACKS   = new long[NUM_OF_SQUARES];
    private static final long[] BLACK_PAWN_ATTACKS   = new long[NUM_OF_SQUARES];
    
    static 
    {
    	bitOfSquare = new long [NUM_OF_SQUARES];
    	
    	for(int sqi = 0; sqi < NUM_OF_SQUARES; sqi++)
    		bitOfSquare[sqi] = 1L << sqi;
    	
        for (int from = 0; from <= 63; from++) 
        {
            KNIGHT_ATTACKS[from] = 0L;
            BISHOP_ATTACKS[from] = 0L;
            ROOK_ATTACKS[from] = 0L;
            KING_ATTACKS[from] = 0L;
            WHITE_PAWN_MOVES[from] = 0L;
            BLACK_PAWN_MOVES[from] = 0L;
            WHITE_PAWN_ATTACKS[from] = 0L;
            BLACK_PAWN_ATTACKS[from] = 0L;
            
            for (int to = 0; to <= 63; to++) 
            {
                if (to != from) 
                {
                    long bbTo = OfSquare(to);
                    int dcol = deltaFile(from, to);
                    int drow = deltaRank(from, to);
                    
                    if (Math.abs(dcol * drow) == 2) 
                    {
                        KNIGHT_ATTACKS[from] |= bbTo;
                    }
                    else if (dcol == drow || dcol == -drow) 
                    {
                        BISHOP_ATTACKS[from] |= bbTo;
                    } 
                    else if (dcol * drow == 0) 
                    {
                        ROOK_ATTACKS [from] |= bbTo;
                    }
                    
                    if (Math.abs(dcol) <= 1 && Math.abs(drow) <= 1) 
                    {
                        KING_ATTACKS [from] |= bbTo;
                    }
                    
                    if (dcol ==  0 && drow ==  1) 
                    	WHITE_PAWN_MOVES[from]   |= bbTo;
                    if (dcol ==  0 && drow == -1) 
                    	BLACK_PAWN_MOVES[from]   |= bbTo;
                    if (dcol == -1 && drow ==  1) 
                    	WHITE_PAWN_ATTACKS[from] |= bbTo;
                    if (dcol ==  1 && drow ==  1) 
                    	WHITE_PAWN_ATTACKS[from] |= bbTo;
                    if (dcol == -1 && drow == -1) 
                    	BLACK_PAWN_ATTACKS[from] |= bbTo;
                    if (dcol ==  1 && drow == -1) 
                    	BLACK_PAWN_ATTACKS[from] |= bbTo;
                }
            }
            QUEEN_ATTACKS[from] = BISHOP_ATTACKS[from] | ROOK_ATTACKS[from];
            ALL_ATTACKS[from] = QUEEN_ATTACKS[from] | KNIGHT_ATTACKS[from];
        }
    }
    
//  PUBLIC METHODS
    
    public static final int deltaFile(int index1, int index2)
    {
    	return (index2 % NUM_OF_FILES) - (index1 % NUM_OF_FILES);
    }
    
    public static final int deltaRank(int sqi1, int sqi2)
    {
        return (sqi2 / NUM_OF_RANKS) - (sqi1 / NUM_OF_RANKS);
    }
    
    public static final long OfSquare(int index)
    {
    	return bitOfSquare[index];
    }
    
    public static final Boolean doesColorPieceAttack(Color c, Piece p, int from, int to)
    {
    	boolean b = false;
    	
    	switch(p)
    	{
    	case KNIGHT:
    	case BISHOP:
    	case ROOK:
    	case QUEEN:
    	case KING:
    		 b = doesPieceAttack(p, from, to);
    		 break;
    		 
    	case PAWN:
    	default:
    		 if (c == Color.WHITE)
    			 b = (WHITE_PAWN_ATTACKS[from] & OfSquare(to)) != 0;
    		 else
    			 b = (BLACK_PAWN_ATTACKS[from] & OfSquare(to)) != 0;
    		 break;    	
    	}
    	
    	return b;
    }
    
    public static final Boolean doesPieceAttack(Piece p, int from, int to)
    {
    	boolean b = false;
    	
    	switch(p)
    	{
    	case KNIGHT:
    		 b = (KNIGHT_ATTACKS[from] & OfSquare(to)) != 0;
    		 break;
    		
    	case BISHOP:
    		 b = (BISHOP_ATTACKS[from] & OfSquare(to)) != 0;
    		 break;
    
    	case ROOK:
     		 b = (ROOK_ATTACKS[from] & OfSquare(to)) != 0;
    		 break;
    		 
    	case QUEEN:
   		 	 b = (QUEEN_ATTACKS[from] & OfSquare(to)) != 0;
   		 	 break;

    	case KING:
   		 	 b = (ROOK_ATTACKS[from] & OfSquare(to)) != 0;
   		 	 break;
   		 	 
   		default:
   			 break;
    	}
    	
    	return b;
    }
    
	public static long generateBitBoardHash(String fen)
	{
		byte [] bytes = new byte [0];
		
		if (fen.contains(" "))
		    bytes = fen.substring(0, fen.indexOf(' ')).getBytes();
		else
		    bytes = fen.getBytes();
				
		int len = bytes.length-1;
		int bitpos = 0;
		BitSet bitboard = new BitSet(64);
				
		for (int i = len; i >= 0; i--) {
		    byte c = bytes[i];
		    if (c == 47)
		        continue;
		     if (c > 57) {
		        bitboard.set(bitpos);
		        bitpos++;
		    }
		    else {
		        bitpos = bitpos + (c - 48);
		    }
		}
		return bitboard.toLongArray()[0];
	}
}
