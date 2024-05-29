package model.world;
import java.util.*;
public class TrapCell extends Cell{
	private int trapDamage;
	public TrapCell() {
		super();
		trapDamage= (new Random().nextInt(1,4))*10;
	}
	public int getTrapDamage() {
		return trapDamage;
	}
}
