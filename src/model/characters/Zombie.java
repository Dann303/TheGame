package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class Zombie extends Character{

    private static int ZOMBIES_COUNT = 0;

    public Zombie() {
        super("Zombie " + ++ZOMBIES_COUNT, 40, 10);
    }

    @Override
    public void attack() throws InvalidTargetException, NotEnoughActionsException {
        super.attack();
    }
}