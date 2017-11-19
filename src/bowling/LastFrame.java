package bowling;

import java.util.ArrayList;
import java.util.Scanner;

public class LastFrame extends Frame {
	
	
	public LastFrame() {
		super();
	}
	
	public LastFrame(ArrayList prevRolls, ArrayList prevScore, int preRoll, int preFrame, boolean preSpare) {
		rolls = prevRolls;
		score = prevScore;
		roll =  preRoll;
		frame = preFrame;
		spare = preSpare;
	}
	
	public int lastFrame = 0;
	
	@Override
	public void roll(int pinsDown) {
		if(lastFrame == 3) return;
		super.roll(pinsDown);
		lastFrame++;
	}
	
	@Override
	public void Strike() {
		strike++;
		if(spare) saveSpareScore(); // 이전 프레임이 스페어면 여기서 이전 프레임 총점을 계산 해야한다.
		if(strike == 3) saveMultiStrikeScore(); 
		nextFrame();
	}
	
	@Override
	public void isSpare() {
		saveFrameScore(); 
	}
	
/*-------------------------------------------------------------------*/
	
	@Override
	public void saveRollScore(int pinsDown) {
		rolls.add(roll, pinsDown);
		int lastRoll = rolls.get(roll-1);
		PrintScore.printScoreBoredLastFrame(frame, pinsDown, isSameFrame, lastRoll);
	}
	
	@Override
	public void saveFrameScore() {
		int localSubTotal = score.get(frame-1);
		int localscore = localSubTotal + rolls.get(roll-1) + rolls.get(roll);
		score.add(frame, localscore);
		PrintScore.printTotalScore(frame, localscore);
	}
	
	@Override
	public void saveStrikeScore() {
		int localSubTotal = score.get(frame-2);
		int localscore = localSubTotal + 10 + rolls.get(roll-1) + rolls.get(roll);
		score.add(frame-1, localscore);
		PrintScore.printTotalScore(frame-1, localscore);
		strike = 0;
	}
	
	@Override
	public void saveMultiStrikeScore() {
		int localscore = score.get(8) + 30;
		score.add(9, localscore);
		PrintScore.printTotalScore(9, localscore);
	}
	
	@Override
	public void saveSpareScore() {
		int localSubTotal = score.get(frame-2);
		int localscore = localSubTotal + 10 + rolls.get(roll);
		score.add(frame-1, localscore);
		PrintScore.printTotalScore(frame-1, localscore);
		spare = false;
	}
}