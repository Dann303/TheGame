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
        //shelna cure 3shan kda hateb2a habal cuz we call vaccine.use inside el cure nafsaha.
//        h.cure();
        Game.vaccinesUsed++;

        // keda keda ben3mel remove fel hero zat nafso ------ UPDATE --- BRUH ---- me7tag teremove it hena
        h.getVaccineInventory().remove(this);
    }

}