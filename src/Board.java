import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	private int width;
	private int height;
	
	private int matrix[][];
	
	private Location freeCell;
	private int pieacesInPlace;
	
	public static final int FREE_CELL_VALUE = 0;
	
	
	
	
	public Board(int width, int height) {
		init(width, height);
		randomizeBoard();
	}
	
	public Board(int width, int height, int[][] designedBoard) {
		init(width, height);
		matrix = designedBoard;
	}
	
	private void init(int width, int height) {
		this.width = width;
		this.height = height;
		this.matrix = new int[height][width];
		this.pieacesInPlace = 0;
	}
	
	private void randomizeBoard() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		for (int i=0; i< width*height; i++) {
			numbers.add(i);
		}
		
		for(int i=0; i < height; i++) {
			for(int j=0; j<width; j++) {
				int index = rand(0, numbers.size()-1);
				matrix[i][j] = numbers.get(index);
				if(numbers.get(index).equals(FREE_CELL_VALUE)) {
					freeCell = new Location(j, i);
				} else if (isPieaceInPlace(new Location(j, i))) {
					pieacesInPlace++;
				}
				numbers.remove(index);
			}
		}
	}
	
	int rand(int lower, int higher) {
		 return lower + (int)(Math.random() * (higher - lower + 1));
	}
	
	private boolean isPieaceInPlace(Location l) {
		return ((l.getX()+1)*width)+l.getY() == matrix[l.getX()][l.getY()];
	}
	
	public boolean isLocationOffBoard(Location l) {
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
