import java.util.HashMap;

public class Presentation {
	//this map is used for easy refrance of the pieaces location. instead of asking the user for x and y cordinates,
	//we can just ask for the value of the cell
	private HashMap<Integer, Location> pieacesMap;
	private Board board;
	
	private static final int DEFAULT_HEIGHT = 4;
	private static final int DEFAULT_WIDTH = 4;
	
	public Presentation() {
		this.pieacesMap  = new HashMap<Integer, Location>();
		this.board = new Board(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
	
	
}
