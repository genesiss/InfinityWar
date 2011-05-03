import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;



public class GeneticOptimization extends OptimizationAlgorithm {
	
	int POPULATION_SIZE;	//size of one population
	int MINMAX_DEPTH;
	int GAME_PER_INDIVIDUAL_REPEAT; 
	double MUTATION_FACTOR;
	double ELITE_FACTOR;
	boolean ALFABETA;
	
	int generationNumber = 0;
	
	ArrayList<Individual> population;	//population representation
	private double scoreSum = 0; 	//sum of all individuals scores in population
	
	Individual overallBest = null;

	
	public GeneticOptimization(Warrior w1, Warrior w2, int populationSize, int minmax_depth, int game_per_individual_repeat, double mutation_factor, double elite_factor, boolean alfabeta) {
		super(w1, w2);
		this.POPULATION_SIZE = populationSize;
		this.MINMAX_DEPTH = minmax_depth;
		this.GAME_PER_INDIVIDUAL_REPEAT = game_per_individual_repeat;
		this.MUTATION_FACTOR = mutation_factor;
		this.ELITE_FACTOR = elite_factor;
		this.ALFABETA = alfabeta;
		
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
			printStats();
			saveState();	//saves current state
			reproduction();	//currently without elitism! 
			generationNumber++;
		}

	}

	@Override
	protected void saveState() {
		Iterator<Individual> it = this.population.iterator();
		Individual best = null; double bestScore = -1;
		while(it.hasNext()) {
			Individual next = it.next();
			if(next.score > bestScore) {
				bestScore = next.score;
				best = next;
			}
		}
		
		if(overallBest == null)	{
			overallBest = best;
			try{
			    // Create file 
			    FileWriter fstream = new FileWriter("bestState");
			        BufferedWriter out = new BufferedWriter(fstream);
			    out.write(overallBest.w1.toString()); out.write(overallBest.w2.toString());
			    out.write("Score: "+overallBest.score+"\nLife Diff: "+overallBest.lifeDist/overallBest.games);
			    out.write("\nGeneration: "+this.generationNumber);
			    //Close the output stream
			    out.close();
			    }catch (Exception e){//Catch exception if any
			      System.err.println("Error: " + e.getMessage());
			    }
		}
		else if(overallBest.score < best.score) {
			overallBest = best;
			try{
			    // Create file 
			    FileWriter fstream = new FileWriter("bestState");
			        BufferedWriter out = new BufferedWriter(fstream);
			    out.write(overallBest.w1.toString()); out.write(overallBest.w2.toString());
			    out.write("Score: "+overallBest.score+"\nLife Diff: "+overallBest.lifeDist/overallBest.games);
			    out.write("\nGeneration: "+this.generationNumber);
			    //Close the output stream
			    out.close();
			    }catch (Exception e){//Catch exception if any
			      System.err.println("Error: " + e.getMessage());
			    }
		}
		
		
		
	}

	private void evaluation() {
		Iterator<Individual> it = this.population.iterator();
		while(it.hasNext()) {
			Individual temp = it.next();
			this.evaluateIndividual(temp, this.GAME_PER_INDIVIDUAL_REPEAT, this.MINMAX_DEPTH, this.ALFABETA);	//evaluate individuals in current population (set the score attribute)
			this.scoreSum += temp.score;
		}
		Collections.sort(this.population, new ScoreComparator());	//sort the population in descdending score order (bigger = better)
		calcCumSum();	//calculate cumulative sum of scores
	}

	private void reproduction() {
		
		ArrayList<Individual> children = new ArrayList<OptimizationAlgorithm.Individual>(this.POPULATION_SIZE);
		
		//elite
		for(int i = 0; i < this.POPULATION_SIZE*this.ELITE_FACTOR; i++)
			children.add(new Individual(population.get(i).w1.deepclone(), population.get(i).w2.deepclone()));
		
		
		
		for(int i = (int) Math.floor(this.POPULATION_SIZE*this.ELITE_FACTOR); i < this.POPULATION_SIZE; i+=2) {
			
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
		
		if(children.size() > this.POPULATION_SIZE)
			children.remove(children.size()-1);
		
		this.population = children;
		
		
	}


	private ArrayList<Individual> mutation(ArrayList<Individual> succesors) {
		Iterator<Individual> succIterator = succesors.iterator();
		while(succIterator.hasNext()) {
			Individual succ = succIterator.next();
			ArrayList<ArrayList<Property>> chromosomes = getChromosome(succ);
			
			for(int i = 0; i < chromosomes.get(0).size(); i++) {
				if(Math.random() < this.MUTATION_FACTOR) {
					chromosomes.get(0).get(i).setRandomValue();
				}
			}
			
			for(int i = 0; i < chromosomes.get(1).size(); i++) {
				if(Math.random() < this.MUTATION_FACTOR) {
					chromosomes.get(1).get(i).setRandomValue();
				}
			}
			
			
		}
		return succesors;
	}

	private ArrayList<Individual> crossover(Individual parent1, Individual parent2) {
		
		ArrayList<ArrayList<Property>> chromosomesParent1 = getChromosome(parent1);
		ArrayList<ArrayList<Property>> chromosomesParent2 = getChromosome(parent2);
		
		int cuttingPoint1 = (int) (Math.random() * chromosomesParent1.get(0).size());
		int cuttingPoint2 = (int) (Math.random() * chromosomesParent1.get(1).size());
		
		HashMap<String, Property> child1GenesW1 = new HashMap<String, Property>();
		HashMap<String, Property> child2GenesW1 = new HashMap<String, Property>();
		HashMap<String, Property> child1GenesW2 = new HashMap<String, Property>();
		HashMap<String, Property> child2GenesW2 = new HashMap<String, Property>();
		
		for(int i = 0; i < cuttingPoint1; i++) {
			Property c1 = chromosomesParent1.get(0).get(i);
			Property c2 = chromosomesParent2.get(0).get(i);
			
			child1GenesW1.put(c1.name, c1);
			child2GenesW1.put(c2.name, c2);
		}
		
		for(int i = cuttingPoint1; i < chromosomesParent1.get(0).size(); i++) {
			Property c1 = chromosomesParent2.get(0).get(i);
			Property c2 = chromosomesParent1.get(0).get(i);
			
			child1GenesW1.put(c1.name, c1);
			child2GenesW1.put(c2.name, c2);
		}
		
		for(int i = 0; i < cuttingPoint2; i++) {
			Property c1 = chromosomesParent1.get(1).get(i);
			Property c2 = chromosomesParent2.get(1).get(i);
			
			child1GenesW2.put(c1.name, c1);
			child2GenesW2.put(c2.name, c2);
		}
		
		for(int i = cuttingPoint2; i < chromosomesParent1.get(1).size(); i++) {
			Property c1 = chromosomesParent2.get(1).get(i);
			Property c2 = chromosomesParent1.get(1).get(i);
			
			child1GenesW2.put(c1.name, c1);
			child2GenesW2.put(c2.name, c2);
		}
		
		Individual child1 = new Individual(Warrior.warriorFromProps(child1GenesW1, parent1.w1.deepclone()), Warrior.warriorFromProps(child1GenesW2, parent1.w2.deepclone()));
		Individual child2 = new Individual(Warrior.warriorFromProps(child2GenesW1, parent1.w1.deepclone()), Warrior.warriorFromProps(child2GenesW2, parent1.w2.deepclone()));
		
		ArrayList<Individual> children = new ArrayList<OptimizationAlgorithm.Individual>(2);
		children.add(child1); children.add(child2);
		
		return children;
	}


	private ArrayList<ArrayList<Property>> getChromosome(Individual ind) {
		
		ArrayList<Property> chromosome1 = new ArrayList<Property>();
		ArrayList<Property> chromosome2 = new ArrayList<Property>();
		
		Warrior w1 = ind.w1;
		Warrior w2 = ind.w2;
		
		chromosome1.add(w1.life); chromosome1.add(w1.energy); chromosome1.add(w1.speed);
		chromosome2.add(w2.life); chromosome2.add(w2.energy); chromosome2.add(w2.speed);
		
		Iterator<String> it = w1.actions.keySet().iterator();
		boolean moveAdded = false;
		while(it.hasNext()) {
			Action next = w1.actions.get(it.next());
			
			if(next.isMove && !moveAdded) {
				chromosome1.add(next.reach);
				chromosome1.add(next.maxdmg);
				chromosome1.add(next.mindmg);
				chromosome1.add(next.energyNeeded);
				moveAdded = true;
			}
			else if(!next.isMove) {
				chromosome1.add(next.reach);
				chromosome1.add(next.maxdmg);
				chromosome1.add(next.mindmg);
				chromosome1.add(next.energyNeeded);
			}
			
		}
		
		it = w2.actions.keySet().iterator();
		moveAdded = false;
		while(it.hasNext()) {
			Action next = w2.actions.get(it.next());
			
			if(next.isMove && !moveAdded) {
				chromosome2.add(next.reach);
				chromosome2.add(next.maxdmg);
				chromosome2.add(next.mindmg);
				chromosome2.add(next.energyNeeded);
				moveAdded = true;
			}
			else if(!next.isMove) {
				chromosome2.add(next.reach);
				chromosome2.add(next.maxdmg);
				chromosome2.add(next.mindmg);
				chromosome2.add(next.energyNeeded);
			}
			
		}
		
		ArrayList<ArrayList<Property>> chromosomeArray = new ArrayList<ArrayList<Property>>();
		chromosomeArray.add(chromosome1); chromosomeArray.add(chromosome2);
		
		return chromosomeArray;
		
		
		
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

	@Override
	protected void printStats() {
		double score = 0;
		double lifeDiff = 0;
		Iterator<Individual> it = this.population.iterator();
		Individual bestInGen = null;
		while(it.hasNext()) {
			Individual next = it.next();
			if(bestInGen == null)	bestInGen = next;
			else if(bestInGen.score < next.score)	bestInGen = next;
			score += next.score;
			lifeDiff += next.lifeDist/next.games;
		}
		
		System.out.println(this.generationNumber+": "+score+" LifeDiff: "+lifeDiff/this.population.size()+" Best: "+bestInGen.lifeDist/bestInGen.games);
		try{
		    // Create file 
		    FileWriter fstream = new FileWriter("geneticOut", true);
		        BufferedWriter out = new BufferedWriter(fstream);
		    out.write(this.generationNumber+": "+score+" LifeDiff: "+lifeDiff/this.population.size()+" Best: "+bestInGen.lifeDist/bestInGen.games+"\n");
		    //Close the output stream
		    out.close();
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }
	}

}
