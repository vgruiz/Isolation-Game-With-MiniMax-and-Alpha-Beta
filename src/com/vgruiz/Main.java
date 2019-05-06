package com.vgruiz;

import java.util.Scanner;

public class Main {

	static Scanner scanner = new Scanner(System.in);
	static Computer computer;
	static double timeLimit;
	static int moveCount = 0;

	//to track the history of moves, to be printed
	static String[] computerMoves = new String[32];
	static String[] opponentMoves = new String[32];

	public static void main(String[] args) {
		/**
		 * true = two computer players against themselves
		 * false = human player vs computer
		 */
		boolean gameMode = false;
		
		Board board = new Board();
		System.out.println("Initial game board:");
		board.print();
		
		
		if(gameMode == true) {
			
			Board cur = board;
			Boolean maxPlayer = true;
			while(!cur.isTerminal()) {
				cur = computerMove(cur, maxPlayer);
				cur.print();
				maxPlayer = !maxPlayer;
			}
			
			System.out.println("done");
			
		} else {
			Board cur = board;
			Boolean maxPlayer = false;
			
			//get player order
			System.out.println("Who goes first? C for computer, O for opponent.");
			char firstPlayer = scanner.next().charAt(0);

			
			//get time limit
			System.out.println("Enter a time limit:");
			timeLimit = scanner.nextDouble();
			System.out.println("\n");
			
			
			if(firstPlayer == 'c') {
				while(!cur.isTerminal()) {
					cur = computerMove(cur, true);
					cur.print(computerMoves, opponentMoves);
					
					playerMove(cur, false);
					cur.print(computerMoves, opponentMoves);
					
					moveCount++;
				}
			} else {
				while(!cur.isTerminal()) {
					playerMove(cur, true);
					cur.print(computerMoves, opponentMoves);
					
					cur = computerMove(cur, false);
					cur.print(computerMoves, opponentMoves);
					
					moveCount++;
				}
			}
			
			System.out.println("done");
		}
		
	}
	
	/**
	 * Takes the computers most recent move and places it in the computerMoves array, to later be printed
	 * @param cur
	 * @param isX
	 */
	public static void recordComputerMove(Board cur, boolean isX) {
		if(isX) {
			char[] xChars = {getRow(cur.xRow), (char) (cur.xCol + '0' + 1)};
			String xMove = String.valueOf(xChars);
			System.out.println("Computer's move is: " + xMove + "\n");
			computerMoves[moveCount] = xMove;
			
		} else {
			char[] oChars = {getRow(cur.oRow), (char) (cur.oCol + '0' + 1)};
			String oMove = String.valueOf(oChars);
			System.out.println("Computer's move is: " + oMove + "\n");
			computerMoves[moveCount] = oMove;
		}
		
	}
	
	/**
	 * Takes the opponents most recent move and places it in the opponentMoves array, to later be printed
	 * @param cur
	 * @param isX
	 */
	public static void recordOpponentMove(Board cur, boolean isX) {
		if(isX) {
			char[] xChars = {getRow(cur.xRow), (char) (cur.xCol + '0' + 1)};
			String xMove = String.valueOf(xChars);
			//System.out.println("This is X move: " + xMove);
			opponentMoves[moveCount] = xMove;
		} else {
			char[] oChars = {getRow(cur.oRow), (char) (cur.oCol + '0' + 1)};
			String oMove = String.valueOf(oChars);
			//System.out.println("This is O move: " + oMove);
			opponentMoves[moveCount] = oMove;
		}
	}

	/**
	 * Performs a move by the computer. The character (X or O) is decided by the maxPlayer boolean.
	 * @param board
	 * @param maxPlayer
	 * @return
	 */
	public static Board computerMove(Board board, boolean maxPlayer) {
		Board cur = board;
		computer = new Computer(timeLimit);
		cur = new Board(cur.board);
		
		cur = computer.MinimaxIterativeDeepening(cur, maxPlayer);
		computer = null;
		System.gc();
		recordComputerMove(cur, maxPlayer);
		return cur;
	}
	
	/**
	 * Performs a move on the character (X or O) that is decided by the opponents computer.
	 * @param board
	 * @param isX
	 */
	public static void playerMove(Board board, boolean isX) {
		Board cur = board;
		while(!cur.manualMove(isX, getMove())) {
			
		}
		recordOpponentMove(cur, isX);
	}
	
	/**
	 * Prompts the user to enter a move.
	 * @return
	 */
	public static String getMove() {
		System.out.print("Enter move: ");
		String move = scanner.next();

		System.out.println();
		return move;
	}
	
	/**
	 * Converts a row value to the letter representation.
	 * @param row
	 * @return
	 */
	public static char getRow(int row) {
		char answer = 'Z';
		
		switch(row) {
		case 0: answer = 'A'; break;
		case 1: answer = 'B'; break;
		case 2: answer = 'C'; break;
		case 3: answer = 'D'; break;
		case 4: answer = 'E'; break;
		case 5: answer = 'F'; break;
		case 6: answer = 'G'; break;
		case 7: answer = 'H'; break;
		}
		
		return answer;
	}

}
