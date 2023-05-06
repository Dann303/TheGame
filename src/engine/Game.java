package engine;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.characters.Character;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.*;

import java.awt.*;
import java.util.ArrayList;
import java.io. * ;
import java.util.Scanner;

public class Game {

    public static ArrayList<Hero> availableHeroes;
    public static ArrayList<Hero> heroes = new ArrayList<Hero>();
    public static ArrayList<Zombie> zombies = new ArrayList<Zombie>(10); // maktoob "an arraylist representing te 10 zombies generated in the game" so initialize el size to 10?
    public static Cell[][] map = new Cell[15][15];

    public static int vaccinesUsed = 0;

    // note that there's no constructor

    public static void loadHeroes(String filePath) throws Exception {
        availableHeroes = getHeroesFromCSVFile(filePath);
    }

    public static void startGame(Hero h) {
        initializeGrid();
        spreadCells();

        availableHeroes.remove(h);
        heroes.add(h);
        h.setLocation(new Point(0,0));
    }

    public static boolean checkWin() {
        if (vaccinesUsed >= 5 && heroes.size() >= 5) {
            return true;
        }
        return false;
    }

    public static boolean checkGameOver() {
        if (vaccinesUsed >= 5 && heroes.size() < 5) {
            return true;
        }
        return false;
    }

    public static void endTurn() throws InvalidTargetException, NotEnoughActionsException{
        // zombies attack
        zombiesAttack();

        resetHeroes();

    }

    private static void zombiesAttack() throws InvalidTargetException, NotEnoughActionsException {
        for (int i = 0; i<zombies.size(); i++) {
            Zombie currentZombie = zombies.get(i);
            ArrayList<Cell> surroundingCells = getAdjacentCells(currentZombie.getLocation());
            for (int j = 0; j<surroundingCells.size(); j++) {
                if (surroundingCells.get(j) instanceof CharacterCell && !currentZombie.isSameCharacterType()) {
                    // attack !!!!!!!!!!
                    Character target = ((CharacterCell) surroundingCells.get(j)).getCharacter();
                    currentZombie.setTarget(target);
                    currentZombie.attack();
                }
            }
        }
    }

    private static void resetHeroes() {
        
    }

    private static ArrayList<Cell> getAdjacentCells(Point location) {
        int x = location.x;
        int y = location.y;

        ArrayList<Cell> cells = new ArrayList<Cell>();

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j<= y+1 && !Character.isOutGrid( new Point(i,j) ); j++) {
                cells.add(map[i][j]);
            }
        }

        return cells;
    }

    private static void spreadCells() {
        for (int i = 0; i < 5; i++) {
            Point location = getRandomLocation();
            map[location.x][location.y] = new CollectibleCell(new Vaccine());
            location = getRandomLocation();
            map[location.x][location.y] = new CollectibleCell(new Supply());
            location = getRandomLocation();
            map[location.x][location.y] = new TrapCell();
            location = getRandomLocation();

            map[location.x][location.y] = new CharacterCell(new Zombie());
            location = getRandomLocation();
            map[location.x][location.y] = new CharacterCell(new Zombie());
        }

//        switch (type) {
//            case "Vaccine":
//                for (int i = 0; i < number; i++) {
//                    Point location = getRandomLocation();
//                    map[location.x][location.y] = new CollectibleCell(new Vaccine());
//                }
//                break;
//
//            case "Supply":
//                for (int i = 0; i < number; i++) {
//                    Point location = getRandomLocation();
//                    map[location.x][location.y] = new CollectibleCell(new Supply());
//                }
//                break;
//
//            case "Traps":
//                for (int i = 0; i < number; i++) {
//                    Point location = getRandomLocation();
//                    map[location.x][location.y] = new TrapCell();
//                }
//                break;
//
//            case "Zombies":
//                for (int i = 0; i < number; i++) {
//                    Point location = getRandomLocation();
//                    map[location.x][location.y] = new CharacterCell(new Zombie());
//                }
//                break;
//        }
    }

    private static Point getRandomLocation(){
        int x = (int)(Math.random()*15);
        int y = (int)(Math.random()*15);

        if (map[x][y] instanceof EmptyCell) {
           return new Point(x,y);
        } else {
            return getRandomLocation();
        }
    }


    private static void initializeGrid() {
        for (int i = 0; i <= 14; i++) {
            for (int j = 0; j <= 14; j++) {
                Game.map[i][j] = new EmptyCell();
            }
        }
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