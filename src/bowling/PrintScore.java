package bowling;

import java.util.ArrayList;
import java.util.Scanner;

public class PrintScore {

	public static String userName;
	public static int strikeNumb = 0;
	
	public static String input() {
		Scanner scan = new Scanner(System.in);
		String inputString = scan.nextLine();
		return inputString;
	}

	public static void inputUserName() {
		System.out.print("�÷��̾� �̸���(3 english letters)?: ");
		userName = input();
	}
	
	public static int inputScore() {
		System.out.print(userName+"'s score : ");
		String userScoreString = input();
		int userScoreInteger = Integer.parseInt(userScoreString);
		return userScoreInteger;
	}
	
	public static StringBuilder scoreHeader = new StringBuilder("| NAME |  01  |  02  |  03  |  04  |  05  |  06  |  07  |  08  |  09  |  10  |");
	public static StringBuilder scoreBoard = new StringBuilder("|  PJS |      |      |      |      |      |      |      |      |      |      |");
	public static StringBuilder scoreTotal = new StringBuilder("|      |      |      |      |      |      |      |      |      |      |      |");
	
	public static void printScoreBored(int frame, int pinsDown, boolean isSameFrame, int lastRoll) {
		int addSpace = 10 + (frame * 7);
		if(pinsDown == 10) scoreBoard.replace(addSpace, addSpace+1, "X");

		if(isSameFrame == false) {
			if(pinsDown < 10) scoreBoard.replace(addSpace, addSpace+1, String.valueOf(pinsDown));
		}
		
		if(isSameFrame) {
			if(lastRoll + pinsDown == 10) scoreBoard.replace(addSpace+1, addSpace+3, "|/");
			if(lastRoll + pinsDown < 10) scoreBoard.replace(addSpace+1, addSpace+3, "|"+String.valueOf(pinsDown));
			if(lastRoll + pinsDown < 10 && pinsDown == 0) scoreBoard.replace(addSpace+1, addSpace+3, "|-");
		}
	}
	
	public static void printScoreBoredLastFrame(int frame, int pinsDown, boolean isSameFrame, int lastRoll) {
		int addSpace = 72;
		if(pinsDown == 10 && strikeNumb == 1) scoreBoard.replace(addSpace+strikeNumb, addSpace+strikeNumb+2, "|X");
		if(pinsDown == 10 && strikeNumb == 2) scoreBoard.replace(addSpace+strikeNumb+1, addSpace+strikeNumb+3, "|X");
		if(pinsDown == 10) scoreBoard.replace(addSpace, addSpace+1, "X"); strikeNumb++;
		
		if(isSameFrame == false) {
			if(pinsDown < 10) scoreBoard.replace(addSpace, addSpace+1, String.valueOf(pinsDown));
		}
		
		if(isSameFrame) {
			if(lastRoll + pinsDown == 10) scoreBoard.replace(addSpace+1, addSpace+3, "|/");
			if(lastRoll + pinsDown < 10) scoreBoard.replace(addSpace+1, addSpace+3, "|"+String.valueOf(pinsDown));
			if(lastRoll + pinsDown < 10 && pinsDown == 0) scoreBoard.replace(addSpace+1, addSpace+3, "|-");
		}
	}
	
	public static void printTotalScore(int frame, int totalScore) {
		int addSpace = 10 + (frame * 7);
		if(totalScore < 10) scoreTotal.replace(addSpace, addSpace+1, String.valueOf(totalScore));
		if(totalScore > 10) scoreTotal.replace(addSpace,   (totalScore < 100) ? addSpace+2 : addSpace+3,   String.valueOf(totalScore));
	}
	
	public static void printScore() {
		System.out.println(scoreHeader);
		System.out.println(scoreBoard);
		System.out.println(scoreTotal);
	}
}
