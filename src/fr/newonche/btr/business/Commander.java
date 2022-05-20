package fr.newonche.btr.business;

import java.util.ArrayList;

public class Commander {

    private static Long counter = 0L;

    private Long identifier;
    private String name;
    private int shipTurnIndex;

    private ArrayList<Ship> ships;

    public Commander(String name) {
        identifier = ++counter;
        this.name = name;
        shipTurnIndex = 0;
        ships = new ArrayList<Ship>();
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public int getShipTurnIndex() {
        return shipTurnIndex;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public String makeMove(int x, int y, int code) {
        String name = ships.get(shipTurnIndex).getName();
        if (code == 2) ships.get(shipTurnIndex).fire(x, y);
        shipTurnIndex = (shipTurnIndex == ships.size() - 1 ? 0 : shipTurnIndex + 1);
        return name;
    }

    public void sleep() {
        // TODO : Implementation.
    }

    public void surrender() {
        // TODO : Implementation.
    }

}