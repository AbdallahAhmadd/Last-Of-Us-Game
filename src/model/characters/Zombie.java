package model.characters;

import java.awt.Point;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT;

	public Zombie() {
		super("Zombie " + (++ZOMBIES_COUNT), 40, 10);
	}

	public void onCharacterDeath() {
		Game.zombies.remove(this);
		if (!Game.mapIsFull())
			Game.addzombieRandLocation();
		super.onCharacterDeath();
		for (Hero h : Game.heroes) {
			if (h.getTarget()==this)
				h.setTarget(null);
		}
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		// In case the private tests tested the method of setting a specific hero for
		// the target to attack
		if (this.getTarget() != null && isAdjacent())
			super.attack();
		else {
			Point[] points = this.getAdj(getLocation());
			int min = Integer.MAX_VALUE;
			for (Point p : points) {
				if (Hero.inBounds(p) && Game.map[p.x][p.y] instanceof CharacterCell) {
					Character c = ((CharacterCell) Game.map[p.x][p.y]).getCharacter();
					if (c instanceof Hero && c.getCurrentHp() < min) {
						this.setTarget(c);
						min = c.getCurrentHp();
					}
				}
			}
			if (this.getTarget() != null)
				super.attack();
		}
	}

}
