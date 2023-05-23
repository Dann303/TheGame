package engine;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import gui.mrd.Main;
import gui.mrd.Scene3;
import model.characters.*;
import model.characters.Character;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.*;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io. * ;
import java.util.Scanner;

public class Game {

    public static ArrayList<Hero> availableHeroes = null;
    public static ArrayList<Hero> heroes = new ArrayList<Hero>();
    public static ArrayList<Zombie> zombies = new ArrayList<Zombie>(10); // maktoob "an arraylist representing te 10 zombies generated in the game" so initialize el size to 10?
    public static Cell[][] map = new Cell[15][15];

    // note that there's no constructor

    public static void loadHeroes(String filePath) throws Exception {
        availableHeroes = getHeroesFromCSVFile(filePath);
    }

    public static void startGame(Hero h) {

        initializeGrid();

        availableHeroes.remove(h);
        heroes.add(h);
        h.setLocation(new Point(0,0));
        map[0][0] = new CharacterCell(h);
    
        spreadCells();

        h.setSquareVisible();

        setCellsIcons();

        Main.s3 = new Scene3();
    }

    private static void setCellsIcons() {
        for (int i = 0; i<15; i++) {
            for (int j=0; j<15; j++) {
                Cell currentCell = map[i][j];

                if (!currentCell.isVisible()) {
                    currentCell.setIcon("invisible");
                } else if (currentCell instanceof CharacterCell) {
                    if (((CharacterCell) currentCell).getCharacter() == null) {
                        // empty cell
                        currentCell.setIcon("nothing");
                    } else if (((CharacterCell) currentCell).getCharacter() instanceof Hero) {
                        // hero
                        currentCell.setIcon("hero");
                    } else {
                        // zombie
                        currentCell.setIcon("zombie");
                    }
                } else if (currentCell instanceof CollectibleCell) {
                    if (((CollectibleCell) currentCell).getCollectible() instanceof Vaccine) {
                        // vaccine
                        currentCell.setIcon("vaccine");
                    } else {
                        // supply
                        currentCell.setIcon("supply");
                    }
                } else {
                    // trap cell
                    currentCell.setIcon("nothing");
                }

            }
        }
    }

    public static boolean checkWin() {
        if (heroes.size()>=5 && (countOfVaccinesOnMap()<=0 && vaccinesWithHeroes()<=0))
            return true;
        return false;
    }

    public static boolean checkGameOver() {
        if (heroes.size() <= 0 || (countOfVaccinesOnMap() <= 0 && vaccinesWithHeroes() <= 0))
            return true;
        return false;
    }

    private static int vaccinesWithHeroes() {
        int result = 0;
        for (int i = 0; i < heroes.size(); i++){
            Hero currentHero = heroes.get(i);
            result += currentHero.getVaccineInventory().size();
        }
        return result;
    }

    private static int countOfVaccinesOnMap() {
        int result = 0;
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++) {
                Cell cell = map[i][j];
                if (cell instanceof CollectibleCell && ((CollectibleCell) cell).getCollectible() instanceof Vaccine)
                    result++;
            }
        }
        return result;
    }

    public static void endTurn() throws InvalidTargetException, NotEnoughActionsException{
        // zombies attack
        zombiesAttack();

        // reset heroes action points, target and special action
        resetHeroes();

        // reset zombies
        resetZombies();
 
        // spawn zombie
        spawnZombie();
        
        // visibility
        resetVisibility();

    }

    private static void zombiesAttack() throws InvalidTargetException, NotEnoughActionsException {
        for (int i = 0; i<zombies.size(); i++) {
            Zombie currentZombie = zombies.get(i);
            currentZombie.attack();
        }
    }

    private static void resetHeroes() {
        for (int i = 0; i < heroes.size(); i++) {
            Hero currentHero = heroes.get(i);
            currentHero.setActionsAvailable(currentHero.getMaxActions());
            currentHero.setSpecialAction(false);
            currentHero.setTarget(null);
        }
    }

    private static void resetZombies() {
        for (int i = 0; i < zombies.size(); i++) {
            Zombie currentZombie = zombies.get(i);
            currentZombie.setTarget(null);
        }
    }

    private static void resetVisibility() {
        // tafy el noor
        for (int i = 0; i <= 14; i++) {
            for (int j = 0; j <= 14; j++) {
                Game.map[i][j].setVisible(false);
            }
        }

        for (int i = 0; i < heroes.size(); i++) {
            Hero currentHero = heroes.get(i);
            Point currentLocation = currentHero.getLocation();
            ArrayList<Cell> surroundingCells = getAdjacentCells(currentLocation);

            for (int j = 0; j < surroundingCells.size(); j++) {
                surroundingCells.get(j).setVisible(true);
            }

        }
    }

    public static void spawnZombie() {
        Zombie newZombie = new Zombie();
        zombies.add(newZombie);
        Point newLocation = getRandomLocation();
        newZombie.setLocation(newLocation);
        map[newLocation.x][newLocation.y] = new CharacterCell(newZombie);
    }

    public static ArrayList<Cell> getAdjacentCells(Point location) {
        int x = location.x;
        int y = location.y;

        ArrayList<Cell> cells = new ArrayList<Cell>();

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j<= y+1; j++) {
                 if (!Character.isOutGrid( new Point(i,j) )) {
                	 cells.add(map[i][j]);
                 }                    
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

            Zombie zombie1 = new Zombie();
            location = getRandomLocation();
            map[location.x][location.y] = new CharacterCell(zombie1);
            zombie1.setLocation(location);
            zombies.add(zombie1);

            Zombie zombie2 = new Zombie();
            location = getRandomLocation();
            map[location.x][location.y] = new CharacterCell(zombie2);
            zombie2.setLocation(location);
            zombies.add(zombie2);

        }
    }

    private static Point getRandomLocation(){
        int x = (int)(Math.random()*15);
        int y = (int)(Math.random()*15);

        if (map[x][y] instanceof CharacterCell && ((CharacterCell)map[x][y]).getCharacter() == null) {
           return new Point(x,y);
        } else {
            return getRandomLocation();
        }
    }


    public static void initializeGrid() {
        for (int i = 0; i <= 14; i++) {
            for (int j = 0; j <= 14; j++) {
                Game.map[i][j] = new CharacterCell(null);
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

    private static void showVisibility() {
        for (int i=0; i<15; i++){
            for (int j=0; j<15; j++){
                if (map[i][j].isVisible())
                    System.out.print("X ");
                else
                    System.out.print("O ");
            }
            System.out.println();
        }
    }

}