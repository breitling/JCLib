--
INSERT INTO SOURCES (name,path) VALUES ('TestPositions','C:\Users\bobbr\Desktop\Chess\Games\test.pgn');
INSERT INTO SOURCES (name,path) VALUES ('JoToGames','C:\Users\bobbr\Desktop\Chess\Games\test.pgn');
--
INSERT INTO GAMES (source_id,white,black,game_date,result,move_count,moves) VALUES (1, 'Bob Breitling','Joe Bloe','2008-03-28','1-0',4,'1. e4 e5 2. Bc4 Nc6 3. Qh5 a5 4. Qxf7# 1-0');
INSERT INTO GAMES (source_id,white,black,game_date,result,move_count,moves) VALUES (2,'Jo To','Joe Bloe','2008-03-28','1-0',4,'1. e4 e5 2. Bc4 Nc6 3. Qh5 a5 4. Qxf7# 1-0');
INSERT INTO GAMES (source_id,white,black,game_date,result,move_count,moves) VALUES (2,'Jo To','Stan Smith','2008-03-28','1-0',4,'1. e4 e5 2. Bc4 Nc6 3. Qh5 a5 4. Qxf7# 1-0');
--
--INSERT INTO POSITIONS (bitboardhash,fen,created)VALUES (-281474976645121,'rnbkqbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1','2024-03-18');
--INSERT INTO GAMEPOSITIONS (id,game_id,pos_id) VALUES (1,1,1);