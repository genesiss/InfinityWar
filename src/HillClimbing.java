import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.omg.CORBA.LocalObject;


public class HillClimbing extends OptimizationAlgorithm
{
	int GAME_PER_INDIVIDUAL_REPEAT;
	boolean ALFABETA;
	int MINIMAX_DEPTH;
	ArrayList<Individual> seeds;
	

	public HillClimbing(Warrior w1, Warrior w2, int game_per_individual_repeat, boolean alfabeta, int minimax_depth, ArrayList<Individual> seeds) {
		super(w1, w2);
		this.GAME_PER_INDIVIDUAL_REPEAT = game_per_individual_repeat;
		this.ALFABETA = alfabeta;
		this.seeds = seeds;
		this.MINIMAX_DEPTH = minimax_depth;
		Collections.sort(this.seeds, new ScoreComparator());
	}

	@Override
	public void run() {
		
		int i = 0;
		Property bestPropChange = null;
		int dir = -1;
		double bestScore = -1;
		double bestLifeDist = -1;
		double bestCUmulativeScore = -1;
		int bestGames = -1;
		
		int numOfEvaluations = 0;
		
		while(numOfEvaluations < 90*25) {
		
			Individual seed = chooseNextBest(i);
			if(seed == null) {
				System.out.println("NULL SEED");
				return;
			}
			
			System.out.println("NEW SEED ("+i+"): "+seed.lifeDist/seed.games);
			
			do {
				dir = -1;
				bestScore = seed.score;
				bestLifeDist = seed.lifeDist;
				bestCUmulativeScore = seed.cumulativeScore;
				bestGames = seed.games;
				seed.score = 0;
				seed.lifeDist = 0;
				seed.cumulativeScore = 0;
				seed.games = 0;
				
				System.out.println(bestLifeDist/bestGames+" "+numOfEvaluations);
				
				ArrayList<ArrayList<Property>> chromosome = GeneticOptimization.getChromosome(seed);
				
				for(Property p : chromosome.get(0)) {
					if(p.levelUp()) {
						numOfEvaluations++;
						double currScore = evaluate(seed);
						if(currScore > bestScore) {
							dir = 1;
							bestPropChange = p;
							bestScore = currScore;
							bestLifeDist = seed.lifeDist;
							bestCUmulativeScore = seed.cumulativeScore;
							bestGames = seed.games;
						}
						seed.score = 0;
						seed.lifeDist = 0;
						seed.cumulativeScore = 0;
						seed.games = 0;
						p.levelDown();
					}
					
					if(p.levelDown()) {
						numOfEvaluations++;
						double currScore = evaluate(seed);
						if(currScore > bestScore) {
							dir = 0;
							bestPropChange = p;
							bestScore = currScore;
							bestLifeDist = seed.lifeDist;
							bestCUmulativeScore = seed.cumulativeScore;
							bestGames = seed.games;
						}
						p.levelUp();
						seed.score = 0;
						seed.lifeDist = 0;
						seed.cumulativeScore = 0;
						seed.games = 0;
					}
					
				}
				
				for(Property p : chromosome.get(1)) {
					if(p.levelUp()) {
						numOfEvaluations++;
						double currScore = evaluate(seed);
						if(currScore > bestScore) {
							dir = 1;
							bestPropChange = p;
							bestScore = currScore;
							bestLifeDist = seed.lifeDist;
							bestCUmulativeScore = seed.cumulativeScore;
							bestGames = seed.games;
						}
						p.levelDown();
						seed.score = 0;
						seed.lifeDist = 0;
						seed.cumulativeScore = 0;
						seed.games = 0;
					}
					
					if(p.levelDown()) {
						numOfEvaluations++;
						double currScore = evaluate(seed);
						if(currScore > bestScore) {
							dir = 0;
							bestPropChange = p;
							bestScore = currScore;
							bestLifeDist = seed.lifeDist;
							bestCUmulativeScore = seed.cumulativeScore;
							bestGames = seed.games;
						}
						p.levelUp();
						seed.score = 0;
						seed.lifeDist = 0;
						seed.cumulativeScore = 0;
						seed.games = 0;
					}
				}
				
				modifyIndividual(bestPropChange, dir, bestScore, bestLifeDist, bestCUmulativeScore, bestGames, seed);
			}	while(dir != -1);
			try{
			    FileWriter fstream = new FileWriter("logHill//best_"+i);
			        BufferedWriter out = new BufferedWriter(fstream);
			    out.write(seed.w1.toString()); out.write(seed.w2.toString());
			    out.write("Score: "+bestScore+"\nLife Diff: "+bestLifeDist/bestGames);
			    //Close the output stream
			    out.close();
			}
			catch (Exception e){//Catch exception if any
			      System.err.println("Error: " + e.getMessage());
			}
			i++;
			System.out.println("------------------");
			System.out.println();
		}
		System.out.println("OUT OF EVALUATIONS");
		
	}

	private void modifyIndividual(Property bestPropChange, int dir,
			double bestScore, double betLifeDif, double bestCumSum, int bestGames, Individual i) {
		
		if(dir == 0) {
			bestPropChange.levelDown();
		}
		else if(dir == 1) {
			bestPropChange.levelUp();
		}
		
		if(dir == 0 || dir == 1) {
			i.score = bestScore;
			i.lifeDist = betLifeDif;
			i.cumulativeScore = bestCumSum;
			i.games = bestGames;
		}
		
	}

	private double evaluate(Individual ind) {
			this.evaluateIndividual(ind, this.GAME_PER_INDIVIDUAL_REPEAT, this.MINIMAX_DEPTH, this.ALFABETA);	//evaluate individuals in current population (set the score attribute)
			return ind.score;
		}

	private Individual chooseNextBest(int i) {
		if(seeds.size() == i) return null;
		
		return seeds.get(i);
		
		
	}
	

	@Override
	protected void saveState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void printStats() {
		// TODO Auto-generated method stub
		
	}
	
	class ScoreComparator implements Comparator<Individual> {
		
	    @Override
	    public int compare(Individual x, Individual y) {
	        if (x.score < y.score)	return 1;
	        if (x.score > y.score)	return -1;

	        return 0;
	    }

	}


}
