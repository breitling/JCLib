package com.breitling.jclib.pgn;

import java.util.List;

import com.breitling.jclib.chess.Board;
import com.breitling.jclib.persistence.Game;
import com.breitling.jclib.persistence.Source;
import com.breitling.jclib.pgn.PGNReaderImpl.Move;

public interface PGNReader 
{
	public List<String> getFENsFromMoves();
	
	public List<String> getFENsFromMoves(Board b);
	
	public List<Game> getGames();
	
	public List<Move> getMoveList();
	
//  FACTORIES
	
	public static PGNReader createReader()
	{
		return new PGNReaderImpl();
	}
	
	public static PGNReader createReader(String moves)
	{
		return new PGNReaderImpl(moves);
	}
	
	public static PGNReader createReader(Source source) throws PGNException
	{
		return new PGNReaderImpl(source);
	}
}
