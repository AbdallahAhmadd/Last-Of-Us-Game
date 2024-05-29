package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.*;
import model.collectibles.*;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public abstract class Hero extends Character {
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		vaccineInventory = new ArrayList<Vaccine>();
		supplyInventory = new ArrayList<Supply>();
		specialAction = false;

	}

	public int getActionsAvailable() {
		return actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		if (this.getTarget() == null)
			throw new InvalidTargetException("You must select a zombie to attack");
		if (this.getTarget() instanceof Hero)
			throw new InvalidTargetException("A Hero cannot attack another Hero");
		if (!this.isAdjacent())
			throw new InvalidTargetException("You cannot attack targets in non adjacent cells");
		boolean fighteronSpecial= this instanceof Fighter && this.isSpecialAction();
		if (actionsAvailable == 0 && !fighteronSpecial )
			throw new NotEnoughActionsException("You need at least one action point to attack");
		super.attack();
		if (!fighteronSpecial)
			actionsAvailable--;
	}

	public static boolean inBounds(Point p) {
		return p.getX() >= 0 && p.getX() <= 14 && p.getY() >= 0 && p.getY() <= 14;
	}

	public static void setVis(Point[] a) {
		for (int i = 0; i < a.length; i++) {
			if (inBounds(a[i]) && Game.map[a[i].x][a[i].y] != null)
				Game.map[a[i].x][a[i].y].setVisible(true);
		}
	}

	public void move(Direction d) throws MovementException, NotEnoughActionsException {
		if (actionsAvailable == 0)
			throw new NotEnoughActionsException("You need at least one action point to move");
		int i = (int) this.getLocation().getX();
		int j = (int) this.getLocation().getY();
		int newi = i;
		int newj = j;
		switch (d) {
		case UP: newi++;break;
		case DOWN: newi--;break;
		case LEFT: newj--;break;
		case RIGHT: newj++;break;
		default : throw new MovementException("You can only move UP,DOWN,LEFT, or RIGHT");
		}
		if (!inBounds(new Point(newi, newj)))
			throw new MovementException("You cannot move out if map bounds");
		if (((Game.map[newi][newj] instanceof CharacterCell)
				&& ((CharacterCell) (Game.map[newi][newj])).getCharacter() != null))
			throw new MovementException("Cell is already occupied");
		if (Game.map[newi][newj] instanceof CollectibleCell)
			((CollectibleCell) Game.map[newi][newj]).getCollectible().pickUp(this);
		else if (Game.map[newi][newj] instanceof TrapCell) {
			int trapDmg = ((TrapCell) Game.map[newi][newj]).getTrapDamage();
			this.setCurrentHp(this.getCurrentHp() - trapDmg);
		}
		((CharacterCell) Game.map[i][j]).setCharacter(null);
		Game.map[newi][newj] = new CharacterCell(this);
		Game.map[newi][newj].setVisible(true);
		this.setLocation(new Point(newi, newj));
		actionsAvailable--;
		if (this.getCurrentHp() == 0)
			this.onCharacterDeath();
		else 
			setVis(this.getAdj(new Point(newi, newj)));
	}

	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException {
		if (this.supplyInventory.isEmpty())
			throw new NoAvailableResourcesException("You don't have enough supply");
		supplyInventory.get(0).use(this);
	}

	public void onCharacterDeath() {
		Game.heroes.remove(this);
		super.onCharacterDeath();
		for (Zombie z : Game.zombies) {
			if (z.getTarget()==this)
				z.setTarget(null);
		}
	}

	public void cure() throws InvalidTargetException, NoAvailableResourcesException, NotEnoughActionsException {
		if (actionsAvailable == 0)
			throw new NotEnoughActionsException("You need at least one point to cure a zombie");
		if (this.getTarget() == null)
			throw new InvalidTargetException("You must select a zombie to cure");
		if (this.getTarget() instanceof Hero)
			throw new InvalidTargetException("You cannot cure a hero");
		if (!(this.isAdjacent()))
			throw new InvalidTargetException("You can only cure zombies in adjacent cells");
		if (this.getVaccineInventory().isEmpty())
			throw new NoAvailableResourcesException("You don't have enough vaccine");
		this.vaccineInventory.get(0).use(this);
		this.setActionsAvailable(this.getActionsAvailable() - 1);
	}
	
}
