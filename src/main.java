import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		Presentation p = new Presentation();
		Scanner scan = new Scanner(System.in);
		String s = "start";
		while(!s.equalsIgnoreCase("q")) {
			p.printStatus();
			p.printBoard();
			System.out.println("What tile would you like to move next?");
			s = scan.next();
			
			p.makeMove(s);
		}
		
		if(s.equalsIgnoreCase("q")) {
			System.out.println("Quiting...");
		}

	}

}
