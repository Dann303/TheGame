package model.characters;

<<<<<<< Updated upstream
public class Zombie extends Character{
=======
public class Zombie extends Character {
>>>>>>> Stashed changes
    private static int ZOMBIES_COUNT;
    public Zombie() {
<<<<<<< Updated upstream
        String name = "Zombie " + this.ZOMBIES_COUNT;
        super(name, 40, 10);
=======
        super("Zombie " + ZOMBIES_COUNT, 40, 10);
>>>>>>> Stashed changes
    }

}
