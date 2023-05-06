package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.EmptyCell;

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

	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		if(this.isTargetAdjacent() && !this.isSameCharacterType()) {
			Character target = this.getTarget();
			target.setCurrentHp(target.getCurrentHp() - this.getAttackDmg());

			// target died, al baka2 lelah
			if (target.getCurrentHp() <= 0) {
				// character dies
				this.onCharacterDeath();
			} else {
				// if target didn't die, defend
				target.defend(this); // f1 attacks f2, f1 is this, f2 is the target of this...
			}
		} else {
			// attack failed
			throw new InvalidTargetException("Invalid Target");
		}
	}

	public void defend(Character c){
		int defendingDmg = this.getAttackDmg() / 2;
		c.setCurrentHp(c.currentHp - defendingDmg);

		//check if the character that was defended against died or not
		if (c.currentHp <= 0) {
			// character dies
			this.onCharacterDeath();
		} else {
			// if target didn't die then do nothing, until now...
		}
	}

	public void onCharacterDeath(){
		if (this instanceof Hero) {
			Game.heroes.remove(this);
		} else if (this instanceof  Zombie) {
			Game.zombies.remove(this);
		}
	}

	public boolean isTargetAdjacent() {
		int x1 = this.location.x;
		int y1 = this.location.y;

		int x2 = this.target.location.x;
		int y2 = this.target.location.y;


		// overlapping walla la2
		if (x1 == x2 && y1 == y2) {
			System.out.println("Target overlapping character!");
			return false;
		}

		// this checks whether the character exists outside the board for some reason...
		if (this.isOutGrid(this.getLocation())) {
			System.out.println("Character out of board!");
			return false;
		}

		// this checks whether the target exists outside the board for some reason...
		if (this.isOutGrid(this.getTarget().getLocation())) {
			System.out.println("Target out of board!");
			return false;
		}

		//      Demorgan's       gowa el box             rip Demorgan's
		return (Math.abs(x2-x1) <= 1) && (Math.abs(y2-y1) <= 1);

	}

	// checks if the character and its target are the same type or not ie: hero and hero aw zombie w zombie
	public boolean isSameCharacterType() {
		Character target = this.target;
		if (this instanceof Zombie && target instanceof Zombie)
			return true;
		if (this instanceof Hero && target instanceof Hero)
			return true;
		return false;
	}

	public static boolean isOutGrid(Point p){
		int x = p.x;
		int y = p.y;

		if (x < 0 || x > 14 || y < 0 || y > 14)
			return true;
		return false;
	}

}