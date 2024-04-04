package com.breitling.jclib.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.breitling.jclib.chess.Board;
import com.breitling.jclib.dao.GameDAO;
import com.breitling.jclib.dao.GameDAOImpl;
import com.breitling.jclib.model.Game;
import com.breitling.jclib.model.Source;
import com.breitling.jclib.pgn.PGNReader;
import com.breitling.jclib.pgn.PGNReaderImpl.Move;
import com.breitling.jclib.util.Factory;

@Service
public class GameServiceImpl implements GameService 
{

	@Override
	public void saveGamesFromSource(String path)
	{
		saveGamesFromSource(getNameFromPath(path), path);
	}

	@Override
	public void saveGamesFromSource(String name, String path) 
	{
		try
		{
			var source = Factory.Model.Source.create(name, path);
			var reader = PGNReader.createReader(source);		
			var games = reader.getGames();
			
			for (Game g : games)
			{
				reader = PGNReader.createReader(g.getMoves());
				var moves = reader.getMoveList();
				var fens = reader.getFENsFromMoves(Board.create(), moves);
				
				persistToDB(source, g);
				persistToDB(source, moves, fens);
			}
		}
		catch (Exception e)
		{
			
		}
	}

	@Override
	public void saveGameFromMoves(String moves) 
	{
		try
		{
			var reader = PGNReader.createReader(moves);
			var moveList = reader.getMoveList();
			var fens = reader.getFENsFromMoves(Board.create(), moveList);
			
			persistToDB((Source) null, moveList, fens);
		}
		catch (Exception e)
		{
			
		}
	}
	
//  PRIVATE METHODS	
	
	private String getNameFromPath(String path)
	{
		String [] parts = path.split("/");
		return parts[parts.length-1].substring(0, parts[parts.length-1].indexOf("."));
	}
	
	private void persistToDB(Source source, Game g)
	{
		 var dao = (GameDAO) Factory.DAO.createDAO(GameDAOImpl.class, source.getName());
		 var rc = dao.persistGame(Factory.Persistence.Game.create(g));
		 
		 if (rc != 1)
		 {
		 }
		 else
		 {
		 }
	}
	
	private void persistToDB(Source source, List<Move> moves, List<String >fens)
	{
	}
}