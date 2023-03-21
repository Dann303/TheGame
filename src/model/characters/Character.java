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

	this.currentHp = maxHp;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Point getLocation() {
	return this.location;
}

public void setLocation(Point location) {
	this.location = location;
}

public int getMaxHp() {
	return maxHp;
}

public int getCurrentHp() {
	return currentHp;
}

public void setCurrentHp(int currentHp) {
	// validate whether currentHp has a higher value than maxHp or not if it is higher than currentHp should get the highest possible value for its hp which is maxHp
	if (currentHp > this.maxHp) {
		this.currentHp = this.maxHp;
	} else {
		this.currentHp = currentHp;
	}
}

public int getAttackDmg() {
	return attackDmg;
}

public Character getTarget() {
	return target;
}

public void setTarget(Character target) {
	this.target = target;
}

}
