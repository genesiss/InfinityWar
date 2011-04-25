public class State {
	
	Warrior player1;
	Warrior player2;
	
	public double value;
	public boolean isMax;
	
	public Action action = null;	//action which led to this state
	
	public State(Warrior p1, Warrior p2, boolean isMax) {
		player1 = p1;
		player2 = p2;
		this.isMax = isMax;
	}

}
