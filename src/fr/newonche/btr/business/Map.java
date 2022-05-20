package fr.newonche.btr.business;

import fr.newonche.btr.business.utility.Color;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private static Random r = new Random();

    private boolean showEnemies;
    private int size_x;
    private int size_y;
    private int[][] board;
    private Hit lastKnownHit;
    private ArrayList<Hit> hits;
    private ArrayList<Commander> players;

    public Map() {
        showEnemies = true;
        size_x = 10;
        size_y = 10;
        board = new int[16][16];
        hits = new ArrayList<Hit>();
        players = new ArrayList<Commander>();
    }

    public Hit getLastKnownHit() {
        return lastKnownHit;
    }

    public void setPlayers(ArrayList<Commander> players) {
        this.players = players;
    }

    public ArrayList<Hit> getHits() {
        return hits;
    }

    public void addHit(Hit hit) {
        hits.add(hit);
    }

    public boolean canPlace(int x, int y, int size, Long orientation, int space) {
        if (board[y][x] != 0) return false;
        if (size == space) return true;

        if (orientation == 0L) return canPlace(x + 1, y, size, orientation, space + 1);
        else return canPlace(x, y + 1, size, orientation, space + 1);
    }

    public void place() {
        // On parcours les navires de chaque joueur en y ajoutant ses positions x y.
        for (Commander player : players) {
            for (Ship ship : player.getShips()) {
                do {
                    // Des coordonnées aléatoires sont générées.
                    int random_x = r.nextInt(((size_x - ship.getLength()) - 1) + 1) + 1;
                    int random_y = r.nextInt(((size_y - ship.getLength()) - 1) + 1) + 1;

                    if (canPlace(random_x, random_y, ship.getLength(), ship.getOrientation(), 0)) {
                        for (int l = 0; l < ship.getLength(); l++) {
                            if (ship.getOrientation() == 0) {
                                board[random_y][random_x + l] = player.getIdentifier().intValue();
                                ship.addPosition(new Position(random_x + l, random_y));
                            }
                            else {
                                board[random_y + l][random_x] = player.getIdentifier().intValue();
                                ship.addPosition(new Position(random_x, random_y + l));
                            }
                        }
                    }
                } while (ship.getPositions().size() < ship.getLength() - 1);
            }
        }
    }

    public void update() {
        for (Hit hit : hits) {
            // Si ce coup vient d'être crée.
            if (hit.getElapsedTurn() == 0) {
                for (Commander player : players) {
                    // Pour chaque navire de ce joueur.
                    for (Ship ship : player.getShips()) {
                        // Pour chaque coordonnées du navire.
                        for (Position position : ship.getPositions()) {
                            // Si le navire se trouve aux coordonnées de la frappe.
                            if (position.getPosition_x() == hit.getLocation_x() && position.getPosition_y() == hit.getLocation_y()) {
                                // Ajouter un coup sur le bateau.
                                ship.addHit(new Hit(hit.getLocation_x(), hit.getLocation_y(), hit.getInstigator()));
                                hit.setTouche(true);

                                // Ajuster le feedback de la carte.
                                board[hit.getLocation_y()][hit.getLocation_x()] = 8;
                                lastKnownHit = hit;
                            }
                        }
                    }
                }
            }

            // On ajuste le tour de cette frappe.
            hit.setElapsedTurn(hit.getElapsedTurn() + 1);

            // On met à jour les frappes qui sont tombées à l'eau.
            if (!hit.isTouche()) board[hit.getLocation_y()][hit.getLocation_x()] = 9;
        }
    }

    public void show() {
        char[] letters = {'N', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        System.out.println();

        for (int i = 0; i < letters.length; i++) {
            System.out.printf("%s ", letters[i]);
        }

        System.out.println();

        for (int i = 0; i < size_y; i++) {
            System.out.printf("%d ", i);

            for (int j = 0; j < size_x; j++) {
                if (board[i][j] == 2 && showEnemies) System.out.print(Color.red("O"));
                else if (board[i][j] == 1) System.out.print(Color.blue("O"));
                else if (board[i][j] == 9) System.out.print(Color.green("X"));
                else if (board[i][j] == 8) System.out.print(Color.yellow("X"));
                else System.out.print("O");

                System.out.print(" ");
            }
            System.out.println();
        }
    }
}