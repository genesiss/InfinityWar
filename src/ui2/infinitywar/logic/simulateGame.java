package ui2.infinitywar.logic;

import java.util.List;

public class simulateGame {
	
	private int x_size=500;
	private int y_size=500;
	
	private int depth;
	
	public enum Events {
		PLAYING,
		ENDED
	}
	
	class State {	
		Warrior player1;
		Warrior player2;
		List<State> children;
		
		boolean isMax;
		
		Events event;
	}
	
	public simulateGame(int depth, int x_size, int y_size) {
		this.depth = depth;
		this.x_size = x_size;
		this.y_size = y_size;
	}
	
	public State chooseMove(State startState) {	//player1 always on the move
		minmax(startState, depth, Double.MIN_VALUE, Double.MAX_VALUE);
		return bestMove(startState);
	}

	private double minmax(State state, int currDepth, double min,
			double max) {
		
		if(state.event == state.event.ENDED || currDepth == 0)
			return eval(state);
		
		if(state.isMax){
			double V = min;
			double _V;
			State stateChild=null;
			while((stateChild= genNextChild(state)) != null) {
				_V = minmax(state, currDepth-1, V, max);
				if(_V > V)	V=_V;
				if(V > max) return max;
			}
			return V;
		}
		
		else if(!state.isMax) {
			double V = max;
			double _V;
			State stateChild;
			while((stateChild= genNextChild(state)) != null) {
				_V = minmax(state, currDepth-1, min, V);
				if(_V < V)	V=_V;
				if(V < min) return min;
			}
			return V;
		}
		
		return -1;
	}

	private State genNextChild(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	private double eval(State state) {
		// TODO Auto-generated method stub
		return 0;
	}

	private State bestMove(State startState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
