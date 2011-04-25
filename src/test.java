import java.util.HashMap;


public class test {
	
	public static double lifeDist = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Game game = new Game(500, 500);
		
		Property reach = new Property(2, 2, 1, 3, 1);
		Property maxDmg = new Property(30, 30, 20, 30, 5);  
		Property minDmg = new Property(20, 20, 20 , 20 ,1);
		Property energyNeeded = new Property(10, 10, 10, 10, 1);
		Action a1 = new Action("Action", reach, maxDmg, minDmg, energyNeeded);
		
		Property reacha2 = new Property(4, 4, 4, 4, 1);
		Action ap1 = new Action("Action", reacha2, maxDmg, minDmg, energyNeeded);
		
		Property reach2 = new Property(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 1);
		Property maxDmg2 = new Property(0, 0, 0, 0, 1);  
		Property minDmg2 = new Property(0, 0, 0 , 0 ,1);
		Property energyNeeded2 = new Property(8, 8, 8, 8, 1);
		Action a2 = new Action("UP", reach2, maxDmg2, minDmg2, energyNeeded2);
		a2.isMove = true; a2.d = Action.Direction.UP;
		
		Action a3 = new Action("DOWN", reach2, maxDmg2, minDmg2, energyNeeded2);
		a3.isMove = true; a3.d = Action.Direction.DOWN;
		
		Action a4 = new Action("LEFT", reach2, maxDmg2, minDmg2, energyNeeded2);
		a4.isMove = true; a4.d = Action.Direction.LEFT;
		
		Action a5 = new Action("RIGHT", reach2, maxDmg2, minDmg2, energyNeeded2);
		a5.isMove = true; a5.d = Action.Direction.RIGHT;
		
		Property reach3 = new Property(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 1);
		Property maxDmg3 = new Property(0, 0, 0, 0, 1);  
		Property minDmg3 = new Property(0, 0, 0 , 0 ,1);
		Property energyNeeded3 = new Property(-30, -30, -30, -30, 1);
		Action a6 = new Action("PASS", reach3, maxDmg3, minDmg3, energyNeeded3);
		
		HashMap<String, Action> p1Actions = new HashMap<String, Action>();
		p1Actions.put("Action", a1);
		p1Actions.put("UP", a2);
		p1Actions.put("DOWN", a3);
		p1Actions.put("LEFT", a4);
		p1Actions.put("RIGHT", a5);
		p1Actions.put("PASS", a6);
		
		HashMap<String, Action> p2Actions = new HashMap<String, Action>();
		p2Actions.put("Action", ap1);
		p2Actions.put("UP", a2);
		p2Actions.put("DOWN", a3);
		p2Actions.put("LEFT", a4);
		p2Actions.put("RIGHT", a5);
		p2Actions.put("PASS", a6);
		
		Property lifep1 = new Property(100, 100, 100, 100, 1);
		Property energyp1 = new Property(100, 100, 100, 100, 1);
		Property speedp1 = new Property(1, 1, 1, 1, 1);
		
		Property lifep2 = new Property(100, 100, 100, 100, 1);
		Property energyp2 = new Property(100, 100, 100, 100, 1);
		Property speedp2 = new Property(1, 1, 1, 1, 1);
		
		Warrior p1 = new Warrior("John", 12, 12, p1Actions, lifep1, energyp1, speedp1);
		Warrior p2 =  new Warrior("Denver", 6, 12, p1Actions, lifep2, energyp2, speedp2);
		
		State start = new State(p2, p1, true);
		HashMap<String, Integer> wins  = new HashMap<String, Integer>();
		wins.put("John", 0);
		wins.put("Denver", 0);
		
		
		
		for(int i = 0; i < 100 ; i++) {
			lifep1 = new Property(100, 100, 100, 100, 1);
			energyp1 = new Property(100, 100, 100, 100, 1);
			speedp1 = new Property(3, 3, 3, 3, 1);
			
			lifep2 = new Property(100, 100, 100, 100, 1);
			energyp2 = new Property(100, 100, 100, 100, 1);
			speedp2 = new Property(1, 1, 1, 1, 1);
			
			p1 = new Warrior("John", 12, 12, p1Actions, lifep1, energyp1, speedp1);
			p2 =  new Warrior("Denver", 6, 12, p1Actions, lifep2, energyp2, speedp2);
			
			if(i%2 == 0) start = new State(p2, p1, true);
			else start = new State(p1, p2, true);
			String name = game.simulateGame(start, 4, true);
			wins.put(name, wins.get(name)+1);
		}	
		
		System.out.println("John"+wins.get("John"));
		System.out.println("Denver"+wins.get("Denver"));
		System.out.println("Life dist: "+test.lifeDist/100);

	}

}
