package com.vgruiz;

public class Board {
	final int BOARD_SIZE = 4;
	
	/**
	 * An array of the states that were are in the decision tree to the chosen terminal state
	 */
	Board[] chosenStates = new Board[BOARD_SIZE * BOARD_SIZE];
	
	int chosenStatesCounter = 0;
	
	/**
	 * Stores the successors of the current board state
	 */
	Board[] successors;
	/**
	 * As Computer.MinValue or .MaxValue returns v, the projected value will be passed to the parent board.
	 * projectedValue should not be -1.
	 */
	int projectedValue = -1000000;

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
	int oRow = BOARD_SIZE - 1, oCol = BOARD_SIZE - 1; //O is player

	public Board() {
		board = new char[BOARD_SIZE][BOARD_SIZE];

		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = boardSymbols[0];
			}
		}

		board[xRow][xCol] = boardSymbols[2];
		board[oRow][oCol] = boardSymbols[3];

		//Computer computer = new Computer();
	}

	public Board(char[][] newBoard){
		board = newBoard.clone();
		updateLocations();
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

		//check that it does not cross or land on an already used space, #
		if(oRow == tmpRow) {

			//go from smallest row to largest row
			int low, high;
			if(oRow < tmpRow){
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
		//System.out.println("  1 2 3 4 5 6 7 8");

		System.out.println("  1 2 3 4");
		
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

	public void print(Board[] boards) {
		for(int i = 0; i < boards.length; i++) {
			boards[i].print();
			boards[i].updateLocations();
			System.out.println("projected value: " +  boards[i].projectedValue);
			//System.out.println("utility: " + boards[i].getUtilityValue());
			//System.out.println("X score: " + boards[i].getXScore());
			//System.out.println("O score: " + boards[i].getOScore());
			System.out.println();
		}
	}
	
	// checks to see if a terminal state is reached (a winner is chosen)
	public boolean isTerminal() {
		if(getOScore() == 0 || getXScore() == 0)
			return true;
		else
			return false;
	}
	
	public char[][] clone(char[][] board) {
		int len = board.length;
		
		char[][] newBoard = new char[board.length][board.length];
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		
		return newBoard;
	}

	public void generateSuccessors(boolean isMaximizingPlayer){
		int counter = 0;
		Board[] boards;
		char currentChar;
		int row, col;
		
		if(isMaximizingPlayer == false) {
			boards = new Board[getOScore()];
			currentChar = boardSymbols[3];
			row = oRow;
			col = oCol;
		} else {
			boards = new Board[getXScore()];
			currentChar = boardSymbols[2];
			row = xRow;
			col = xCol;
		}
			
		char[][] newCharBoard = clone(board);
		
		// diagonal successors
		
		//down and right
		for(int i = row + 1, j = col + 1; i < BOARD_SIZE && j < BOARD_SIZE; i++, j++) {
			if(board[i][j] == boardSymbols[0]) {
				newCharBoard = clone(board);
				newCharBoard[i][j] = currentChar;
				newCharBoard[row][col] = boardSymbols[1];
				Board newBoard = new Board(newCharBoard);
				boards[counter] = newBoard;
				counter++;
			}
			else
				break;
		}

		//up and left
		for(int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
			if(board[i][j] == boardSymbols[0]) {
				newCharBoard = clone(board);
				newCharBoard[i][j] = currentChar;
				newCharBoard[row][col] = boardSymbols[1];
				Board newBoard = new Board(newCharBoard);
				boards[counter] = newBoard;
				counter++;
			}
			else
				break;
		}

		//up and right
		for(int i = row - 1, j = col + 1; i >= 0 && j < BOARD_SIZE; i--, j++) {
			if(board[i][j] == boardSymbols[0]) {
				newCharBoard = clone(board);
				newCharBoard[i][j] = currentChar;
				newCharBoard[row][col] = boardSymbols[1];
				Board newBoard = new Board(newCharBoard);
				boards[counter] = newBoard;
				counter++;
			}
			else
				break;
		}
		
		//down and left
		for(int i = row + 1, j = col - 1; i < BOARD_SIZE && j >= 0; i++, j--) {
			if(board[i][j] == boardSymbols[0]) {
				newCharBoard = clone(board);
				newCharBoard[i][j] = currentChar;
				newCharBoard[row][col] = boardSymbols[1];
				Board newBoard = new Board(newCharBoard);
				boards[counter] = newBoard;
				counter++;
			}
			else
				break;
		}
		

		// horizontal successors
		
		for(int i = row + 1; i < BOARD_SIZE; i++) {
			if(board[i][col] == boardSymbols[0]) {
				newCharBoard = clone(board);
				newCharBoard[i][col] = currentChar;
				newCharBoard[row][col] = boardSymbols[1];
				Board newBoard = new Board(newCharBoard);
				boards[counter] = newBoard;
				counter++;
			}
			else
				break;
		}
		
		for(int i = row - 1; i >= 0; i--) {
			if(board[i][col] == boardSymbols[0]) {
				newCharBoard = clone(board);
				newCharBoard[i][col] = currentChar;
				newCharBoard[row][col] = boardSymbols[1];
				Board newBoard = new Board(newCharBoard);
				boards[counter] = newBoard;
				counter++;
			}
			else
				break;
		}
		
		// vertical successors
		
		for(int i = col + 1; i < BOARD_SIZE; i++) {
			if(board[row][i] == boardSymbols[0]) {
				newCharBoard = clone(board);
				newCharBoard[row][i] = currentChar;
				newCharBoard[row][col] = boardSymbols[1];
				Board newBoard = new Board(newCharBoard);
				boards[counter] = newBoard;
				counter++;
			}
			else
				break;
		}
		
		for(int i = col - 1; i >= 0; i--) {
			if(board[row][i] == boardSymbols[0]) {
				newCharBoard = clone(board);
				newCharBoard[row][i] = currentChar;
				newCharBoard[row][col] = boardSymbols[1];
				Board newBoard = new Board(newCharBoard);
				boards[counter] = newBoard;
				counter++;
			}
			else
				break;
		}

		successors = boards;
	}

	/**
	 * Update xRow, xCol, oRow, oCol
	 */
	private void updateLocations() {
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				if(board[i][j] == boardSymbols[2]) {
					xRow = i;
					xCol = j;
				} else if(board[i][j] == boardSymbols[3]) {
					oRow = i;
					oCol = j;
				}
			}
		}
		
	}

	/**
	 *  
	 * @return the utility score for the board. X is the maximizing player.
	 */
	public int getUtilityValue() {
		return getXScore() - getOScore(); // can add aggression later on
	}

	/**
	 * 
	 * @return the number of available moves for O
	 */
	public int getOScore()
	{
		return getDiagonalValue(oRow, oCol) + getHorizontalValue(oRow, oCol) + getVerticalValue(oRow, oCol);
	}

	/**
	 *  
	 * @return the number of available moves for X
	 */
	public int getXScore()
	{
		return getDiagonalValue(xRow, xCol) + getHorizontalValue(xRow, xCol) + getVerticalValue(xRow, xCol);
	}

	/**
	 *  
	 * @param row
	 * @param col
	 * @return the number of moves diagonally for a given location of [row, col]
	 */
	private int getDiagonalValue(int row, int col)
	{
		int counter = 0;
		
		//going down and right
		for(int i = row + 1, j = col + 1; i < BOARD_SIZE && j < BOARD_SIZE; i++, j++) {
			if(board[i][j] == boardSymbols[0]) {
				counter++;
				//System.out.println("d0 " + i + " " + j);
			} else
				break;
		}
		
		//System.out.println("butts");
		
		//going up and left
		for(int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
			if(board[i][j] == boardSymbols[0]) {
				counter++;
				//System.out.println("d1 " + i + " " + j);
			} else
				break;
		}
		
		//System.out.println(counter);
		
		//going down and left
		for(int i = row + 1, j = col - 1; i < BOARD_SIZE && j >= 0; i++, j--) {
			if(board[i][j] == boardSymbols[0]) {
				counter++;
				//System.out.println("d2 " + i + " " + j);				
			} else
				break;
		}

		//System.out.println(counter);
		
		//going up and right
		for(int i = row - 1, j = col + 1; i >= 0 && j < BOARD_SIZE; i--, j++) {
			if(board[i][j] == boardSymbols[0]) {
				counter++;
				//System.out.println("d3 " + i + " " + j);
			} else
				break;
		}
		
		//System.out.println(counter);
		
		return counter;
	}

	/**
	 *  
	 * @param row
	 * @param col
	 * @return the number of moves vertically for a given location of [row, col]
	 */
	private int getVerticalValue(int row, int col)
	{
		int counter = 0;
		for(int i = row + 1; i < BOARD_SIZE; i++) {
			if(board[i][col] == boardSymbols[0]) {
				counter++;
				//System.out.println("v0");
			} else
				break;
		}
		
		//System.out.println(counter);
		
		for(int i = row - 1; i >= 0; i--) {
			if(board[i][col] == boardSymbols[0]) {
				counter++;
				//System.out.println("v1");
			} else
				break;
		}
		
		//System.out.println(counter);
		
		return counter;
	}

	/**
	 *  
	 * @param row
	 * @param col
	 * @return the number of moves horizontally for a given location of [row, col]
	 */
	private int getHorizontalValue(int row, int col)
	{
		int counter = 0;
		for(int i = col + 1; i < BOARD_SIZE; i++) {
			if(board[row][i] == boardSymbols[0]) {
				counter++;
				//System.out.println("h0");				
			} else
				break;
		}
		
		//System.out.println(counter);
		
		for(int i = col - 1; i >= 0; i--) {
			if(board[row][i] == boardSymbols[0]) {
				counter++;
				//System.out.println("h1");
			} else
				break;
		}
		
		//System.out.println(counter);
		
		return counter;
	}
}