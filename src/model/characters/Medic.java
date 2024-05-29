package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

public class Medic extends Hero{
	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name,maxHp,attackDmg,maxActions);
	}
	public void useSpecial() throws NoAvailableResourcesException,InvalidTargetException, NotEnoughActionsException{
		if (this.getTarget() instanceof Zombie)
			throw new InvalidTargetException("You cannot heal a zombie");
		if (this.getTarget()==null)
			throw new InvalidTargetException("You must select a hero to heal");
		if (!this.isAdjacent())
			throw new InvalidTargetException("Your target is not close");
		super.useSpecial();
		this.getTarget().setCurrentHp(this.getTarget().getMaxHp());
		this.setSpecialAction(false);
	}
}
