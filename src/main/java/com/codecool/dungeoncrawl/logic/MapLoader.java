package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.armor.Helmet;
import com.codecool.dungeoncrawl.logic.items.crown.Crown;
import com.codecool.dungeoncrawl.logic.items.key.Key;
import com.codecool.dungeoncrawl.logic.items.potion.Potion;
import com.codecool.dungeoncrawl.logic.items.weapon.Sword;


import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class MapLoader {

    public GameMap loadMap (InputStream is) {
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ' -> {
                            cell.setType(CellType.EMPTY);
                        }
                        case '#' -> {
                            cell.setType(CellType.WALL);
                        }
                        case '.' -> {
                            cell.setType(CellType.FLOOR);
                        }
                        case 'c' -> {
                            cell.setType(CellType.CLOSED_DOOR);
                        }
                        case 'l' -> {
                            cell.setType(CellType.LEAFY_TREE);
                        }
                        case '^' -> {
                            cell.setType(CellType.CONIFER);
                        }
                        case 'p' -> {
                            cell.setType(CellType.PATH);
                        }
                        case 'f' -> {
                            cell.setType(CellType.FORESTERS_LODGE);
                        }
                        case 'g' -> {
                            cell.setType(CellType.GRASS);
                        }
                        case 'e' -> {
                            cell.setType(CellType.CLOSED_EXIT);
                        }
                        case 'E' -> {
                            cell.setType(CellType.OPENED_EXIT);
                        }
                        case 's' -> {
                            cell.setType(CellType.FLOOR);
                            map.addMonster(new Skeleton(cell, MonstersStats.SKELETON.getHealthPoints(), MonstersStats.SKELETON.getAttackStrength()));
                        }
                        case '@' -> {
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(0 ,cell, PlayerDefaultStats.HEALTH.getDefaultValue(), "Mariusz"));
                        }
                        case 'k' -> {
                            cell.setType(CellType.FLOOR);
                            new Key(cell, "Blue key");
                        }
                        case 'P' -> {
                            cell.setType(CellType.FLOOR);
                            new Potion(cell, "potion", new Random().nextInt(10));
                        }
                        case '1' -> {
                            cell.setType(CellType.FLOOR);
                            new Sword(cell, "Two-handed sword", 4);
                        }
                        case 'S' -> {
                            cell.setType(CellType.FLOOR);
                            map.addMonster(new Spider(cell, MonstersStats.SPIDER.getHealthPoints(), MonstersStats.SPIDER.getAttackStrength()));
                        }
                        case 'W' -> {
                            cell.setType(CellType.FLOOR);
                            map.addMonster(new Warrior(cell, MonstersStats.WARRIOR.getHealthPoints(), MonstersStats.WARRIOR.getAttackStrength()));
                        }
                        case 'H' -> {
                            cell.setType(CellType.FLOOR);
                            new Helmet(cell, "Iron helmet", 2);
                        }
                        case 'C' -> {
                            cell.setType(CellType.FLOOR);
                            new Crown(cell, "Golden crown");
                        }
                        default -> {
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                        }
                    }
                }
            }
        }
        return map;
    }

}
