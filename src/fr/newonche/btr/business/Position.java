package fr.newonche.btr.business;

public class Position {
    private int position_x;
    private int position_y;

    public Position(int x, int y) {
        this.position_x = x;
        this.position_y = y;
    }

    public int getPosition_x() {
        return position_x;
    }

    public int getPosition_y() {
        return position_y;
    }
}
