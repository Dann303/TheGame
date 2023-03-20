package model.collectibles;
public abstract class Character {
private String name;
private java.awt.Point location;
private int maxHp;
private int currentHp;
private int attackDmg;
private Character target;
Character(String name, int maxHp, int attackDmg)
{
	
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public java.awt.Point getLocation() {
	return location;
}
public void setLocation(java.awt.Point location) {
	this.location = location;
}
public int getMaxHp() {
	return maxHp;
}
public void setMaxHp(int maxHp) {
	this.maxHp = maxHp;
}
public int getCurrentHp() {
	return currentHp;
}
public void setCurrentHp(int currentHp) {
	this.currentHp = currentHp;
}
public int getAttackDmg() {
	return attackDmg;
}
public void setAttackDmg(int attackDmg) {
	this.attackDmg = attackDmg;
}
public Character getTarget() {
	return target;
}
public void setTarget(Character target) {
	this.target = target;
}
}
