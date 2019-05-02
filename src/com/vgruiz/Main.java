package com.vgruiz;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		board.print();
		
		Computer computer = new Computer();		
		
		/**
		 * An array of the successor[] array indexes that represent the states that are in the decision PATH to the chosen terminal state
		 */
		int[] chosenStates = new int[board.BOARD_SIZE * board.BOARD_SIZE];
		
		int chosenStatesCounter = 0;
		
		
		System.out.println("starting minimax");
		
		//this board should now be the root node of the full tree
		board = computer.MinimaxDecision(board, true);
		
		//board.print(board.successors);
		
		Board cur = board;
		boolean maxPlay = true;
		int testVal;
		
		while(!cur.isTerminal()) {
			if(maxPlay) {
				testVal = Integer.MIN_VALUE;				
			} else {
				testVal = Integer.MAX_VALUE;
			}
			
			for(int i = 0; i < cur.successors.length; i++) {
				
				if(maxPlay) {
					testVal = Math.max(testVal, cur.successors[i].projectedValue);
				} else {
					testVal = Math.min(testVal, cur.successors[i].projectedValue);
				}
			}
			
			for(int i = 0; i < cur.successors.length; i++) {
				
				if(cur.successors[i].projectedValue == testVal) {
					chosenStates[chosenStatesCounter] = i;
					chosenStatesCounter++;
					cur = cur.successors[i];
					break;
				}
			}
			
			maxPlay = !maxPlay;
			
		}
		
		//printing the results
		board.print(); //initial board
		Board current = board;
		
		for(int i = 0; i < chosenStatesCounter; i++) {
			current.successors[chosenStates[i]].print();
			current = current.successors[chosenStates[i]];
		}
		
		
		
		
		
		
		
		
//		System.out.println("______");
		
//		Board cur = null;
//		cur = board;
//		boolean maxTurn = true;
//		
//		while(!cur.isTerminal()) {
//			cur = computer.MinimaxDecision(cur, maxTurn);
//			maxTurn = !maxTurn;
//			System.out.println("minimax complete, projected value: " + cur.projectedValue);
//			
//			//System.out.println("minimax complete, utility value: " + cur.getUtilityValue());
////			System.out.println("X score: " + cur.getXScore());
////			System.out.println("O score: " + cur.getOScore());
//			cur.print();
//		}
		
		System.out.println("done");
	}

	public static String getMove() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter move: ");
		String move = scanner.next();

		return move;
	}

}
