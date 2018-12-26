package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import game.Board;
import game.Location;

class BoardTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	void testIsSolvedFalse() {
		Board b = new Board(new int[][] 
				 {{1,2,3,4},
				  {5,6,7,8},
				  {9,10,11,12},
				  {13,14,0,15}});

		assertFalse(b.isSolved());		
	}

	@Test
	void testIsSolvedTrue() {
		Board b = new Board(new int[][] 
				 {{1,2,3,4},
				  {5,6,7,8},
				  {9,10,11,12},
				  {13,14,15,0}});

		assertTrue(b.isSolved());		
	}
	
	@Test
	void testIsSolvableTrue() {
		try {
			Board b = new Board(new int[][] 
					 {{1,2,3,4},
					  {5,6,7,8},
					  {9,10,11,12},
					  {13,14,15,0}});
		} catch (RuntimeException e) {
			fail("Board creation failed when the board is solvable");
		}
	}
	
	@Test
	void testIsSolvableFalse() {
		
		try {
			Board b = new Board(new int[][] 
					 {{1,2,3,4},
					  {5,6,7,8},
					  {9,10,11,12},
					  {13,15,14,0}});
		    fail("Board creation succeded when the board is unsolvable" );
		} catch (RuntimeException expectedException) {
		}
	}
	
	@Test
	void testGetPieacesInPlaceSolvedBoard() {

		int[][] matrix = new int[][] 
				 {{1,2,3,4},
			  {5,6,7,8},
			  {9,10,11,12},
			  {13,14,15,0}};
		
		Board b = new Board(matrix);

		assertEquals(matrix.length*matrix[0].length-1, b.getTilesInPlace());		
	}
	
	@Test
	void testGetPieacesInPlaceAllPiecesInWrongPlace() {

		int[][] matrix = new int[][] 
				 {{9,10,11,12},
			      {13,14,15,0},
				  {1,2,3,4},
			      {5,6,7,8}};
		
		Board b = new Board(matrix);

		assertEquals(0, b.getTilesInPlace());		
	}

	@Test
	void testGetPieacesInPlaceAllPiecesInWrongPlaceExceptForFreeSpace() {

		int[][] matrix = new int[][] 
				{{9,10,11,12},
		         {13,15,14,4},
			     {2,1,3,8},
		         {5,6,7,0}};
		
		Board b = new Board(matrix);

		assertEquals(0, b.getTilesInPlace());		
	}
	
	@Test
	void testCreatingABoardTooNarrow() {
		try {
			Board b = new Board(1, 2);
			fail("Board too narrow was created succesfully");
		}catch (RuntimeException e) {
			// Test passed
		}
	}
	
	@Test
	void testCreatingABoardTooShort() {
		try {
			Board b = new Board(2, 1);
			fail("Board too short was created succesfully");
		}catch (RuntimeException e) {
			// Test passed
		}
	}
	
	@Test
	void testCreatingABoardWithNoFreeCell() {
		try {
			Board b = new Board(new int[][] {{1,2},{3,4}});
			fail("Board with no free cell was created succesfully");
		}catch (RuntimeException e) {
			// Test passed
		}
	}
	
	@Test
	void testCreatingABoardWithSameNumberTwice() {
		try {
			Board b = new Board(new int[][] {{1,2},{2,0}});
			fail("Board with same number twice created succesfully");
		}catch (RuntimeException e) {
			// Test passed
		}
	}
	
	@Test
	void testMoveToAVaildPosition() {
		Board b = new Board(new int[][] {{0,1},{3,2}});
		b.move(new Location(1, 0));
		assertTrue(b.getMatrix()[0][0]==1 && b.getMovesMade()==1);
	}
	
	@Test
	void testMoveToAInvaildPosition() {
		try {
			Board b = new Board(new int[][] {{0,1},{3,2}});
			b.move(new Location(1, 1));
			fail("Moved piece to an invalid location.");
		} catch (RuntimeException e) {
			// Test passed
		}
	}
	
	@Test
	void testMoveTheFreeCellPosition() {
		try {
			Board b = new Board(new int[][] {{0,1},{3,2}});
			b.move(new Location(0, 0));
			fail("Moved piece to an invalid location.");
		} catch (RuntimeException e) {
			// Test passed
		}
	}
	
	@Test
	void testMoveToAPostionOffTheBoard() {
		try {
			Board b = new Board(new int[][] {{1,0},{3,2}});
			b.move(new Location(2, 0));
			fail("Moved piece to an invalid location.");
		} catch (RuntimeException e) {
			// Test passed
		}
	}
	
	@Test
	void testMoveToANullPostion() {
		try {
			Board b = new Board(new int[][] {{1,0},{3,2}});
			b.move(null);
			fail("Moved piece to a null location.");
		} catch (RuntimeException e) {
			// Test passed
		}
	}
}
