package ui2.infinitywar.logic;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Warrior {
	
	private final int LIFE_MAX = 100;
	private final int ENERGY_MAX = 100;
	
	private int pos_x;
	private int pos_y;
	
	private Map<String, Integer> attributes;
	private List<Action> actions;
	
	private String name;
	private int id;
	
	private String error = "";
	
	private enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	
	private boolean canMove(Direction d) {
		
		return true;
	}
	
	private int action(Action a, Warrior opponent) {
		
		if(!checkConstraints(a.getInAttName(), a.getInAttDelta())) {
			return -1;
		}
		
		for(int i=0; i<a.getInAttName().length; i++) {
			attributes.put(a.getInAttName()[i],attributes.get(a.getInAttName()[i])+a.getInAttDelta()[i]);
		}
		
		
		fightOpponent(a.getOutAttName(), a.getOutAttDelta());
		
		
		return 0;
	}
	
	private void fightOpponent(String[] output_att_name, int[] output_att_delta) {
		// TODO Auto-generated method stub
		
	}

	
	private int move(Direction d) {
		
		//check if can make a move in direction d
		//check boundaries and emptiness of desired cell
		if(!canMove(d))
			return -1;
		
		switch(d) {
			case UP:
				this.pos_y-=1;
				break;
			case DOWN:
				this.pos_y-=1;
				break;
			case LEFT:
				this.pos_x-=1;
				break;
			case RIGHT:
				this.pos_x+=1;
				break;			
		}
		
		return 0;
	}
	
	private boolean checkConstraints(String[] att_name, int[] att_delta) {
		
		//check if length of both arrays is zero
		if(att_name.length != att_delta.length) {
			this.error = "Attribute names and deltas are not in match.";
			return false;
		}
		
		//check if delta change is possible
		for(int i = 0; i < att_name.length; i++)
			if(!attributes.containsKey(att_name[i])) {
				this.error = "Attribute: "+att_name[i]+" doen't exists.";
				return false;
			}
		
			else if(attributes.get(att_name[i])+att_delta[i] < 0) {
				this.error = "Delta change not possible: "+att_name[i]+": "+attributes.get(att_name[i])+" Delta: "+att_delta[i];
				return false;
			}
		
		
		return true;
	}
	
	
}
