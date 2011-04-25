package ui2.infinitywar.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import ui2.infinitywar.logic.simulateGame.State;

public class Warrior{
	
	public final int LIFE_MAX = 100;
	public final int ENERGY_MAX = 100;
	
	Random rand;
	
	public Map<String, Integer> attributes;
	public List<Action> actions;
	
	public String name;
	
	private String error = "";
	
	public Warrior() {
		rand = new Random();
		attributes = new HashMap<String, Integer>();
		
		actions = new LinkedList<Action>();
		
		String[] input_att_name = {"Energy", "Life", "pos_x"};
		int[] input_att_delta = {0, -5, 1};		
		actions.add(new Action(input_att_name, input_att_delta, null, null));
		
		String[] input_att_name2 = {"Energy", "Life", "pos_x"};
		int[] input_att_delta2 = {-5, 0, -1};				
		actions.add(new Action(input_att_name2, input_att_delta2, null, null));
		
		String[] input_att_name3 = {"Energy", "Life", "pos_y"};
		int[] input_att_delta3 = {-5, 0, -1};			
		actions.add(new Action(input_att_name3, input_att_delta3, null, null));
		
		String[] input_att_name4 = {"Energy", "Life", "pos_y"};
		int[] input_att_delta4 = {-5, 0, 1};			
		actions.add(new Action(input_att_name4, input_att_delta4, null, null));
		
		
	}
	
	
	public Warrior returnClone() {
		Warrior newWarrior = new Warrior();
		newWarrior.attributes = new HashMap<String, Integer>(this.attributes);
		newWarrior.actions = this.actions;
		newWarrior.name = this.name;
		return newWarrior;
	}
	
	
	public int action(Action a, Warrior opponent) {
		
		if(!checkConstraints(a.getInAttName(), a.getInAttDelta(), opponent)) {
			return -1;
		}
		
		for(int i=0; i<a.getInAttName().length; i++) {
			if(a.getInAttName()[i].equals("Distance") || a.getInAttName()[i].equals("Life_max") || a.getInAttName()[i].equals("Life_min"))
				continue;
			attributes.put(a.getInAttName()[i],attributes.get(a.getInAttName()[i])+a.getInAttDelta()[i]);
		}
		
		if(a.getOutAttName() != null && a.getOutAttDelta() != null)
			fightOpponent(a.getOutAttName(), a.getOutAttDelta(), opponent);
		
		if(opponent.attributes.get("Life")<=0)
			return 1;
		
		return 0;
	}
	
	public void fightOpponent(String[] name, int[] delta, Warrior opponent) {
		int min =0;;
		int max=0;
		int reach=0;
		
		for(int i=0; i<name.length; i++) {
			if(name[i].contains("Life_min")) {
				min = delta[i];
			}
			else if(name[i].contains("Life_max")) {
				max = delta[i];
			}
			else if(name[i].contains("Distance")) {
				reach = delta[i];
			}
		}
		
		int step = (max - min) / reach;
        max = min + (reach - getDistance(opponent)) * step;
        if(max-min==0)
        	max++;
		
		int damage = min + rand.nextInt(Math.abs(max-min));
		
		opponent.attributes.put("Life", opponent.attributes.get("Life")-damage);
		
	}
	
	private int getDistance(Warrior opp) {
		return (Math.abs(opp.attributes.get("pos_x")-this.attributes.get("pos_x")) + Math.abs(opp.attributes.get("pos_y")-this.attributes.get("pos_y"))) / this.attributes.get("size");
	}

	
	private boolean checkConstraints(String[] att_name, int[] att_delta, Warrior opp) {
		
		//check if length of both arrays is zero
		if(att_name.length != att_delta.length) {
			this.error = "Attribute names and deltas are not in match.";
			return false;
		}
		
		//check if delta change is possible
		for(int i = 0; i < att_name.length; i++) {
			if(!att_name[i].equals("Distance") && !att_name[i].equals("Life_max") && !att_name[i].equals("Life_min") && !attributes.containsKey(att_name[i])) {
				this.error = "Attribute: "+att_name[i]+" doen't exists.";
				return false;
			}
		
			else if(!att_name[i].equals("Distance") && !att_name[i].equals("Life_max") && !att_name[i].equals("Life_min") && attributes.get(att_name[i])+att_delta[i] < 0) {
				this.error = "Delta change not possible: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
			
			//check boundaries (x direction)
			else if(att_name[i].equals("pos_x") && attributes.get(att_name[i])+att_delta[i] > simulateGame.x_size) {
				this.error = "Delta change not possible: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
			
			//check boundaries (y direction)
			else if(att_name[i].equals("pos_y") && attributes.get(att_name[i])+att_delta[i] > simulateGame.y_size) {
				this.error = "Delta change not possible: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
			
			//check if opponent is at my goal
			else if(att_name[i].contains("pos") && attributes.get(att_name[i])+att_delta[i] == attributes.get(att_name[i])) {
				this.error = "Delta change not possible (opponent is on a goal cell): "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
			
			//check if distance is ok
			else if(att_name[i].contains("Distance") && this.getDistance(opp) > att_delta[i]) {
				this.error = "Opponent is too far: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
			
			//check if i have enough life and energy
			else if(att_name[i].contains("Life") && (this.attributes.get("Life")-att_delta[i]) <= 0) {
				this.error = "I will die if i do this: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
			else if(att_name[i].contains("Energy") && (this.attributes.get("Energy")-att_delta[i]) <= 0) {
				this.error = "I will die if i do this: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
		}
		
		return true;
	}
	
	
}
