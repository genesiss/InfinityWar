import java.io.BufferedWriter;
import java.io.FileWriter;
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
		actionPath = new LinkedList<Action>();
		printState(startState);
		do {
			
			if(!alfabeta)
				AI.minimax(startState, depth);
			else
				AI.alfabeta(startState, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			State bestState = null;
			bestState=getBest();
				//bestState = AI.bestStates.get((int) (Math.random() * (AI.bestStates.size()-1)));
			actionPath.addLast(bestState.action);
			startState = bestState;
			startState.isMax = true;
			AI.bestStates = null;
			printState(startState);
			//System.out.println("kk: "+Game.kk);
			Game.kk = 0;
			if(actionPath.size() > 100) break;
		} while(startState.player1.life.value > 0 && startState.player2.life.value > 0);
		
		lifeDist += Math.abs(startState.player1.life.value-startState.player2.life.value);
		
		return lifeDist;
		
	}
	
	private State getBest() {
		return AI.bestStates.get((int) (Math.random() * (AI.bestStates.size()-1)));
	}

	private void printState(State s) {
		/*try{
		    // Create file 
		    FileWriter fstream = new FileWriter("state",true);
		        BufferedWriter out = new BufferedWriter(fstream);
		        if(s.action != null)	out.write("Action: "+s.action.name+" "+s.value+"\n");
		        out.write(s.player1.name+" [L:"+s.player1.life.value+" E:"+s.player1.energy.value+" X:"+s.player1.pos_x+" Y:"+s.player1.pos_y+" S:"+s.player1.speed.value+"]\n");
		        out.write(s.player2.name+" [L:"+s.player2.life.value+" E:"+s.player2.energy.value+" X:"+s.player2.pos_x+" Y:"+s.player2.pos_y+" S:"+s.player2.speed.value+"]\n");
		        out.write("---------------\n");
		    //Close the output stream
		    out.close();
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }*/

		/*if(s.action != null)	System.out.println("Action: "+s.action.name+" "+s.value);
		System.out.println(s.player1.name+" [L:"+s.player1.life.value+" E:"+s.player1.energy.value+" X:"+s.player1.pos_x+" Y:"+s.player1.pos_y+" S:"+s.player1.speed.value+"]");
		System.out.println(s.player2.name+" [L:"+s.player2.life.value+" E:"+s.player2.energy.value+" X:"+s.player2.pos_x+" Y:"+s.player2.pos_y+" S:"+s.player2.speed.value+"]");
		System.out.println("---------------");*/
	}

	
}
