import java.util.ArrayList;

public class Board {
	private int width;
	private int height;
	
	private int matrix[][];
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.matrix = new int[width][height];
		
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
				numbers.remove(index);
			}
		}
		
		
	}
	
	int rand(int lower, int higher) {
		 return lower + (int)(Math.random() * (higher - lower + 1));
	}
}
