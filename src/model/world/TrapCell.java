package model.world;

public class TrapCell extends Cell{
	private int trapDamage;
	
	public TrapCell() {
		int randomNumber = ( ( (int)(Math.random()*3) ) + 1 ) * 10;
		this.trapDamage = randomNumber;
	}

	public int getTrapDamage() {
		return trapDamage;
	}
	
}
