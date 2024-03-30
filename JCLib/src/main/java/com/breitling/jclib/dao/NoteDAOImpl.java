package com.breitling.jclib.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.breitling.jclib.persistence.Note;
import com.breitling.jclib.persistence.Position;
import com.breitling.jclib.util.Factory;

public class NoteDAOImpl extends GenericDAO implements NoteDAO
{
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(NoteDAOImpl.class);

	@Override
	public List<Note> findByPositionId(long posId)
	{
		List<Note> list = new ArrayList<>();
		
		try
		{
			list = getJdbcTemplate().query(new StringBuilder().append("SELECT id,pos_id,note ")
					.append("FROM NOTES WHERE pos_id=").append(posId).toString(),
				    Factory.Persistence.Note.getRowMapper());
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage());
		}
			
		return list;
	}

	@Override
	public int addNote(long posId, String note)
	{
		SimpleJdbcInsert s = new SimpleJdbcInsert(getDataSource()).withTableName("NOTES").usingGeneratedKeyColumns("ID");
		Map<String,Object> params = new HashMap<>();
		params.put("POS_ID", posId);
		params.put("NOTE", note);
		
		return s.execute(params);
	}
}
