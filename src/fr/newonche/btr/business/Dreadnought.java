package fr.newonche.btr.business;

public class Dreadnought extends Ship {

    private int blastRadius;

    public Dreadnought(Map map) {
        super(map, 5);
        this.category = "Cuirassé";
        this.blastRadius = 2;
    }

    public Dreadnought(Map map, String name) {
        this(map);
        this.name = name;
    }

}