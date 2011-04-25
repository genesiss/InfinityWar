import java.util.LinkedList;


public class executeTests {
	
	public static void main(String[] args) {
		
		LinkedList<Warrior> warriors = Specs.readSpecs("specs");
		
		GeneticOptimization genetic = new GeneticOptimization(warriors.get(0), warriors.get(1), 10);
		genetic.run();
	
	}

}
