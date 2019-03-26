package com.vgruiz;

public class Board {
	final int BOARD_SIZE = 8;

	/**
	 *	- is an empty square,
	 * 	# is a used square,
	 * 	X is the current position of the X player(the computer),
	 * 	O is the current position of the O player(the opponent).
	 */
	final char[] boardSymbols = {'-', '#', 'X', 'O'};
	
	char[][] board;
	
	//starting positions for a new board
	int xRow = 0, xCol = 0;
	int oRow = 7, oCol = 7;
	
	public Board() {
		board = new char[BOARD_SIZE][BOARD_SIZE];
		
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = boardSymbols[0];
			}
		}
		
		board[xRow][xCol] = boardSymbols[2];
		board[oRow][oCol] = boardSymbols[3];
	}
	
	public void print() {
		System.out.println("  1 2 3 4 5 6 7 8");
		
		for(int i = 0; i < BOARD_SIZE; i++) {
			switch(i) {
			case 0: System.out.print('A'); break;
			case 1: System.out.print('B'); break;
			case 2: System.out.print('C'); break;
			case 3: System.out.print('D'); break;
			case 4: System.out.print('E'); break;
			case 5: System.out.print('F'); break;
			case 6: System.out.print('G'); break;
			case 7: System.out.print('H');break;
			}
			for(int j = 0; j < BOARD_SIZE; j++) {
				System.out.print(" " + board[i][j]);
			}
			System.out.println();
		}
	}
	
}
