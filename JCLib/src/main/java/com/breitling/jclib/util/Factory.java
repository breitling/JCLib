package com.breitling.jclib.util;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.breitling.jclib.chess.BitBoard;
import com.breitling.jclib.chess.Result;

public class Factory 
{
	public static class Persistence
	{
		public static class Game
		{
			public static com.breitling.jclib.persistence.Game create()
			{
				return new com.breitling.jclib.persistence.Game();
			}
			
			public static com.breitling.jclib.persistence.Game create(String w, String b, Result r, String moves) 
			{
				var g = new com.breitling.jclib.persistence.Game();
				
				g.setWhite(w);
				g.setBlack(b);
				g.setResult(r);
				g.setDate(Date.valueOf(LocalDate.now()));
				g.setMoves(moves);
				
				return g;
			}
			
			public static RowMapper<com.breitling.jclib.persistence.Game> getRowMapper()
			{
				return new RowMapper<com.breitling.jclib.persistence.Game>() {
					@Override
					public com.breitling.jclib.persistence.Game mapRow(ResultSet rs, int rowNum) throws SQLException
					{
						var g = new com.breitling.jclib.persistence.Game();
						
						g.setId(rs.getLong(1));
						g.setSourceId(rs.getLong(2));
						g.setWhite(rs.getString(3));
						g.setWhiteELO(rs.getString(4));
						g.setBlack(rs.getString(5));
						g.setBlackELO(rs.getString(6));
						g.setEvent(rs.getString(7));
						g.setSite(rs.getString(8));
						g.setEventDate(DateUtils.stringToDate(rs.getString(9)));
						g.setTimeControl(rs.getString(10));
						g.setRound(rs.getInt(11));
						g.setDate(DateUtils.stringToDate(rs.getString(12)));
						g.setResult(Result.valueOfResult(rs.getString(13)));
						g.setECO(rs.getString(14));
						g.setFEN(rs.getString(15));
						g.setMoveCount(rs.getInt(16));
						g.setMoves(rs.getString(17));
						
						return g;
					}
				};
			}
			
			private Game() {};
		}
		
		public static class Position 
		{
			public static com.breitling.jclib.persistence.Position create(String fen)
			{
				var p = new com.breitling.jclib.persistence.Position();
				
				p.setBitBoardHash(BitBoard.generateBitBoardHash(fen));
				p.setFen(fen);
				p.setCreated(Date.valueOf(LocalDate.now()));
				
				return p;
			}
			
			private Position() {};
		}
		
		public static class Source
		{
			public static com.breitling.jclib.persistence.Source create(String path)
			{
				var s = new com.breitling.jclib.persistence.Source();
				
				s.setPath(path);
				
				return s;
			}
			
			public static RowMapper<com.breitling.jclib.persistence.Source> getRowMapper()
			{
				return new RowMapper<com.breitling.jclib.persistence.Source>() {
					@Override
					public com.breitling.jclib.persistence.Source mapRow(ResultSet rs, int rowNum) throws SQLException
					{
						var s = new com.breitling.jclib.persistence.Source();
						
						s.setId(rs.getLong(1));
						s.setName(rs.getString(2));
						s.setPath(rs.getString(3));
						
						return s;
					}
				};
			}
		}
		
		private Persistence() {};
	}
	
	public static class Model
	{
		private Model() {};
	}
	
	public static class DateUtils
	{
		private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		
		public static Date stringToDate(String d)
		{
			try
			{
				return new Date(sdf.parse(d).getTime());
			}
			catch (Exception e)
			{
				return Date.valueOf(LocalDate.now());
			}
		}
		
		public static String dateToString(Timestamp t)
		{
			return sdf.format(t);
		}
		
		private DateUtils() {};
	}
	
	private Factory() {};
}
