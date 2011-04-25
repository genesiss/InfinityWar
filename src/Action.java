import java.util.Random;


public class Action {
	
	Property reach;
	Property maxdmg;
	Property mindmg;
	Property energyNeeded;
	
	String name;
	
	Random random;
	
	boolean isMove = false;
	
	enum Direction {UP, DOWN, LEFT, RIGHT};
	
	Direction d;	//direction of move
	
	public Action(String name, Property reach, Property maxdmg, Property mindmg, Property energyNeeded) {
		
		this.reach = reach;
		this.maxdmg = maxdmg;
		this.mindmg = mindmg;
		this.energyNeeded = energyNeeded;
		this.random = new Random();
		this.name = name;
		
	}
	
	/**
	 * Changes Warriors values according to action properties.
	 * @param me
	 * @param you
	 * @return	true if action succeeded, false if something went wrong.
	 */
	public boolean fight(Warrior me, Warrior you) {
		
		if(!(me.goingSlow > 0)) {
			me.speed.value = me.speed.INIT_VALUE;
		}
		
		double currDistance = this.getDistance(me, you);
		
		//check if I have enough energy for this action
		if(!checkEnergy(me, this.energyNeeded.value))	return false;
	
		//check if there is an obstacle on the path		
		if(!checkObstacles(me, you)) return false;
		double newDistance = this.getDistance(me, you);	//compute new distance
		if(newDistance > currDistance)	me.runningAway=true;	//set running away
		else	me.runningAway=false;
		
		//check if opponent is in reach
		//if(!checkReach(me, you)) return false;
		
		//calculate damage
		double damage = calculateDamage(me, you);
		
		//if opponent is running away and is hit, slow him
		if(damage > 0 && you.runningAway) {
			you.speed.value = you.speed.value/2;
			you.goingSlow = 2;
		}
		
		if(me.goingSlow > 0)
			me.goingSlow--;

		
		if(me.energy.value-this.energyNeeded.value > me.energy.INIT_VALUE)	return false;
		
		//change my energy
		me.energy.value -= this.energyNeeded.value;
		
		//change opponents life
		you.life.value -= damage;
	
		return true;

		
	}

	private double calculateDamage(Warrior me, Warrior you) {
		if(checkReach(me, you))
			return this.mindmg.value + (random.nextDouble() * (this.maxdmg.value - this.mindmg.value));
		else {
			double distance = this.getDistance(me, you);
			double delta = distance-this.reach.value;
			double dmg =  this.mindmg.value + (random.nextDouble() * (this.maxdmg.value - this.mindmg.value));
			return dmg / Math.pow(2, delta);
		}
	}

	private boolean checkReach(Warrior me, Warrior you) {
		if(this.reach.value >= Math.sqrt(Math.pow(me.pos_x-you.pos_x,2) + Math.pow(me.pos_y-you.pos_y,2)))
				return true;
		
		return false;
	}
	
	private double getDistance(Warrior me, Warrior you) {
		return Math.sqrt(Math.pow(me.pos_x-you.pos_x,2) + Math.pow(me.pos_y-you.pos_y,2));
	}

	private boolean checkObstacles(Warrior me, Warrior you) {
		if(!isMove)	return true;	//if action is not move, don't check for obstacles
		
		int speed = (int) me.speed.value;
		int myX = me.pos_x;
		int myY = me.pos_y;
		int yourX = you.pos_x;
		int yourY = you.pos_y;
		
		int distance = 0;
		
		switch(d) {
		
			case UP:
				for(int i = 1; i <= speed; i++) {
					if(myY-i < 0)	break;
					if((myY-i) == yourY && myX == yourX)	break;
					distance++;
				}
				break;
				
			case DOWN:
				for(int i = 1; i <= speed; i++) {
					if(myY+i > Game.sizeX) break;
					if((myY+i) == yourY && myX == yourX)	break;
					distance++;
				}
				break;
				
			case LEFT:
				for(int i = 1; i <= speed; i++) {
					if(myX-i < 0) break;
					if((myX-i) == yourX && myY == yourY)	break;
					distance++;
				}
				break;
				
			case RIGHT:
				for(int i = 1; i <= speed; i++) {
					if(myX+i > Game.sizeY) break;
					if((myX+i) == yourX && myY == yourY)	break;
					distance++;
				}
				break;
				
		}
		
		
		if(distance > 0) {
			switch(d) {
				case UP:
					me.pos_y -= distance;
					break;
				case DOWN:
					me.pos_y += distance;
					break;
				case LEFT:
					me.pos_x -= distance;
					break;
				case RIGHT:
					me.pos_x += distance;
					break;
			}
			return true;
		}
		
		return false;
	}

	private boolean checkEnergy(Warrior me, double value) {
		if(me.energy.value-value < 0)
			return false;
		
		return true;
	}

}
