import java.util.UUID;


public abstract class Bojevnik {
	
	private double LIFE_MAX;
	private double ENERGY_MAX;
	
	private UUID ID;
	
	private String ime;
	private double trpeznost;
	
	private double domet;
	private double hitrost;
	
	private double life;
	private double energy;
	
	private int pos_x;
	private int pos_y;
	
	private int size;
	
	/**
	 * Konstruktor ustvari novega bojevnika.
	 * @param ime 
	 * @param trpeznost	
	 * @param domet	
	 * @param hitrost 
	 * @param life
	 * @param energy
	 */
	public Bojevnik(
			String ime,
			double trpeznost,
			double domet,
			double hitrost,
			double life,
			double energy,
			double LIFE_MAX,
			double ENERGY_MAX,
			int size )
	{
		this.setIme(ime);
		this.setTrpeznost(trpeznost);
		this.setDomet(domet);
		this.setHitrost(hitrost);
		this.setLife(life);
		this.setEnergy(energy);
		this.setLIFE_MAX(LIFE_MAX);
		this.setENERGY_MAX(ENERGY_MAX);
		this.setSize(size);
		this.generateID();
	}
	
	private void generateID() {
		this.ID = UUID.randomUUID();
	}	

	private enum Move
	{
		UP,
		RIGHT,
		LEFT,
		DOWN
	}
	
	private void move(Move where)
	{
		switch(where) {
			case UP:
				break;
			case DOWN:
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
		}
	}
	
	private int distance(Bojevnik nasprotnik) {
		return ( Math.abs( nasprotnik.getPos_x() - this.getPos_x() ) + Math.abs(nasprotnik.getPos_y() - this.getPos_y() ) / this.getSize() );
	}
	
	private enum Action {
		FIRE1,
		FIRE2,
		SPECIAL,
		PASS
	}
	
	abstract void fire1(double reach, double energy, double min_damage, double max_damage);
	abstract void fire2(double reach, double energy, double min_damage, double max_damage);
	abstract void special();
	abstract void pass(double energy);
	
	
	/**
	 * 
	 * JUST GETTERS AND SETTERS DOWN HERE.
	 * 
	 */
	public double getTrpeznost() {
		return trpeznost;
	}

	public double getLIFE_MAX() {
		return LIFE_MAX;
	}

	public void setLIFE_MAX(double lIFE_MAX) {
		LIFE_MAX = lIFE_MAX;
	}

	public double getENERGY_MAX() {
		return ENERGY_MAX;
	}

	public void setENERGY_MAX(double eNERGY_MAX) {
		ENERGY_MAX = eNERGY_MAX;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setTrpeznost(double trpeznost) {
		this.trpeznost = trpeznost;
	}

	public double getHitrost() {
		return hitrost;
	}

	public void setHitrost(double hitrost) {
		this.hitrost = hitrost;
	}

	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}
	
	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public double getDomet() {
		return domet;
	}

	public void setDomet(double domet) {
		this.domet = domet;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public int getPos_x() {
		return pos_x;
	}

	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}

	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}
	
	
	

}
