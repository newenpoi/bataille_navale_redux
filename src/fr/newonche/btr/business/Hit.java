package fr.newonche.btr.business;

public class Hit {

    private int location_x;
    private int location_y;
    private int elapsedTurn;
    private boolean touche;
    private Ship instigator;
    private Ship target;

    public Hit(int x, int y, Ship instigator) {
        location_x = x;
        location_y = y;
        this.instigator = instigator;
        elapsedTurn = 0;
    }

    public int getLocation_x() {
        return location_x;
    }

    public int getLocation_y() {
        return location_y;
    }

    public int getElapsedTurn() {
        return elapsedTurn;
    }

    public void setElapsedTurn(int elapsedTurn) {
        this.elapsedTurn = elapsedTurn;
    }

    public Ship getInstigator() {
        return instigator;
    }

    public boolean isTouche() {
        return touche;
    }

    public void setTouche(boolean touche) {
        this.touche = touche;
    }
}