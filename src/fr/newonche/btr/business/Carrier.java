package fr.newonche.btr.business;

public class Carrier extends Ship {

    private int aircraftCount;

    public Carrier(Map map) {
        super(map, 4);
        this.category = "Portavions";
        this.aircraftCount = 3;
    }

    public Carrier(Map map, String name) {
        this(map);
        this.name = name;
    }
}