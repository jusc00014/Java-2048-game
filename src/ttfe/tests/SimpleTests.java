package ttfe.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import java.beans.Transient;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ttfe.MoveDirection;
import ttfe.SimulatorInterface;
import ttfe.TTFEFactory;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class SimpleTests {

	private SimulatorInterface game;

	@Before
	public void setUp() {
		game = TTFEFactory.createSimulator(4, 4, new Random(0));
	}
	
	@Test
	public void testInitialGamePoints() {
		assertEquals("The initial game did not have zero points", 0,
				game.getPoints());
	}
	
	@Test
	public void testInitialBoardHeight() {
		assertTrue("The initial game board did not have correct height",
				4 == game.getBoardHeight());
	}

	@Test
	public void testInitialBoardWidth() {
		assertTrue("The initial game board did not have correct width",
				4 == game.getBoardWidth());
	}

	@Test
	public void testInitialgetNumMoves() {
		assertEquals("The initial game did not have zero moves", 0, game.getNumMoves());
	}

	@Test
	public void testInitialNumPieces() {
		assertEquals("The initial game does not start with two peaces", 2, game.getNumPieces());
	}

	@Test
	public void testGetPieceAt() {
		int x = game.getBoardHeight();
		int y = game.getBoardWidth();
		assertThrows("The Coordinates must be within the board dimensions", IllegalArgumentException.class, () -> {game.getPieceAt((x-1), (y+1));});
		assertThrows("The Coordinates must be within the board dimensions", IllegalArgumentException.class, () -> {game.getPieceAt((x+1), (y-1));});
		assertThrows("The Coordinates must be within the board dimensions", IllegalArgumentException.class, () -> {game.getPieceAt(-1, y-1);});
		assertThrows("The Coordinates must be within the board dimensions", IllegalArgumentException.class, () -> {game.getPieceAt((x-1), -1);});
	}

	@Test
	public void testRightValueInitial() {
		for(int i = 0; i<4; i++){
			for (int j = 0; j < 4; j++) {
				assertTrue(0 == game.getPieceAt(i, j) || 2 == game.getPieceAt(i, j) || 4 == game.getPieceAt(i, j));
			}
		}
	}

	@Test
	public void testAddPiece() {
		if (game.isSpaceLeft() == false){
			assertThrows("There is no space to add piece", IllegalStateException.class, () -> {game.addPiece();});
		} else {
			int x = game.getNumPieces();
			game.addPiece();
			assertTrue(x == game.getNumPieces());
		}
	}

	@Test
	public void testSetPieceAt() {
		int x = game.getBoardHeight();
		int y = game.getBoardWidth();
		assertThrows("The Coordinates must be within the board dimensions", IllegalArgumentException.class, () -> {game.setPieceAt((x-1), (y+1), 2);});
		assertThrows("The Coordinates must be within the board dimensions", IllegalArgumentException.class, () -> {game.setPieceAt((x+1), (y-1), 2);});
		assertThrows("The Coordinates must be within the board dimensions", IllegalArgumentException.class, () -> {game.setPieceAt(-1, y-1, 2);});
		assertThrows("The Coordinates must be within the board dimensions", IllegalArgumentException.class, () -> {game.setPieceAt((x-1), -1, 2);});
		assertThrows("The value must be non-negative", IllegalArgumentException.class, () -> {game.setPieceAt(3,3, -4);});
	}

	@Test
	public void testIsMovePossibleNull() {
		assertThrows("The given direction is not valid", IllegalArgumentException.class, () -> {game.isMovePossible(null);});
	}

	@Test
	public void testIsSpaceLeft() {
		if(game.getNumPieces() < (game.getBoardHeight() * game.getBoardWidth())) {
			assertTrue(game.isSpaceLeft());
		} else {
			assertFalse(game.isSpaceLeft());
		}
	}

	@Test
	public void testIsMovePossible() {
		if(game.isSpaceLeft()){
			assertTrue(game.isMovePossible(MoveDirection.WEST) || game.isMovePossible(MoveDirection.EAST) || game.isMovePossible(MoveDirection.SOUTH) || game.isMovePossible(MoveDirection.NORTH));
		}
	}

	@Test
	public void testPerformMoveNull() {
		assertThrows("The given direction is not valid", IllegalArgumentException.class, () -> {game.performMove(null);});
	}

	@Test
	public void testPerformMove() {
		assertTrue(game.performMove(MoveDirection.WEST) || game.performMove(MoveDirection.EAST) || game.performMove(MoveDirection.SOUTH) || game.performMove(MoveDirection.NORTH));
	}

	@Test
	public void testIncreaseMoves() {
		int x = game.getNumMoves();
		int y;
		if (game.performMove(MoveDirection.WEST)) {
			y = game.getNumMoves();
			assertTrue(x == (y-1));
			x = y;
		}
		if (game.performMove(MoveDirection.EAST)) {
			y = game.getNumMoves();
			assertTrue(x == (y-1));
			x = y;
		}
		if (game.performMove(MoveDirection.SOUTH)) {
			y = game.getNumMoves();
			assertTrue(x == (y-1));
			x = y;
		}
		if (game.performMove(MoveDirection.NORTH)) {
			y = game.getNumMoves();
			assertTrue(x == (y-1));
			x = y;
		}
	}

	@Test
	public void testRun() {
		assertThrows(IllegalArgumentException.class, () -> {game.run(null, null);});
	}
}