package model.characters;

import java.awt.Point;

public abstract class Character {

	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;

	public Character(String name, int maxHp, int attackDmg) {
		this.name = name;
		this.maxHp = maxHp;
		this.attackDmg = attackDmg;

		// initially currentHp is set to maxHp
		this.currentHp = maxHp;
	}

	public String getName() {
		return this.name;
	}

	public Point getLocation() {
		return this.location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return this.maxHp;
	}

	public int getCurrentHp() {
		return this.currentHp;
	}

	public void setCurrentHp(int currentHp) {
		// validate currentHp against maxHp and 0
		// if it is higher than currentHp should get the highest possible value for its hp which is maxHp
		if (currentHp > this.maxHp) {
			this.currentHp = this.maxHp;
		} else if (currentHp < 0) {
			// if its lower than 0 then currentHp should be set to 0
			this.currentHp = 0;
		} else {
			this.currentHp = currentHp;
		}
	}

	public int getAttackDmg() {
		return this.attackDmg;
	}

	public Character getTarget() {
		return this.target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

}