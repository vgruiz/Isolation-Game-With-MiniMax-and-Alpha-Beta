package com.vgruiz;

public class Board {
	/**
	 * The board size. Used for many calculations throughout this class.
	 */
	final int BOARD_SIZE = 8;
	
	/**
	 * Stores the successors of the current board state
	 */
	Board[] successors;
	
	/**
	 * As Computer.MinValue or .MaxValue returns v, the projected value will be passed to the parent board.
	 * projectedValue should not be -1000000.
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
	int xRow = 0, xCol = 0;
	int oRow = BOARD_SIZE - 1, oCol = BOARD_SIZE - 1;

	/**
	 * Constructor that is used initially.
	 */
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

	/**
	 * Constructor that creates a board object with the passed char matrix.
	 * @param newBoard
	 */
	public Board(char[][] newBoard){
		board = newBoard.clone();
		updateLocations();
	}

	/**
	 * Handles manual player moves for either X or O.
	 * @param isX
	 * @param move
	 * @return
	 */
	public boolean manualMove(boolean isX, String move) {
		
		//set curRow and curCol
		int curRow, curCol;
		char opposingPlayer;
		if(isX) {
			curRow = xRow;
			curCol = xCol;
			opposingPlayer = boardSymbols[3];
		} else {
			curRow = oRow;
			curCol = oCol;
			opposingPlayer = boardSymbols[2];
		}
		
		//interpret move string, determine new row and col
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
		//make sure it is not in the exact same spot or in the spot of the opponent
		if(oRow == tmpRow && oCol == tmpCol || xRow == tmpRow && xCol == tmpCol) {
			System.out.println("This move is invalid - there is a player already in that location");
			return false;
		}

		//check if it is a valid direction
		if(!((curRow == tmpRow) /*horizontal move*/ ||
				(curCol == tmpCol) /*vertical move*/ ||
				(Math.abs(tmpRow - curRow) == Math.abs(tmpCol - curCol)) /*diagonal move*/ )) {
			
			System.out.println("This move is invalid - move is not in a valid direction");
			return false;
		}
	
		//check that it does not cross or land on an already used space, #
		if(curRow == tmpRow) { //if curRow == tmpRow, this is a horizontal move

			//go from smallest row to largest row
			int low, high;
			if(curCol < tmpCol){
				low = curCol;
				high = tmpCol;
			} else {
				low = tmpCol;
				high = curCol;
			}

			//perform a check for # at each location
			for(int i = low; i < low + (high - low) + 1; i++) {

				//found a # symbol on the way to the new move or at the new location
				if(board[curRow][i] == boardSymbols[1]) {
					System.out.println("This move is invalid - crosses paths with #");
					return false;
				} else if(board[curRow][i] == opposingPlayer) {
					System.out.println("This move is invalid - crosses paths with the opposing player");
					return false;
				}
			}
		} else if(curCol == tmpCol) { //if oCol == tmpCol, this is a vertical move

			//go from smallest col to largest col
			int low, high;
			if(curRow < tmpRow) {
				low = curRow; //0
				high = tmpRow; //7
			} else {
				low = tmpRow;
				high = curRow;
			}

			//perform a check for # at each location
			for(int i = low; i < low + (high - low) + 1; i++) {
				if(board[i][curCol] == boardSymbols[1]) {
					//found a # symbol on the way to the new move or at the new location
					System.out.println("This move is invalid - crosses paths with #");
					return false;
				} else if(board[i][curCol] == opposingPlayer) {
					System.out.println("This move is invalid - crosses paths with the opposing player");
					return false;
				}
			}

		} else if(Math.abs(tmpRow - curRow) == Math.abs(tmpCol - curCol)) {
			int difference = Math.abs(tmpRow - curRow);

			//determine direction of movement
			boolean rowSign = (tmpRow - curRow) > 0; //going in the direction towards tmpRow, is tmpRow at a greater row than the current position oRow. true +, false -
			boolean colSign = (tmpCol - curCol) > 0;

			//check for # in that direction only
			if(rowSign) {
				if(colSign) {	// (+, +)

					for(int i = 1; i < difference + 1; i++) {
						if(board[curRow + i][curCol + i] == boardSymbols[1]) {
							System.out.println("This move is invalid - crosses paths with #");
							return false;
						} else if(board[curRow + i][curCol + i] == boardSymbols[2]) {
							System.out.println("This move is invalid - crosses paths with the opposing player");
							return false;
						}
					}

				} else {		// (+, -)

					for(int i = 1; i < difference + 1; i++) {
						if(board[curRow + i][curCol - i] == boardSymbols[1]) {
							System.out.println("This move is invalid - crosses paths with #");
							return false;
						} else if(board[curRow + i][curCol - i] == boardSymbols[2]) {
							System.out.println("This move is invalid - crosses paths with the opposing player");
							return false;
						}
					}

				}
			} else {
				if(colSign) {	// (-, +)

					for(int i = 1; i < difference + 1; i++) {
						if(board[curRow - i][curCol + i] == boardSymbols[1]) {
							System.out.println("This move is invalid - crosses paths with #");
							return false;
						} else if(board[curRow - i][curCol + i] == boardSymbols[2]) {
							System.out.println("This move is invalid - crosses paths with the opposing player");
							return false;
						}
					}

				} else {		// (-, -)

					for(int i = 1; i < difference + 1; i++) {
						if(board[curRow - i][curCol - i] == boardSymbols[1]) {
							System.out.println("This move is invalid - crosses paths with #");
							return false;
						} else if(board[curRow - i][curCol - i] == boardSymbols[2]) {
							System.out.println("This move is invalid - crosses paths with the opposing player");
							return false;
						}
					}

				}
			}
		}	
		
		//performs changes to board if valid
		//System.out.println("Valid Move");

		board[curRow][curCol] = boardSymbols[1]; //#

		if(isX) {
			xRow = tmpRow;
			xCol = tmpCol;
			board[xRow][xCol] = boardSymbols[2];
		} else {
			oRow = tmpRow;
			oCol = tmpCol;			
			board[oRow][oCol] = boardSymbols[3]; //O
		}

		return true;
	}

	/**
	 *  Print the board.
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
				
				//print the computer v opponent moves here
			}
			
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 *  Print the board with the computer and opponent moves
	 */
	public void print(String[] computerMoves, String[] opponentMoves) {
		System.out.println("  1 2 3 4 5 6 7 8	   Computer vs. Opponent");
		
		for(int i = 0; i < computerMoves.length; i++) {
			
			if (i < BOARD_SIZE) {
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
			}
			
			//print the computer v opponent moves here
			if(i >= BOARD_SIZE && computerMoves[i] == null && opponentMoves[i] == null) {
				break;
			}
			
			if(i >= BOARD_SIZE) {
				System.out.print("		");
			}
			
			if(computerMoves[i] != null) {
				System.out.print("	" + (i+1) + ". " + computerMoves[i]);
			} else {
				System.out.print("	");
			}
			
			if(opponentMoves[i] != null) {
				System.out.println("		" + opponentMoves[i]);
			} else {
				System.out.println();
			}

			//System.out.println();
		}
		System.out.println("--------------------------------------------------");
		System.out.println();
	}

	/**
	 * Print the array of boards that is passed as the argument. Primarily used to print the full board.successors array.
	 * @param boards
	 */
	public void print(Board[] boards) {
		for(int i = 0; i < boards.length; i++) {
			boards[i].print();
			boards[i].updateLocations();
			//System.out.println("projected value: " +  boards[i].projectedValue);
			//System.out.println("utility: " + boards[i].getUtilityValue());
			//System.out.println("X score: " + boards[i].getXScore());
			//System.out.println("O score: " + boards[i].getOScore());
			System.out.println();
		}
	}
	
	/**
	 * Checks to see if a terminal state is reached
	 * @return
	 */
	public boolean isTerminal() {
		if(getOScore() == 0 || getXScore() == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Clones the char matrix that represents the board.
	 * @param board
	 * @return
	 */
	public char[][] clone(char[][] board) {		
		char[][] newBoard = new char[board.length][board.length];
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		
		return newBoard;
	}

	/**
	 * Generates all successors of the current board.
	 * @param isMaximizingPlayer
	 */
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
		
		// 						diagonal successors
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
		

		// 							horizontal successors
		//to the right
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
		
		//to the left
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
		
		// 							vertical successors
		//down
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
		
		//up
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

		successors = boards;
	}

	/**
	 * Update xRow, xCol, oRow, oCol.
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
	 * Gets number of empty spaces diagonal to the given (row, col) position
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
	 * Gets number of empty spaces directly vertical to the given (row, col) position 
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
	 * Gets number of empty spaces directly horizontal to the given (row, col) position
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