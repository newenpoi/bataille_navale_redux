package fr.newonche.btr.business;

import fr.newonche.btr.business.utility.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Game {
    private static Long counter = 0L;
    private static String[] ships = {"Destroyer", "Cuirassé", "Utilitaire", "Portavions"};

    private Long id;
    private int playerCount;
    private int turn;
    private int maxShipPerPlayer;
    private boolean playing = true;
    private Map map;
    private ArrayList<Commander> players;
    private Scanner sc = new Scanner(System.in);

    private Game() {
        id = ++counter;
    }

    public Game(int playerCount, int maxShipPerPlayer) {
        this();
        this.playerCount = playerCount;
        this.maxShipPerPlayer = maxShipPerPlayer;
        turn = 0;
        map = new Map();
        players = new ArrayList<Commander>();
    }

    private void addPlayers() {
        do {
            System.out.println("Entrez le nom de votre Commandant :");
            String nickname = sc.nextLine();
            this.players.add(new Commander(nickname));
        } while (players.size() < playerCount);
    }

    private void addShips() {
        // On crée les navires de chacun de nos commandants.
        do {
            System.out.printf("Quel type de navire souhaiteriez-vous créer commandant ? [%d/%d]\n",
                    players.get(0).getShips().size() + 1, maxShipPerPlayer);
            String choice = askChoice(ships, ships.length, 1);

            // TODO : Peut on factoriser cette partie ?
            if (choice.equals("Destroyer")) players.get(0).getShips().add(new Destroyer(map, "Ayanami"));
            if (choice.equals("Cuirassé")) players.get(0).getShips().add(new Dreadnought(map, "Enterprise"));
            if (choice.equals("Utilitaire")) players.get(0).getShips().add(new Utility(map, "Ming"));
            if (choice.equals("Portavions")) players.get(0).getShips().add(new Carrier(map, "Le Gaulois"));

            System.out.printf("Un navire [%s] est ajouté à votre armada.\n", choice);

        } while (players.get(0).getShips().size() < maxShipPerPlayer);

        // Génère les navires de l'ordinateur.
        System.out.println("Génération des navires de l'ordinateur.");
        players.get(1).getShips().add(new Destroyer(map));

        // On défini les joueurs sur la carte et on place leurs navires.
        map.setPlayers(players);
        map.place();
    }

    private void loop() {
        // Gérer les actions.
        do {
            map.show();

            Ship currPlayerShip = players.get(0).getShips().get(players.get(0).getShipTurnIndex());
            System.out.printf("\nEntrez les coordonnées de tir du %s %s [ex : A4] : ", currPlayerShip.getCategory(), currPlayerShip.getName());
            String coordinates = sc.nextLine();
            int x = 0, y = 0;

            try {
                x = Character.getNumericValue(coordinates.charAt(0));
                y = Character.getNumericValue(coordinates.charAt(1));
            } catch (IllegalArgumentException e) {
                System.out.println("Veuillez entrer des coordonnées réelles commandant.");
            }

            if (x > 19 || y > 9) continue;
            String name = players.get(0).makeMove(x - 10, y, 2);
            map.update();

            // Si le dernier coup connu sur un navire est différent du dernier coup établi.
            if (map.getLastKnownHit() != map.getHits().get(map.getHits().size() - 1)) System.out.println("Rien, mon commandant.");
            else System.out.printf("\n%s a touché sa cible !\n", currPlayerShip.getName());

            this.update();
        } while (playing);
    }

    private String askChoice(String[] list, int max, int min) {
        int choice = 0;

        System.out.printf("Entrez un choix entre %d et %d :\n", min, max);

        // Affiche les choix de la liste en commençant par 1 dans le but ergonomique.
        for (int i = 0; i < max; i++) {
            System.out.printf("%d. %s\n", i + 1, list[i]);
        }

        do {
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.printf("Vous devez entrer un choix valide entre %d et %d !\n", min, max);
            }
        } while (choice < min || choice > max);

        return list[choice - 1];
    }

    private void update() {
        for (Commander player : players) {
            for (Ship ship : player.getShips()) {
                if (ship.getHits().size() == ship.getLength()) {
                    ship.setSunk(true);
                    System.out.print(Color.red("Un navire vient d'être coulé !\n"));
                }
            }

            for (Iterator<Ship> iterator = player.getShips().iterator(); iterator.hasNext(); ) {
                Ship ship = iterator.next();
                if (ship.isSunk()) {
                    iterator.remove();
                }
            }

            if (player.getShips().isEmpty()) {
                System.out.printf("Le commandant %s est hors jeu !\n", player.getName());
                playing = false;
            }
        }
    }

    public void init() {
        addPlayers();
        addShips();
        loop();

        System.out.println("La partie est terminée !");
    }
}