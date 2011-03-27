
public class Game {
	
	private final int DEPTH = 3;
	
	public static void main(String[] args) {
		
		Vojak a = new Vojak("Uros", 23, 23, 23, 23, 23, 23, 23, 23);
		Tank b = new Tank("Marko", 23, 23, 23, 23, 23, 23, 23, 23);
		
		MinMax tree = new MinMax(a, b, 3, new MinMax.Node(a, b, 0, 1)); //a=player1, b=player2, 3=maxDepth, Node=start node(player, atDepth, 1=player 1 (has to be 1 or 2)
		
		tree.calcMinMax(tree.root);
		
	}

}
