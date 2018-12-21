import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	private int width;
	private int height;
	
	private int matrix[][];
	
	private Location freeCell;
	private int pieacesInPlace;
	
	private static final int FREE_CELL_VALUE = 0;
	
	private HashMap<Integer, Location> pieacesMap;
	
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.matrix = new int[width][height];
		this.pieacesInPlace = 0;
		this.pieacesMap  = new HashMap<Integer, Location>();
		randomizeBoard();
		
	}
	
	private void randomizeBoard() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		for (int i=0; i< width*height; i++) {
			numbers.add(i);
		}
		
		for(int i=0; i < width; i++) {
			for(int j=0; j<height; j++) {
				int index = rand(0, numbers.size());
				matrix[i][j] = numbers.get(index);
				if(numbers.get(index).equals(FREE_CELL_VALUE)) {
					freeCell = new Location(i, j);
				} else if (isPieaceInPlace(new Location(i, j), numbers.get(index))) {
					pieacesInPlace++;
				}
				pieacesMap.put(numbers.get(index), new Location(i, j));
				numbers.remove(index);
			}
		}
	}
	
	int rand(int lower, int higher) {
		 return lower + (int)(Math.random() * (higher - lower + 1));
	}
	
	private boolean isPieaceInPlace(Location l, int value) {
		return ((l.getX()+1)*width)+l.getY() == value;
	}
	
	public boolean isLocationOffBoard(Location l) {
		return l.getX()<0 || l.getY()<0 || l.getX()>= width || l.getY()>= height;
	}
	
	public void move(int pieaceId) {
		Location currentLocation = pieacesMap.get(pieaceId);
		
		// Validations
		if(currentLocation == null) {
			// TODO: Throw exception no such pieace
		}
		if (isLocationOffBoard(currentLocation)) {
			// TODO: Throw exception pieace is off the board
		}
		if (!currentLocation.isNear(freeCell)) {
			// TODO: Throw exception pieace is not near the free cell
		}
		if (currentLocation.equals(freeCell)) {
			// TODO: Throw exception pieace is the same as the free cell
		}
		
		// Passed Validation, make the move
		if(isPieaceInPlace(currentLocation, pieaceId)) {
			pieacesInPlace--;
		}
		switchSpots(currentLocation, freeCell);
		
		Location tempLocation = currentLocation;
		currentLocation = freeCell;
		freeCell = tempLocation;
		
		if(isPieaceInPlace(currentLocation, pieaceId)) {
			pieacesInPlace++;
		}
	}
	
	private void switchSpots(Location l1, Location l2) {
		int tempValue = matrix[l1.getX()][l1.getY()];
		 matrix[l1.getX()][l1.getY()] = matrix[l2.getX()][l2.getY()];
		 pieacesMap.put(matrix[l1.getX()][l1.getY()], l2);
		 matrix[l2.getX()][l2.getY()] = tempValue;
		 pieacesMap.put(matrix[l2.getX()][l2.getY()], l1);
	}
}
