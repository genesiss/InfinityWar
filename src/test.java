import java.util.HashMap;
import java.util.LinkedList;


public class test {
	
	public static double lifeDist = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LinkedList<Warrior> warriors = Specs.readSpecs("specs");
		
		Warrior roleModel1 = warriors.get(0);
		Warrior roleModel2 = warriors.get(1);
		
		Game g = new Game(500, 500);
		State startState = new State(roleModel1, roleModel2, true);
		double a = 0;
		int i = 0;
		while(true) {
		while(i < 100) {
			i++;
			g = new Game(500, 500);
		a =  g.simulateGame(startState, 2, true);
		warriors = Specs.readSpecs("specs");
		startState = new State(warriors.get(0), warriors.get(1), true);
		
		}
		
		System.out.println(a/i);
		i=0;
		}

	}

}
