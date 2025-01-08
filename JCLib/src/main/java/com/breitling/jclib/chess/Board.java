package com.breitling.jclib.chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntBinaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Board 
{
	private static Logger LOG = LoggerFactory.getLogger(Board.class);
	
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
    private static final int[][] dirOfSquares = new int [NUM_OF_SQUARES][NUM_OF_SQUARES];
   
    private static final int   NO_SQUARE = -1, NO_FILE = -1, NO_RANK = -1, NO_DIR = -1;
    
    @SuppressWarnings("unused")
	private static final int   RANK = 1, FILE = 2, DIAGONAL = 3;
    private static final int   WHITE = 1, BLACK = 0;
    
//  private static final int   NUM_OF_DIRS = 8;
    private static final int   SW = 0, S = 1, SE = 2, E = 3, NE = 4, N = 5, NW = 6, W = 7;  
    private static final int[] DIR_MOVE_DELTA = {-9, -8, -7, 1, 9, 8, 7, -1};

    private static final int   ASCII_SPACE  = 32;
    private static final int   ASCII_OFFSET = 48;
        
    private long[] whiteBitBoards = new long [6];
    private long[] blackBitBoards = new long [6];
    
    private int whoseTurnIsIt;
    private int fullMoveNumber;
    private int halfMoveClock;
    private String castling;
    private String enPassantTargetSquare;
    
    private char [] buffer;
    private int last;
    
    static 
    {
    	squareToIndex = new HashMap<>();
    	
    	for (int n = 0; n < NUM_OF_SQUARES; n++)
    		squareToIndex.put(squares[n], n);
    	
    	IntBinaryOperator getDir = (f,t) -> {
   	     	int tr = t/8;
   			int tf = t%8;
   			int fr = f/8;
   			int ff = f%8;
   			int dr = fr - tr;
   			int df = ff - tf;
   			
   			if (dr ==0 && df == 0)
   				return NO_DIR;
   			
   			if (dr == 0)
   			{
   				if (df > 0)
   					return W;
   				else
   					return E;
   			}
   			if (df == 0)
   			{
   				if (dr > 0)
   					return S;
   				else
   					return N;
   			}
   			if (dr > 0)
   			{
   				if (df > 0)
   					return SW;
   				else
   					return SE;
   			}
   			else
   			{
   				if (df > 0)
   					return NW;
   				else
   					return NE;
   			}
    	};
    	
       	for (int from = 0; from < NUM_OF_SQUARES; from++)
       	{
       	   	for (int to = 0; to < NUM_OF_SQUARES; to++)
       	   	{
       	   		dirOfSquares[from][to] = getDir.applyAsInt(from, to);
       	   	}
       	}
    }
    
//  CONSTRUCTORS
    
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
    	
    	if (parts[1].equals("w"))
    		board.whoseTurnIsIt = WHITE;
    	else
    		board.whoseTurnIsIt = BLACK;
    	
    	board.castling = parts[3];
    	board.halfMoveClock = Integer.valueOf(parts[4]);
    	board.fullMoveNumber = Integer.valueOf(parts[5]);
    	
    	return board;
    }
    
//  PUBLIC METHODS
    
    public void move(String notation)
    {
    	if (notation.matches("[0-9]+\\."))
    	{
    		String [] parts = notation.split("\\.");
    		notation = parts[1];
    	}
    			
    	this.buffer = notation.toCharArray();
    	this.last = buffer.length-1;
    	
    	while (buffer[last] == '+' || buffer[last] == '#')
    		last--;
    	
    	char piece = buffer[0];
    	
    //  SPECIAL CASES FOR UCI CASTLING
    	if (notation.equals("e1g1") || notation.equals("e8g8"))
    		castle(whoseTurnIsIt, "O-O");
    	else
        if (notation.equals("e1c1") || notation.equals("e8c8"))
        	castle(whoseTurnIsIt, "O-O-O");
        else
    	if ("RNBQK".indexOf(piece) >= 0)
    		pieceMove(whoseTurnIsIt, piece);
    	else
    	if ("abcedfgh".indexOf(piece) >= 0)
    		pawnMovePlus(whoseTurnIsIt);
    	else
    	if (piece == 'O' || piece == '0')
    		castle(whoseTurnIsIt);
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
    			blackBitBoards[findPieceAt(BLACK, to).ordinal()] ^= BitBoard.OfSquare(to);
    		}
    		else
    		{
    			blackBitBoards[p.ordinal()] ^= BitBoard.OfSquare(from);
    			blackBitBoards[p.ordinal()] |= BitBoard.OfSquare(to);
    			whiteBitBoards[findPieceAt(WHITE, to).ordinal()] ^= BitBoard.OfSquare(to);
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
    	var b = BitBoard.OfSquare(at);
    	
   		if (c == Color.WHITE)
   		{
   			whiteBitBoards[0] ^= b;
   			whiteBitBoards[p.ordinal()] |= b;
   		}
   		else
   		{
   			blackBitBoards[0] ^= b;
   			blackBitBoards[p.ordinal()] |= b;
   		}
    }
    
    public long pieceBitBoard(Color c, Piece p)
    {
    	return bitBoards(c, p.ordinal());
    }
    
    public long pieceBitBoard(int c, Piece p)
    {
    	return bitBoards(c == WHITE ? Color.WHITE : Color.BLACK, p.ordinal());
    }
    
    public Boolean pieceAt(Color c, Piece p, int at)
    {
    	return (bitBoards(c, p.ordinal()) & BitBoard.OfSquare(at)) != 0;
    }
    
    public void removePawnAt(Color c, int at)
    {
    	var b = BitBoard.OfSquare(at);
    	
   		if (c == Color.WHITE)
   			whiteBitBoards[0] ^= b;
   		else
   			blackBitBoards[0] ^= b;
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
    
    private void adjustCastling(int color)
    {
		if (color == WHITE)
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
    }
    
    private long bitBoards(Color c, int index)
    {
    	if (c == Color.WHITE)
    		return whiteBitBoards[index];
    	else
    		return blackBitBoards[index];
    }
    
    private int calcSquareIndex(char rank, char file)
    {
    	return (int)((rank - '1') * 8 + (file - 'a'));
    }

    @SuppressWarnings("unused")
	private int findSquareIndex(String square)
    {
    	return squareToIndex.get(square);
    }
    
    private Piece findPieceAt(int c, int to)
    {
    	if (c == WHITE)
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
    
    private List<Integer> findStartingSquares(long bitboard, int color, int piece)
    {
    	List<Integer> list = new ArrayList<>();
    		
    	for (int i = 0; i < NUM_OF_SQUARES; i++)
    	{
    		if ((bitboard & BitBoard.OfSquare(i)) != 0)
    		{
    			long sbb = (color == WHITE) ? whiteBitBoards[piece] : blackBitBoards[piece];
    			
    			if ((sbb & BitBoard.OfSquare(i)) != 0)
    			{
    				list.add(i);
    			}
    		}
    	}
    	
    	return list;
    }
    
    private List<Integer> findAllAttacks(long bitboard, int color)
    {
    	List<Integer> list = new ArrayList<>();
    		
    	for (int i = 0; i < NUM_OF_SQUARES; i++)
    	{
    		if ((bitboard & BitBoard.OfSquare(i)) != 0)
    		{
    			long sbb = 0;
    			
    			if (color == WHITE)
    				sbb = whiteBitBoards[0] | whiteBitBoards[1] | whiteBitBoards[2] | whiteBitBoards[3] | whiteBitBoards[4];
    			else
    				sbb = blackBitBoards[0] | blackBitBoards[1] | blackBitBoards[2] | blackBitBoards[3] | blackBitBoards[4];

    			if ((sbb & BitBoard.OfSquare(i)) != 0)
    			{
    				list.add(i);
    			}
    		}
    	}
    	
    	return list;
    }
    
    private Boolean isPieceAt(int color, int at)
    {
    	long b = 0;
    	
    	if (color == WHITE)
			b = whiteBitBoards[0] | whiteBitBoards[1] | whiteBitBoards[2] | whiteBitBoards[3] | whiteBitBoards[4] | whiteBitBoards[5];
		else
			b = blackBitBoards[0] | blackBitBoards[1] | blackBitBoards[2] | blackBitBoards[3] | blackBitBoards[4] | blackBitBoards[5];
		
		if ((b & BitBoard.OfSquare(at)) != 0) // SQUARE HAS A PIECE ON IT
			return true;
		else
			return false;
    }
    
//  NOTE: O-O O-O-O 0-0 0-0-0   
    
    private void castle(int c, String notation)
    {
    	this.buffer = notation.toCharArray();
    	this.castle(c);
    }
    
    private void castle(int c)
    {
    	int rto = F1, rfrom = H1, kto = G1, kfrom = E1;
    	Color color = (c == WHITE) ? Color.WHITE : Color.BLACK;
    	
    	for (int n = 0; n <= last; n++)
    		if (buffer[n] == '0')
    			buffer[n] = 'O';
    			
    	if (buffer[0] == 'O' && buffer[1] == '-' && buffer[2] == 'O')
    	{
    		last -= 3;
    	}
    	if (last > 0 && buffer[3] == '-' && buffer[4] == 'O')
    	{
    		kto = C1;
    		rto = D1;
   			rfrom = A1;
   			last -= 2;
    	}
    	if (last != -1)
    	{
    		throw new RuntimeException(new StringBuilder("Bad move: ").append(buffer).toString());
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

		adjustCastling(c);
		
    	if (c == BLACK)
    		fullMoveNumber++;
    	
		halfMoveClock++;
		enPassantTargetSquare = "-";
    }
     
//  NOTE: e4 bxc4 exf8=Q e4xd5 e8=Q e2-e4 e2e4 e7e8q e7f8q e7-e8=Q e7xf8=Q b8c6
    private void pawnMovePlus(int color)
    {
    	int starting = NO_SQUARE;
    	int capture = 0;
    	
		int delta = (color == WHITE) ? -1 : 1;
		
    	Piece promote = null;

    	if ("QRBN".indexOf(buffer[last]) >= 0)
    		promote = Piece.valueOfPiece(buffer[last--]);
    	
    	if (buffer[last] == '=')
    		last--;
    	
    	int target = calcSquareIndex(buffer[last], buffer[last-1]);
    	last -= 2;
    	
		char r = ASCII_SPACE;
		char f = ASCII_SPACE;
    	
    	if (last > 0)
    	{
	    	if (buffer[last] == 'x')
				capture = last--;
	    	
	    	if (buffer[last] == '-')
	    		last--;
	    	
			if ("12345678".indexOf(buffer[last]) >= 0)
				r = buffer[last--];
			    	
			if ("abcdefgh".indexOf(buffer[last]) >= 0)
	    		f = buffer[last];
			
	    	if (r != ASCII_SPACE && f != ASCII_SPACE)
	    		starting = calcSquareIndex(r, f);
    	}
    	
    	if (starting == NO_SQUARE)
    	{
    		if (f != ASCII_SPACE)
    		{
    			r = (char)('1' + target / 8);
    			starting = calcSquareIndex((char)(r+delta), f);
    		}
    		if (r == ASCII_SPACE)
    		{
    			r = buffer[1];	//?
    			f = buffer[0];
    			starting = calcSquareIndex((char)(r+delta), f);
    		}

    		if (isPieceAt(color, starting) == false)
    			starting = calcSquareIndex((char)(r + (2 * delta)), f);
    	}
    	
    	Color c = (color == WHITE) ? Color.WHITE : Color.BLACK;
    	Piece p = findPieceAt(color, starting);
    	
    	if (capture == 0)
    	{
    		movePiece(c, p, starting, target);
    	}
    	else
    	{
    		if (p == Piece.PAWN && isPieceAt(WHITE-color, target) == false && 
    			pieceAt(c.getOppositeColor(), Piece.PAWN, target+(delta*8)))
    		{
    			movePiece(c, p, starting, target);
    			removePawnAt(c.getOppositeColor(), target+(delta*8));
    		}
    		else
    		{
    			capturePiece(c, p, starting, target);
    		}
    	}
    	
    	if (promote != null)
    		placePiece(c, promote, target);
    	
    	if (color == BLACK)
    		fullMoveNumber++;
    	
		halfMoveClock = 0;
		enPassantTargetSquare = "-";
    }

//  NOTE: Bg5 Rxd5 R1a4 Rfe1 Qh4e1 R1xa5 Rexa5 Qh4xe1 Qh4-e1 Nbd2
    private void pieceMove(int color, char piece)
    {
    	int starting = NO_SQUARE;
    	int capture = 0;
    	
    	Piece p = Piece.valueOfPiece(piece);
    	int target = calcSquareIndex(buffer[last], buffer[last-1]);
    	last -= 2;
    	
    	if (buffer[last] == 'x')
			capture = last--;
    	
    	if (buffer[last] == '-')
    	{
    		starting = calcSquareIndex(buffer[last], buffer[last-1]);
    		last -= 2;
    	}

    	long bitboard = BitBoard.getPieceBitBoard(p, target);
    	List<Integer> list = findStartingSquares(bitboard, color, p.ordinal());
    	
    	if (list.size() == 1)
    		starting = list.get(0);

    	if (starting == NO_SQUARE)
    	{
    		int rank = NO_RANK;
    		int file = NO_FILE;
	        	
    		if ("12345678".indexOf(buffer[last]) >= 0)
    			rank = (buffer[last--] - '1') * 8;
			    	
    		if ("abcdefgh".indexOf(buffer[last]) >= 0)
	    		file = buffer[last] - 'a';
		    
    		if (rank + file > -2)
    		{
	    		for (int i : list)
	    		{
	    			if (rank >= 0 && rank == ((i/8) * 8) && isValidMoveNoPins(color, p, i, target, NO_SQUARE, NO_SQUARE))
	    			{
	    				starting = i;	    				
	    				break;
	    			}
	    			if (file >= 0 && file == (i%8) && isValidMoveNoPins(color, p, i, target, NO_SQUARE, NO_SQUARE))
	    			{
	    				starting = i;
	    				break;
		    		}
	    		}
    		}
    		
    		for (int i : list)
	    	{
	    		if (starting != NO_SQUARE)
	    			break;
	    			
	    		if (isValidMove(color, p, i, target, NO_SQUARE, NO_SQUARE))
	    			starting = i;
	    	}
        //  HACK: DOES THIS WORK?
    		if (starting == NO_SQUARE)
    		{
    			starting = list.get(list.size()-1);
    			LOG.warn("using last square as the move!");
    		}
    	}

    	assert(starting != NO_SQUARE);
    	
    	if (capture == 0)
    		movePiece(color == WHITE ? Color.WHITE : Color.BLACK, p, starting, target);
    	else
    		capturePiece(color == WHITE ? Color.WHITE : Color.BLACK, p, starting, target);
    	
    	if (p == Piece.KING)
    		adjustCastling(color);
    	
    	if (color == BLACK)
    		fullMoveNumber++;
    	
    	if (capture == 0)
    		halfMoveClock++;
    	else
    		halfMoveClock = 0;
    		
		enPassantTargetSquare = "-";
    }
    
    private boolean isValidMove(int c, Piece p, int from, int to, int skip, int blocks)
    {
    	boolean rc = false;

    	switch (p)
    	{
    	case Piece.ROOK:
    		 rc |= isValidMove(c, from, to, skip, blocks);
    	     rc &= !isPinned(c, from, to);
    		 break;
    		 
    	case Piece.QUEEN:
    		 rc |= isValidMove(c, from, to, skip, blocks);
    	     rc &= !isPinned(c, from, to);
    		 break;
    		 
    	case Piece.BISHOP:
    		 rc |= isValidMove(c, from, to, skip, blocks);
    	     rc &= !isPinned(c, from, to);
    		 break;
    		 
    	case Piece.KNIGHT:
    		 rc = !isPinned(c, from, to);
    		 break;
    		 
    	default:
    		 break;
    	}
    	
    	return rc;
    }
    
    private boolean isValidMoveNoPins(int c, Piece p, int from, int to, int skip, int blocks)
    {
    	boolean rc = true;

    	switch (p)
    	{
    	case Piece.ROOK:
    		 rc = isValidMove(c, from, to, skip, blocks);
    		 break;
    		 
    	case Piece.QUEEN:
    		 rc = isValidMove(c, from, to, skip, blocks);
    		 break;
    		 
    	case Piece.BISHOP:
    		 rc = isValidMove(c, from, to, skip, blocks);
    		 break;
    		 
    	case Piece.KNIGHT:
    		 break;
    		 
    	default:
    		 break;
    	}
    	
    	return rc;
    }
    
    private boolean isValidMove(int color, int from, int to, int skip, int blocks)
    {
    	boolean rc = true;
		
    	int delta = DIR_MOVE_DELTA[dirOfSquares[from][to]];
   		
    	for (int n = from+delta; n != to; n = n + delta)
    	{
    		if (n == skip)
    			continue;
    		
    		if (isPieceAt(color, n) || isPieceAt(WHITE-color, n) || n == blocks) 
    		{
    			rc = false;
    			break;
    		}
    	}
    	
    	return rc;
    }
    
    private boolean isPinned(int color, int from, int to)
    {
    	boolean rc = false;
    	
		int kingidx = BitBoard.getFirstSquareIndex(pieceBitBoard(color, Piece.KING));
		var attacks = findAllAttacks(BitBoard.getPieceBitBoard(Piece.QUEEN, kingidx), WHITE-color);
		
		if (attacks.size() > 0)
		{
			int oppositecolor = WHITE-color;
			
			for (int i : attacks)
			{
				if (i != to)
				{
					Piece piece = findPieceAt(oppositecolor, i);
					
					if (piece == Piece.QUEEN || piece == Piece.ROOK || piece == Piece.BISHOP)
					{
						long bb = BitBoard.getPieceBitBoard(piece, i);
						
						if ((bb & BitBoard.OfSquare(from)) > 0 && isValidDirection(piece, i, kingidx))
							rc = isValidMove(oppositecolor, i, kingidx, from, to);
						
						if (rc)
							break;
					}
				}
			}
		}
		
		return rc;
    }
    
    private boolean isValidDirection(Piece p, int from, int to)
    {
    	boolean rc = true;
    	
    	int dir = dirOfSquares[from][to];
    	
    	switch(p)
    	{
    	case QUEEN:
    		 break;
    		 
    	case ROOK:
    		 if (dir != N && dir != S && dir != E && dir != W)
    			 rc = false;
    		 break;
    		 
    	case BISHOP:
    		if (dir != NE && dir != SW && dir != SE && dir != NW)
				 rc = false;
	   		 break;
	   		 
	   	default:
	   		 rc = false;
	   		 break;
    	}
    	
    	return rc;
    }
}
