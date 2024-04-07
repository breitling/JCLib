package com.breitling.jclib.pgn;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.breitling.jclib.chess.Result;
import com.breitling.jclib.util.Factory;

public class PGNReaderTest 
{
	@Test
	public void testGetFENsFromMoves_GoodMoves_List()
	{
		var reader = PGNReader.createReader("1. e4 e5 2. Bc4 Nc6 3. Qh5 a5 4. Qxf7# 1-0");
		
		var fens = reader.getFENsFromMoves();
		
		assertNotNull(fens);
		assertEquals(7, fens.size());
	}
	
	@Test
	public void testGetFENsFromMoves_GoodMoves2_List()
	{
		var reader = PGNReader.createReader("1. e4 e5 2. Nf3 Nc6 3. Bb5 {This opening is called the Ruy Lopez.} 3... a6 "
				+ "4. Ba4 Nf6 5. O-O Be7 6. Re1 b5 7. Bb3 d6 8. c3 O-O 9. h3 Nb8 10. d4 Nbd7 "
				+ "11. c4 c6 {Comment on Black move} 12. cxb5 axb5 13. Nc3 Bb7 14. Bg5 b4 15. Nb1 h6 16. Bh4 c5 17. dxe5 "
				+ "Nxe4 18. Bxe7 Qxe7 19. exd6 Qf6 20. Nbd2 Nxd6 21. Nc4 Nxc4 22. Bxc4 Nb6 "
				+ "23. Ne5 Rae8 24. Bxf7+ Rxf7 25. Nxf7 Rxe1+ 26. Qxe1 Kxf7 27. Qe3 Qg5 28. Qxg5 "
				+ "hxg5 29. b3 Ke6 30. a3 Kd6 31. axb4 cxb4 32. Ra5 Nd5 33. f3 Bc8 34. Kf2 Bf5 "
				+ "35. Ra7 g6 36. Ra6+ Kc5 37. Ke1 Nf4 38. g3 Nxh3 39. Kd2 Kb5 40. Rd6 Kc5 41. Ra6 "
				+ "Nf2 42. g4 Bd3 43. Re6 1/2-1/2");
		
		var fens = reader.getFENsFromMoves();
		
		assertNotNull(fens);
		assertEquals(85, fens.size());
	}
	
	@Test
	public void testGetFENsFromMoves_GoodMoves3_List()
	{
		var reader = PGNReader.createReader("1. d4 Nf6 2. c4 e6 3. Nf3 d5 4. Nc3 c6 5. e3 Nbd7 6. Bd3 Bb4 7. a3 Ba5 8. O-O O-O" +
	            " 9. Ne5 Nxe5 10. dxe5 dxc4 11. Bxc4 Nd7 12. f4 Qe7 13. b4 Bb6 14. Qb3 f6 15. Bxe6+ Kh8 16. Ne4 fxe5 17. Kh1" + 
				" exf4 18. exf4 Nf6 19. Ng5 Ne4 20. Nf7+ Rxf7 21. Bxf7 Nf2+ 22. Kg1 Nd3+ 23. Kh1 Qe2 24. Bb2 Bh3 25. Bxg7+ " + 
	            "Kxg7 26. Bd5 cxd5 27. Qxd5 Kh8 28. Qg5 Bd4 29. gxh3 Rg8 30. Rae1 Nxe1 0-1");
		
		var fens = reader.getFENsFromMoves();
		
		assertNotNull(fens);
		assertEquals(60, fens.size());
	}
	
	@Test
	public void testGetFENsFromMoves_GoodMoves4_List()
	{
		var reader = PGNReader.createReader("1. e4 c5 2. Nf3 d6 3. d4 cxd4 4. Nxd4 Nf6 5. Nc3 a6 6. Bg5 e6 7. f4 Be7 8. Qf3 Nbd7 " +
	            "9. O-O-O Qc7 10. Bd3 b5 11. Bxf6 Nxf6 12. Rhe1 Bb7 13. Kb1 Rc8 14. g4 Nd7 15. g5 Nb6 16. f5 e5 17. f6 gxf6 18. gxf6 Bf8 " +
				"19. Nd5 Nxd5 20. exd5 Kd8 21. Nc6+ Bxc6 22. dxc6 Qxc6 23. Be4 Qb6 24. Qh5 Kc7 25. Bf5 Rd8 26. Qxf7+ Kb8 27. Qe6 Qc7 " +
	            "28. Re3 Bh6 29. Rc3 Qb7 30. f7 Bg7 31. Rcd3 Bf8 32. Qxe5 dxe5 33. Rxd8+ Ka7 34. R1d7 h5 35. Rxb7+ Kxb7 36. c3 Kc7 " +
				"37. Ra8 Kd6 38. Rxa6+ Ke7 39. Re6+ Kxf7 40. Rxe5 b4 41. cxb4 Bxb4 42. h3 Kf6 43. Rb5 Bd6 44. Be4 Re8 45. Rf5+ Kg7 " +
	            "46. Bf3 Re1+ 47. Kc2 Rf1 48. Rd5 Rf2+ 49. Rd2 Rxd2+ 50. Kxd2 h4 51. Kd3 Kf6 52. Kc4 Ke7 53. Kb5 Kd7 54. a4 Kc7 " +
				"55. b4 Kb8 56. a5 Ka7 57. Kc4 Bg3 58. b5 Bf2 59. Be2 Be3 60. Kb3 Bd2 61. b6+ Kb7 62. Ka4 Kc6 63. Bb5+ Kc5 1/2-1/2");
		
		var fens = reader.getFENsFromMoves();
		
		assertNotNull(fens);
		assertEquals(126, fens.size());
	}
	
	@Test
	public void testGetMoveList_GoodPGN_List()
	{
		var reader = PGNReader.createReader("1. e4 c5 2. Nf3 d6 3. d4 cxd4 4. Nxd4 Nf6 5. Nc3 a6 6. Bg5 e6 7. f4 Be7 8. Qf3 Qc7 " + 
	            "9. O-O-O Nbd7 10. Qg3 h6 11. Bh4 Rg8 12. Be2 g5 13. fxg5 Ne5 14. g6 Nxg6 15. Rhf1 Nxh4 16. Qxh4 Rg6 17. Bd3 Ng4 " + 
				"18. Qh5 Ne5 19. Nf3 Qc5 20. Nxe5 Qxe5 21. Qxe5 dxe5 22. g3 Bd7 23. Be2 Bc6 24. Bh5 Rf6 25. a3 Rd8 26. Rxd8+ Bxd8 " + 
	            "27. Rxf6 Bxf6 28. Kd2 Bg5+ 29. Kd3 Bc1 30. Nd1 Bb5+ 31. c4 Ba4 32. Nc3 Bc6 33. Nd1 Kf8 34. h4 Ba4 35. Nc3 1/2-1/2");
		
		var moves = reader.getMoveList();
		
		assertNotNull(moves);
		assertEquals(Result.DRAW, Result.valueOfResult(moves.get(35).getScore()));
		assertEquals("Bd7", moves.get(21).getBlackmove());
		assertEquals("O-O-O", moves.get(8).getWhitemove());
	}
	
	@Test
	public void testGetMoveList_BasicReader_EmptyList()
	{
		var reader = PGNReader.createReader();
		
		var moves = reader.getMoveList();
		
		assertNotNull(moves);
		assertEquals(0, moves.size());
	}
	
	@Test
	public void testGetFENsFromMoves_BasicReader_EmptyList()
	{
		var reader = PGNReader.createReader();
		
		var fens = reader.getFENsFromMoves();
		
		assertNotNull(fens);
		assertEquals(0, fens.size());
	}
	
	@Test
	public void testGetGames_GoodPGNFile_ListOfGames() throws PGNException
	{
		var source = Factory.Model.Source.create("RJF60", "/Users/bobbr/Desktop/Chess/Games/RJF60.pgn");
		var reader = PGNReader.createReader(source);
		
		var games = reader.getGames();
		
		assertNotNull(games);
	}
	
	@Test
	public void testGetGames_BigPGNFile_ListOfGames() throws PGNException
	{
		var source = Factory.Model.Source.create("RJF60", "/Users/bobbr/Desktop/Chess/Games/RetiKIA.pgn");
		var reader = PGNReader.createReader(source);
		
		var games = reader.getGames();
		
		assertNotNull(games);
		assertEquals(22784, games.size());
	}
	
	@Test
	public void testGetMoveList_PGNWithVariations_List()
	{
		var reader = PGNReader.createReader("1. g4+ Kg5 2. Kg7 Kxg4 3. Kf6 Kf4 4. h4 ( 4. Ke6 Ke4 5. h4 Kf4 6. Kd5 Kg4 " +
				                            "7. Kc6 Kxh4 8. Kxc7 ) 4... Kg4 5. Ke5 Kxh4 6. Kd5 Kg4 7. Kc6 Kf4 " +
				                            "8. Kxc7 { Game is drawn by insufficient material} 1/2-1/2");
		var moves = reader.getMoveList();
		
		assertNotNull(moves);
		assertEquals(9, moves.size());
		assertEquals(Result.DRAW, Result.valueOfResult(moves.get(8).getScore()));		
	}
	
	@Test
	public void testGetGames_PGNFileWithFENs_ListOfPositions() throws PGNException
	{
		var source = Factory.Model.Source.create("EndGames", "/Users/bobbr/Desktop/Chess/Games/EndGameStudies.pgn");
		var reader = PGNReader.createReader(source);
		var games = reader.getGames();
		
		assertNotNull(games);
		assertEquals(47, games.size());
//		
//		System.out.println(" ");
//		System.out.println("Found " + games.size() + " games.");
//		
//		int n = 1;
//		
//		for (Game g : games)
//		{
//			Board b = Board.create(g.getFEN());
//			reader = PGNReader.createReader(g.getMoves());
//			
//			var fens = reader.getFENsFromMoves(b);
//			
// 			System.out.println("" + g.getRound() + "[" + (n++) + "] FEN=" + g.getFEN() + " Result: " + g.getResult());
// 			System.out.println(" ");
//			
//			for (String f : fens)
//			{
//				System.out.println(f);
//			}
//			
// 			System.out.println("------------------------------------");
//		}
	}
}
