package game;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		Presentation p = new Presentation();
		Scanner scan = new Scanner(System.in);
		String s = "start";
		System.out.println("Any anytime you can press \'q\' to quit the program.");
		while(!s.equalsIgnoreCase("q") && !p.isBoardSolved()) {
			p.printStatus();
			p.printBoard();
			System.out.println("What tile would you like to move next?");
			s = scan.next();
			
			if(!s.equalsIgnoreCase("q")) {
				p.makeMove(s);
			}
		}
		
		if(p.isBoardSolved()) {
			p.printStatus();
			p.printBoard();
			System.out.println("The board is solved, all the pieces are in place! Good job!");
		}
		else if(s.equalsIgnoreCase("q")) {
			System.out.println("Quiting...");
		}
	}
}
