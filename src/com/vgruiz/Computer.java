public class Computer {

	Board[] successors;

	public String AlphaBetaSearch(Board board) {
		int v = MaxValue(board, Integer.MIN_VALUE, Integer.MAX_VALUE);

		String move = getMove(v); //this will consider all moves with utility value v and randomly select one

		return move;
	}

	private String getMove(int v) {

		return null;
	}

	public int MaxValue(Board board, int alpha, int beta) {

		if(board.isTerminal()) {
			return board.getUtilityValue();
		}

		int v = Integer.MIN_VALUE; //Utility Value v

		successors = board.generateSuccessors(board.oRow, board.oCol, true);

		for(int i = 0; i < successors.length; i++) {
			Board s = successors[i];

			v = Math.max(v, MinValue(s, alpha, beta));

			if(v >= beta) {
				return v;
			}

			alpha = Math.max(alpha, v);

		}

		return v; //Utility Value
	}

	public int MinValue(Board board, int alpha, int beta) {

		if(board.isTerminal()) {
			return board.getUtilityValue();
		}

		int v = Integer.MAX_VALUE; //Utility Value v

		successors = board.generateSuccessors(board.xRow, board.oCol, false); // we prune the tree after 1 terminal state

		for(int i = 0; i < successors.length; i++) {
			Board s = successors[i];

			v = Math.min(v, MaxValue(s, alpha, beta));

			if(v <= alpha) {
				return v;
			}

			beta = Math.min(beta, v);

		}

		return v; //Utility Value
	}


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
