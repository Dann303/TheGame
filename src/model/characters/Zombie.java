package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;

import java.util.ArrayList;

public class Zombie extends Character{

    private static int ZOMBIES_COUNT = 0;

    public Zombie() {
        super("Zombie " + ++ZOMBIES_COUNT, 40, 10);
    }

    @Override
    public void attack() throws InvalidTargetException, NotEnoughActionsException {
        ArrayList<Cell> surroundingCells = Game.getAdjacentCells(this.getLocation());
        boolean attackedOnce = false;
        
        int index = (int)(Math.random()*surroundingCells.size());
        while (surroundingCells.size()>0 && (surroundingCells.get(index) instanceof CharacterCell)) {
            Character target = ((CharacterCell) surroundingCells.get(index)).getCharacter();
            this.setTarget(target);
            if (target != null && !attackedOnce && !this.isSameCharacterType()) {
                // attack !!!!!!!!!!
                attackedOnce = true;
                super.attack();
            }
            surroundingCells.remove(index);
            index = (int)(Math.random()*surroundingCells.size());
        }
    }
}