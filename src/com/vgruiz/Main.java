package com.vgruiz;

import java.util.Random;
import java.util.Scanner;

public class Main {

	static Scanner scanner = new Scanner(System.in);
	static Computer computer;
	static double timeLimit;

	public static void main(String[] args) {
		/**
		 * true = two computer players against themselves
		 * false = human player vs computer
		 */
		boolean gameMode = false;
		
		Board board = new Board();
		System.out.println("INITIAL BOARD:");
		board.print();
		
		//System.out.println("starting minimax");
		if(gameMode == true) {
			
			Board cur = board;
			Boolean maxPlayer = true;
			while(!cur.isTerminal()) {
				cur = computerMove(cur, maxPlayer);
				
//				computer = new Computer();
//				cur = new Board(cur.board);
//				cur = computer.MinimaxIterativeDeepening(cur, maxPlayer);
//				System.out.println("Chosen move: ");
//				cur.print();
//				computer = null; //to free up all of the memory computer uses, trying to avoid an OutOfMemoryError
//				System.gc();

				maxPlayer = !maxPlayer;
			}
			
			System.out.println("done");
			
		} else {
			//computer = new Computer();
			Board cur = board;
			Boolean maxPlayer = false;
			
			//get player order
			System.out.println("Who goes first? C for computer, O for opponent.");
			char firstPlayer = scanner.next().charAt(0);
			/**
			 * if Grant is going first, my program should initially
			 * be waiting for a move input.
			 */
			
			//get time limit
			System.out.println("Enter a time limit:");
			timeLimit = scanner.nextDouble();
			
			
			if(firstPlayer == 'c') {
				while(!cur.isTerminal()) {
					cur = computerMove(cur, true);
					playerMove(cur, false);					
				}
			} else {
				while(!cur.isTerminal()) {
					playerMove(cur, true);
					cur = computerMove(cur, false);
				}
			}
			
//			while(!cur.isTerminal()) {
//				computer = new Computer(timeLimit);
//				cur = new Board(cur.board);
//				System.out.println("Computer turn...");
//				cur = computer.MinimaxIterativeDeepening(cur, maxPlayer);
//				cur.print();
//				computer = null;
//				System.gc();
//
//				System.out.println("Human turn...");
//				while(!cur.oMove(getMove())) {
//					//this is so the getMove() function repeats until there is a valid move
//				}
//				cur.print();
//			}
			
			System.out.println("done");
		}
		
	}

	public static Board computerMove(Board board, boolean maxPlayer) {
		Board cur = board;
		computer = new Computer(timeLimit);
		cur = new Board(cur.board);
		
		System.out.println("Computer turn...");
		cur = computer.MinimaxIterativeDeepening(cur, maxPlayer);
		cur.print();
		computer = null;
		System.gc();
		return cur;
	}
	
	public static void playerMove(Board board, boolean isX) {
		Board cur = board;
		
		System.out.println("Human turn...");
		while(!cur.manualMove(isX, getMove())) {
			
		}
		
		cur.print();
	}
	
	public static String getMove() {
		System.out.print("Enter move: ");
		String move = scanner.next();

		return move;
	}

}
