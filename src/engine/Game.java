package engine;

import model.characters.*;
import model.world.Cell;

import java.util.ArrayList;
import java.io. * ;
import java.util.Scanner;

public class Game {
    public static ArrayList<Hero> availableHeroes;
    public static ArrayList<Hero> heroes;
    public static ArrayList<Zombie> zombies;
    public static Cell[][] map;

    public static void loadHeroes(String filePath) throws Exception {
        availableHeroes = getHeroesFromCSVFile(filePath);
    }

    private static ArrayList<Hero> getHeroesFromCSVFile(String filePath) throws Exception {
        Scanner sc = new Scanner(new File(filePath));
//        sc.useDelimiter(",");
        sc.useDelimiter("\n");

        ArrayList<Hero> result = new ArrayList<Hero>();

        while (sc.hasNext()) {

            // extract one line that contains all the hero's data
            String line = sc.next();

            // split the string containing the hero's data into an array of strings by using the seperator , >>>> eg: "name,age,gender" --> ["name", "age", "gender]
            String[] heroes = line.split(",");

            // assign each value to its corresponding variable
            String name = heroes[0];
            String type = heroes[1];
            int maxHp = Integer.parseInt(heroes[2]);
            int maxActions = Integer.parseInt(heroes[3]);
            int attackDmg = Integer.parseInt(heroes[4].trim());


            // According to 'type' create an appropriate object (Fighter / Medic / Explorer)
            Hero h = null;
            switch (type) {
                case "FIGH":
                    h = new Fighter(name, maxHp, attackDmg, maxActions);
                    break;
                case "EXP":
                    h = new Explorer(name, maxHp, attackDmg, maxActions);
                    break;
                case "MED":
                    h = new Medic(name, maxHp, attackDmg, maxActions);
                    break;
            }

            // add the created object to the result array
            result.add(h);
        }

        sc.close();

        return result;
    }

}

