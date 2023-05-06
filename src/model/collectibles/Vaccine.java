package model.collectibles;

import engine.Game;
import exceptions.InvalidTargetException;
import model.characters.Hero;

public class Vaccine implements Collectible{

    public Vaccine() {

    }

    @Override
    public void pickUp(Hero h) {
        h.getVaccineInventory().add(this);
    }

    @Override
    public void use(Hero h) throws InvalidTargetException {
        h.cure();
        Game.vaccinesUsed++;

        h.getVaccineInventory().remove(this);
    }

}