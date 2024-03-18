package com.breitling.jclib.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.breitling.jclib.chess.Result;
import com.breitling.jclib.model.BitBoard;

public class Factory 
{
	public static class Persistence
	{
		public static class Game
		{
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
		
		private Persistence() {};
	}
	
	public static class Model
	{
		private Model() {};
	}
	
	public static class DateUtils
	{
		private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
		
		public static Timestamp stringToGameDate(String d)
		{
			try
			{
				return new Timestamp(sdf.parse(d).getTime());
			}
			catch (Exception e)
			{
				return Timestamp.valueOf(LocalDateTime.now());
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
