import java.util.ArrayList;
import java.util.Iterator;


public class AI {
	
	public static ArrayList<State> bestStates;
	public static int depth;
	public static int kk = 0;
	
	static double minimax (State n, int d) {
		if( leaf(n) || d == 0) return evaluate(n);
		
		if(n.isMax) {
			double V = Double.NEGATIVE_INFINITY;
			Iterator<Action> actions = n.player1.actions.values().iterator();
			State child = null;
			while((child=generateChild(n, actions, d)) != null) {
				kk++;
				double VC = minimax(child, d-1);
				if(VC > V) V = VC;
				//if we are at root level, set the best child
				if(d == AI.depth)
					setBestState(V, child);
			}
			return V;
		}
		
		else {
			double V = Double.POSITIVE_INFINITY;
			Iterator<Action> actions = n.player1.actions.values().iterator();
			State child = null;
			while((child=generateChild(n, actions, -1)) != null) {
				kk++;
				double VC = minimax(child, d-1);
				if(VC < V) V = VC;
			}
			return V;
		}
		
	}
	
	static double alfabeta(State n, int d, double min, double max) {
		if( leaf(n) || d == 0) return evaluate(n);
		
		if(n.isMax) {
			double V = min;
			Iterator<Action> actions = n.player1.actions.values().iterator();
			State child = null;
			while((child=generateChild(n, actions, d)) != null) {
				double VC = alfabeta(child, d-1, V, max);
				kk++;
				if(VC > V) V = VC;
				//if we are at root level, set the best child
				if(d == AI.depth)
					setBestState(V, child);
				
				if(V > max)	return max;
			}
			return V;
		}
		
		else {
			double V = max;
			Iterator<Action> actions = n.player1.actions.values().iterator();
			State child = null;
			while((child=generateChild(n, actions, -1)) != null) {
				kk++;
				double VC = alfabeta(child, d-1, min, V);
				if(VC < V) V = VC;
				if(V < min) return min;
			}
			return V;
		}
		
	}

	/**
	 * Sets the best state after current state.
	 * @param v
	 * @param child
	 */
	private static void setBestState(double v, State child) {
		if(AI.bestStates == null) {
			AI.bestStates = new ArrayList<State>();
			child.value = v;
			bestStates.add(child);
		}
		
		else if(v > bestStates.get(0).value) {
			AI.bestStates = new ArrayList<State>();
			child.value = v;
			bestStates.add(child);
		}
		
		else if(v == bestStates.get(0).value) {
			child.value = v;
			bestStates.add(child);
		}
		
		System.out.println(child.action.name+": "+v);
		
	}

	/**
	 * Generates next state with available action or returns null.
	 * @param n
	 * @param actions
	 * @return
	 */
	private static State generateChild(State n, Iterator<Action> actions, int d) {
		
		Warrior w1 = n.player2.clone();
		Warrior w2 = n.player1.clone();
		
		State child = new State(w1, w2, !n.isMax);
		
		while(actions.hasNext()) {
			Action nextAction = actions.next();
			if(nextAction.fight(w2, w1)) {
				if(d == AI.depth)
					child.action = nextAction;
				return child;
			}
		}
		
		return null;
	}

	/**
	 * Evaluates the state n.
	 * @param n
	 * @return
	 */
	private static double evaluate(State n) {
		
		double retVal = 0;
		Warrior me=null, you=null;

		if(n.isMax) {
			me=n.player1;
			you=n.player2;
		}
		else {
			me=n.player2;
			you=n.player1;
		}
		
		double myLife = me.life.value;
		double yourLife = you.life.value;
		
		double myEnergy = me.energy.value;
		double yourEnergy = you.energy.value;

		if(myLife <= 0)
			retVal -= 1000;

		if(yourLife  <= 0)
			retVal += 1000;

		
		retVal += (1.0*myLife/me.life.INIT_VALUE);
		retVal += (-1.2*yourLife/you.life.INIT_VALUE);
		retVal += (0.1*myEnergy/me.energy.INIT_VALUE);
		retVal += (-0.1*yourEnergy/you.energy.INIT_VALUE);
		
		retVal += 0.1*me.speed.value/me.speed.INIT_VALUE;
		
		//retVal += -0.1*(Math.abs(me.pos_x-you.pos_x)+Math.abs(me.pos_y-you.pos_y))/(double)(Game.sizeX+Game.sizeY);
		
		return retVal;
		
	}

	/**
	 * If state is a leaf (somebody is dead) return true.
	 * @param n
	 * @return
	 */
	private static boolean leaf(State n) {
		if(n.player1.life.value <= 0 || n.player2.life.value <= 0)
			return true;
		
		return false;
	}

}
