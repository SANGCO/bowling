package bowling;

import java.util.Scanner;

public class StartBowling {

	public static void main(String[] args) {
		Frame frame = new Frame();
		PrintScore.inputUserName();
		int pinsDown = 0;
		
		for (int i = 0; i < 21; i++) {
			if(frame.frame == 9) break;
			pinsDown = PrintScore.inputScore();
			frame.roll(pinsDown);
			PrintScore.printScore();
		}
/*		for (int i = 0; i < 9; i++) {
			frame.roll(10);
		}*/
		LastFrame lastFrame = new LastFrame(frame.rolls, frame.score, frame.roll, frame.frame, frame.spare);
		for (int i = 0; i < 3; i++) {
			pinsDown = PrintScore.inputScore();
			lastFrame.roll(pinsDown);
			PrintScore.printScore();
		}
	}
	
}
