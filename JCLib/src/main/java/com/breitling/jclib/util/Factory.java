package com.breitling.jclib.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
