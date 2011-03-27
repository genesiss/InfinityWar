import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class MinMax {
	
	private static int maxDepth;
	public static Node root;
	public static int currDepth;
	public Bojevnik player1;
	public Bojevnik player2;

	public static class Node {
		
		Bojevnik bojevnik1;
		Bojevnik bojevnik2;
		
		double value;
		double prob;
		ArrayList<Node> children;
		int depth;
		boolean terminal;
		int player;
		
		public Node(Bojevnik bojevnik1, Bojevnik bojevnik2, int depth, int player) {
			this.bojevnik1 = bojevnik1;
			this.bojevnik2 = bojevnik2;
			this.depth = depth;
			this.player = player;
		}
	}
	
	public MinMax(Bojevnik bojevnik1, Bojevnik bojevnik2, int depth, Node root) {
		this.root = root;
		player1 = bojevnik1;
		player2 = bojevnik2;
		maxDepth = depth;
		currDepth = 0;
		buildTree(root, depth);
		printTree(root);
	}
	
	
	public static void main(String[] args) {
		Vojak a = new Vojak("Uros", 23, 23, 23, 23, 23, 23, 23, 23);
		Tank b = new Tank("Uro2", 23, 23, 23, 23, 23, 23, 23, 23);
		//MinMax minMaxTree = new MinMax(a, b, 3);
	}
	
	public void printTree(Node node) {
		
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addLast(node);
		while(!queue.isEmpty()) {
			Node curr = queue.pollFirst();
			//System.out.println(curr.bojevnik.getIme()+" "+curr.depth);
			if(curr.children != null)
				for(Node child : curr.children)
					queue.addLast(child);
		}
		
			
	}
	
	
	private void buildTree(Node node, int depth) {
		if(depth == 0)
			return;

		
		addLevel(node);
		
		for(Node child : node.children) {
			buildTree(child, depth-1);
		}
		
	}
		
	public double calcMinMax(Node node) {
		if(node.terminal || node.depth == maxDepth)
			return calcHeuristics(node);
		
		double alfa = Double.MIN_VALUE;
		for (Node child : node.children) {
			alfa = Math.max(alfa, -calcMinMax(child));
		}
		
		return alfa;
	}
	
	public double calcHeuristics(Node node) {
		double retVal = 0;
		Bojevnik me=null, you=null;
		
		if(node.player == 1) {
			me=node.bojevnik1;
			you=node.bojevnik2;
		}
		else {
			me=node.bojevnik2;
			you=node.bojevnik1;
		}
		
		if(me.getLife() <= 0)
			retVal -= 1000;
		
		if(you.getLife() <= 0)
			retVal += 1000;
		
		retVal += (1.2*me.getLife()/me.getLIFE_MAX());
		retVal += (-1.0*you.getLife()/you.getLIFE_MAX());
		retVal += (0.1*me.getEnergy()/me.getENERGY_MAX());
		retVal += (-1.0*you.getEnergy()/you.getENERGY_MAX());
		
		return retVal;

	}
		
	public void addLevel(Node node) {
		if(node.children == null || node.children.isEmpty()) {
			generateChildren(node);
			
			return;
		}
		
	}
	
	public void generateChildren(Node node) {
		node.children = new ArrayList<Node>();
		for(Bojevnik.Action a : Bojevnik.Action.values()) {
				Node newNode = null;
				try {
					if(node.player == 1) {
						newNode = new Node(node.bojevnik1.clone(), node.bojevnik2.clone(), node.depth+1, 2);
						newNode.bojevnik1.action = a;
						updateStats(newNode.bojevnik1, newNode.bojevnik2);
					}
					else {
						newNode = new Node(node.bojevnik1.clone(), node.bojevnik2.clone(), node.depth+1, 1);
						newNode.bojevnik2.action = a;
						updateStats(newNode.bojevnik2, newNode.bojevnik1);
					}
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				node.children.add(newNode);
				
		}
		
	}

	
	
}
