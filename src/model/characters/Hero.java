package model.characters;

public class Hero extends Character {
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
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

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name,maxHp,attackDmg,maxActions);
		this.maxActions=maxActions;
	}

 }


