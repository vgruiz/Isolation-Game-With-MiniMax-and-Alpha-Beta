package com.vgruiz;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		
		board.print();
		
		while(true) {
			System.out.println("X");
			board.xMove(getMove());
			board.print();
			
			System.out.println("O");
			board.oMove(getMove());
			board.print();
		}
		
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