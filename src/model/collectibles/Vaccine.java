package model.collectibles;

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

        h.getVaccineInventory().remove(this);
    }

}