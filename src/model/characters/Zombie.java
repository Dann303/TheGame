package model.characters;

import model.collectibles.Character;

public class Zombie extends Character {
    private static int ZOMBIES_COUNT;
    public Zombie() {
        super("Zombie " + ZOMBIES_COUNT, 40, 10);
    }

}
