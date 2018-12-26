package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Board {
	private int width;
	private int height;
	
	private int matrix[][];
	
	private Location freeCell;
	private int tilesInPlace;
	private int movesMade = 0;
	
	public static final int FREE_CELL_VALUE = 0;
	public static final int MIN_BOARD_WIDTH = 2;
	public static final int MIN_BOARD_HEIGHT = 2;
	
	public Board(int width, int height) {
		this(generateRandomBoard(height, width));
	}
	
	public Board(int[][] designedBoard) {
		validateBoard(designedBoard);
		
		this.width = designedBoard[0].length;
		if(this.width<MIN_BOARD_WIDTH) {
			throw new RuntimeException("Board width is under the minimum of [" + MIN_BOARD_WIDTH + "]");
		}
		this.height = designedBoard.length;
		if(this.height<MIN_BOARD_HEIGHT) {
			throw new RuntimeException("Board width is under the minimum of [" + MIN_BOARD_HEIGHT + "]");
		}
		this.matrix = designedBoard;
		this.tilesInPlace = 0;
		
		for(int i=0; i < height; i++) {
			for(int j=0; j<width; j++) {
				if(matrix[i][j]==FREE_CELL_VALUE) {
					freeCell = new Location(j, i);
				} else if (isTileInPlace(new Location(j, i))) {
					tilesInPlace++;
				}
			}
		}
	}
		
	private static int[][] generateRandomBoard(int h, int w) {
		int randomBoardMatrix[][] = new int [h][w];
					
		// Init the board in the solved state
		for(int i=0; i < h; i++) {
			for(int j=0; j<w; j++) {
				randomBoardMatrix[i][j] = (i*w)+j+1;
			} 
		}
		randomBoardMatrix[h-1][w-1] = FREE_CELL_VALUE;
		
		
		Random rand = new Random();
		int totalNumbers = h*w-1;
		int randomTile1; 
		int randomTile2;
		
		if(totalNumbers>1) {
			// Swapping tiles an even number of time guarantees the board to be solvable.
			// (An odd number of times will guarantee it to be unsolvable)
			for (int i=1; i <=h*w*2; i++) {
				// Pick two different tile to swap
				do {
					randomTile1 = rand.nextInt(totalNumbers);
					randomTile2 = rand.nextInt(totalNumbers);
				} while  (randomTile1==randomTile2);
				
				// Swap tiles
				int tmp = randomBoardMatrix[randomTile1/w][randomTile1%w];
				randomBoardMatrix[randomTile1/w][randomTile1%w] = randomBoardMatrix[randomTile2/w][randomTile2%w];
				randomBoardMatrix[randomTile2/w][randomTile2%w] = tmp;
			}
		}
		
		return randomBoardMatrix;
	}
	
	private static boolean isSolvable(int[][] inputMatrix)
	{
		int matrixWidth = inputMatrix[0].length;
		int matrixHeight = inputMatrix.length;
		int blankRow = 0;
	    int parity = 0;
	    
	    
	    for (int i = 0; i < matrixWidth*matrixHeight; i++) {
	        for (int j = i + 1; j < matrixWidth*matrixHeight; j++) {
	            if (inputMatrix[i/matrixWidth][i%matrixWidth] > inputMatrix[j/matrixWidth][j%matrixWidth] && 
	            		inputMatrix[j/matrixWidth][j%matrixWidth] != 0) {
	                parity++;
	            } else if (inputMatrix[i/matrixWidth][i%matrixWidth] == 0) {
	            	blankRow = (i/matrixWidth)+1;
	            }
	        }
	    }

	    boolean isSolvable;
	    if (matrixWidth % 2 == 0 && blankRow % 2 != 0) {
	    	isSolvable = parity % 2 != 0;
	    } else {
	    	isSolvable = parity % 2 == 0;
	    }
	    
	    return isSolvable;
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
		
		if (!isSolvable(boardToTest)){
			throw new RuntimeException("The board is unsolvable!");
		}
	}
		
	private boolean isTileInPlace(Location l) {
		return (((l.getY())*width)+l.getX())+1 == matrix[l.getY()][l.getX()];
	}
	
	private boolean isLocationOffBoard(Location l) {
		return l.getX()<0 || l.getY()<0 || l.getX()>= width || l.getY()>= height;
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
		if(isTileInPlace(pieaceToMoveLocation)) {
			tilesInPlace--;
		}
		switchSpots(pieaceToMoveLocation, freeCell);
		
		Location tempLocation = pieaceToMoveLocation;
		pieaceToMoveLocation = freeCell;
		freeCell = tempLocation;
		
		if(isTileInPlace(pieaceToMoveLocation)) {
			tilesInPlace++;
		}
		
		movesMade++;
	}
	
	public boolean isSolved() {
		return tilesInPlace==(width*height)-1;
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

	public int getTilesInPlace() {
		return tilesInPlace;
	}

	public int getMovesMade() {
		return movesMade;
	}
}
