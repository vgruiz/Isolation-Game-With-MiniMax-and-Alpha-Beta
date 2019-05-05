package com.vgruiz;

import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Computer computer = new Computer();		
		Board board = new Board();
		board.print();
		
		System.out.println("starting minimax");
		
		//this board should now be the root node of the full tree
		board = computer.MinimaxIterativeDeepening(board, true);
		
		board.print();
		
		System.out.println("done");
	}

	public static String getMove() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter move: ");
		String move = scanner.next();

		return move;
	}

}
