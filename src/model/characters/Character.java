package model.characters;

import java.awt.Point;
import exceptions.*;
import model.world.CharacterCell;

import java.util.Random;

import org.hamcrest.core.IsInstanceOf;
import engine.*;

public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;

	public Character(String name, int maxHp, int attackDmg) {
		this.name = name;
		this.maxHp = maxHp;
		this.attackDmg = attackDmg;
		this.currentHp = maxHp;
		this.location = new Point();
	}

	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if (currentHp > maxHp)
			this.currentHp = maxHp;
		else if (currentHp < 0)
			this.currentHp = 0;
		else
			this.currentHp = currentHp;
	}

	public Point[] getAdj(Point point) {
		int x = (int) point.getX();
		int y = (int) point.getY();
		Point[] a = new Point[8];
		a[0] = new Point(x + 1, y);
		a[1] = new Point(x - 1, y);
		a[2] = new Point(x, y + 1);
		a[3] = new Point(x, y - 1);
		a[4] = new Point(x + 1, y + 1);
		a[5] = new Point(x + 1, y - 1);
		a[6] = new Point(x - 1, y + 1);
		a[7] = new Point(x - 1, y - 1);
		return a;

	}

	public int getAttackDmg() {
		return attackDmg;
	}

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public boolean isAdjacent() {
		int diffx = Math.abs(target.getLocation().x - location.x);
		int diffy = Math.abs(target.getLocation().y - location.y);
		return diffx <= 1 && diffy <= 1;
	}

	public void onCharacterDeath() {
		((CharacterCell) Game.map[this.getLocation().x][this.getLocation().y]).setCharacter(null);

	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		target.setCurrentHp(target.getCurrentHp() - this.attackDmg);
		target.defend(this);
		if (target.getCurrentHp() == 0)
			target.onCharacterDeath();

	}

	public void defend(Character c) {
		this.setTarget(c);
		c.setCurrentHp(c.getCurrentHp() - (this.attackDmg / 2));
		if (c.getCurrentHp() == 0)
			c.onCharacterDeath();

	}

}
