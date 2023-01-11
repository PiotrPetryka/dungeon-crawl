package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;

import java.util.ArrayList;

public class Player extends Actor {

    private ArrayList<Item> equipment = new ArrayList<>();
    public void addItemToEq(Item item) {
        equipment.add(item);
    }




    private int attackStrength =5;

    public Player(Cell cell) {
        super(cell);
    }

    @Override
    public int getAttackStrength() {
        return attackStrength;
    }

    public String getTileName() {
        return "player";
    }
    public ArrayList<Item> getEquipment(){
        return equipment;
    }

    // TODO: usunąc bo to metoda testowa wypełniająca ekwipunek
//    public void testFillEq(){
//        equipment.add(new Weapon("Sword"));
//        equipment.add(new Weapon("Bow"));
//        equipment.add(new Weapon("Axe"));
//        equipment.add(new Weapon("Halabard"));
//    }
    @Override
    public void fight (Actor skeleton){

        while (skeleton.getHealth()>0 && getHealth()>0) {
            if ((skeleton.getHealth() - attackStrength)<=0) {
                skeleton.setHealth((skeleton.getHealth() - attackStrength));
                this.getCell().setActor(null);
                System.out.println("You win");
                skeleton.getCell().setActor(this);
                this.setCell(skeleton.getCell());
            }
            else {
                skeleton.setHealth((skeleton.getHealth() - attackStrength));
                this.setHealth(getHealth() - skeleton.getAttackStrength());
            }
        }
    }

    @Override
    public boolean canGoThrough(Cell cell) {
        if (cell.getType() == CellType.CLOSED_DOOR &&
                equipment.stream()
                        .anyMatch(o -> (o instanceof Key))) {
            cell.setType(CellType.OPENED_DOOR);
            return true;
        }  else return cell.getType() != CellType.WALL && cell.getType() != CellType.CLOSED_DOOR;
    }
}
