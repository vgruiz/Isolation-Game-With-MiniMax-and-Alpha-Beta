package com.vgruiz;

public class Computer {
	int cnt = 0;

	/**
	 * This stores the immediate successors of the current state, to choose the successor that's best for the current player.
	 */
	Board[] immediateSuccessors;

	public Board MinimaxDecision(Board state, int depth, boolean isMaximizingPlayer) {
		int v;
		
		if(isMaximizingPlayer) {
			v = MaxValue(state, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, isMaximizingPlayer);			
		} else {
			//if isMaximizingPlayer == false, start with MinValue for the O move
			v = MinValue(state, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, isMaximizingPlayer);	
		}
		
		Board selected = null;
		
		//find value v in successors (NEEDS TO BE RANDOMIZED)
		for(int i = 0; i < immediateSuccessors.length; i++) {
			if (immediateSuccessors[i].projectedValue == v) {
				selected = immediateSuccessors[i];
			}
		}
		
		return state;
		//return selected;
	}
	
	public int MaxValue(Board state, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
		//cnt++;
		//System.out.println(cnt);
		
		state.generateSuccessors(true);
		
		if (depth == 0 || state.isTerminal()) {
			int utilityValue = state.getUtilityValue();
			//System.out.println("IS TERMINAL: " + utilityValue);
			//state.print();
			return utilityValue;
		}
		
		int v = Integer.MIN_VALUE;
		
		//state.generateSuccessors(true);
		
		for(int i = 0; i < state.successors.length; i++) {
			int minVal = MinValue(state.successors[i], depth - 1, alpha, beta, isMaximizingPlayer);
			v = Math.max(minVal, v);
			state.successors[i].projectedValue = v;
			
			//new additions for alpha-beta
			if(v >= beta) {
				//System.out.println("PRUNE v >= beta");
				cnt++;
				return v;
			}
			alpha = Math.max(alpha, v);
			//end new additions
		}
		
		/**
		 * At this point, the 
		 */
		if(isMaximizingPlayer) {
			immediateSuccessors = state.successors;
		}
		return v;
	}
	
	public int MinValue(Board state, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
		
		state.generateSuccessors(false);
		
		if (depth == 0 || state.isTerminal()) {
			int utilityValue = state.getUtilityValue();
			//System.out.println("IS TERMINAL: " + utilityValue);
			//state.print();
			return utilityValue;
		}
		
		int v = Integer.MAX_VALUE;
		
		//state.generateSuccessors(false);
		
		for(int i = 0; i < state.successors.length; i++) {
			int maxVal = MaxValue(state.successors[i], depth - 1, alpha, beta, isMaximizingPlayer);
			v = Math.min(maxVal, v);
			state.successors[i].projectedValue = v;
			
			if(v <= alpha) {
				//System.out.println("PRUNE v <= alpha");
				cnt++;
				return v;
			}
			beta = Math.min(beta, v);
		}
		
		if(!isMaximizingPlayer) {
			immediateSuccessors = state.successors;
		}
		return v;
	}
	
	public Board getNextState(Board state) {
		
		
		
		
		return null;
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
