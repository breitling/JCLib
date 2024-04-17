package com.breitling.jclib.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.breitling.jclib.chess.BitBoard;
import com.breitling.jclib.chess.Board;
import com.breitling.jclib.dao.GameDAO;
import com.breitling.jclib.dao.GameDAOImpl;
import com.breitling.jclib.dao.GamePositionDAO;
import com.breitling.jclib.dao.GamePositionDAOImpl;
import com.breitling.jclib.dao.PositionDAO;
import com.breitling.jclib.dao.PositionDAOImpl;
import com.breitling.jclib.dao.SourceDAO;
import com.breitling.jclib.dao.SourceDAOImpl;
import com.breitling.jclib.model.Game;
import com.breitling.jclib.model.Source;
import com.breitling.jclib.persistence.Position;
import com.breitling.jclib.pgn.PGNReader;
import com.breitling.jclib.pgn.PGNReaderImpl.Move;
import com.breitling.jclib.util.Factory;

@Service
public class GameServiceImpl implements GameService 
{
	private static Logger LOG = LoggerFactory.getLogger(GameServiceImpl.class);
	
	@Override
	public void saveGamesFromPath(String path)
	{
		saveGamesFromSource(Factory.Model.Source.create(getNameFromPath(path), path));
	}

	@Override
	public void saveGamesFromSource(Source source) 
	{
		try
		{
			if (source.getId() == 0)
				persistToDB(source);
			
			var reader = PGNReader.createReader(source);		
			var games = reader.getGames();
			var positions = 0L;
			
			for (Game g : games)
			{
				LOG.debug("-----");
				
				reader = PGNReader.createReader(g.getMoves());
				var moves = reader.getMoveList();
				var fens = reader.getFENsFromMoves(Board.create(), moves);
				
				var gid = persistToDB(source, g);
				
				LOG.debug("Game: {}", gid);
				
				positions = persistToDB(source, moves, fens, gid, positions);
			}
		}
		catch (Exception e)
		{
			LOG.error("Error saving a game: {}", e.getMessage());
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
			
			var count = persistToDB((Source) null, moveList, fens, 0, 0);
			
			LOG.debug("Added {} positions", count);
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
	}
	
//  PRIVATE METHODS	
	
	private String getNameFromPath(String path)
	{
		String [] parts = path.split("/");
		return parts[parts.length-1].substring(0, parts[parts.length-1].indexOf("."));
	}
	
	private long persistToDB(Source source)
	{
		var dao = (SourceDAO) Factory.DAO.createDAO(SourceDAOImpl.class, source.getName());
		var n = dao.persistSource(Factory.Persistence.Source.create(source));
		
		var id = n.longValue();
		
		source.setId(id);
		
		return id;
	}
	
	private long persistToDB(Source source, Game g)
	{
		 var dao = (GameDAO) Factory.DAO.createDAO(GameDAOImpl.class, source.getName());
		 var p = Factory.Persistence.Game.create(g);
		 
		 p.setSourceId(source.getId());
		 var n = dao.persistGame(p);
		 
		 return n.longValue();
	}
	
	private long persistToDB(Source source, List<Move> moves, List<String> fens, long gid, long count)
	{
		var dao = (PositionDAO) Factory.DAO.createDAO(PositionDAOImpl.class, source.getName());
		var da0 = (GamePositionDAO) Factory.DAO.createDAO(GamePositionDAOImpl.class, source.getName());
		
		List<Position> newpositions = new ArrayList<>();
		
		for (String fen : fens)
		{
			long pid = 0;
			var positions = dao.findByHash(BitBoard.generateBitBoardHash(fen));
			
			if (positions.size() > 0)
			{
				for (Position p : positions)
				{
					if (p.getFen().equals(fen))
					{
						pid = p.getId();
						LOG.debug("Found position: {}", pid);
						da0.persistRecord(gid, pid);
						break;
					}
				}
			}
			
			if (pid == 0)
				newpositions.add(Factory.Persistence.Position.create(fen));
		}
		
		long n = 0;
		
		if (newpositions.size() > 0)
		{
			n = dao.persistPositions(newpositions);
			da0.persistRecords(gid, newpositions);
			
			LOG.debug("Added {} new positions [size={}]", (n > count ? n - count : 0), newpositions.size());
		}
		
		return n;
	}
}