package com.vgruiz;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		board.print();
		
		Computer computer = new Computer();		
		
		System.out.println("starting minimax");
		
		board = computer.MinimaxDecision(board, true);
		
		board.print(board.successors);
		
		System.out.println("______");
		
//		Board cur = null;
//		cur = board;
//		boolean maxTurn = true;
//		
//		while(!cur.isTerminal()) {
//			cur = computer.MinimaxDecision(cur, maxTurn);
//			maxTurn = !maxTurn;
//			System.out.println("minimax complete, utility value: " + cur.getUtilityValue());
////			System.out.println("X score: " + cur.getXScore());
////			System.out.println("O score: " + cur.getOScore());
//			cur.print();
//		}
//		
		System.out.println("done");
	}

	public static String getMove() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter move: ");
		String move = scanner.next();

		return move;
	}

}
