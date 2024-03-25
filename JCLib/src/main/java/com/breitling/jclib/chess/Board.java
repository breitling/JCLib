package com.breitling.jclib.chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public static final String [] squares = {
    "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", 
    "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", 
    "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", 
    "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", 
    "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", 
    "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", 
    "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", 
    "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", 
    };
    
    private static final Map<String,Integer> squareToIndex;
    
    private static final int NO_SQUARE = (-1);
    
    private static final int RANK = 1, FILE = 2;
    private static final int WHITE = 1, BLACK = 0;
    
    private static final int ASCII_SPACE  = 32;
    private static final int ASCII_OFFSET = 48;
    private static final int ASCII_LOWERA = 97;
    
    private long[] whiteBitBoards = new long [6];
    private long[] blackBitBoards = new long [6];
    
    private int whoseTurnIsIt;
    private int fullMoveNumber;
    private int halfMoveClock;
    private String castling;
    private String enPassantTargetSquare;
    
    static 
    {
    	squareToIndex = new HashMap<>();
    	
    	for (int n = 0; n < NUM_OF_SQUARES; n++)
    		squareToIndex.put(squares[n], n);
    }
    
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
    	var parts = fen.split(" ");
    	var ranks = parts[0].split("/");
    	
    	for (int n = 0; n < 6; n++)
    		board.whiteBitBoards[n] = board.blackBitBoards[n] = 0L;
    	
    	for (int r = 0; r < NUM_OF_ROWS; r++)
    	{
    		String rank = ranks[r];
    		int col = 0;
    		
    		for (int c = 0; c < rank.length(); c++)
    		{
    			char p = rank.charAt(c);
    			
    			if (Character.isDigit(p))
    			{
    				col += (p - ASCII_OFFSET);
    			}
    			else
    			{
    				int square = ((7 - r) * 8) + (col++);
    				
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
    
    public void move(String notation)
    {
    	if (notation.endsWith("+") || notation.endsWith("#"))
    		notation = notation.substring(0, notation.length()-1);
    	
    	char c = notation.charAt(0);
    	
    //  SPECIAL CASES FOR UCI CASTLING
    	if (notation.equals("e1g1") || notation.equals("e8g8"))
    		castle(whoseTurnIsIt, "O-O");
    	else
        if (notation.equals("e1c1") || notation.equals("e8c8"))
        	castle(whoseTurnIsIt, "O-O-O");
        else
    	if ("RNBQK".indexOf(c) >= 0)
    		pieceMove(whoseTurnIsIt, notation);
    	else
    	if ("abcedfgh".indexOf(c) >= 0)
    		pawnMove(whoseTurnIsIt, notation);
    	else
    	if (c == 'O' || c == '0')
    		castle(whoseTurnIsIt, notation);
    	else
    		throw new RuntimeException(new StringBuilder("Bad move: ").append(notation).toString());
    	
    	whoseTurnIsIt = 1 - whoseTurnIsIt;
    }
    
    public void capturePiece(Color c, Piece p, int from, int to)
    {
    	if (pieceAt(c, p, from))
    	{
    		if (c == Color.WHITE)
    		{
    			whiteBitBoards[p.ordinal()] ^= BitBoard.OfSquare(from);
    			whiteBitBoards[p.ordinal()] |= BitBoard.OfSquare(to);
    			blackBitBoards[findPieceAt(Color.BLACK, to).ordinal()] ^= BitBoard.OfSquare(to);
    		}
    		else
    		{
    			blackBitBoards[p.ordinal()] ^= BitBoard.OfSquare(from);
    			blackBitBoards[p.ordinal()] |= BitBoard.OfSquare(to);
    			whiteBitBoards[findPieceAt(Color.WHITE, to).ordinal()] ^= BitBoard.OfSquare(to);
    		}
    	}
    	else
    	{
    		throw new RuntimeException(new StringBuilder("Error: no ").append(Piece.getName(p)).append(" at ").append(squares[from]).toString());
    	}
    }
    
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
    		throw new RuntimeException(new StringBuilder("Error: no ").append(Piece.getName(p)).append(" at ").append(squares[from]).toString());
    	}
    }
    
    public void placePiece(Color c, Piece p, int at)
    {
   		if (c == Color.WHITE)
   			whiteBitBoards[p.ordinal()] |= BitBoard.OfSquare(at);    			
   		else
   			blackBitBoards[p.ordinal()] |= BitBoard.OfSquare(at);  
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
    	
    	sb.append(" ").append(whoseTurnIsIt == WHITE ? "w" : "b").append(" ").append(castling)
    	  .append(" ").append(enPassantTargetSquare).append(" ").append(halfMoveClock)
    	  .append(" ").append(fullMoveNumber);
    	
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
    	
        whoseTurnIsIt = WHITE;
        fullMoveNumber = 1;
        halfMoveClock = 0;
        castling = "KQkq";
        enPassantTargetSquare = "-";
    }
    
    private long bitBoards(Color c, int index)
    {
    	if (c == Color.WHITE)
    		return whiteBitBoards[index];
    	else
    		return blackBitBoards[index];
    }

    @SuppressWarnings("unused")
	private int findSquareIndex(String square)
    {
    	return squareToIndex.get(square);
    }
    
    private Piece findPieceAt(Color c, int to)
    {
    	if (c == Color.WHITE)
    	{
    		if ((whiteBitBoards[Piece.PAWN.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.PAWN;
    		
    		if ((whiteBitBoards[Piece.KNIGHT.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.KNIGHT;
    		
    		if ((whiteBitBoards[Piece.BISHOP.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.BISHOP;
    		
    		if ((whiteBitBoards[Piece.ROOK.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.ROOK;
    		
    		if ((whiteBitBoards[Piece.QUEEN.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.QUEEN;
    	}
    	else
    	{
    		if ((blackBitBoards[Piece.PAWN.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.PAWN;
    		
    		if ((blackBitBoards[Piece.KNIGHT.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.KNIGHT;
    		
    		if ((blackBitBoards[Piece.BISHOP.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.BISHOP;
    		
    		if ((blackBitBoards[Piece.ROOK.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.ROOK;
    		
    		if ((blackBitBoards[Piece.QUEEN.ordinal()] & BitBoard.OfSquare(to)) != 0)
    			return Piece.QUEEN;
    	}
    	
    	throw new RuntimeException(new StringBuilder("Error: no piece found at ").append(squares[to]).toString());
    }
    
    private List<String> findStartingSquares(long bitboard, int color, int index)
    {
    	List<String> list = new ArrayList<>();
    		
    	for (int i = 0; i < NUM_OF_SQUARES; i++)
    	{
    		if ((bitboard & BitBoard.OfSquare(i)) != 0)
    		{
    			long sbb = (color == WHITE) ? whiteBitBoards[index] : blackBitBoards[index];
    			
    			if ((sbb & BitBoard.OfSquare(i)) != 0)
    			{
    				list.add(squares[i]);
    			}
    		}
    	}
    	
    	return list;
    }    
    
//  NOTE: O-O O-O-O 0-0 0-0-0    
    private void castle(int c, String notation)
    {
    	int rto, rfrom, kto, kfrom = E1;
    	Color color = (c == WHITE) ? Color.WHITE : Color.BLACK;
    	
    	if (notation.equals("O-O") || notation.equals("0-0"))
    	{
    		kto = G1;
    		rto = F1;
    		rfrom = H1;
    	}
    	else
    	if (notation.equals("O-O-O") || notation.equals("0-0-0"))
    	{
    		kto = C1;
    		rto = D1;
   			rfrom = A1;
    	}
    	else
    	{
    		throw new RuntimeException(new StringBuilder("Bad move: ").append(notation).toString());
    	}
		
		if (c == BLACK)
		{
			kto += 56; 
			kfrom += 56;
			rto += 56;
			rfrom += 56;
		}
		
		movePiece(color, Piece.KING, kfrom, kto);
		movePiece(color, Piece.ROOK, rfrom, rto);

		if (c == WHITE)
		{
			if (castling.contains("kq"))
				castling = "kq";
			else
				castling = "-";
		}
		else
		{
			if (castling.contains("KQ"))
				castling = "KQ";
			else
				castling = "-";
		}
		
    	if (c == BLACK)
    		fullMoveNumber++;
    	
		halfMoveClock++;
		enPassantTargetSquare = "-";
    }
     
//  NOTE: e4 bxc4 exf8=Q e8=Q e2-e4 e2e4 e7e8q e7f8q e7-e8=Q e7xf8=Q
    private void pawnMove(int color, String notation)
    {
    	int n;
    	int target = NO_SQUARE;
    	int starting = NO_SQUARE;
    	int capture = 0;    	
		int delta = (color == WHITE) ? -1 : 1;
    	
    	char [] chars = notation.toCharArray();
    	int len = chars.length - 1;
    	
    	Piece promote = null;

    	if (Character.isLetter(chars[len]))
    	{
    		char p = chars[len];
    		
    		if (p > ASCII_LOWERA)
    			p -= ASCII_SPACE;
    		
    		promote = Piece.valueOfPiece(p);
    		
    		chars[len--] = ' ';
    		
    		if (chars[len] == '=')
    			chars[len--] = ' ';
    	}
    	
    	if ((n = notation.indexOf("x")) >= 0)
			capture = n;
    	
    	if (len > 2 && (chars[2] == '-' || chars[2] == 'x') && notation.length() >= 5)
    		starting = squareToIndex.get(new StringBuilder().append(chars[0]).append(chars[1]).toString());
    	else
    	if (len > 2 && Character.isDigit(chars[1]))
    		starting = squareToIndex.get(new StringBuilder().append(chars[0]).append(chars[1]).toString());
    	else
    	if (chars[1] == 'x' && notation.length() == 4)
    	{
    		starting = squareToIndex.get(new StringBuilder().append(chars[0]).append((char)(chars[3]+delta)).toString());
    	}
    	
    	target = squareToIndex.get(new StringBuilder().append(chars[len-1]).append(chars[len]).toString());
    	
    	Color c = (color == WHITE) ? Color.WHITE : Color.BLACK;

    	if (starting == NO_SQUARE)
    	{
    		starting = squareToIndex.get(new StringBuilder().append(chars[0]).append((char)(chars[1]+delta)).toString());
    		
    		try
    		{
    			findPieceAt(c, starting);
    		}
    		catch (Exception e)
    		{
    			starting = squareToIndex.get(new StringBuilder().append(chars[0]).append((char)(chars[1]+(2 * delta))).toString());
    		}
    	}
    	
    	Piece p = findPieceAt(c, starting);
    	
    	if (capture == 0)
    		movePiece(c, p, starting, target);
    	else
    		capturePiece(c, p, starting, target);
    	
    	if (promote != null)
    		placePiece(c, promote, target);
    	
    	if (color == BLACK)
    		fullMoveNumber++;
    	
		halfMoveClock = 0;
		enPassantTargetSquare = "-";
    }

//  NOTE: Bg5 Rxd5 R1a4 Rfe1 Qh4e1 R1xa5 Rexa5 Qh4xe1 Qh4-e1
    private void pieceMove(int color, String notation)
    {
    	int n;
    	int rank = -1;
    	int file = -1;
    	int target = NO_SQUARE;
    	int starting = NO_SQUARE;
    	int capture = 0;
    	
    	int len = notation.length();
    	
    	Piece p = Piece.valueOfPiece(notation.charAt(0));

    	if ((n = notation.indexOf("x")) >= 0)
			capture = n;
    	
    	target = squareToIndex.get(notation.substring(notation.length()-2));
    	
    	if (notation.contains("-") || (capture > 0 && len == 6))
    		starting = squareToIndex.get(notation.substring(1,3));
    	
    	if (starting == NO_SQUARE)
    	{
	    	long bitboard = BitBoard.getPieceBitBoard(p, target);

	    	if ("12345678".indexOf(notation.charAt(2)) >= 0)
	    		rank = notation.charAt(2);
	    	
	    	if ("abcdefgh".indexOf(notation.charAt(1)) >= 0)
	    		file = notation.charAt(1);
	    	
	    	List<String> list = findStartingSquares(bitboard, color, p.ordinal());
	    	
	    	if (list.size() == 1)
	    	{
	    		starting = squareToIndex.get(list.get(0));	    		
	    	}
	    	else
	    	{
	    		for (String s : list)
	    		{
	    			if (rank >= 0 && s.charAt(1) == rank)
	    			{
	    				starting = squareToIndex.get(s);
	    				if (isValidMove(color, p, starting, target, RANK))
	    					break;
	    			}
	    			if (file >= 0 && s.charAt(0) == file)
	    			{
	    				starting = squareToIndex.get(s);
	    				if (isValidMove(color, p, starting, target, FILE))
	    					break;
	    			}
	    		}
	    	}
    	}

    	assert(starting != NO_SQUARE);
    	
    	if (capture == 0)
    		movePiece(color == WHITE ? Color.WHITE : Color.BLACK, p, starting, target);
    	else
    		capturePiece(color == WHITE ? Color.WHITE : Color.BLACK, p, starting, target);
    	
    	if (color == BLACK)
    		fullMoveNumber++;
    	
    	if (capture == 0)
    		halfMoveClock++;
    	else
    		halfMoveClock = 0;
    		
		enPassantTargetSquare = "-";
    }
    
    private boolean isValidMove(int color, Piece p, int from, int to, int rankorfile)
    {
    	boolean rc = true;
    	
    	if (p == Piece.ROOK)
    	{
    		int delta = 1;
    		
    		if (rankorfile == FILE)
    			delta = 8;
    			
    		long b = 0;
    		
    		for (int n = from+1; n < to; n = n + delta)
    		{
    			if (color == WHITE)
    				b = whiteBitBoards[0] | whiteBitBoards[1] | whiteBitBoards[2] | whiteBitBoards[4] | whiteBitBoards[5];
    			else
    				b = blackBitBoards[0] | blackBitBoards[1] | blackBitBoards[2] | blackBitBoards[4] | blackBitBoards[5];
    			
    			if ((b & BitBoard.OfSquare(n)) != 0) // SQUARE HAS A PIECE ON IT
    			{
    				rc = false;
    				break;
    			}
    		}
    	}
    	
    	return rc;
    }
}
