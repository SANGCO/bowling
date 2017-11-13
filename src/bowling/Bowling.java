package bowling;

import java.util.HashMap;
import java.util.Scanner;

public class Bowling {

	StringBuilder scoreBoard = new StringBuilder("|  PJS |      |      |      |      |      |      |      |      |      |      |");
	StringBuilder totalScoreBoard = new StringBuilder("|      |      |      |      |      |      |      |      |      |      |      |");
	
	HashMap<Integer, Integer> thisStageScore = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> totalScore = new HashMap<Integer, Integer>();
	
	int currentFrame = 1;
	int count = 1;
	int strike = 0;
	int spare = 0;
	int findPosition = 10;
	String user;
	String inputScoreString;
	int inputScoreInt;
	
	Scanner scan = new Scanner(System.in);

	public void header() {
		System.out.println("| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |");
		return;
	}

	public void startBowling() {
		System.out.print("플레이어 이름은(3 english letters)?: ");
		user = scan.nextLine();
		return;
	}
	
	public void variablePlus() {
		findPosition += 7; 
		count++;
		currentFrame++;
		return;
	}
	
	public void printTotalScoreNomal() {
		int currentTotalScore = 0; 
		if(totalScore.get(currentFrame - 1) != null) currentTotalScore = totalScore.get(currentFrame - 1);
		int localTotalScore = currentTotalScore + thisStageScore.get(count-1) + thisStageScore.get(count);
		if(localTotalScore < 10) totalScoreBoard.replace(findPosition, findPosition, String.valueOf(localTotalScore));
		totalScoreBoard.replace(findPosition, (localTotalScore < 100) ? findPosition+2 : findPosition+3, String.valueOf(localTotalScore));
		totalScore.put(currentFrame, localTotalScore);
		System.out.println(totalScoreBoard.toString());
		return;
	}
	
	public void printTotalScoreSpare() {
		int findPastCount = count - (spare + 1);      int findPastSpace = findPosition - (spare * 7);      int findPastFrame = currentFrame - spare;      int preTotalScore = 0;	
		
		if(totalScore.get(findPastFrame - 1) != null) preTotalScore = totalScore.get(findPastFrame - 1);
		
		int localTotalScore = preTotalScore + thisStageScore.get(findPastCount) + thisStageScore.get(findPastCount+1) + inputScoreInt;
		totalScoreBoard.replace(findPastSpace,   (localTotalScore < 100) ? findPastSpace+2 : findPastSpace+3,    String.valueOf(localTotalScore));
		totalScore.put(findPastFrame, localTotalScore);
		spare = 0;   return;
	}
	
	public void printTotalScoreStrike() {
		int findPastCount = count - (strike + 1);      int findPastSpace = findPosition - (strike * 7);      int findPastFrame = currentFrame - strike;      int preTotalScore = 0;	
		
		for (int i = 0; i < strike; i++) {
			if(totalScore.get(findPastFrame - 1) != null) preTotalScore = totalScore.get(findPastFrame - 1);
			
			int localTotalScore = preTotalScore + thisStageScore.get(findPastCount) + thisStageScore.get(findPastCount+1) + thisStageScore.get(findPastCount+2);
			totalScoreBoard.replace(findPastSpace,   (localTotalScore < 100) ? findPastSpace+2 : findPastSpace+3,    String.valueOf(localTotalScore));
			totalScore.put(findPastFrame, localTotalScore);
			
			findPastSpace+=7;      findPastCount++;      findPastFrame++;
		}
		strike = 0;   return;
	}
	
	public void printNotStrikeSignNumb() {
		scoreBoard.replace(findPosition, findPosition+1, inputScoreString);
		System.out.println(scoreBoard.toString());
		thisStageScore.put(count, inputScoreInt);
		return;
	}
	
	public void printSpareSign() {
		scoreBoard.replace(findPosition+1, findPosition+3, "|/");
		System.out.println(scoreBoard.toString());
		return;
	}
	
	public void printNotSpareSign() {
		if(inputScoreInt == 0) scoreBoard.replace(findPosition+1, findPosition+3, "|-");
		if(inputScoreInt != 0) scoreBoard.replace(findPosition+1, findPosition+3, "|"+inputScoreString);
		System.out.println(scoreBoard.toString());
		return;
	}
	
	public void spare() {
		spare++;
		header();
		printSpareSign();
		if(strike != 0) printTotalScoreStrike();
		printEmptyScore();
		return;
	}
	
	public void notSpare() {
		header();
		printNotSpareSign();
		if(strike != 0) printTotalScoreStrike();
		printTotalScoreNomal();
		return;
	}
	
	public void isSpareOrNot() {
		if((thisStageScore.get(count-1)+inputScoreInt) == 10) {
			spare();
		}
		if((thisStageScore.get(count-1)+inputScoreInt) < 10) {
			notSpare();
		}
		return;
	}
	
	public void goNextStage() {
		count++;
		inputUserScore();
		thisStageScore.put(count, inputScoreInt);
		isSpareOrNot();
		variablePlus();
		return;
	}
	
	public void NotStrike() {
		if(spare != 0) printTotalScoreSpare();
		header();
		printNotStrikeSignNumb();
		printEmptyScore();
		goNextStage();
		return;
	}
	
	public void printStrikeSign() {
		scoreBoard.replace(findPosition, findPosition+1, "X");
		System.out.println(scoreBoard.toString());
		thisStageScore.put(count, inputScoreInt);
		return;
	}

	public void printEmptyScore() {
		System.out.println(totalScoreBoard.toString());
		return;
	}
	
	public void Strike() {
		strike++;
		header();
		printStrikeSign();
		printEmptyScore();
		variablePlus();
		return;
	}
	
	public void isStrikeOrNot(int inputScoreInt) {
		if(inputScoreInt == 10) {
			Strike();
		}
		if(inputScoreInt < 10) {
			NotStrike();
		}
		return;
	}
	
	public void inputUserScore() {
		System.out.print(user+"'s score : ");
		inputScoreString = scan.nextLine();
		inputScoreInt = Integer.parseInt(inputScoreString);
		return;
	}
	
	public void start() {
		startBowling();
		while(currentFrame <= 10) {
			inputUserScore();
			isStrikeOrNot(inputScoreInt);			
		}
	}
}