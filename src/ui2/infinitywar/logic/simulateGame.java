package ui2.infinitywar.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class simulateGame {
	
	public static int x_size=500;
	public static int y_size=500;
	
	public HashMap<String, Integer> a = new HashMap<String, Integer>();
	
	public int cuts = 0;
	
	private int depth;
	
	public enum Events {
		PLAYING,
		ENDED
	}
	
	class State {	
		Warrior player1;
		Warrior player2;
		List<State> children;
		double value;
		
		boolean isMax;
		
		Events event = Events.PLAYING;
		
		public State() {
			player1 = new Warrior();
			player2 = new Warrior();
			children = new LinkedList<State>();
		}
	}
	
	public simulateGame(int depth, int x_size, int y_size) {
		this.depth = depth;
		this.x_size = x_size;
		this.y_size = y_size;
	}
	
	public State runGame(State startState) {
		State state = startState;
		int i = 1;
		while((state=chooseMove(state)).event != Events.ENDED) {
			i++;
			state.isMax=true;
		}
		if(startState.player1.name.equals(state.player2.name)) {
			a.put(startState.player1.name, a.get(startState.player1.name)+1);
		}
		else
			a.put(startState.player2.name, a.get(startState.player2.name)+1);
		
		System.out.println(startState.player2.name+":"+a.get(startState.player2.name)+"  "+startState.player1.name+":"+a.get(startState.player1.name));
		return state;
	}
	
	public State chooseMove(State startState) {	//player1 always on the move
		minmax(startState, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		//System.out.println(cuts);
		return bestMove(startState);
	}

	private double minmax(State state, int currDepth, double min,
			double max) {
		
		if(state.event == state.event.ENDED || currDepth == 0) {
			state.value = eval(state);
			return state.value;
		}
		
		if(state.isMax){
			double V = min;
			double _V;
			State stateChild;
			Iterator<Action> it = state.player1.actions.iterator();
			while((stateChild= genNextChild(state, it, currDepth)) != null) {
				_V = minmax(stateChild, currDepth-1, V, max);
				if(_V > V)	V=_V;
				if(V > max) {
					cuts++;
					state.value = max;
					return state.value;
				}
			}
			state.value = V;
			return state.value;
		}
		
		else if(!state.isMax) {
			double V = max;
			double _V;
			State stateChild;
			Iterator<Action> it = state.player1.actions.iterator();
			while((stateChild = genNextChild(state, it, currDepth)) != null) {
				_V = minmax(stateChild, currDepth-1, min, V);
				if(_V < V)	V=_V;
				if(V < min) {
					cuts++;
					state.value = min;
					return state.value;
				}
			}
			state.value = V;
			return state.value;
		}
		
		return -1;
	}

	private State genNextChild(State state, Iterator<Action> it, int currDepth) {
		State newPos = new State();
		newPos.player1 = state.player2.returnClone();
		newPos.player2 = state.player1.returnClone();
		
		newPos.isMax = !state.isMax;
		
		Action action = null;
		
		while(it.hasNext()) {	//pridobi naslednjo legalno akcijo
			action = it.next();
			int status = -1;
			if((status = newPos.player2.action(action, newPos.player1)) >= 0) {	//action is legal
				if(status > 0)	//pomeni da je nasprotnik umrl
					newPos.event=Events.ENDED;
				if(currDepth==depth)
					state.children.add(newPos);
				return newPos;
			}
		}

		
		
		return null;
	}

	private double eval(State state) {
		if(state.event == Events.ENDED) {
			if(state.isMax) {
				return Double.MIN_VALUE;
			}
			else
				return Double.MAX_VALUE;
		}
		else {
			return calcHeuristics(state);
		}
	}

	private double calcHeuristics(State state) {
		double retVal = 0;
		Warrior me=null, you=null;

		if(state.isMax) {
			me=state.player1;
			you=state.player2;
		}
		else {
			me=state.player2;
			you=state.player1;
		}
		
		int myLife = me.attributes.get("Life");
		int yourLife = you.attributes.get("Life");
		
		int myEnergy = me.attributes.get("Energy");
		int yourEnergy = you.attributes.get("Energy");

		if(myLife <= 0)
			retVal -= 1000;

		if(yourLife  <= 0)
			retVal += 1000;

		retVal += (1.2*yourLife/me.LIFE_MAX);
		retVal += (-1.0*yourLife/you.LIFE_MAX);
		retVal += (0.1*myEnergy/me.ENERGY_MAX);
		retVal += (-1.0*yourEnergy/you.ENERGY_MAX);
		//if(retVal > 0)
			//System.out.println(retVal);
		return retVal;
		
	}

	private State bestMove(State startState) {
		Iterator<State> i = startState.children.iterator();
		State max = i.next();
		while(i.hasNext()) {
			State test = i.next();
			if(test.value > max.value)
				max = test;
		}
		return max;
	}
	
	

}
