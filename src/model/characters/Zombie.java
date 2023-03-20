package model.characters;

public class Zombie extends Character{
    private static int ZOMBIES_COUNT;
    public Zombie() {
        String name = "Zombie " + this.ZOMBIES_COUNT;
        super(name, 40, 10);
    }

}
