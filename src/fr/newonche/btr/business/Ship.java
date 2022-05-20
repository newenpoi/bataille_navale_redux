package fr.newonche.btr.business;
import java.util.*;

public abstract class Ship {

    private static Long counter = 0L;

    protected Long identifier;
    protected String name;
    protected String category;
    protected int length;
    protected Long orientation;
    protected boolean sunk;

    protected Map map;
    protected Commander commander; // TODO : Le navire a un commandant, mais...

    protected ArrayList<Hit> hits;
    protected ArrayList<Position> positions;

    public Ship(Map map, int length) {
        this.identifier = ++counter;
        this.name = "Unidentified";
        this.length = length;
        this.orientation = Math.round(Math.random());
        this.map = map;
        hits = new ArrayList<Hit>();
        positions = new ArrayList<Position>();
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getLength() {
        return length;
    }

    public Long getOrientation() {
        return orientation;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public ArrayList<Hit> getHits() {
        return hits;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public void addHit(Hit hit) {
        hits.add(hit);
    }

    public void move(int x, int y) {
        // TODO : Code.
    }

    public void fire(int x, int y) {
        map.addHit(new Hit(x, y, this));
    }

}