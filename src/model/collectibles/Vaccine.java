package model.collectibles;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Hero;
import model.characters.Zombie;
import model.world.CharacterCell;

public class Vaccine implements Collectible {
	public Vaccine() {
	}

	public void pickUp(Hero h) {
		h.getVaccineInventory().add(this);
	}

	public void use(Hero h){
		h.getVaccineInventory().remove(this);
		Zombie z=(Zombie)h.getTarget();
		Game.zombies.remove(z);
		int randind=((int)(Math.random()*Game.availableHeroes.size()));
		Hero newhero=Game.availableHeroes.remove(randind);
		Game.heroes.add(newhero);
		newhero.setLocation(h.getTarget().getLocation());
		//Hero.setVis(newhero.getAdj(newhero.getLocation()));
		((CharacterCell)Game.map[newhero.getLocation().x][newhero.getLocation().y]).setCharacter(newhero);
		for (Hero hero : Game.heroes) {
			if ((hero.getTarget() instanceof Zombie) && (Zombie) hero.getTarget() == z)
				hero.setTarget(null);
		}
		h.setTarget(null);
		
	}
}
