import java.util.ArrayList;
import java.util.LinkedList;


public class executeTests {
	
	public static void main(String[] args) {
		
		if(!(args.length > 0)) {
			System.out.println("Za izbiro algoritma podaj \"genetic\" ali \"hill\" kot argument.\n" +
			"Pozor: za hill climbing je potrebna inicializirana začetna množica seedov v mapi initPop.");
			return;
		}
		


		
		LinkedList<Warrior> warriors = Specs.readSpecs("specs");
		
		Warrior roleModel1 = warriors.get(0);
		Warrior roleModel2 = warriors.get(1);
		final int POPULATION_SIZE = 25;
		final int MINMAX_DEPTH = 4;
		final int GAME_PER_INDIVIDUAL_REPEAT = 40;
		final double MUTATION_FACTOR = 0.1;
		final double ELITE_FACTOR = 0.2;
		final boolean ALFABETA = true;
		
		GeneticOptimization genetic = new GeneticOptimization(
				roleModel1,
				roleModel2,
				POPULATION_SIZE,
				MINMAX_DEPTH,
				GAME_PER_INDIVIDUAL_REPEAT,
				MUTATION_FACTOR,
				ELITE_FACTOR,
				ALFABETA);
		
		if(args[0].equals("genetic")) {
			System.out.println("Running genetic optimization...");
			genetic.run();
		}
		else if(args[0].equals("hill")) {
			System.out.println("Running hill climbing optimization...");
			ArrayList<OptimizationAlgorithm.Individual> seeds = Specs.readSpecsForSeeds("initPop//ind", POPULATION_SIZE, genetic);
			HillClimbing hill = new HillClimbing(warriors.get(0), warriors.get(1), GAME_PER_INDIVIDUAL_REPEAT, ALFABETA, MINMAX_DEPTH, seeds);
			hill.run();
		}
		
		else {
				System.out.println("Napacen parameter.");
				return;
		}
		
		
	
	}

}
