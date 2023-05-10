package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

import java.awt.*;
import java.util.ArrayList;

public abstract class Hero extends Character {

	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name,maxHp,attackDmg);
		this.maxActions=maxActions;
		this.vaccineInventory = new ArrayList<>();
		this.supplyInventory = new ArrayList<>();

		// initially actionsAvailable are equal to maxActions
		this.actionsAvailable = maxActions;
	}


	public int getActionsAvailable() {
		return this.actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public int getMaxActions() {
		return this.maxActions;
	}

	public boolean isSpecialAction() {
		return this.specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		// thought for later, if you just wanna return a copy of this array (array slicing) not the actual array for security purposes then return new ArrayList<Hero>(this.vaccineInverntory);
		return this.vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		// thought for later, if you just wanna return a copy of this array (array slicing) not the actual array for security purposes then return new ArrayList<Hero>(this.supplyInventory);
		return this.supplyInventory;
	}

	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		super.attack();
	}

	public void move(Direction d) throws MovementException, NotEnoughActionsException {
		if (this.getActionsAvailable() <= 0) {
			throw new NotEnoughActionsException("Not enough action points to move");
		}

		Point oldPosition = this.getLocation();
		int x = oldPosition.x;
		int y = oldPosition.y;

		switch (d) {
			case UP:
				x++;
				break;
			case DOWN:
				x--;
				break;
			case RIGHT:
				y++;
				break;
			case LEFT:
				y--;
				break;
			default:
				throw new MovementException("Invalid Direction");
		}

		Point newPosition = new Point(x,y);

		if (this.isOutGrid(newPosition))
			throw new MovementException("Out of bounds!");

		if (Game.map[x][y] instanceof CharacterCell && ((CharacterCell)Game.map[x][y]).getCharacter() != null)
			throw new MovementException("A character already stands there!");

		// Position is valid, character can move, will move later

		// check if there is a collectible or a trap.

		if (Game.map[x][y] instanceof CollectibleCell) {
			CollectibleCell cell = (CollectibleCell) Game.map[x][y];
			cell.getCollectible().pickUp(this);
		}

		if (Game.map[x][y] instanceof TrapCell) {
			int trapDamage = ((TrapCell) Game.map[x][y]).getTrapDamage();

			this.setCurrentHp(this.getCurrentHp() - trapDamage);

			// mat walla lesa yakhwana? check if died

		}

		// ba3d ma shofna el cell dy kan feeha eh abl manet7arek feeha, move ba2a to it fel map w ka location fel character
		this.setLocation(newPosition);
		Game.map[x][y] = new CharacterCell(this);
		this.setActionsAvailable(this.getActionsAvailable() - 1);

		// erase el old position
		Game.map[oldPosition.x][oldPosition.y] = new CharacterCell(null);

		if (this.getCurrentHp() <= 0) {
			this.onCharacterDeath();
		} else {
			//visibility
			this.setSquareVisible();
		}
	}

	public void useSpecial() throws InvalidTargetException, NoAvailableResourcesException {
		if (this.supplyInventory.size()<=0) {
			// no supplies yeb2a error
			throw new NoAvailableResourcesException("No enough supplies");
		}

		if (this.isSpecialAction()) {
			// matkamelsh el method 3shan mafrood mate2darsh testakhdem beta3 w howa already activated
			// 7eta fel gui disable button
			return;
		}

		if (this instanceof Explorer) {
			this.observeMap();
		}
		if (this instanceof Medic) {
			this.healTarget();
			this.setSpecialAction(false);
		}

		this.setSpecialAction(true);

		//get random supply from inventory
		int index = (int)(Math.random()*this.supplyInventory.size());
		Supply supplyUsed = this.getSupplyInventory().get(index);
		supplyUsed.use(this);


	}

	public void cure() throws InvalidTargetException, NoAvailableResourcesException, NotEnoughActionsException {
		if (this.getActionsAvailable() <= 0){
			throw new NotEnoughActionsException("No enough action points");
		}
		if(this.getVaccineInventory().size() <= 0) {
			// empty
			throw new NoAvailableResourcesException("Vaccines Inventory is empty!");
		}
		if(this.getTarget()!=null && !this.isSameCharacterType() && this.isTargetAdjacent()) {

			// use el vaccine
			// first get random vaccine item from the vaccine inventory
			// ne3melo random bardo
			int index = (int)(Math.random()*this.getVaccineInventory().size());
			Vaccine vaccineUsed = this.getVaccineInventory().get(index);
			vaccineUsed.use(this);
		} else {
			throw new InvalidTargetException("Cannot cure!");
		}
	}

	private void observeMap() {
		for (int i=0; i < 15; i++) {
			for (int j=0; j < 15; j++) {
				Game.map[i][j].setVisible(true);
			}
		}
	}

	private void healTarget(){
		int maxHealth = this.getTarget().getMaxHp();

		this.getTarget().setCurrentHp(maxHealth);
	}

	public void setSquareVisible() {
		int x = this.getLocation().x;
		int y = this.getLocation().y;

		for (int i = x-1; i <= x+1; i++) {
			for (int j=y-1; j<= y+1; j++) {
				if (!isOutGrid( new Point(i,j) ))
					Game.map[i][j].setVisible(true);
			}
		}
	}

	public static void main(String[] args) throws InvalidTargetException, NotEnoughActionsException {

		Game.initializeGrid();
		Fighter rubina = new Fighter("rubina", 100, 10, 3);
		rubina.setLocation(new Point(2,2));
		Game.heroes.add(rubina);
		
		
		Game.endTurn();
		
		Fighter danny = new Fighter("danny", 100, 10, 3);
		danny.setLocation(new Point(5,0));
		Game.heroes.add(danny);
		
		
		for(int i=1; i<=3; i++) {
			for (int j=1; j<=3; j++) {
				new Zombie().setLocation(new Point(i,j));
			}
		}

		Game.endTurn();
		
		Game.showVisibility();
		
//		for (int i=-1; i<=15; i++) {
//			for (int j=-1; j<=15; j++) {
//				System.out.print(Character.isOutGrid(new Point(i, j)));
//			}
//			System.out.println();
//		}
		
//		Medic rubina = new Medic("Rubina", 2, 20, 3);
//		Zombie z = new Zombie();
//
//		Game.heroes.add(rubina);
//
//		Game.map = new Cell[15][15];
//
//		Game.map[2][2] = new CharacterCell(rubina);
//		Game.map[2][3] = new CharacterCell(z);
//
//		rubina.setLocation(new Point(2,2));
//		z.setLocation(new Point(2,3));
//
//		rubina.setTarget(z);
//
//		System.out.println(Game.heroes.size());
//		System.out.println(rubina.getCurrentHp());
//
//		try {
//			rubina.attack();
//		} catch (InvalidTargetException e) {
//			e.printStackTrace();
//		} catch (NotEnoughActionsException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(Game.heroes.size());
//		System.out.println(rubina.getCurrentHp());

//		try {
//			Game.loadHeroes("src/shared files/Heros.csv");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//		Fighter f1 = new Fighter("Joel Miller",140,5,30);
//		Game.startGame(f1);
//
//		f1.setSquareVisible();


//		Fighter f1 = new Fighter("Rubina", 300, 1, 2);
//		Zombie z = new Zombie();
//
//		f1.setTarget(z);
//		f1.setSpecialAction(true);
//
//		f1.setLocation(new Point(3,4));
//		z.setLocation(new Point(3,3));
//
//		for (int i = 0; i<35; i++) {
//			try {
//				f1.attack();
//			} catch (NotEnoughActionsException e) {
//				e.printStackTrace();
//			} catch (InvalidTargetException e) {
//				e.printStackTrace();
//			}
//			System.out.println(z.getCurrentHp());
//		}

	}


}