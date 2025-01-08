package com.breitling.jclib.dao;

import java.util.List;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.common.mapper.JacksonMapperModule;
import org.dizitart.no2.repository.ObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.breitling.jclib.chess.Board;
import com.breitling.jclib.model.DataSource;
import com.breitling.jclib.model.Game;
import com.breitling.jclib.model.GamePosition;
import com.breitling.jclib.model.Position;
import com.breitling.jclib.pgn.PGNReader;
import com.breitling.jclib.util.Factory;

@Component
public class GameDAOImpl extends CrudNitriteRepository<Game> implements GameDAO
{
	private static Logger LOG = LoggerFactory.getLogger(GameDAOImpl.class);
	
	public GameDAOImpl(String name) {
		super(name);
	}
	
//  CONTRACT METHODS
	
	public int importGames(DataSource d)
	{
		int n = 0;
		int p = 0;
		
		try
		{
			LOG.debug("Source at {}", d.getPath());
			
			long t = System.currentTimeMillis();
			
			PGNReader reader = PGNReader.createReader(d);
			List<Game> games = reader.getGames();
			
			LOG.debug("Found {} games", games.size());
			
			setStoreModule(d.getName());
			
			try (Nitrite db = Nitrite.builder().loadModule(getStoreModule()).loadModule(new JacksonMapperModule()).openOrCreate("user", "sa"))
			{
				ObjectRepository<Game> grepo = db.getRepository(Game.class);
				ObjectRepository<Position> prepo = db.getRepository(Position.class);
				ObjectRepository<GamePosition> gprepo = db.getRepository(GamePosition.class);
				
				for (Game g : games)
				{
					g.setId(Factory.DAO.generateId());
					grepo.insert(g);
					
					var r = PGNReader.createReader(g.getMoves());
					var moveList = r.getMoveList();
					var fens = r.getFENsFromMoves(Board.create(), moveList);
					
					for (String f : fens)
					{
						Position pos = Factory.Model.Position.create(f);
						
						pos.setId(Factory.DAO.generateId());
						prepo.insert(pos);
						gprepo.insert(GamePosition.create(g.getId(), pos.getId()));
						
						p++;
					}
					
					n++;
				}
				
				LOG.debug("Processing time: {}ms", (System.currentTimeMillis() - t));
				LOG.debug("Games found: {} (positions={})", n, p);
			}
			catch (Exception e)
			{
				LOG.error(e.getMessage());
			}
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
		
		return n;
	}
}
