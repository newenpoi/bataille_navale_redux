package fr.newonche.btr.business;

public class Destroyer extends Ship {

    private int torpedoCount;

    public Destroyer(Map map) {
        super(map, 3);
        this.category = "Destroyer";
        this.torpedoCount = 2;
    }

    public Destroyer(Map map, String name) {
        this(map);
        this.name = name;
    }
}