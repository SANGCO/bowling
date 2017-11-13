package bowling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Frame {
	HashMap<Integer, Integer> gameOfRule = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> recordTotalScore = new HashMap<Integer, Integer>();
	int gameTotalScore = 0;
	int currentFrame = 1;
	
	
	int count = 1;

	
	
	boolean isStrike = true;
	boolean isSpare = true;
	int strike = 0;
	
	
	
	boolean trueOrFalse = true;
	
	
	int addSpace = 10;
	int totalIntScore = 0;
	String score;
	Scanner scan = new Scanner(System.in);
	
	StringBuilder string = new StringBuilder("|  PJS |      |      |      |      |      |      |      |      |      |      |");
	StringBuilder string2 = new StringBuilder("|      |      |      |      |      |      |      |      |      |      |      |");
	
	
	
	
	
	
	public void frameOfGame(String user) {
		System.out.print(user+"'s score : ");
		score = scan.nextLine();
		
		int numberOfScore = Integer.parseInt(score);
		totalIntScore +=  numberOfScore;
		String totalStringScore = String.valueOf(totalIntScore);
		
		header();
		
		
		
		
		if(numberOfScore == 10) {
			string.replace(addSpace, addSpace+1, "X");
			System.out.println(string.toString());
			gameOfRule.put(count, numberOfScore);
			gameTotalScore += numberOfScore;
			viewTotalScoreStrike(addSpace, count, currentFrame);
		}
		if(numberOfScore < 10) {
			gameOfRule.put(count, numberOfScore);
			notStrike(addSpace, user, score, numberOfScore, totalStringScore);
		}		
		
		
		

		
		System.out.println(count);
		count++;
		System.out.println(addSpace);
		addSpace += 7;
		currentFrame++;
		return;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void notStrike(int space, String user, String score, int scoreIntType, String total) {
		string.replace(space, space+1, score);
		System.out.println(string.toString());
		viewTotalScoreNonStrike(space, total);
		System.out.println();
		count++;
		//우선 처음 시도는 화면에 뿌려주고
		
		//나머지 받을 준지
		System.out.print(user+"'s score : ");
		String numberOfSpare = scan.nextLine();
		int numberOfScore = Integer.parseInt(numberOfSpare);
		
		gameOfRule.put(count, numberOfScore);
		String secondtotal = String.valueOf(totalIntScore);
		gameTotalScore += numberOfScore;
		if((numberOfScore + scoreIntType) == 10) {
			string.replace(addSpace+1, addSpace+3, "|/");
			viewTotalScoreSpare(space, secondtotal);
			
		}		
		if((numberOfScore + scoreIntType) < 10) {
			string.replace(addSpace+1, addSpace+3, "|-");
			viewTotalScoreNonStrike(space, secondtotal);
		}
		
		header();
		System.out.println(string.toString());
		System.out.println(gameOfRule.values());
		System.out.println();
		
		return;
	}
	
	
	//score을 물어볼때 repeat 값을 올리고 
	//여기서 조건이 성립되면 repeat 값을 다시 0으로 해준다.
	
	public void viewTotalScoreStrike(int space, int passedCount, int passedCurrentFrame) {
		//count 인자값으로 넘겨 받자.
		if(isStrike == true) {
			System.out.println(string2.toString());
			recordTotalScore.put(currentFrame, 0);
			return;
		}
		
		int startCount = passedCount - (strike+1);
		int startSpace = space;
		int SaveCurrentFrame = passedCurrentFrame - strike;
		
		
		if(isStrike == false) {
			startSpace -= strike * 7; 
			for (int i = 0; i < strike; i++) {
				int totalScore = recordTotalScore.get(SaveCurrentFrame-1) + gameOfRule.get(startCount) + gameOfRule.get(startCount+1) + gameOfRule.get(startCount+2);
				if(totalScore < 100) {
					string2.replace(startSpace, startSpace+2, String.valueOf(totalScore));
				}
				if(totalScore >= 100) {
					string2.replace(startSpace, startSpace+3, String.valueOf(totalScore));
				}
				recordTotalScore.put(SaveCurrentFrame, totalScore);
				startSpace += 7; startCount++; SaveCurrentFrame++;
			}
			strike = 0;
			return;
		}
	}
	
	public void viewTotalScoreSpare(int space, String total) {
		
		if(strike > 0) {
			isStrike = false;
			//여기서  viewTotalScoreStrike에 space 넘기면서 호출 currentFrame도 저장하고 넘겨야 한다.
		}
		
		if(totalIntScore < 100) {
			string2.replace(space, space+2, total);
		}
		if(totalIntScore >= 100) {
			string2.replace(space, space+3, total);
		}
		
		
		
		return;
	}
	
	public void viewTotalScoreNonStrike(int space, String total) {
		
		if(totalIntScore < 100) {
			string2.replace(space, space+2, total);
		}
		if(totalIntScore >= 100) {
			string2.replace(space, space+3, total);
		}
		return;
	}
	
	public void header() {
		System.out.println("| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |");
	}
	
	public void strikeFrame() {
		
	}
	
	public void spareFrame() {
		
	}
	
	
}
