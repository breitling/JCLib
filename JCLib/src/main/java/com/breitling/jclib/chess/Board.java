package com.breitling.jclib.chess;

public class Board 
{
    public static final int
    NUM_OF_COLS = 8,
    NUM_OF_ROWS = 8,
    NUM_OF_SQUARES = NUM_OF_COLS * NUM_OF_ROWS;
    
    public static final int
    A8 = 56, B8 = 57, C8 = 58, D8 = 59, E8 = 60, F8 = 61, G8 = 62, H8 = 63,
    A7 = 48, B7 = 49, C7 = 50, D7 = 51, E7 = 52, F7 = 53, G7 = 54, H7 = 55,
    A6 = 40, B6 = 41, C6 = 42, D6 = 43, E6 = 44, F6 = 45, G6 = 46, H6 = 47,
    A5 = 32, B5 = 33, C5 = 34, D5 = 35, E5 = 36, F5 = 37, G5 = 38, H5 = 39,
    A4 = 24, B4 = 25, C4 = 26, D4 = 27, E4 = 28, F4 = 29, G4 = 30, H4 = 31,
    A3 = 16, B3 = 17, C3 = 18, D3 = 19, E3 = 20, F3 = 21, G3 = 22, H3 = 23,
    A2 =  8, B2 =  9, C2 = 10, D2 = 11, E2 = 12, F2 = 13, G2 = 14, H2 = 15,
    A1 =  0, B1 =  1, C1 =  2, D1 =  3, E1 =  4, F1 =  5, G1 =  6, H1 =  7;
    
    public static final String [] square = {
    "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", 
    "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", 
    "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", 
    "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", 
    "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", 
    "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", 
    "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", 
    "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", 
    };
    
    private long[] whiteBitBoards = new long [6];
    private long[] blackBitBoards = new long [6];
    
    public Board()
    {
    	init();
    }
    
    public static Board create()
    {
    	return new Board();
    }
    
    public static Board create(String fen)
    {
    	var board = new Board();
    	var ranks = fen.split(" ")[0].split("/");
    	
    	for (int n = 0; n < 6; n++)
    	{
    		board.whiteBitBoards[n] = board.blackBitBoards[n] = 0L;
    	}
    	
    	for (int r = 0; r < NUM_OF_ROWS; r++)
    	{
    		String rank = ranks[r];
    		int col = 0;
    		
    		for (int c = 0; c < rank.length(); c++)
    		{
    			char p = rank.charAt(c);
    			
    			if (Character.isDigit(p))
    			{
    				col += (p - 48);
    			}
    			else
    			{
    				int square = ((7 - r) * 8) + col;
    				
    				switch(p)
    				{
    				case 'P':
    					 board.whiteBitBoards[0] |= BitBoard.OfSquare(square);
    					 break;
    					 
    				case 'N':
	   					 board.whiteBitBoards[1] |= BitBoard.OfSquare(square);
	   					 break;
	   					 
    				case 'B':
	   					 board.whiteBitBoards[2] |= BitBoard.OfSquare(square);
	   					 break;
	   					 
    				case 'R':
	   					 board.whiteBitBoards[3] |= BitBoard.OfSquare(square);
	   					 break;
	   					 
    				case 'Q':
	   					 board.whiteBitBoards[4] |= BitBoard.OfSquare(square);
	   					 break;
	   					 
    				case 'K':
	   					 board.whiteBitBoards[5] |= BitBoard.OfSquare(square);
	   					 break;
	   					 
    				case 'p':
	   					 board.blackBitBoards[0] |= BitBoard.OfSquare(square);
	   					 break;
   					 
	   				case 'n':
						 board.blackBitBoards[1] |= BitBoard.OfSquare(square);
						 break;
		   					 
	   				case 'b':
						 board.blackBitBoards[2] |= BitBoard.OfSquare(square);
						 break;
		   					 
	   				case 'r':
						 board.blackBitBoards[3] |= BitBoard.OfSquare(square);
						 break;
		   					 
	   				case 'q':
	   					 board.blackBitBoards[4] |= BitBoard.OfSquare(square);
		   				 break;
		   					 
	   				case 'k':
		   				 board.blackBitBoards[5] |= BitBoard.OfSquare(square);
		   				 break;
    				}
    			}
    		}
    	}
    	
    	return board;
    }
    
//  PUBLIC METHODS
    
    public void movePiece(Color c, Piece p, int from, int to)
    {
    	if (pieceAt(c, p, from))
    	{
    		if (c == Color.WHITE)
    		{
    			whiteBitBoards[p.ordinal()] ^= BitBoard.OfSquare(from);
    			whiteBitBoards[p.ordinal()] |= BitBoard.OfSquare(to);    			
    		}
    		else
    		{
    			blackBitBoards[p.ordinal()] ^= BitBoard.OfSquare(from);
    			blackBitBoards[p.ordinal()] |= BitBoard.OfSquare(to);  
    		}
    	}
    	else
    	{
    		throw new RuntimeException(new StringBuilder("Error: no ").append(Piece.getName(p)).append(" at ").append(square[from]).toString());
    	}
    }
    
    public Boolean pieceAt(Color c, Piece p, int at)
    {
    	return (bitBoards(c, p.ordinal()) & BitBoard.OfSquare(at)) != 0;
    }
    
    public void resetToStartingPosition()
    {
    	init();
    }
    
    public String toFEN()
    {
    	char [] b = new char [NUM_OF_SQUARES];
    	
    	for (int n = 0; n < NUM_OF_SQUARES; n++)
    		b[n] = ' ';
    	
    	for (int n = 0; n < NUM_OF_SQUARES; n++)
    	{
    		b[n] = ((whiteBitBoards[Piece.PAWN.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'P' : b[n];
    		b[n] = ((whiteBitBoards[Piece.KNIGHT.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'N' : b[n];
    		b[n] = ((whiteBitBoards[Piece.BISHOP.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'B' : b[n];
    		b[n] = ((whiteBitBoards[Piece.ROOK.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'R' : b[n];
    		b[n] = ((whiteBitBoards[Piece.QUEEN.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'Q' : b[n];
    		b[n] = ((whiteBitBoards[Piece.KING.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'K' : b[n];
 
    		b[n] = ((blackBitBoards[Piece.PAWN.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'p' : b[n];
    		b[n] = ((blackBitBoards[Piece.KNIGHT.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'n' : b[n];
    		b[n] = ((blackBitBoards[Piece.BISHOP.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'b' : b[n];
    		b[n] = ((blackBitBoards[Piece.ROOK.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'r' : b[n];
    		b[n] = ((blackBitBoards[Piece.QUEEN.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'q' : b[n];
    		b[n] = ((blackBitBoards[Piece.KING.ordinal()] & BitBoard.OfSquare(n)) != 0) ? 'k' : b[n];
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for (int r = 7; r >= 0; r--)
    	{
			int spaces = 0;

			for (int c = 0; c < 8; c++)
    		{
    			char p = b[(r * NUM_OF_COLS) + c];
    			
    			if (p == ' ')
    			{
    				spaces++;
    			}
    			else
    			{
    				if (spaces > 0)
    					sb.append(spaces);
    				
    				sb.append(p);
    				
    				spaces = 0;
    			}
    		}
    		
    		if (spaces > 0)
    			sb.append(spaces);
    		
    		if (r > 0)
    			sb.append("/");
    	}
    	
    	sb.append(" w KQkq - 0 1");
    	
    	return sb.toString();
    }
    
//  PRIVATE METHODS
    
    private void init()
    {
    	whiteBitBoards[Piece.PAWN.ordinal()]   = 0xff00L;
    	whiteBitBoards[Piece.KNIGHT.ordinal()] = 0x42L;
    	whiteBitBoards[Piece.BISHOP.ordinal()] = 0x24L;
    	whiteBitBoards[Piece.ROOK.ordinal()]   = 0x81L;
    	whiteBitBoards[Piece.QUEEN.ordinal()]  = 0x8L;
    	whiteBitBoards[Piece.KING.ordinal()]   = 0x10L;

    	blackBitBoards[Piece.PAWN.ordinal()]   = 0xff000000000000L;
    	blackBitBoards[Piece.KNIGHT.ordinal()] = 0x4200000000000000L;
    	blackBitBoards[Piece.BISHOP.ordinal()] = 0x2400000000000000L;
    	blackBitBoards[Piece.ROOK.ordinal()]   = 0x8100000000000000L;
    	blackBitBoards[Piece.QUEEN.ordinal()]  = 0x800000000000000L;
    	blackBitBoards[Piece.KING.ordinal()]   = 0x1000000000000000L;
    }
    
    private long bitBoards(Color c, int index)
    {
    	if (c == Color.WHITE)
    		return whiteBitBoards[index];
    	else
    		return blackBitBoards[index];
    }
}
