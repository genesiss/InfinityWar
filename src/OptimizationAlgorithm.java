import java.util.ArrayList;
import java.util.LinkedList;


public abstract class OptimizationAlgorithm {
	
	Warrior roleModel1;
	Warrior roleModel2;
	
	public OptimizationAlgorithm(Warrior w1, Warrior w2) {
		this.roleModel1 = w1;
		this.roleModel2 = w2;
	}

	public abstract void run();
	protected abstract void saveState();
	protected abstract void printStats();
	
	/**
	 * Simulates games and returns average life difference between warriors.
	 * @param ind	
	 * @param games	Number of games to simulate
	 * @param depth	Depth of minmax tree
	 * @param alfabeta	If true, alfabeta cuts are enabled.
	 */
	protected void evaluateIndividual(Individual ind, int games, int depth, boolean alfabeta) {
		
		double lifeDist = 0;
		
		for(int i = 0; i < games; i++) {
			Warrior p1 = ind.w1.deepclone();
			Warrior p2 = ind.w2.deepclone();
			
			State startState = null;
			
			if(i%2 == 0)	startState = new State(p1, p2, true);
			else	startState = new State(p2, p1, true);
			
			Game game = new Game(500, 500);
			
			lifeDist += game.simulateGame(startState, depth, alfabeta);
			
		}
		
		ind.lifeDist.add(lifeDist);
		double sum = 0;
		for(double a : ind.lifeDist)	sum+=a;
		sum = sum/ind.lifeDist.size();
		ind.games = games;
		ind.score = 1 / (sum/(double)games);	//so that bigger number means bigger score
		ind.avgLifeDist = sum/(double)games;
	}
	
	/**
	 * 
	 * Represents an individual.
	 *
	 */
	class Individual {
		
		Warrior w1;
		Warrior w2;
		
		public double score;
		public double cumulativeScore;
		public ArrayList<Double> lifeDist;
		public double avgLifeDist;
		public int games;
		
		public Individual(Warrior w1, Warrior w2) {
			this.w1 = w1;
			this.w2 = w2;
			lifeDist = new ArrayList<Double>();
		}
		
	}
	
}
