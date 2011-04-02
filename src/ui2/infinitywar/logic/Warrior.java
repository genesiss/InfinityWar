package ui2.infinitywar.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ui2.infinitywar.logic.simulateGame.State;

public class Warrior{
	
	public final int LIFE_MAX = 100;
	public final int ENERGY_MAX = 100;
	
	public Map<String, Integer> attributes;
	public List<Action> actions;
	
	public String name;
	
	private String error = "";
	
	public Warrior() {
		actions = new LinkedList<Action>();
		
		String[] input_att_name = {"Energy", "Life", "pos_x"};
		int[] input_att_delta = {-10, -5, 1};		
		actions.add(new Action(input_att_name, input_att_delta, null, null));
		
		String[] input_att_name2 = {"Energy", "Life", "pos_x"};
		int[] input_att_delta2 = {-10, -5, -1};				
		actions.add(new Action(input_att_name2, input_att_delta2, null, null));
		
		String[] input_att_name3 = {"Energy", "Life", "pos_y"};
		int[] input_att_delta3 = {-10, -5, -1};			
		actions.add(new Action(input_att_name3, input_att_delta3, null, null));
		
		String[] input_att_name4 = {"Energy", "Life", "pos_y"};
		int[] input_att_delta4 = {-10, -5, 1};			
		actions.add(new Action(input_att_name4, input_att_delta4, null, null));
		
		
	}
	
	
	public Warrior returnClone() {
		Warrior newWarrior = new Warrior();
		newWarrior.attributes = new HashMap<String, Integer>(this.attributes);
		newWarrior.actions = this.actions;
		return newWarrior;
	}
	
	
	public int action(Action a, Warrior opponent) {
		
		if(!checkConstraints(a.getInAttName(), a.getInAttDelta())) {
			return -1;
		}
		
		for(int i=0; i<a.getInAttName().length; i++) {
			attributes.put(a.getInAttName()[i],attributes.get(a.getInAttName()[i])+a.getInAttDelta()[i]);
		}
		
		if(a.getOutAttName() != null && a.getOutAttDelta() != null)
			fightOpponent(a.getOutAttName(), a.getOutAttDelta(), opponent);
		
		
		return 0;
	}
	
	public void fightOpponent(String[] name, int[] delta, Warrior opponent) {
		
		for(int i=0; i<name.length; i++)
			opponent.attributes.put(name[i], opponent.attributes.get(name[i])+delta[i]);
		
	}

	
	private boolean checkConstraints(String[] att_name, int[] att_delta) {
		
		//check if length of both arrays is zero
		if(att_name.length != att_delta.length) {
			this.error = "Attribute names and deltas are not in match.";
			return false;
		}
		
		//check if delta change is possible
		for(int i = 0; i < att_name.length; i++) {
			if(!attributes.containsKey(att_name[i])) {
				this.error = "Attribute: "+att_name[i]+" doen't exists.";
				return false;
			}
		
			else if(attributes.get(att_name[i])+att_delta[i] < 0) {
				this.error = "Delta change not possible: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
			
			else if(att_name[i].equals("pos_x") && attributes.get(att_name[i])+att_delta[i] > simulateGame.x_size) {
				this.error = "Delta change not possible: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
			
			else if(att_name[i].equals("pos_y") && attributes.get(att_name[i])+att_delta[i] > simulateGame.y_size) {
				this.error = "Delta change not possible: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
		}
		
		return true;
	}
	
	
}
