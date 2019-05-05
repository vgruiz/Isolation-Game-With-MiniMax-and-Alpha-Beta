package com.vgruiz;

import java.util.Random;

public class Computer {
	int cnt = 0;

	/**
	 * This stores the immediate successors of the current state, to choose the successor that's best for the current player.
	 */
	Board[] immediateSuccessors;
	int[] chosenStates;
	int chosenStatesCounter;
	long startTime;
	long curTime;
	double diffTime;
	
	/**
	 * Get's assigned the next move from the root node and gets updated for each iteration of MinimaxDecision at different depths
	 */
	Board nextState;
	
	public Board MinimaxIterativeDeepening(Board state, boolean isMaximizingPlayer) {
		startTime = System.currentTimeMillis();
		Board cur = state;		
		
//		try {
			for(int i = 0; i < 20; i++) {
				cur = MinimaxDecision(cur, i, true);
				System.out.println("________________"+diffTime+"________" + i);
			}
//		} catch (NullPointerException n) {
//			System.out.println("NullPointerException caught, returning the best nextState.");
//			return nextState;
//		}
		
		return cur;
	}
	
	public Board MinimaxDecision(Board state, int depth, boolean isMaximizingPlayer) {
		int v;
		
		if(isMaximizingPlayer) {
			v = MaxValue(state, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, isMaximizingPlayer);			
		} else {
			//if isMaximizingPlayer == false, start with MinValue for the O move
			v = MinValue(state, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, isMaximizingPlayer);	
		}
		
		chosenStates = new int[state.BOARD_SIZE * state.BOARD_SIZE];
		chosenStatesCounter = 0;
		
		///////////////////////
		//at this point, state should represent the root node of a complete tree
		///////////////////////
		Board cur = state;
		boolean maxPlayer = isMaximizingPlayer;
		int testVal;
		
		while(!cur.isTerminal() && depth > 0) {
			depth--;
			
			//setting the initial testVal based on which players turn it is
			if(maxPlayer) {
				testVal = Integer.MIN_VALUE;
			} else {
				testVal = Integer.MAX_VALUE;
			}
			
			//if maxPlayer, find the max projected value in it's successors and set it to testVal
			//if !maxPlayer, find the min "						"
			for(int i = 0; i < cur.successors.length; i++) {
				if(maxPlayer) {
					testVal = Math.max(testVal, cur.successors[i].projectedValue);
				} else {
					testVal = Math.min(testVal, cur.successors[i].projectedValue);
				}
			}
			
			//finding the first successor with projectedValue == testVal
			//putting the index of that successor in the chosenStates array
			//setting cur to that successor
			for(int i = 0; i < cur.successors.length; i++) {
				if(cur.successors[i].projectedValue == testVal) {
					chosenStates[chosenStatesCounter] = i;
					chosenStatesCounter++;
					cur = cur.successors[i];
					break;
				}
			}
			
			//count how many projectedValues are the same as testVal
			//pick a random int between zero and the value above
			//search through the successors again, and every time you come across a value equal to testVal, increment a counter until 
			//it equals the random int, then assign that
//			Random random = new Random();
//			int numOptions = 0;
//			
//			for(int i = 0; i < cur.successors.length; i++) {
//				if(cur.successors[i].projectedValue == testVal) {
//					numOptions++;
//				}
//			}
//			
//			int randomInt = random.nextInt(numOptions) + 1; //+1 because the next loop increments on the first encounter
//			
//			int cnt = 0;
//			for(int i = 0; i < cur.successors.length; i++) {
//				
//				if(cur.successors[i].projectedValue == testVal) {
//					cnt++; //counting how many times we come across a projectedValue == testVal
//					if(cnt == randomInt) {
//						//System.out.println("Selected Random Next State: " + cnt);
//						chosenStates[chosenStatesCounter] = i;
//						chosenStatesCounter++;
//						
//						//System.out.println("HEREHEREHERE");
//						
//						cur.print(cur.successors);
//						
//						cur = cur.successors[i];
//						if(cur.successors==null)
//						{
//							System.out.println("failed");
//						}
//						break;
//					}
//				}
//			}
			
			
			maxPlayer = !maxPlayer;
			
		}
		
		//printing the results and returning the deepest chosen state
		Board current = state;
		for(int i = 0; i < chosenStatesCounter; i++) {
			current.successors[chosenStates[i]].print();
			//saving the next move (from the root node) from this Minimax run
			if(i + 1 == chosenStatesCounter) {
				nextState = state.successors[chosenStates[0]];
			}
			current = current.successors[chosenStates[i]];
		}
		
		return state;
		//return selected;
	}
	
	public int MaxValue(Board state, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
		curTime = System.currentTimeMillis();
		diffTime = (curTime - startTime)/1000.0;
		
		if (depth == 0 || state.isTerminal() || diffTime > 20) {
			if(diffTime > 19) {
				//System.out.println(diffTime);
			}
			return state.getUtilityValue();
		}
		
		int v = Integer.MIN_VALUE;
		state.generateSuccessors(true);
		
		for(int i = 0; i < state.successors.length; i++) {
			int minVal = MinValue(state.successors[i], depth - 1, alpha, beta, isMaximizingPlayer);
			v = Math.max(minVal, v);
			state.successors[i].projectedValue = v;
			
			//new additions for alpha-beta
			if(v >= beta) {
				return v;
			}
			alpha = Math.max(alpha, v);
			//end new additions
		}
		
		return v;
	}
	
	public int MinValue(Board state, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
		curTime = System.currentTimeMillis();
		diffTime = (curTime - startTime)/1000.0;
		
		if (depth == 0 || state.isTerminal() || diffTime > 20) {
			if(diffTime > 19) {
				//System.out.println(diffTime);
			}
			return state.getUtilityValue();
		}
		
		int v = Integer.MAX_VALUE;
		state.generateSuccessors(false);
		
		for(int i = 0; i < state.successors.length; i++) {
			int maxVal = MaxValue(state.successors[i], depth - 1, alpha, beta, isMaximizingPlayer);
			v = Math.min(maxVal, v);
			state.successors[i].projectedValue = v;
			
			if(v <= alpha) {
				return v;
			}
			beta = Math.min(beta, v);
		}
		
		return v;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public String AlphaBetaSearch(Board board) {
//	int v = MaxValue(board, Integer.MIN_VALUE, Integer.MAX_VALUE);
//
//	String move = getMove(v); //this will consider all moves with utility value v and randomly select one
//
//	return move;
//}
	
	
//	private String getMove(int v) {
//		return null;
//	}
	
//	public int Minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
//		if(depth == 0 || board.isTerminal()) {
//			return board.getUtilityValue();
//		}
//		
//		if(maximizingPlayer) {
//			int maxEval = Integer.MIN_VALUE;
//			
//			successors = board.generateSuccessors(maximizingPlayer);
//			
//			for(int i = 0; i < successors.length; i++) {
//				int eval = Minimax(successors[i], depth - 1, alpha, beta, false);
//				maxEval = Math.max(maxEval, eval);
//				alpha = Math.max(alpha, eval);
//				if( beta <= alpha ) {
//					break;
//				}
//			}
//			return maxEval;
//			
//		} else {
//			int minEval = Integer.MAX_VALUE;
//			
//			successors = board.generateSuccessors(false);
//			
//			for(int i = 0; i < successors.length; i++) {
//				int eval = Minimax(successors[i], depth - 1, alpha, beta, true);
//				minEval = Math.min(minEval, eval);
//				beta = Math.min(beta, eval);
//				if( beta <= alpha ) {
//					break;
//				}
//			}
//			return minEval;
//		}
//	}
	
//	function minimax(position, depth, alpha, beta, maximizingPlayer)
//	if depth == 0 or game over in position
//		return static evaluation of position
//
//	if maximizingPlayer
//		maxEval = -infinity
//		for each child of position
//			eval = minimax(child, depth - 1, alpha, beta false)
//			maxEval = max(maxEval, eval)
//			alpha = max(alpha, eval)
//			if beta <= alpha
//				break
//		return maxEval
//
//	else
//		minEval = +infinity
//		for each child of position
//			eval = minimax(child, depth - 1, alpha, beta true)
//			minEval = min(minEval, eval)
//			beta = min(beta, eval)
//			if beta <= alpha
//				break
//		return minEval
//
//
//// initial call
//minimax(currentPosition, 3, -∞, +∞, true)	

//	public int MaxValue(Board board, int alpha, int beta) {
//
//		if(board.isTerminal()) {
//			return board.getUtilityValue();
//		}
//
//		int v = Integer.MIN_VALUE; //Utility Value v
//
//		successors = board.generateSuccessors(board.oRow, board.oCol, true);
//
//		for(int i = 0; i < successors.length; i++) {
//			Board s = successors[i];
//
//			v = Math.max(v, MinValue(s, alpha, beta));
//
//			if(v >= beta) {
//				return v;
//			}
//
//			alpha = Math.max(alpha, v);
//
//		}
//
//		return v; //Utility Value
//	}
//
//	public int MinValue(Board board, int alpha, int beta) {
//
//		if(board.isTerminal()) {
//			return board.getUtilityValue();
//		}
//
//		int v = Integer.MAX_VALUE; //Utility Value v
//
//		successors = board.generateSuccessors(board.xRow, board.oCol, false); // we prune the tree after 1 terminal state
//
//		for(int i = 0; i < successors.length; i++) {
//			Board s = successors[i];
//
//			v = Math.min(v, MaxValue(s, alpha, beta));
//
//			if(v <= alpha) {
//				return v;
//			}
//
//			beta = Math.min(beta, v);
//
//		}
//
//		return v; //Utility Value
//	}
	
	



//	public anAction AlphaBetaSearch(State state) {
//		UtilityValue v = MaxValue(state, -infinity, +infinity);
//		
//		return (anAction) //in Successors(state) with value v
//	}
//	
//	
//	public UtilityValue MaxValue(State state, Value alpha, Value beta) {
//		if TerminalTest(state) {
//
//			return Utility(state);
//		}
//		
//		UtilityValue v = -100000000; //negative infinity
//		
//		for(int i = 0; i < Successors(state); i++) {
//			Successor s = successors[i];
//			
//			v = Max( v, MinValue(s(state), alpha, beta) );
//			
//			if (v >= beta) {
//				return v;
//			}
//			alpha = Max(alpha, v);
//		}
//		
//		return v;
//	}
//	
//	public UtilityValue MinValue(State state, Value alpha, Value beta) {
//		if(TerminalTest(state)) {
//			return Utility(state);
//		}
//		
//		UtilityValue v = 10000000;
//		
//		for(int i = 0; i < Successors(state); i++) {
//			Successor s = successors[i];
//			
//			v = Min(v, MaxValue( s(state), alpha, beta ));
//			
//			if(v <= alpha) {
//				return v;
//			}
//			
//			beta = Min(beta, v);
//		}
//		
//		return v;
//	}
}
