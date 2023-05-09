package model.collectibles;

import exceptions.InvalidTargetException;
import model.characters.Hero;

public class Supply implements Collectible{
	
    public Supply () {

    }

    @Override
    public void pickUp(Hero h) {
        h.getSupplyInventory().add(this);
    }

    @Override
    public void use(Hero h) throws InvalidTargetException {

    }
}