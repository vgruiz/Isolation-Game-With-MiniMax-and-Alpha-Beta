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
	
	//positions for a new board
	//initially at start positions as specified
	int xRow = 0, xCol = 0; //X is computer
	int oRow = 7, oCol = 7; //O is player 
	
	public Board() {
		board = new char[BOARD_SIZE][BOARD_SIZE];
		
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = boardSymbols[0];
			}
		}
		
		board[xRow][xCol] = boardSymbols[2];
		board[oRow][oCol] = boardSymbols[3];
		
		Computer computer = new Computer();
	}
	
	public void xMove(String move /* Computer object will run AlphaBetaSearch, which will provide a legal move */) {
		board[xRow][xCol] = boardSymbols[1]; //old position to be marked with a # symbol
		
		//interpret move string
		char[] moveArray = move.toCharArray();
		
		switch(moveArray[0]) {
		case 'A': xRow = 0; break;
		case 'B': xRow = 1; break;
		case 'C': xRow = 2; break;
		case 'D': xRow = 3; break;
		case 'E': xRow = 4; break;
		case 'F': xRow = 5; break;
		case 'G': xRow = 6; break;
		case 'H': xRow = 7; break;
		}

		xCol = Character.getNumericValue(moveArray[1]) - 1;
		
		//perform changes to board
		board[xRow][xCol] = boardSymbols[2];
		
	}
	
	public void oMove(String move) {
		//interpret string, determine new row and col
		int tmpRow = -1, tmpCol = -1;
		
		char[] moveArray = move.toCharArray();
		
		switch(moveArray[0]) {
		case 'A': tmpRow = 0; break;
		case 'B': tmpRow = 1; break;
		case 'C': tmpRow = 2; break;
		case 'D': tmpRow = 3; break;
		case 'E': tmpRow = 4; break;
		case 'F': tmpRow = 5; break;
		case 'G': tmpRow = 6; break;
		case 'H': tmpRow = 7; break;
		}
		
		tmpCol = Character.getNumericValue(moveArray[1]) - 1;
		
		//determines if the new move is valid
			//make sure it is not in the exact same spot
		if(oRow == tmpRow && oCol == tmpCol) {
			System.out.println("This move is invalid - the player is already in that location");
			return;
		}
		
			//check if it is a valid direction
		if(!((oRow == tmpRow) /*horizontal move*/ || 
				(oCol == tmpCol) /*vertical move*/ || 
				(Math.abs(tmpRow - oRow) == Math.abs(tmpCol - oCol)) /*diagonal move*/ )) {
			
			System.out.println("This move is invalid - move is not in a valid direction");
			return;
		}
		
			//check check that it does not cross or land on an already used space, #
		if(oRow == tmpRow) {
			
			//go from smallest row to largest row
			int low, high;
			if(oRow < tmpRow) {
				low = oRow;
				high = tmpRow;
			} else {
				low = tmpRow;
				high = oRow;
			}
			
			//perform a check for # at each location
			for(int i = low; i < high - low; i++) {
				if(board[oRow][i] == boardSymbols[1]) {
					//found a # symbol on the way to the new move or at the new location
					System.out.println("This move is invalid - crosses paths with #");
					return;
				}
			}
			
		} else if(oCol == tmpCol) {
			
			//go from smallest col to largest col
			int low, high;
			if(oCol < tmpCol) {
				low = oCol;
				high = tmpCol;
			} else {
				low = tmpCol;
				high = oCol;
			}
			
			//perform a check for # at each location
			for(int i = low; i < high - low; i++) {
				if(board[i][oCol] == boardSymbols[1]) {
					//found a # symbol on the way to the new move or at the new location
					System.out.println("This move is invalid - crosses paths with #");
					return;
				}
			}
			
		} else if(Math.abs(tmpRow - oRow) == Math.abs(tmpCol - oCol)) {
			int difference = Math.abs(tmpRow - oRow);
			
			//determine direction of movement
			boolean rowSign = (tmpRow - oRow) > 0; //going in the direction towards tmpRow, is tmpRow at a greater row than the current position oRow. true +, false -
			boolean colSign = (tmpCol - oCol) > 0;
			
			//check for # in that direction only
			if(rowSign) {
				if(colSign) {	// (+, +)
					
					for(int i = 1; i < difference + 1; i++) {
						if(board[oRow + i][oCol + i] == boardSymbols[1]) {
							System.out.println("This move is invalid - crosses paths with #");
							return;
						}
					}
					
				} else {		// (+, -)
					
					for(int i = 1; i < difference + 1; i++) {
						if(board[oRow + i][oCol - i] == boardSymbols[1]) {
							System.out.println("This move is invalid - crosses paths with #");
							return;
						}
					}
					
				}
			} else {
				if(colSign) {	// (-, +)
					
					for(int i = 1; i < difference + 1; i++) {
						if(board[oRow - i][oCol + i] == boardSymbols[1]) {
							System.out.println("This move is invalid - crosses paths with #");
							return;
						}
					}
					
				} else {		// (-, -)
					
					for(int i = 1; i < difference + 1; i++) {
						if(board[oRow - i][oCol - i] == boardSymbols[1]) {
							System.out.println("This move is invalid - crosses paths with #");
							return;
						}
					}
					
				}
			}
		}
		
		
		//performs changes to board if valid
		System.out.println("If you made it to this point, that move was valid!!!");
		
		board[oRow][oCol] = boardSymbols[1]; //#
		
		oRow = tmpRow;
		oCol = tmpCol;
		
		board[oRow][oCol] = boardSymbols[3]; //O
		
	}
	
	/**
	 *  Print the board to the project's specifications
	 */
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
		System.out.println();
	}

	public boolean isTerminal() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getUtilityValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}