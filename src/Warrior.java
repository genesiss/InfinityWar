import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Warrior {
	
	Property life;
	Property energy;
	Property speed;

	HashMap<String, Action> actions;
	
	String name;
	
	int pos_x;
	int pos_y;
	
	public boolean runningAway = false;	//if set, he is running away from the opponent
	public int goingSlow = 0;			//if > 0, he is slowed down for this amount of moves 
	
	public Warrior(String name, int pos_x, int pos_y, HashMap<String, Action> actions, Property life, Property energy, Property speed) {
		
		this.name = name;
		this.actions = actions;
		this.life = life;
		this.energy = energy;
		this.speed = speed;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		
	}
	

	/**
	 * Construct new random Warrior 
	 * according to role model limitations.
	 * @param roleModel
	 */
	public Warrior newRandomWarrior() {
		Warrior randomW = this.deepclone();	//create a clone
		randomW.life.setRandomValue();
		randomW.energy.setRandomValue();
		randomW.speed.setRandomValue();
		
		Iterator<String> it = randomW.actions.keySet().iterator();
		while(it.hasNext()) {
			Action a = randomW.actions.get(it.next());
			a.reach.setRandomValue();
			a.maxdmg.setRandomValue();
			a.mindmg.setRandomValue();
			a.energyNeeded.setRandomValue();
		}
		return randomW;
	}

	/**
	 * Returns cloned Warrior. Actions are not deep copied, because they are not changing.
	 */
	public Warrior clone() {
		
		
		Warrior dolly = new Warrior(this.name, this.pos_x, this.pos_y, this.actions,
				new Property(life.value, life.INIT_VALUE, life.LOW_VAL, life.HIGH_VAL, life.CLASS_LENGTH, "life"),
				new Property(energy.value, energy.INIT_VALUE, energy.LOW_VAL, energy.HIGH_VAL, energy.CLASS_LENGTH, "energy"),
				new Property(speed.value, speed.INIT_VALUE, speed.LOW_VAL, speed.HIGH_VAL, speed.CLASS_LENGTH, "speed"));
		
		dolly.goingSlow = this.goingSlow;
		dolly.runningAway = this.runningAway;
		
		return dolly;
	
	}
	
	/**
	 * Returns cloned Warrior.
	 * Actions are deep cloned.
	 */
	public Warrior deepclone() {

		Warrior dolly = new Warrior(this.name, this.pos_x, this.pos_y, this.actions,
				new Property(life.value, life.INIT_VALUE, life.LOW_VAL, life.HIGH_VAL, life.CLASS_LENGTH, "life"),
				new Property(energy.value, energy.INIT_VALUE, energy.LOW_VAL, energy.HIGH_VAL, energy.CLASS_LENGTH, "energy"),
				new Property(speed.value, speed.INIT_VALUE, speed.LOW_VAL, speed.HIGH_VAL, speed.CLASS_LENGTH, "speed"));
		
		dolly.goingSlow = this.goingSlow;
		dolly.runningAway = this.runningAway;
		
		HashMap<String, Action> actionsCopy = new HashMap<String, Action>();
		//these properties are shared between all move actions
		Property reachMove = null;
		Property maxdmgMove = null;
		Property mindmgMove = null;
		Property energyNeededMove = null;
		
		Iterator<String> it = dolly.actions.keySet().iterator();
		while(it.hasNext()) {
			Action a = dolly.actions.get(it.next());
			if(a.isMove) {
				if(reachMove == null) {
					reachMove = new Property(a.reach.value, a.reach.INIT_VALUE, a.reach.LOW_VAL, a.reach.HIGH_VAL, a.reach.CLASS_LENGTH, "Move_reach");
					maxdmgMove = new Property(a.maxdmg.value, a.maxdmg.INIT_VALUE, a.maxdmg.LOW_VAL, a.maxdmg.HIGH_VAL, a.maxdmg.CLASS_LENGTH, "Move_maxdmg");
					mindmgMove = new Property(a.mindmg.value, a.mindmg.INIT_VALUE, a.mindmg.LOW_VAL, a.mindmg.HIGH_VAL, a.mindmg.CLASS_LENGTH, "Move_mindmg");
					energyNeededMove = new Property(a.energyNeeded.value, a.energyNeeded.INIT_VALUE, a.energyNeeded.LOW_VAL, a.energyNeeded.HIGH_VAL, a.energyNeeded.CLASS_LENGTH, "Move_energyNeeded");
				}
				Action newAction = new Action(a.name, reachMove, maxdmgMove, mindmgMove, energyNeededMove);
				newAction.isMove = true; newAction.d = a.d;
				actionsCopy.put(newAction.name, newAction);
			}
			else {
				Property reach = new Property(a.reach.value, a.reach.INIT_VALUE, a.reach.LOW_VAL, a.reach.HIGH_VAL, a.reach.CLASS_LENGTH, a.name+"_reach");
				Property maxdmg = new Property(a.maxdmg.value, a.maxdmg.INIT_VALUE, a.maxdmg.LOW_VAL, a.maxdmg.HIGH_VAL, a.maxdmg.CLASS_LENGTH, a.name+"_maxdmg");
				Property mindmg = new Property(a.mindmg.value, a.mindmg.INIT_VALUE, a.mindmg.LOW_VAL, a.mindmg.HIGH_VAL, a.mindmg.CLASS_LENGTH, a.name+"_mindmg");
				Property energyNeeded = new Property(a.energyNeeded.value, a.energyNeeded.INIT_VALUE, a.energyNeeded.LOW_VAL, a.energyNeeded.HIGH_VAL, a.energyNeeded.CLASS_LENGTH, a.name+"_energyNeeded");
				Action newAction = new Action(a.name, reach, maxdmg, mindmg, energyNeeded);
				actionsCopy.put(newAction.name, newAction);
			}
		}
		
		dolly.actions = actionsCopy;
		
		return dolly;
	
	}
	
}
