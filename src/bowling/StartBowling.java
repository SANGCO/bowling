package bowling;

import java.util.Scanner;

public class StartBowling {

	public static void main(String[] args) {
		Frame frame = new Frame();
		for (int i = 0; i < 21; i++) {

			Scanner scan = new Scanner(System.in);
			String stringPin = scan.nextLine();
			int pinsDown = Integer.parseInt(stringPin);
			
			frame.roll(pinsDown);
		}
	}
	
}
