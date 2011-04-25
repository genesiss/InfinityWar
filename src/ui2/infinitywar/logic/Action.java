package ui2.infinitywar.logic;

public class Action {

	
	private String[] input_att_name;
	private int[] input_att_delta;
	private String[] output_att_name;
	private int[] output_att_delta;

	
	/**
	 * @param input_att_name
	 * @param input_att_delta
	 * @param output_att_name
	 * @param output_att_delta
	 */
	public Action(String[] input_att_name,
			int[] input_att_delta,
			String[] output_att_name,
			int[] output_att_delta) {
		
		this.input_att_name = input_att_name;
		this.input_att_delta = input_att_delta;
		this.output_att_name = output_att_name;
		this.output_att_delta = output_att_delta;
		
	}
	public String[] getInAttName() {
		return input_att_name;
	}
	
	public int[] getInAttDelta() {
		return input_att_delta;
	}
	
	public String[] getOutAttName() {
		return output_att_name;
	}
	
	public int[] getOutAttDelta() {
		return output_att_delta;
	}
	

	
	

	
}
