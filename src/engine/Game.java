package engine;

import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Zombie;

import java.util.ArrayList;
import java.io. * ;
import java.util.Scanner;

public class Game {
    public static ArrayList<Hero> availableHeroes;
    public static ArrayList<Hero> heroes;
    public static ArrayList<Zombie> zombies;
    public static Cell [][] map;

    public static void loadHeroes(String filePath) throws Exception {
        availableHeroes = getHeroesFromCSVFile(filePath);
        System.out.println(availableHeroes);
    }

    private static ArrayList<Hero> getHeroesFromCSVFile(String filePath) throws Exception {
        Scanner sc = new Scanner(new File(filePath));
//        sc.useDelimiter(",");
        sc.useDelimiter("\n");

        ArrayList<Hero> result = new ArrayList<Hero>();

        while (sc.hasNext()) {

            String line = sc.next();
            String[] heroes = line.split(",");

            String name = heroes[0];
            System.out.println(name);
            String type = heroes[1];
            System.out.println(type);
            int maxHp = Integer.parseInt(heroes[2]);
            System.out.println(maxHp);
            int maxActions = Integer.parseInt(heroes[3]);
            System.out.println(maxActions);
            int attackDmg = Integer.parseInt(heroes[4]);
            System.out.println(attackDmg);

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

        sc.close();

        return result;
    }

    public static void main(String[] args) {
        try {
            loadHeroes("src/shared files/Heros.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

