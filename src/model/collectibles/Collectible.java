package model.collectibles;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.*;

public interface Collectible {
	void pickUp(Hero h);
	void use(Hero h) throws NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException;
}
