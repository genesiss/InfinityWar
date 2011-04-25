import java.util.Iterator;
import java.util.LinkedList;


public class Game extends AI {

	public static int sizeX;
	public static int sizeY;
	
	public double lifeDist = 0;
	
	public LinkedList<Action> actionPath = new LinkedList<Action>();
	
	
	public Game(int sizeX, int sizeY) {
		Game.sizeX = sizeX;
		Game.sizeY = sizeY;
	}
	
	public double simulateGame(State startState, int depth, boolean alfabeta) {
		AI.bestStates = null;
		AI.depth = depth;
		printState(startState);
		do {
			
			if(!alfabeta)
				AI.minimax(startState, depth);
			else
				AI.alfabeta(startState, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			State bestState = null;
			if((bestState=getAction()) == null)
				bestState = AI.bestStates.get((int) (Math.random() * (AI.bestStates.size()-1)));
			actionPath.addLast(bestState.action);
			startState = bestState;
			startState.isMax = true;
			AI.bestStates = null;
			printState(startState);
			System.out.println("kk: "+Game.kk);
			Game.kk = 0;
			
		} while(startState.player1.life.value > 0 && startState.player2.life.value > 0);
		
		lifeDist += Math.abs(startState.player1.life.value-startState.player2.life.value);
		
		return lifeDist;
		
	}
	
	private State getAction() {
		Iterator<State> it = AI.bestStates.iterator();
		while(it.hasNext()) {
			State temp = it.next();
			if(temp.action.name.contains("Fire"))
				return temp;
		}
		return null;
	}

	private void printState(State s) {
		if(s.action != null)	System.out.println("Action: "+s.action.name+" "+s.value);
		System.out.println(s.player1.name+" [L:"+s.player1.life.value+" E:"+s.player1.energy.value+" X:"+s.player1.pos_x+" Y:"+s.player1.pos_y+" S:"+s.player1.speed.value+"]");
		System.out.println(s.player2.name+" [L:"+s.player2.life.value+" E:"+s.player2.energy.value+" X:"+s.player2.pos_x+" Y:"+s.player2.pos_y+" S:"+s.player2.speed.value+"]");
		System.out.println("---------------");
	}

	
}
