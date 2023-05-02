package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;
import model.collectibles.Vaccine;

import java.util.ArrayList;

public abstract class Hero extends Character {

	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name,maxHp,attackDmg);
		this.maxActions=maxActions;
		this.vaccineInventory = new ArrayList<>();
		this.supplyInventory = new ArrayList<>();

		// initially actionsAvailable are equal to maxActions
		this.actionsAvailable = maxActions;
	}


	public int getActionsAvailable() {
		return this.actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public int getMaxActions() {
		return this.maxActions;
	}

	public boolean isSpecialAction() {
		return this.specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		// thought for later, if you just wanna return a copy of this array (array slicing) not the actual array for security purposes then return new ArrayList<Hero>(this.vaccineInverntory);
		return this.vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		// thought for later, if you just wanna return a copy of this array (array slicing) not the actual array for security purposes then return new ArrayList<Hero>(this.supplyInventory);
		return this.supplyInventory;
	}

	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		if(this.isAdjacent()) {
			Character target = this.getTarget();
			target.setCurrentHp(target.getCurrentHp() - this.getAttackDmg());

			// target died, al baka2 lelah
			if (target.getCurrentHp() <= 0) {
				// bye bye canboora.
			}
		}
	}

}