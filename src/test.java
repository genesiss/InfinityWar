import java.util.HashMap;
import java.util.LinkedList;


public class test {
	
	public static double lifeDist = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LinkedList<Warrior> warriors = Specs.readSpecs("specs");
		
		Warrior roleModel1 = warriors.get(0).newRandomWarrior();
		Warrior roleModel2 = warriors.get(1).newRandomWarrior();
		
		double a = 0;
		int i = 1;
		int numOfTies = 0;
		double[] d = new double[40];
		while(true) {
			roleModel1 = warriors.get(0).newRandomWarrior();
			roleModel2 = warriors.get(1).newRandomWarrior();
			d = new double[40];
			for(int heh = 1; heh <= 40; heh++) {
				while(i <= 40) {
					Warrior w1 = roleModel1.deepclone();
					Warrior w2 = roleModel2.deepclone();
					State newState = null;
					if(i%2 == 0)
						newState = new State(w1, w2, true);
					else 
						newState = new State(w2, w1, true);
					
					Game g = new Game(500, 500);
					i++;			
					a +=  g.simulateGame(newState, 4, true);
					if(g.tie)	numOfTies++;
				}
				System.out.println(a/i);
				d[heh-1] = a/i;
				i=1;
				a=0;
			}
			System.out.println("Ties: "+((double)numOfTies/40));
			System.out.println(calculateIntLength(d)+"\n");
			numOfTies = 0;
		}
		
		

	}
	
	private static double calculateIntLength(double[] d) {
		double avg = 0;
		for(double num : d)
			avg += num;
		avg = avg/d.length;
		
		double SD = 0;
		for(double num : d) {
			double temp = num-avg;
			temp = Math.pow(temp, 2);
			SD += temp;
		}
		SD = SD*((double)1/(d.length-1));
		SD = Math.sqrt(SD);
		
		double SE = 0;
		SE = SD/(Math.sqrt(d.length));
		
		return((avg+1.96*SE)-(avg-1.96*SE));
		
	}

}
