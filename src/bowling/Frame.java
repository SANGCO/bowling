package bowling;

import java.util.ArrayList;
import java.util.Scanner;

public class Frame {
	
	public static ArrayList<Integer> rolls = new ArrayList<>(17);
	public static ArrayList<Integer> score = new ArrayList<>(8);
	
	public static int frame = 0;
	public static int roll = 0;
	public static int strike = 0;
	public static boolean spare = false;
	public static boolean isSameFrame = false;

	public void roll(int pinsDown) {
		saveRollScore(pinsDown);
		if(pinsDown == 10) {
			Strike();
		}
		if(pinsDown < 10) {
			NotStrike();
		}
		return;
	}
	
	public void Strike() {
		strike++;
		//*****print 夸没*****
		System.out.println("Strike()   rolls : "+rolls.toString()+" score : "+score.toString()+" frame : "+frame+" roll : "+roll+" strike : "+strike+" spare : "+spare);
		nextFrame();
		return;
	}
	
	public void NotStrike() {
		if(isSameFrame != true) FrameHasNextRoll();
		else if(isSameFrame == true) isSpare();
		return;
	}
	
/*-------------------------------------------------------------------*/
	
	public void nextFrame() {
		frame++;
		roll++;
		isSameFrame = false;
		return;
	}
	
	public void isSpare() {
		if(strike == 1) saveStrikeScore();
		if((rolls.get(roll-1) + rolls.get(roll)) == 10) spare();
		else if((rolls.get(roll-1) + rolls.get(roll)) < 10) notSpare();
		nextFrame(); 
		return;
	}
	
	public void spare() {
		//*****print 夸没*****
		System.out.println("spare()   rolls : "+rolls.toString()+" score : "+score.toString()+" frame : "+frame+" roll : "+roll+" strike : "+strike+" spare : "+spare);
		spare = true;
		return;
	}
	
	public void notSpare() {
		saveFrameScore(); 

		//*****print 夸没*****
		System.out.println("notSpare()   rolls : "+rolls.toString()+" score : "+score.toString()+" frame : "+frame+" roll : "+roll+" strike : "+strike+" spare : "+spare);
		return;
	}
	
	public void FrameHasNextRoll() {
		isSameFrame = true;
		if(strike > 1) saveMultiStrikeScore();
		if(spare) saveSpareScore();

		//*****print 夸没*****
		System.out.println("FrameHasNextRoll()   rolls : "+rolls.toString()+" score : "+score.toString()+" frame : "+frame+" roll : "+roll+" strike : "+strike+" spare : "+spare);
		
		roll++;
		return;
	}
	
/*-------------------------------------------------------------------*/
	
	public void saveRollScore(int pinsDown) {
		rolls.add(roll, pinsDown);
		int lastRoll = 0; 
		if(roll-1 > 0) lastRoll = rolls.get(roll-1);
		PrintScore.printScoreBored(frame, pinsDown, isSameFrame, lastRoll);
		return;
	}
	
	public void saveFrameScore() {
		int localSubTotal = 0;
		if(frame-1 >= 0) localSubTotal = score.get(frame-1);
		int localscore = localSubTotal + rolls.get(roll-1) + rolls.get(roll);
		score.add(frame, localscore);
		PrintScore.printTotalScore(frame, localscore);
		return;
	}
	
	public void saveStrikeScore() {
		int localSubTotal = 0;
		if(frame-2 >= 0) localSubTotal = score.get(frame-2);
		int localscore = localSubTotal + 10 + rolls.get(roll-1) + rolls.get(roll);
		score.add(frame-1, localscore);
		PrintScore.printTotalScore(frame-1, localscore);
		strike = 0;
		return;
	}
	
	public void saveMultiStrikeScore() {
		for(int i=0; i < strike-1; i++) {
			int localscore = 0;
			int localSubTotal = 0;
			if(frame + i - strike > 0) localSubTotal = score.get(frame + i - strike - 1);
			if(strike-2 > i) localscore = localSubTotal + 30;
			if(strike-2 == i) localscore = localSubTotal + 20 + rolls.get(roll);
			score.add(frame-(strike-i), localscore);
			PrintScore.printTotalScore(frame-(strike-i), localscore);
		}
		strike = 1;
		return;
	}
	
	public void saveSpareScore() {
		int localSubTotal = score.get(frame-2);
		int localscore = localSubTotal + 10 + rolls.get(roll);
		score.add(frame-1, localscore);
		PrintScore.printTotalScore(frame-1, localscore);
		spare = false;
		return;
	}
}