import java.util.Random;


public class Property {
	
	String name;
	
	double value;	//value of the property
	
	double INIT_VALUE; //value at initialization
	
	int LOW_VAL; //lowest possible value
	int HIGH_VAL; //highest possible value
	
	int CLASS_LENGTH; //length of one discretization class
	
	private int NUM_OF_CLASSES; //number of discretization classes
	
	private Random random;
	
	
	/**
	 * The value must be in compliance with property rules!!
	 * @param value
	 * @param LOW_VAL
	 * @param HIGH_VAL
	 * @param D_FACTOR
	 */
	public Property(double value, double INIT_VALUE, int LOW_VAL, int HIGH_VAL, int CLASS_LENGTH, String name) {
		
		this.value = value;
		this.INIT_VALUE = INIT_VALUE;
		this.LOW_VAL = LOW_VAL;
		this.HIGH_VAL = HIGH_VAL;
		this.CLASS_LENGTH = CLASS_LENGTH;
		this.NUM_OF_CLASSES = getNumOfDiscClasses();
		this.random = new Random();
		this.name = name;
		
	}
	
	/**
	 * Get number of discretization classes.
	 * @return
	 */
	private int getNumOfDiscClasses() {
		return (this.HIGH_VAL-this.LOW_VAL+1)/this.CLASS_LENGTH;
	}
	
	/**
	 * Make the value discrete and set it.
	 * @param value
	 * @return
	 */
	public boolean setDiscValue(double value) {
		
		int d = this.LOW_VAL;
		while(d < value && d < this.HIGH_VAL)
			d+=this.CLASS_LENGTH;
		
		this.value = d;
		return setValue(d);
		
	}
	
	/**
	 * Promote the level if possible.
	 * @return
	 */
	public boolean levelUp() {
		if(checkValue(this.value+this.CLASS_LENGTH)) {
			this.value = this.value+this.CLASS_LENGTH;
			return true;
		}
		return false;
	}
	
	/**
	 * Degrade the level if possible.
	 * @return
	 */
	public boolean levelDown() {
		if(checkValue(this.value-this.CLASS_LENGTH)) {
			this.value = this.value-this.CLASS_LENGTH;
			return true;
		}
		return false;
	}
	
	/**
	 * Set value (value gets checked).
	 * @param value
	 * @return
	 */
	public boolean setValue(double value) {
		if(checkValue(value)) {
			this.value = value;
			return true;
		}
		return false;		
	}
	
	/**
	 * Check if value is in compliance with property rules.
	 * @param value
	 * @return
	 */
	private boolean checkValue(double value) {
		if(value >= this.LOW_VAL && value <= this.HIGH_VAL)
			return true;
		else
			return false;
	}
	
	/**
	 * Set random value according to limitations.
	 */
	public boolean setRandomValue() {
		double val = this.LOW_VAL + (this.HIGH_VAL - this.LOW_VAL) * random.nextDouble();
		if(setDiscValue(val)) {
			this.INIT_VALUE = this.value;
			return true;
		}
		return false; 
	}
	
	@Override
	public String toString() {
		String out = this.name+": "+this.value+"\n";
		return out;
	}
	
	

}
