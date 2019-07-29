package P3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;


public class ActionTest {
	@Test
	public void testCountPieces1() {
		Game game = new Game();
		game.map = new Board(8);
		game.player1 = new Player("playername1", 1, 0, game.map);
		game.player2 = new Player("playername2", 2, 0, game.map);
		assertEquals(16, game.countPieces("playername1", game));
		assertEquals(16, game.countPieces("playername2", game));
	}
	

	@Test
	public void testCountPieces2() {
		Game game = new Game();
		game.map = new Board(17);
		game.player1 = new Player("playername1", 1, 1, game.map);
		game.player2 = new Player("playername2", 2, 1, game.map);
		assertEquals(180, game.countPieces("playername1", game));
		assertEquals(180, game.countPieces("playername2", game));
	}
	
	@Test
	public void testSearch1() { 
		Game game = new Game();
		game.map = new Board(8);
		game.player1 = new Player("playername1", 1, 0, game.map);
		game.player2 = new Player("playername2", 2, 0, game.map);
		assertEquals("K", game.search(new Position(0, 4), game).type());
		assertEquals("Q", game.search(new Position(0, 3), game).type());
		assertEquals(1, game.search(new Position(0, 4), game).tag());
		assertEquals(2, game.search(new Position(6, 4), game).tag());
		assertEquals(null, game.search(new Position(6, 8), game));
	}
	
	@Test
	public void testSearch2() {
		Game game = new Game();
		game.map = new Board(17);
		game.player1 = new Player("playername1", 1, 1, game.map);
		game.player2 = new Player("playername2", 2, 1, game.map);
		assertEquals(null, game.search(new Position(0, 4), game));
		assertEquals(null, game.search(new Position(-1, 4), game));
	}
	
	@Test
	public void testPutPieceToBoard() {
		Game game = new Game();
		game.map = new Board(17);
		game.player1 = new Player("playername1", 1, 1, game.map);
		game.player2 = new Player("playername2", 2, 1, game.map);
		game.putPieceToBoard("playername1", new Piece("w", 0, 0, 1), 0, 0, game);
		game.putPieceToBoard("playername2", new Piece("b", 0, 0, 1), 2, 2, game);
		//����ԭ��û�����ӵ�λ�ã��ɹ�
		assertNotNull(game.search(new Position(0, 0), game));
		assertNotNull(game.search(new Position(2, 2), game));
		//����ԭ�������ӵ�λ�ã�ʧ��
		assertFalse(game.putPieceToBoard("playername1", new Piece("w", 0, 0, 1), 0, 0, game));
	}
	
	@Test
	public void testMove() {
		Game game = new Game();
		game.map = new Board(8);
		game.player1 = new Player("playername1", 1, 0, game.map);
		game.player2 = new Player("playername2", 2, 0, game.map);
		assertFalse(game.move(game, game.player1, new Piece("P", 1, 2, 2), 1,2, 2, 2));
		
		assertFalse(game.move(game, game.player1, new Piece("P", 1, 2, 1), 1,2, 0, 2));
		
		assertFalse(game.move(game, game.player1, new Piece("P", 3, 2, 1), 3,2, 2, 2));
		
		assertFalse(game.move(game, game.player1, new Piece("P", -1, 2, 1), -1,2, 2, 2));
		
		assertFalse(game.move(game, game.player1, new Piece("P", 1, 2, 1), 1,2, 1, 2));
		
		System.out.println("1111");
		assertNull(game.search(new Position(2, 2), game));
		assertNotNull(game.search(new Position(1, 2), game));
		assertFalse(!game.move(game, game.player1, new Piece("P", 1, 2, 1), 1,2, 2, 2));
		assertNull(game.search(new Position(1, 2), game));
		assertNotNull(game.search(new Position(2, 2), game));
	}
	
	@Test
	public void testRemove() {
		Game game = new Game();
		game.map = new Board(17);
		game.player1 = new Player("playername1", 1, 1, game.map);
		game.player2 = new Player("playername2", 2, 1, game.map);
		assertFalse(game.remove(game, game.player1, 0, 1));
		assertFalse(game.remove(game, game.player1, -1, 1));
		
		game.putPieceToBoard("playername1", new Piece("w", 0, 1, 1), 0, 1, game);
		game.putPieceToBoard("playername2", new Piece("b", 2, 3, 2), 2, 3, game);
		
		assertFalse(game.remove(game, game.player1, 0, 1));
		
		assertNotNull(game.search(new Position(2, 3), game));
		assertFalse(!game.remove(game, game.player1, 2, 3));
		assertNull(game.search(new Position(2, 3), game));
	}
	
	@Test
	public void testEat() {
		Game game = new Game();
		game.map = new Board(8);
		game.player1 = new Player("playername1", 1, 0, game.map);
		game.player2 = new Player("playername2", 2, 0, game.map);
		
		assertFalse(game.changePiece(game, game.player1, new Piece("P", -1, 1, 1), -1, 1, 6, 5));
		assertFalse(game.changePiece(game, game.player1, new Piece("P", 1, 1, 1), 1, 1, 5, 5));
		
		assertFalse(game.changePiece(game, game.player1, new Piece("P", 1, 1, 1), 1, 1, 1, 2));

		assertFalse(game.changePiece(game, game.player1, new Piece("P", 6, 1, 2), 6, 1, 6, 5));
		
		assertNotNull(game.search(new Position(1, 1), game));
		assertNotNull(game.search(new Position(6, 3), game));
		assertEquals(2, game.search(new Position(6, 3), game).tag());
		assertFalse(!game.changePiece(game, game.player1, new Piece("P", 1, 1, 1), 1, 1, 6, 3));
		assertNull(game.search(new Position(1, 1), game));
		assertEquals(1, game.search(new Position(6, 3), game).tag());
	}
	
}
