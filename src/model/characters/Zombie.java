package model.characters;

public class Zombie extends Character{

    private static int ZOMBIES_COUNT = 0;

    public Zombie() {
        super("Zombie " + ++ZOMBIES_COUNT, 40, 10);
    }

    @Override
    public void attack() {
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