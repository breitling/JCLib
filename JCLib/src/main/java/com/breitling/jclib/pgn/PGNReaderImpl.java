package com.breitling.jclib.pgn;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.breitling.jclib.chess.Board;
import com.breitling.jclib.chess.Color;
import com.breitling.jclib.chess.Result;
import com.breitling.jclib.persistence.Game;
import com.breitling.jclib.persistence.Source;
import com.breitling.jclib.util.Factory;

@Service
public class PGNReaderImpl implements PGNReader
{
	private static Logger LOG = LoggerFactory.getLogger(PGNReaderImpl.class);
    
    private static final String TOKEN_EMPTY   = "";
//  private static final String TOKEN_MATE    = "#";
//  private static final String TOKEN_DOT     = ".";    
    private static final String TOKEN_SPACE   = " ";
    private static final String TOKEN_COMMENT = "{";
    private static final String TOKEN_VARIATION = "(";

    private int moveCount;
    
    private int pos;
    private String [] tokens;
    private Path sourcePath;
    
    private Stack<String> peekTokens;
    
    public PGNReaderImpl()
    {
    	this.pos = 0;
    	this.moveCount = 0;
    	this.tokens = new String [0];
    	this.sourcePath = null;
    	
    	this.peekTokens = new Stack<String>();
    }
    
    public PGNReaderImpl(String moves)
    {
    	this();

        tokens = moves.split(" ");
    }
    
    public PGNReaderImpl(Source source) throws PGNException
    {
    	this();
    	
    	try
    	{
    		this.sourcePath = FileSystems.getDefault().getPath(source.getPath());
    	}
    	catch (InvalidPathException e)
    	{
    		throw new PGNException(new StringBuilder("Bad source: ").append(e.getMessage()).toString());
    	}
    }
    
//  CONTRACT METHODS
    
    public List<Move> getMoveList()
    {
    	try
    	{
    		return parseMoves();
    	}
    	catch (PGNException pgne)
    	{
    		return new ArrayList<Move>();
    	}
    }
    
    public List<String> getFENsFromMoves()
    {
    	return getFENsFromMoves(Board.create());
    }
    
    public List<String> getFENsFromMoves(Board b)
    {
    	var board = b;
    	
        List<String> fens = new ArrayList<>();
        
        try
        {
            var list = parseMoves();
            
            for (Move m : list)
            {
            	if (m.getNumber() > 0)
            	{
            		board.move(m.whitemove);
            		fens.add(board.toFEN());
            		
            		if (m.getBlackmove().length() > 0)
            		{
            			board.move(m.blackmove);
            			fens.add(board.toFEN());
            		}
            	}
            }
            
            return fens;
        }
        catch (PGNException pgne)
        {
        	LOG.error("Error generating FENs: {}", pgne.getMessage());
            return new ArrayList<String>();
        }
    }
    
    public List<Game> getGames()
    {
    	List<Game> games = new ArrayList<>();
    	
    	if (this.sourcePath != null)
    	{
    		try
    		{
    			List<String> lines = Files.readAllLines(this.sourcePath);
    			Game game;
    			
    			this.pos = 0;
    			
	    		do
	    		{
	    			game = parseGame(lines);
	    			
	    			if (game != null)
	    				games.add(game);
	    		}
	    		while (game != null);
    		}
    		catch (Exception e)
    		{
    			LOG.error("Error reading games from source: {}", e.getMessage());
    		}
    	}
    	
    	return games;
    }
    
//  PRIVATE METHODS
    
    private List<Move> parseMoves() throws PGNException
    {
        List<Move> list = new ArrayList<>();
        
        if (this.tokens.length > 0)
        {
            var t = TOKEN_EMPTY;

            do
	        {
	            Move m = parseMoveList();
	            
	            this.moveCount++;
	            
	            list.add(m);
	        }
	        while((t = getNextToken()).equals(" "));
	        
	        list.add(new Move(0, t));	// SCORE IS A SPECIAL MOVE
        }
	        
        return list;
    }
    
    private Game parseGame(List<String> lines)
    {
    	Game g = null;
    	
    	if (pos < lines.size())
    	{
	    	g = Factory.Persistence.Game.create();
	    	
	    	while (lines.get(pos).matches("^\\[.*]$"))
	    		parseHeader(g, lines.get(pos++));
	    	
	    	pos++;
	    	
	    	StringBuilder sb = new StringBuilder();
	    	
	    	var l = lines.get(pos++);
	    	
	    	while (l.length() > 1)
	    	{
	    		sb.append(l).append(" ");
	    		l = lines.get(pos++);
	    	}
	    	
	    	g.setMoves(sb.toString());
    	}
    	
    	return g;
    }
    
    private void parseHeader(Game g, String line)
    {
    	String [] tokens = line.split("\"");
    	
    	var tag = tokens[0].substring(1).trim();
    	var value = tokens[1].substring(0, tokens[1].length());
    	
    	try
    	{
	    	switch(tag)
	    	{
	    	case "Event":
	    		 g.setEvent(value);
	    		 break;
	    		 
	    	case "Site":
	    		 g.setSite(value);
	    		 break;
	    		 
	    	case "Round":
	    		 var parts = value.split("[.,]");
	    		 if (parts[0].matches("\\d"))
	    			 g.setRound(Integer.valueOf(parts[0]));
	    		 else
	    			 g.setRound(0);
	    		 break;
	    	
	    	case "Result":
	    		 g.setResult(Result.valueOfResult(value));
	    		 break;
	    		 
	    	case "EventDate":
	    		 g.setEventDate(Factory.DateUtils.stringToDate(value));
	    		 break;
	    		 
	    	case "Date":
	    		 g.setDate(Factory.DateUtils.stringToDate(value));
	    		 break;
	    		 
	    	case "White":
	    		 g.setWhite(value);
	    		 break;
	    		 
	    	case "WhiteElo":
	    		 g.setWhiteELO(value);
	    		 break;
	    	
	    	case "Black":
	    		 g.setBlack(value);
	    		 break;
	    		 
	    	case "BlackElo":
	    		 g.setBlackELO(value);
	    		 break;
	    		 
	    	case "ECO":
	    		 g.setEco(value);
	    		 break;
	    		 
	    	case "FEN":
	    		 g.setFEN(value);
	    		 break;
	    		 
	    	default:
	    		 LOG.error("Unknown tag '{}' with value {}", tag, value);
	    		 break;
	    	}
    	}
    	catch (Exception e)
    	{
    		LOG.error("Error parsing tags: bad value({}) for {} at line {}", value, tag, pos);
    	}
    }
    
//  PARSER METHODS
    
//  NOTE:
//  WHITE MOVE IS REQUIRED
//  BLACK MOVE IS OPTIONAL
    
    private Move parseMoveList() throws PGNException
    {
        int movenumber = parseMoveNumber();
        String whitemove = parseMove(Color.WHITE);
        String blackmove = parseMove(Color.BLACK);
        
        if (peekToken().matches("[0-9]+\\."))   // ANOTHER MOVE?
        	pushToken(TOKEN_SPACE);				// YES
        
        return new Move(movenumber, whitemove, blackmove);
    }
    
    private Integer parseMoveNumber() throws PGNException 
    {
    	var t = getNextToken();
        var n = Integer.valueOf(t.substring(0, t.length()-1));
        
        return n;
    }
    
    private String parseMove(Color color) throws PGNException
    {
        var m = getNextToken();
        
        if (m.matches("([NBRQK].*)|([abcedfgh].*)|(O-O.*)")) // A PIECE MOVE OR PAWN MOVE OR CASTLE?
        {
            parseComment(color); 	// OPTIONAL COMMENT
            return m;
        }
        else
        {
            pushToken(m);     		// NOT A MOVE SO PUSH TOKEN, MUST BE RESULT
            return TOKEN_EMPTY;
        }
    }
    
    private void parseComment(Color color) throws PGNException
    {
    	var t = getNextToken();
    	
    	if (t.startsWith(TOKEN_COMMENT) || t.startsWith(TOKEN_VARIATION))
    	{
    		do
    		{
    			t = getNextToken();				// <numer>... 
    		}
    		while(!(t.endsWith("}") || t.endsWith(")")));
        
	        if (color == Color.WHITE)
	        {
	            t = getNextToken();            // FOR WHITE COMMENTS EAT BLACK MOVE NUMBER...
	            
	            if (!t.matches("[0-9]+\\.\\.\\."))
	            	pushToken(t);
	        }
    	}
    	else
    	{
    		pushToken(t);
    	}
    }
    
//  TOKENIZER METHODS
    
    private String getNextToken() throws PGNException
    {
        String token;
        
        if (!peekTokens.empty())
            token = popToken();
        else
        if (pos < tokens.length)
            token = tokens[pos++];
        else
            throw new PGNException("EOF on game!");
        
        return token;
    }
    
    private String peekToken() throws PGNException
    {
        String token;
        
        if (!peekTokens.empty())
        {
            token = peekTokens.peek();
        }
        else
        if (pos < tokens.length)
        {
            token = getNextToken();
            pushToken(token);
        }
        else
        {
            throw new PGNException("EOF on game!");
        }
        
        return token;
    }
    
    private String popToken()
    {
        return peekTokens.pop();
    }
    
    private void pushToken(String token)
    {
        peekTokens.push(token);
    }
    
//  INTERNAL CLASS FOR MOVES
    
    public static class Move
    {
        private int number;
        private String whitemove;
        private String blackmove;
        private String score;
        
        public Move(int n, String w, String b)
        {
            this.number = n;
            this.whitemove = w;
            this.blackmove = b;
            this.score = "";
        }
        
        public Move(int n, String score)
        {
            this.number = n;
            this.score = score;
            this.whitemove = this.blackmove = "";
        }

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public String getWhitemove() {
			return whitemove;
		}

		public void setWhitemove(String whitemove) {
			this.whitemove = whitemove;
		}

		public String getBlackmove() {
			return blackmove;
		}

		public void setBlackmove(String blackmove) {
			this.blackmove = blackmove;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}
    }
}
