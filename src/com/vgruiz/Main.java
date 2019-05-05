package com.vgruiz;

import java.util.Random;
import java.util.Scanner;

public class Main {

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		/**
		 * true = two computer players against themselves
		 * false = human player vs computer
		 */
		boolean gameMode = false;
		
		Computer computer;	
		Board board = new Board();
		System.out.println("INITIAL BOARD:");
		board.print();
		
		//System.out.println("starting minimax");
		if(gameMode == true) {
			
			Board cur = board;
			Boolean maxPlayer = true;
			while(!cur.isTerminal()) {
				computer = new Computer();
				cur = new Board(cur.board);
				cur = computer.MinimaxIterativeDeepening(cur, maxPlayer);
				System.out.println("Chosen move: ");
				cur.print();
				computer = null; //to free up all of the memory computer uses, trying to avoid an OutOfMemoryError
				System.gc();
				maxPlayer = !maxPlayer;
			}
			
			System.out.println("done");
			
		} else {
			//computer = new Computer();
			Board cur = board;
			Boolean maxPlayer = true;
			
			//get player order
			System.out.println("Who goes first? C for computer, O for opponent.");
			String firstPlayer = scanner.next();
			
			//get time limit
			System.out.println("Enter a time limit:");
			double timeLimit = scanner.nextDouble();	
			
			while(!cur.isTerminal()) {
				computer = new Computer(timeLimit);
				cur = new Board(cur.board);
				System.out.println("Computer turn...");
				cur = computer.MinimaxIterativeDeepening(cur, maxPlayer);
				cur.print();
				computer = null;
				System.gc();

				System.out.println("Human turn...");
				while(!cur.oMove(getMove())) {
					//this is so the getMove() function repeats until there is a valid move
				}
				cur.print();
			}
			
		}
		
		System.out.println("done");
		
		
	}

	public static String getMove() {
		System.out.print("Enter move: ");
		String move = scanner.next();

		return move;
	}

}
