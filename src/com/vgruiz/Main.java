package com.vgruiz;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		board.print();
		
		Computer computer = new Computer();
		
//		board.generateSuccessors(false);
//		
//		board.successors[0].print();
		
//		System.out.println(board.successors[0].getXScore());
		
//		Board board2 = computer.MinimaxDecision(board, false);
//		System.out.println("minimax complete, utility value: " + board2.getUtilityValue());
//		//System.out.println("minimax complete, \nprojected value: " + board2.projectedValue + "\nutility value: " + board2.getUtilityValue());
//		board2.print();
//		
//		Board board3 = computer.MinimaxDecision(board2, true);
//		System.out.println("minimax complete, utility value: " + board3.getUtilityValue());
//		board3.print();
//		
		
		
		System.out.println("starting minimax");
		
		Board cur = null;
		cur = board;
		boolean maxTurn = true;
		
		while(!cur.isTerminal()) {
			cur = computer.MinimaxDecision(cur, maxTurn);
			maxTurn = !maxTurn;
			System.out.println("minimax complete, utility value: " + cur.getUtilityValue());
			cur.print();
		}
		
		System.out.println("done");
		
		//board2.print();

		//Player is asked who goes first
		//Player goes, board accepts move, updates
		//Computer goes- uses AlphaBetaSearch to make decision, board accepts move, updates
		//repeat until board is in a terminal state

	}

	public static String getMove() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter move: ");
		String move = scanner.next();

		return move;
	}

}
