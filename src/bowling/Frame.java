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
		saveRollScore(pinsDown); // ���� ������ �ٷιٷ� rolls��� list�� ��ϵȴ�.
		// �ѹ� ������ �� ũ�Ժ��� ��Ʈ����ũ�� �ƴϳķ� ������.
		if(pinsDown == 10) {
			Strike();
		}
		if(pinsDown < 10) {
			NotStrike();
		}
	}
	
	public void Strike() {
		strike++;
		if(strike == 9) saveStrikeAll(); // ������ ���� ��Ʈ����ũ�� ������ ������ ���Ǵ� �ý����̶� �ε����ϰ� ���� �и�
		if(spare) saveSpareScore(); // ���� �������� ������ ���⼭ ���� ������ ������ ��� �ؾ��Ѵ�.
		nextFrame();
	}
	
	public void NotStrike() {
		// ��Ʈ����ũ�� �ƴ϶�� �������� ù��° �õ����� �ι�° �õ����� �б� �ؾ��Ѵ�.
		if(isSameFrame != true) FrameHasNextRoll(); // isSameFrame�� false�̸� ù��° �õ���
		else if(isSameFrame == true) isSpare(); // �ι�° �õ���� ��������� �ƴ����� �˾ƺ��� �Ѵ�.
	}
	
/*-------------------------------------------------------------------*/
	
	public void isSpare() {
		// ������̴� �ƴϴ� ���� �������� ��Ʈ����ũ�� ���⼭ ������ ����� �־���Ѵ�.
		if(strike == 1) saveStrikeScore(); 
		if((rolls.get(roll-1) + rolls.get(roll)) == 10) spare = true;
		// ���� �ƴ϶�� �̹� �������� ������ ���⼭ �ٷ� ���Ǿ�� �Ѵ�.
		if((rolls.get(roll-1) + rolls.get(roll)) < 10) saveFrameScore(); 
		nextFrame(); 
	}
	
	public void FrameHasNextRoll() {
		isSameFrame = true;
		// ���� �����ӵ鿡�� ��Ʈ����ũ�� �����ؼ� ���� ��Ȳ�̶�� ������ ��Ʈ����ũ �ѹ��� ���� �������� ���⼭ ����� �Ǿ���Ѵ�.
		if(strike > 1) saveMultiStrikeScore();
		// ���� �������� ������� ��쵵 ���⼭ ���� ����� �Ǿ�� �Ѵ�.
		if(spare) saveSpareScore();
		roll++;
	}
	
	public void nextFrame() {
		frame++;
		roll++;
		isSameFrame = false;
	}
	
/*-------------------------------------------------------------------*/
	
	public void saveRollScore(int pinsDown) {
		rolls.add(roll, pinsDown);
		int lastRoll = 0; 
		if(roll-1 >= 0) lastRoll = rolls.get(roll-1);
		PrintScore.printScoreBored(frame, pinsDown, isSameFrame, lastRoll);
	}
	
	public void saveFrameScore() {
		int localSubTotal = 0;
		if(frame-1 >= 0) localSubTotal = score.get(frame-1);
		int localscore = localSubTotal + rolls.get(roll-1) + rolls.get(roll);
		score.add(frame, localscore);
		PrintScore.printTotalScore(frame, localscore);
	}
	
	public void saveSpareScore() {
		int localSubTotal = 0;
		if(frame-2 >= 0) localSubTotal = score.get(frame-2);
		int localscore = localSubTotal + 10 + rolls.get(roll); // ������ ���� �������� 10���� �����ϸ� �ȴ�.
		score.add(frame-1, localscore); // ���� �� �������� ���� ����̴� frame-1
		PrintScore.printTotalScore(frame-1, localscore);
		spare = false; // �����ϰ� ���� ���� �ٽ� false��
	}
	
	public void saveStrikeScore() {
		int localSubTotal = 0;
		if(frame-2 >= 0) localSubTotal = score.get(frame-2);
		// ������̴� �ƴϴ� ��Ʈ����ũ 10�� �ι��� ������ ����� �ش�.
		int localscore = localSubTotal + 10 + rolls.get(roll-1) + rolls.get(roll); 
		score.add(frame-1, localscore);
		PrintScore.printTotalScore(frame-1, localscore); // ���� �����Ӻ��� �ϳ� ���� �����ӿ� ���� ��� frame-1
		strike = 0;
	}
	
	public void saveMultiStrikeScore() {
		for(int i=0; i < strike-1; i++) { // 0���� �����ϴ� strike�� ���� 1
			int localscore = 0;
			int localSubTotal = 0;
			// ù��° �������� ��Ʈ����ũ�� ������ ���⶧���� .get()�� �ϸ� ������Ʈ�ͼ����� �߻��Ѵ�.
			// for���� ���鼭 if���� 1�����ؼ� true ���� ���� ����Ǵ� ������ �� ���� �������� ������ ������ ���°Ŵ�.
			// �׷��� �ڿ� -1�� �ϳ� �� �ٴ´�.
			if(frame + i - strike > 0) localSubTotal = score.get(frame + i - strike -1);
			if(strike-2 > i) localscore = localSubTotal + 30;
			// ������ ��Ʈ����ũ���� ������ ��Ʈ����ũ ���� �����̹��� ��Ʈ����ũ�� �����
			// ���� ��Ʈ����ũ 10 + ������ ��Ʈ����ũ 10 + ���ѹ� �� ������
			if(strike-2 == i) localscore = localSubTotal + 20 + rolls.get(roll);
			score.add(frame-(strike-i), localscore);
			PrintScore.printTotalScore(frame-(strike-i), localscore);
		}
		strike = 1;
	}
	
	public void saveStrikeAll() {
		int total = 0;
		for (int i = 0; i < 9; i++) {
			//�������Ӵ� 30���� 9�� ���� �ȴ�.
			score.add(i, total+=30 );
		}
	}
}