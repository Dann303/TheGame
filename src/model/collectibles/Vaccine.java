package model.collectibles;

import engine.Game;
import exceptions.InvalidTargetException;
import model.characters.Hero;
import model.characters.Zombie;
import model.world.CharacterCell;

import java.awt.*;

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

        Zombie target = (Zombie) h.getTarget();
        Point targetLocation = target.getLocation();

        int index = (int)(Math.random()*Game.availableHeroes.size());

        Hero newHero = Game.availableHeroes.get(index);
        Game.availableHeroes.remove(index);

        newHero.setLocation(targetLocation);
        Game.map[targetLocation.x][targetLocation.y] = new CharacterCell(newHero);

        Game.zombies.remove(target);

        // keda keda ben3mel remove fel hero zat nafso ------ UPDATE --- BRUH ---- me7tag teremove it hena
        h.getVaccineInventory().remove(this);

        h.setActionsAvailable(h.getActionsAvailable() - 1);

        Game.heroes.add(newHero);
//			newHero.setSquareVisible();

    }

}