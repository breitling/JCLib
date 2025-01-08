package com.breitling.jclib.model;

import org.dizitart.no2.index.IndexType;
import org.dizitart.no2.repository.annotations.Entity;
import org.dizitart.no2.repository.annotations.Id;
import org.dizitart.no2.repository.annotations.Index;

import com.breitling.jclib.util.Factory;

@Entity(value = "positions", indices = {@Index (fields = "gameId", type = IndexType.NON_UNIQUE)})
public class GamePosition extends BaseModel
{
	@Id
	private long id;
	private long gameId;
	private long positionId;
	
//  FACTORIES
	
	public static GamePosition create(long gid, long pid)
	{
		var g = new GamePosition();
		
		g.setId(Factory.DAO.generateId());
		g.setGameId(gid);
		g.setPositionId(pid);
		
		return g;
	}
	
//  GETTERS AND SETTERS
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(Long id) {
		this.gameId = id;
	}

	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long id) {
		this.positionId = id;
	}
}
