package fr.newonche.btr.business;

public class Utility extends Ship {

    private int healingAmount;

    public Utility(Map map) {
        super(map, 2);
        this.category = "Utilitaire";
        this.healingAmount = 1;
    }

    public Utility(Map map, String name) {
        this(map);
        this.name = name;
    }

    public void heal() {
        this.hits.clear();
    }

}