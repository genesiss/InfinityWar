import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;



public class GeneticOptimization extends OptimizationAlgorithm {
	
	int POPULATION_SIZE;	//size of one population
	
	ArrayList<Individual> population;	//population representation
	private double scoreSum = 0; 	//sum of all individuals scores in population

	
	public GeneticOptimization(Warrior w1, Warrior w2, int populationSize) {
		super(w1, w2);
		this.POPULATION_SIZE = populationSize;
		this.population = new ArrayList<Individual>(this.POPULATION_SIZE);
	}

	/**
	 * Main method. Runs the optimization.
	 */
	@Override
	public void run() {
		
		chooseInitialPopulation(this.POPULATION_SIZE);	//choose initial population with POPULATION_SIZE individuals
		
		while(true) {
			evaluation();
			saveState();	//saves current state
			reproduction();	//currently without elitism! 

		}

	}

	@Override
	protected void saveState() {
		// TODO Auto-generated method stub
		
	}

	private void evaluation() {
		Iterator<Individual> it = this.population.iterator();
		while(it.hasNext()) {
			Individual temp = it.next();
			this.evaluateIndividual(temp, 10, 2, true);	//evaluate individuals in current population (set the score attribute)
			this.scoreSum += temp.score;
		}
		Collections.sort(this.population, new ScoreComparator());	//sort the population in descdending score order (bigger = better)
		calcCumSum();	//calculate cumulative sum of scores
	}

	private void reproduction() {
		
		ArrayList<Individual> children = new ArrayList<OptimizationAlgorithm.Individual>(this.POPULATION_SIZE);
		
		for(int i = 0; i < this.POPULATION_SIZE; i++) {
			
			//SELECTION PART
			Individual parent1 = null;
			Individual parent2 = null;
			
			for(int j = 0; j < 2; j++) {
				
				double val = Math.random();
				Iterator<Individual> it = this.population.iterator();
				while(it.hasNext()) {
					
					Individual temp = it.next();
					if(temp.cumulativeScore > val) {
						if(j == 0) parent1 = temp;
						else	parent2 = temp;
						break;
					}
					
				}
				
			}
			
			//REPRODUCTION PART
			ArrayList<Individual> succesors = crossover(parent1, parent2);
			succesors = mutation(succesors);
			
			children.addAll(succesors);
			
		}
		
		this.population = children;	//children become new population
		
		
	}


	private ArrayList<Individual> mutation(ArrayList<Individual> succesors) {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<Individual> crossover(Individual parent1, Individual parent2) {
		
		return null;
	}


	private void calcCumSum() {
		
		double cumSum = 0;
		
		Iterator<Individual> it = this.population.iterator();
		while(it.hasNext()) {
			Individual temp = it.next();
			temp.cumulativeScore = cumSum + temp.score/this.scoreSum;
			cumSum = temp.cumulativeScore;
		}
		
		population.get(population.size()-1).cumulativeScore = 1;	//because of precision errors usually has value 0.999... We manually set it to 1.
		
	}

	/**
	 * Constructs initial population according to limitations defined by role models.
	 * @param size
	 */
	private void chooseInitialPopulation(int size) {
		
		for(int i = 0; i < size; i++) {

				Warrior w1 = roleModel1.newRandomWarrior();
				Warrior w2 = roleModel2.newRandomWarrior();
				
				Individual ind = new Individual(w1, w2);
				
				population.add(ind);
			
		}
		
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
