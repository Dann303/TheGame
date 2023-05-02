package model.characters;

public class Medic extends Hero{

	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}

	@Override
	public void attack() {
		if(this.getActionsAvailable() > 0) {
			super.attack();
			this.setActionsAvailable(this.getActionsAvailable() - 1);
		} else {
			// erza3 exception !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// throw new NotEnoughActionsException("Not enough actions are available!");
		}
	}

}