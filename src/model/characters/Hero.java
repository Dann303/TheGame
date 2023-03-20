package model.characters;

import model.collectibles.Character;
import model.collectibles.Supply;
import model.collectibles.Vaccine;

import java.util.ArrayList;

public class Hero extends Character {
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name,maxHp,attackDmg);
		this.maxActions=maxActions;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}

	public void setMaxActions(int maxActions) {
		this.maxActions = maxActions;
	}

	private ArrayList<Supply> supplyInventory;
	
	public int getActionsAvailable() {
		return actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

 }

