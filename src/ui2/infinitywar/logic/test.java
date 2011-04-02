package ui2.infinitywar.logic;

import java.util.HashMap;

import ui2.infinitywar.logic.simulateGame.State;

public class test {
	
	public static void main(String args[]) {
		Warrior player1 = new Warrior();
		player1.name = "Uros";
		player1.attributes = new HashMap<String, Integer>();
		player1.attributes.put("pos_x", 10);
		player1.attributes.put("pos_y", 10);
		player1.attributes.put("Life", 100);
		player1.attributes.put("Energy", 100);
		player1.attributes.put("Distance", 20);
		player1.attributes.put("Power", 20);
		String[] input_att_name = {"Life", "Energy"};
		int[] input_att_delta = {-1, -2};
		String[] output_att_name = {"Life"};
		int[] output_att_delta = {-3};
		player1.actions.add(new Action(input_att_name, input_att_delta, output_att_name, output_att_delta));
		
		Warrior player2 = new Warrior();
		player2.attributes = new HashMap<String, Integer>();
		player2.attributes.put("pos_x", 15);
		player2.attributes.put("pos_y", 15);
		player2.attributes.put("Life", 100);
		player2.attributes.put("Energy", 100);
		player2.attributes.put("Distance", 20);
		player2.attributes.put("Power", 20);
		player2.name = "Marko";
		String[] input_att_name2 = {"Life", "Energy"};
		int[] input_att_delta2 = {-1, -2};
		String[] output_att_name2 = {"Life"};
		int[] output_att_delta2 = {-1};
		player2.actions.add(new Action(input_att_name2, input_att_delta2, output_att_name2, output_att_delta2));
		
		simulateGame game = new simulateGame(10, 450, 450);
		State a = game.new State();
		a.player1 = player1;
		a.player2 = player2;
		a.isMax=true;
		
		a = game.chooseMove(a);
		System.out.println();
		
	}
}
