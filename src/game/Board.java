package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
	private int width;
	private int height;
	
	private int matrix[][];
	
	private Location freeCell;
	private int pieacesInPlace;
	
	public static final int FREE_CELL_VALUE = 0;
	
	public Board(int width, int height) {
		this(randomizeBoard(height, width));
	}
	
	public Board(int[][] designedBoard) {
		validateBoard(designedBoard);
		
		this.width = designedBoard[0].length;
		this.height = designedBoard.length;
		this.matrix = designedBoard;
		this.pieacesInPlace = 0;
		
		for(int i=0; i < height; i++) {
			for(int j=0; j<width; j++) {
				if(matrix[i][j]==FREE_CELL_VALUE) {
					freeCell = new Location(j, i);
				} else if (isPieaceInPlace(new Location(j, i))) {
					pieacesInPlace++;
				}
			}
		}
	}
		
	private static int[][] randomizeBoard(int h, int w) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		int randomBoardMatrix[][] = new int [h][w];
		
		for (int i=0; i< w*h; i++) {
			numbers.add(i);
		}
		
		for(int i=0; i < h; i++) {
			for(int j=0; j<w; j++) {
				int index = rand(0, numbers.size()-1);
				randomBoardMatrix[i][j] = numbers.get(index);
				numbers.remove(index);
			} 
		}
		
		return randomBoardMatrix;
	}
	
	private void validateBoard(int[][] boardToTest) {
		int w = boardToTest[0].length;
		int h = boardToTest.length;
		HashSet<Integer> numbers = new HashSet<Integer>();
		for(int i = 0; i < w*h; i++) {
			numbers.add(i);
		}
		
		for(int i=0; i < h; i++) {
			for(int j=0; j<w; j++) {
				if(numbers.contains(boardToTest[i][j])) {
					numbers.remove(boardToTest[i][j]);
				} else {
					if (boardToTest[i][j]>=0 &&  boardToTest[i][j]<h*w) {
						throw new RuntimeException("The board contains the number [" + boardToTest[i][j] + "] more than once.");
					} else {
						throw new RuntimeException("The board contains the number [" + boardToTest[i][j] + "] which is not a valid tile value. valid values are between 0 and " + (w*h-1));
					}
					
				}
			}
		}
		
		if (numbers.size()>0) {
			StringBuffer sb = new StringBuffer();
			for (int i : numbers) {
				if(sb.length()>0) {
					sb.append(", ");
				}
				sb.append(i);
			}
			throw new RuntimeException("The board is missing the following numbers: [" + sb.toString() + "].");
		}
	}
	
	private static int rand(int lower, int higher) {
		 return lower + (int)(Math.random() * (higher - lower + 1));
	}
	
	private boolean isPieaceInPlace(Location l) {
		return (((l.getY())*width)+l.getX())+1 == matrix[l.getY()][l.getX()];
	}
	
	public boolean isLocationOffBoard(Location l) {
		return l.getX()<0 || l.getY()<0 || l.getX()>= width || l.getY()>= height;
	}
	
	public boolean isBoardSolved() {
		return pieacesInPlace == (width*height)-1;
	}
	
	public void move(Location pieaceToMoveLocation) {
				
		// Validations
		if(pieaceToMoveLocation == null) {
			throw new RuntimeException("no such piece");
		}
		if (isLocationOffBoard(pieaceToMoveLocation)) {
			throw new RuntimeException("piece is off the board");
			// TODO: Throw exception pieace is off the board
		}
		if (!pieaceToMoveLocation.isNear(freeCell)) {
			throw new RuntimeException("piece is not near the free cell");
			// TODO: Throw exception pieace is not near the free cell
		}
		if (pieaceToMoveLocation.equals(freeCell)) {
			throw new RuntimeException("piece is the same as the free cell");
			// TODO: Throw exception pieace is the same as the free cell
		}
		
		// Passed Validation, make the move
		if(isPieaceInPlace(pieaceToMoveLocation)) {
			pieacesInPlace--;
		}
		switchSpots(pieaceToMoveLocation, freeCell);
		
		Location tempLocation = pieaceToMoveLocation;
		pieaceToMoveLocation = freeCell;
		freeCell = tempLocation;
		
		if(isPieaceInPlace(pieaceToMoveLocation)) {
			pieacesInPlace++;
		}
	}
	
	public boolean isSolved() {
		return pieacesInPlace==(width*height)-1;
	}
	
	private void switchSpots(Location l1, Location l2) {
		int tempValue = matrix[l1.getY()][l1.getX()];
		 matrix[l1.getY()][l1.getX()] = matrix[l2.getY()][l2.getX()];
		 matrix[l2.getY()][l2.getX()] = tempValue;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public final int[][] getMatrix() {
		return matrix;
	}

	public final int getPieacesInPlace() {
		return pieacesInPlace;
	}
}
