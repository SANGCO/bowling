package bowling;

import java.util.Scanner;

public class StartBowling {

	public static void main(String[] args) {
		Frame frame = new Frame();
		PrintScore.inputUserName();
		for (int i = 0; i < 21; i++) {
			int pinsDown = PrintScore.inputScore();
			frame.roll(pinsDown);
			PrintScore.printScore();
		}
	}
	
}
