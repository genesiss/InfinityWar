import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class Specs {
	
	public static LinkedList<Warrior> readSpecs(String filename) {
		
		 LinkedList<Warrior> warriors = new LinkedList<Warrior>();
		
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    int numOfWarriors = Integer.parseInt(in.readLine());	//first line is number of warriors	   
		    
		    for(int i = 0; i < numOfWarriors; i++) {
		    	
		    	String nameAndPos = in.readLine();
		    	StringTokenizer st = new StringTokenizer(nameAndPos);
		    	String name = st.nextToken();
		    	int pos_x = Integer.parseInt(st.nextToken());
		    	int pos_y = Integer.parseInt(st.nextToken());
		    	
		    	Property life = getPropertyFromString(in.readLine());
		    	Property energy = getPropertyFromString(in.readLine());
		    	Property speed = getPropertyFromString(in.readLine());
		    	
		    	Action move = getActionFromFile(in);
		    	Action up = new Action("UP", move.reach, move.maxdmg, move.mindmg, move.energyNeeded);
		    	up.d = Action.Direction.UP; up.isMove = true;
		    	Action down = new Action("DOWN", move.reach, move.maxdmg, move.mindmg, move.energyNeeded);
		    	down.d = Action.Direction.DOWN; down.isMove = true;
		    	Action left = new Action("LEFT", move.reach, move.maxdmg, move.mindmg, move.energyNeeded);
		    	left.d = Action.Direction.LEFT; left.isMove = true;
		    	Action right = new Action("RIGHT", move.reach, move.maxdmg, move.mindmg, move.energyNeeded);
		    	right.d = Action.Direction.RIGHT; right.isMove = true;
		    	    	
		    	Action pass = getActionFromFile(in);
		    	Action fire1 = getActionFromFile(in);
		    	HashMap<String, Action> actions = new HashMap<String, Action>();
		    	actions.put(up.name, up); actions.put(down.name, down); actions.put(left.name, left); actions.put(right.name, right); 
		    	actions.put(pass.name, pass); actions.put(fire1.name, fire1);
		    	
		    	Warrior w = new Warrior(name, pos_x, pos_y, actions, life, energy, speed);
		    	warriors.add(w);
		    	
		    	try {
		    		in.readLine();
		    	}
		    	catch(Exception e) {}
		    }
		    
		    
		    in.close();
		} catch (Exception e) {
			System.err.println("Something is wrong with the file!");
			e.printStackTrace();
		}
		
		return warriors;
		
	}

	private static Action getActionFromFile(BufferedReader in) throws IOException {
		StringTokenizer st = new StringTokenizer(in.readLine());
		st.nextToken(); String name = st.nextToken();
		
		Property reach = getPropertyFromString(in.readLine());
		Property maxdmg = getPropertyFromString(in.readLine());
		Property mindmg = getPropertyFromString(in.readLine());
		Property energyNeeded = getPropertyFromString(in.readLine());
		
		return new Action(name, reach, maxdmg, mindmg, energyNeeded);
		
		
	}

	private static Property getPropertyFromString(String line) {
		StringTokenizer st = new StringTokenizer(line);
		st.nextToken(); st.nextToken();
		
		return new Property(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) );
	}
	
}
