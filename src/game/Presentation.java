package game;
import java.util.HashMap;

public class Presentation {
	//this map is used for easy refrance of the pieaces location. instead of asking the user for x and y cordinates,
	//we can just ask for the value of the cell
	private HashMap<Integer, Location> pieacesMap;
	private Board board;
	
	private static final int DEFAULT_HEIGHT = 4;
	private static final int DEFAULT_WIDTH = 4;
	
	public Presentation() {
		
		int [][] testBoard = {{1,2,3,4},
							  {5,6,7,8},
							  {9,10,11,12},
							  {13,14,0,15}};
		
		this.pieacesMap  = new HashMap<Integer, Location>();
		this.board = new Board(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		//this.board = new Board(testBoard);
		//this.board = new Board(3, 3);
		translateMatrixIntoMap();
	}
	
	public void printBoard() {
		int matrix[][] = board.getMatrix();
		
		for(int i=0; i<board.getHeight(); i++) {
			printDividingLine();
			for(int k=0; k<board.getWidth(); k++) {
				int value = matrix[i][k];
				System.out.print("|");
				if (value<10) {
					System.out.print(" ");
				}
				System.out.print(value!=0? value : " ");
			}
			System.out.println("|");
		}
		printDividingLine();
	}
	
	private void printDividingLine() {
		for(int j=0; j<board.getWidth(); j++) {
			System.out.print("+--");
		}
		System.out.println("+");
	}
	
	private void translateMatrixIntoMap() {
		for(int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				pieacesMap.put(board.getMatrix()[i][j], new Location(j, i));
			}
		}
	}
	
	public void makeMove(String move) {
		try {
			Integer cell = Integer.valueOf(move);
			if(!pieacesMap.containsKey(cell)) {
				System.out.println("Tile with value [" + cell + "] does not exist");
			} else {

				this.board.move(pieacesMap.get(cell));
				Location temp = pieacesMap.get(Board.FREE_CELL_VALUE);
				pieacesMap.put(Board.FREE_CELL_VALUE, pieacesMap.get(cell));
				pieacesMap.put(cell, temp);
			}
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		}
		catch (Exception e) {
			System.out.println("[" + move + "] is not a valid value");
		}
	}
	
	public boolean isBoardSolved() {
		return board.isSolved();
	}
	
	public void printStatus() {
		System.out.println("------------------------------------");
		System.out.println("Is the board solved: " + board.isSolved());
		System.out.println("Total pieces in place: " + board.getPieacesInPlace());
		System.out.println("Total moves made: " + board.getMovesMade());
		System.out.println(" ");
		
	}
}
