package engine;

import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.characters.Character;
import model.collectibles.*;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Game {
	public static ArrayList<Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell[][] map = new Cell[15][15];

	public static void loadHeroes(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero = null;
			switch (content[1]) {
			case "FIGH":
				hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]));
				break;
			case "MED":
				hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]));
				break;
			case "EXP":
				hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
						Integer.parseInt(content[3]));
				break;
			}
			availableHeroes.add(hero);
			line = br.readLine();

		}
		br.close();

	}

	public static Point randomPointGenerator() {
		int x, y;
		do {
			x = (int) (Math.random() * 15);
			y = (int) (Math.random() * 15);
		} while (!((Game.map[x][y] instanceof CharacterCell)
				&& ((CharacterCell) Game.map[x][y]).getCharacter() == null));
		return new Point(x, y);
	}

	public static void addzombieRandLocation() {
		Point randPoint = randomPointGenerator();
		Zombie z = new Zombie();
		zombies.add(z);
		z.setLocation(randPoint);
		((CharacterCell) map[randPoint.x][randPoint.y]).setCharacter(z);
		;

	}

	public static void spawnVaccine() {
		Point randPoint = randomPointGenerator();
		map[randPoint.x][randPoint.y] = new CollectibleCell(new Vaccine());

	}

	public static void spawnSupply() {
		Point randPoint = randomPointGenerator();
		map[randPoint.x][randPoint.y] = new CollectibleCell(new Supply());

	}

	public static void spawnTrapCell() {
		Point randPoint = randomPointGenerator();
		map[randPoint.x][randPoint.y] = new TrapCell();

	}

	public static void startGame(Hero h) {
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++)
				Game.map[i][j] = new CharacterCell(null);	
		
		((CharacterCell)Game.map[0][0]).setCharacter(h);
		h.setLocation(new Point(0, 0));
		Game.map[0][0].setVisible(true);
		availableHeroes.remove(h);
		heroes.add(h);
		
//
//		Hero h1=availableHeroes.remove(0);
//		((CharacterCell)Game.map[2][0]).setCharacter(h1);
//		h1.setLocation(new Point(2, 0));
//		Game.map[2][0].setVisible(true);
//		heroes.add(h1);
//
		
		
		for (int i = 0; i < 5; i++) {
			spawnVaccine();
			spawnSupply();
			spawnTrapCell();
		}
		for (int i = 0; i < 10; i++)
			addzombieRandLocation();
		Hero.setVis(h.getAdj(h.getLocation()));
//		Hero.setVis(h1.getAdj(h1.getLocation()));
	}

	private static boolean vaccineCellNOTpresent() {
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++) {
				if (Game.map[i][j] instanceof CollectibleCell
						&& ((CollectibleCell) Game.map[i][j]).getCollectible() instanceof Vaccine)
					return false;
			}
		return true;
	}

	private static boolean allvaccineInventoryEmpty() {
		for (Hero h : heroes) {
			if (!(h.getVaccineInventory().isEmpty()))
				return false;
		}
		return true;
	}

	public static boolean checkWin() {
		return vaccineCellNOTpresent() && allvaccineInventoryEmpty() && heroes.size() >= 5;
	}

	public static boolean checkGameOver() {
		return (heroes.isEmpty() || (vaccineCellNOTpresent() && allvaccineInventoryEmpty()));
	}
	public static boolean mapIsFull() {
		for(int i=0;i<15;i++)
			for(int j=0;j<15;j++) {
				if (Game.map[i][j] instanceof CharacterCell && ((CharacterCell)Game.map[i][j]).getCharacter()==null)
					return false;
			}
		return true;
	}
	public static void endTurn() throws InvalidTargetException, NotEnoughActionsException {
		ArrayList<Zombie> deepcloned=new ArrayList<Zombie>();
		for (int i=0;i<zombies.size();i++) {
			deepcloned.add(zombies.get(i));
		}
		for(Zombie z:deepcloned) {
			z.setTarget(null);
			z.attack();
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++)
				Game.map[i][j].setVisible(false);
		}
		for (Hero hero : heroes) {
			hero.setActionsAvailable(hero.getMaxActions());
			hero.setTarget(null);
			hero.setSpecialAction(false);
			Game.map[hero.getLocation().x][hero.getLocation().y].setVisible(true);
			Hero.setVis(hero.getAdj(hero.getLocation()));
		}
		for (Zombie z : zombies)
			z.setTarget(null);
		if (!mapIsFull())
			addzombieRandLocation();

	}
}
