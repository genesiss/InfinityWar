package ui2.infinitywar.logic;

import java.util.HashMap;

import ui2.infinitywar.logic.simulateGame.State;

public class test {
	
	public static void main(String args[]) {
		
		
		Warrior[] warriors = ReadSpecs.readFile("test");
		
		simulateGame game = new simulateGame(5, 250, 250);
		State a = game.new State();
		a.player1 = warriors[1];
		a.player2 = warriors[0];
		
		game.a.put(a.player1.name, 0);
		game.a.put(a.player2.name, 0);
		
		a.isMax=true;
		int i = 0;
		while(true) {
			game.runGame(a);
			a = game.new State();
			a.isMax=true;
			a.player1 = warriors[i%2];
			i++;
			a.player2 = warriors[i%2];
			
		}
		//System.out.println();
		
	}
}
