package engine;

import model.characters.*;
import model.world.Cell;

import java.util.ArrayList;
import java.io. * ;
import java.util.Scanner;

public class Game {

    public static ArrayList<Hero> availableHeroes;
    public static ArrayList<Hero> heroes = new ArrayList<Hero>();
    public static ArrayList<Zombie> zombies = new ArrayList<Zombie>(10); // maktoob "an arraylist representing te 10 zombies generated in the game" so initialize el size to 10?
    public static Cell[][] map;

    // note that there's no constructor

    public static void loadHeroes(String filePath) throws Exception {
        availableHeroes = getHeroesFromCSVFile(filePath);
    }

    // The method that returns an array of heroes from a csv file by entering its path from content root as a parameter (string format)
    private static ArrayList<Hero> getHeroesFromCSVFile(String filePath) throws Exception {
        Scanner sc = new Scanner(new File(filePath));

        // set delemiter to \n so that it would return all stream of data right until a new line starts, so returns line by line
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
            int attackDmg = Integer.parseInt(heroes[4].trim()); //here trim was used to remove white spaces and the new line that was causing the error when converting the string into an int, since this word was the last word in the line it had \n attached to it when extracted so had to be dealt with

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

        // close the scanner
        sc.close();

        return result;
    }

}