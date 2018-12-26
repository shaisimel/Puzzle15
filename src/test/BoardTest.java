package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;

class BoardTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testBoardIntArrayArray() {
		fail("Not yet implemented");
	}

	@Test
	void testIsLocationOffBoard() {
		fail("Not yet implemented");
	}

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
		int[][] b = new int[][] 
				 {{1,2,3,4},
				  {5,6,7,8},
				  {9,10,11,12},
				  {13,14,0,15}};

		assertTrue(Board.isSolvable(b));		
	}
	
	@Test
	void testIsSolvableFalse() {
		int[][] b = new int[][] 
				 {{1,2,3,4},
				  {5,6,7,8},
				  {9,10,11,12},
				  {13,15,14,0}};

		assertFalse(Board.isSolvable(b));		
	}
	
	@Test
	void testGetPieacesInPlaceSolvedBoard() {

		int[][] matrix = new int[][] 
				 {{1,2,3,4},
			  {5,6,7,8},
			  {9,10,11,12},
			  {13,14,15,0}};
		
		Board b = new Board(matrix);

		assertEquals(matrix.length*matrix[0].length-1, b.getPieacesInPlace());		
	}
	
	@Test
	void testGetPieacesInPlaceAllPiecesInWrongPlace() {

		int[][] matrix = new int[][] 
				 {{13,14,15,0},
				  {1,2,3,4},
			      {5,6,7,8},
			      {9,10,11,12}};
		
		Board b = new Board(matrix);

		assertEquals(0, b.getPieacesInPlace());		
	}

	@Test
	void testGetPieacesInPlaceAllPiecesInWrongPlaceExceptForFreeSpace() {

		int[][] matrix = new int[][] 
				 {{13,14,15,12},
				  {1,2,3,4},
			      {5,6,7,8},
			      {9,10,11,0}};
		
		Board b = new Board(matrix);

		assertEquals(0, b.getPieacesInPlace());		
	}
}
