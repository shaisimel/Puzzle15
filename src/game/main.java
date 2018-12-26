package game;
import java.util.Scanner;

public class main {

	private static final String COMMAND_TO_QUIT = "q";
	
	public static void main(String[] args) {		
		Presentation p = new Presentation();
		Scanner scan = new Scanner(System.in);
		String s = "start";
		System.out.println("Any anytime you can press \'" + COMMAND_TO_QUIT + "\' to quit the program.");
		while(!s.equalsIgnoreCase(COMMAND_TO_QUIT) && !p.isBoardSolved()) {
			p.printStatus();
			p.printBoard();
			System.out.println("What tile would you like to move next?");
			s = scan.next();
			
			if(!s.equalsIgnoreCase(COMMAND_TO_QUIT)) {
				p.makeMove(s);
			}
		}
		
		if(p.isBoardSolved()) {
			p.printStatus();
			p.printBoard();
			System.out.println("The board is solved, all the tiles are in place! Good job!");
		}
		scan.close();
		System.out.println("Quiting...");
	}
}
