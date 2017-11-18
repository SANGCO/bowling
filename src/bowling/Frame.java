package bowling;

import java.util.ArrayList;
import java.util.Scanner;

public class Frame {
	
	public ArrayList<Integer> rolls = new ArrayList<>();
	public ArrayList<Integer> score = new ArrayList<>();
	
	public int frame = 0;
	public int roll = 0;
	public int strike = 0;
	public boolean spare = false;
	public boolean isSameFrame = false;

	public void roll(int pinsDown) {
		saveRollScore(pinsDown); // 공을 던지면 바로바로 rolls라는 list에 기록된다.
		// 한번 던졌을 때 크게보면 스트라이크냐 아니냐로 나뉜다.
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
		if(strike == 9) saveStrikeAll(); // 로직은 연속 스트라이크가 깨져야 점수가 계산되는 시스템이라 부득이하게 따로 분리
		if(spare) saveSpareScore(); // 이전 프레임이 스페어면 여기서 이전 프레임 총점을 계산 해야한다.
		nextFrame();
		return;
	}
	
	public void NotStrike() {
		// 스트라이크가 아니라면 프레임의 첫번째 시도인지 두번째 시도인지 분기 해야한다.
		if(isSameFrame != true) FrameHasNextRoll(); // isSameFrame이 false이면 첫번째 시도임
		else if(isSameFrame == true) isSpare(); // 두번째 시도라면 스페어인지 아닌지를 알아봐야 한다.
		return;
	}
	
/*-------------------------------------------------------------------*/
	
	public void isSpare() {
		// 스페어이던 아니던 이전 프레임이 스트라이크면 여기서 점수를 계산해 주어야한다.
		if(strike == 1) saveStrikeScore(); 
		if((rolls.get(roll-1) + rolls.get(roll)) == 10) spare = true;
		// 스페어가 아니라면 이번 프레임의 점수는 여기서 바로 계산되어야 한다.
		if((rolls.get(roll-1) + rolls.get(roll)) < 10) saveFrameScore(); 
		nextFrame(); 
		return;
	}
	
	public void FrameHasNextRoll() {
		isSameFrame = true;
		// 이전 프레임들에서 스트라이크가 연속해서 나온 상황이라면 마지막 스트라이크 한번을 빼고 나머지는 여기서 계산이 되어야한다.
		if(strike > 1) saveMultiStrikeScore();
		// 이전 프레임이 스페어인 경우도 여기서 점수 계산이 되어야 한다.
		if(spare) saveSpareScore();
		roll++;
		return;
	}
	
	public void nextFrame() {
		frame++;
		roll++;
		isSameFrame = false;
		return;
	}
	
/*-------------------------------------------------------------------*/
	
	public void saveRollScore(int pinsDown) {
		rolls.add(roll, pinsDown);
		int lastRoll = 0; 
		if(roll-1 >= 0) lastRoll = rolls.get(roll-1);
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
	
	public void saveSpareScore() {
		int localSubTotal = 0;
		if(frame-2 >= 0) localSubTotal = score.get(frame-2);
		int localscore = localSubTotal + 10 + rolls.get(roll); // 스페어는 이전 프레임을 10으로 생각하면 된다.
		score.add(frame-1, localscore); // 스페어가 난 프레임의 점수 계산이니 frame-1
		PrintScore.printTotalScore(frame-1, localscore);
		spare = false; // 저장하고 나면 스페어를 다시 false로
		return;
	}
	
	public void saveStrikeScore() {
		int localSubTotal = 0;
		if(frame-2 >= 0) localSubTotal = score.get(frame-2);
		// 스페어이던 아니던 스트라이크 10에 두번더 던진걸 계산해 준다.
		int localscore = localSubTotal + 10 + rolls.get(roll-1) + rolls.get(roll); 
		score.add(frame-1, localscore);
		PrintScore.printTotalScore(frame-1, localscore); // 현제 프레임보다 하나 이전 프레임에 대한 계산 frame-1
		strike = 0;
		return;
	}
	
	public void saveMultiStrikeScore() {
		for(int i=0; i < strike-1; i++) { // 0부터 시작하니 strike수 빼기 1
			int localscore = 0;
			int localSubTotal = 0;
			// 첫번째 프레임의 스트라이크는 이전이 없기때문에 .get()을 하면 널포인트익셉션이 발생한다.
			// for문이 돌면서 if문이 1부터해서 true 값이 들어가면 실행되는 동작은 그 이전 프레임의 점수를 가지고 오는거다.
			// 그래서 뒤에 -1이 하나 더 붙는다.
			if(frame + i - strike > 0) localSubTotal = score.get(frame + i - strike -1);
			if(strike-2 > i) localscore = localSubTotal + 30;
			// 연속한 스트라이크에서 마지막 스트라이크 이전 프레이므이 스트라이크의 계산은
			// 본인 스트라이크 10 + 마지막 스트라이크 10 + 공한번 더 던진건
			if(strike-2 == i) localscore = localSubTotal + 20 + rolls.get(roll);
			score.add(frame-(strike-i), localscore);
			PrintScore.printTotalScore(frame-(strike-i), localscore);
		}
		strike = 1;
		return;
	}
	
	public void saveStrikeAll() {
		int total = 0;
		for (int i = 0; i < 9; i++) {
			//한프레임당 30점씩 9번 돌면 된다.
			score.add(i, total+=30 );
		}
	}
}