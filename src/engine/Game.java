package engine;

import model.characters.Hero;
import model.characters.Zombie;

import java.util.ArrayList;
import java.io. * ;
import java.util.LinkedList;
import java.util.Scanner;

public class Game {
    public static ArrayList<Hero> availableHeroes;
    public static ArrayList<Hero> heroes;
    public static ArrayList<Zombie> zombies;
    public static Cell [][] map;

    public static void loadHeroes(String filePath) throws Exception {
        availableHeroes = getHeroesFromCSVFile(filePath);
    }

    private static ArrayList<Hero> getHeroesFromCSVFile(String filePath) throws Exception {
        Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter(",");

        ArrayList<Hero> result = new ArrayList<Hero>();

        while (sc.hasNext()) {
            for (int i = 0; sc.hasNext() && i < 5; i++) {
                String name = sc.next();
                String type = sc.next();
                int maxHp = Integer.parseInt(sc.next());
                int maxActions = Integer.parseInt(sc.next());
                int attackDmg = Integer.parseInt(sc.next());

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

                result.add(h);
            }
        }

        sc.close();

        return result;
    }

}

