package model.characters;

import model.collectibles.Character;

public class Zombie extends Character {
    private static int ZOMBIES_COUNT;
    public Zombie() {
        super("Zombie " + this.ZOMBIES_COUNT, 40, 10);
    }

}
