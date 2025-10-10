package UI;

import Game.Game;
import Game.Cell;
import Game.Direction;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Responsible for communicating with the user.
 * Only this or other classes in the main.java.UI package should communicate
 * with the user, to separate the game logic and main.java.UI.
 * This class collects inputs and forwards it to the {@link Game}.
 */

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final Game game = new Game();

    /**
     * This method starts the {@link Game}. Asks the user for {@link Game.Table} size and calls
     * the shipPlacement method until the table is set.
     */
    public void start() {
        int size = -1;
        while (size == -1) {
            System.out.println("What size should the map be (small, medium, big): ");
            switch (scanner.nextLine().toLowerCase()) {
                case ("small") :
                    size = 10;
                    break;
                case ("medium") :
                    size = 15;
                    break;
                case ("big") :
                    size = 20;
                    break;
                default:
                    System.out.println("That's not a valid answer!");
                    break;
            }
        }
        game.start(size);
        showTable();
        while (game.areTablesSet()) {
            shipPlacement();
        }

        showTable();
        System.out.println("Your table is set!");

        gameLoop();
    }

    /**
     * Getting input about ship parameters.
     * These parameters are:
     * <ul>
     *     <li>Length</li>
     *     <li>Starting coordinates</li>
     *     <li>Cardinal direction</li>
     * </ul>
     */
    public void shipPlacement() {
        while(true) {
            int length;
            int[] position = new int[2];
            Direction direction;


            String validNumbers = game.getPlayer().getTable().getAvailable().stream().map(String::valueOf).collect(Collectors.joining(", "));
            System.out.print("What length is your ship you want to add? (" + validNumbers + ") (if you want to skip this, type: random)\n");

            try {
                length = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                if (scanner.nextLine().equalsIgnoreCase("random")) {
                    game.getPlayer().randomShipPlacement();
                    break;
                }
                System.out.println("That's not a number!");
                scanner.nextLine();
                continue;
            }
            if (game.getPlayer().isValidLength(length)) {
                while (true) {
                    showTable();
                    System.out.println("What position should it start from? (example: A01) (you can back with: back)");
                    String temp = scanner.nextLine().toUpperCase();
                    if (temp.equals("BACK")) { break; }

                    if (temp.length() == 3 && Character.isLetter(temp.charAt(0)) && Character.isDigit(temp.charAt(1)) && Character.isDigit(temp.charAt(2))) {
                        position[0] = temp.charAt(0) - 'A';
                        position[1] = (temp.charAt(1) - '0') * 10 + (temp.charAt(2) - '1');
                        while (true) {
                            System.out.println("Where should it face? (North, South, East, West) (you can back with: back)");
                            temp = scanner.nextLine().toUpperCase();
                            if (temp.equals("BACK")) { break; }

                            try {
                                direction = Direction.valueOf(temp);
                            } catch (Exception e) {
                                System.out.println("That's not a direction!");
                                continue;
                            }

                            try {
                                game.getPlayer().placeShip(position, direction ,length);
                                showTable();
                                System.out.println("Ship placed!");
                                return;
                            } catch (Exception e) {
                                System.out.print(e.getMessage());
                            }

                        }
                    } else {
                        System.out.println("That's not a valid target!");
                    }

                }
            } else {
                System.out.println("You can only choose one of these numbers: " + validNumbers);
            }

        }
    }

    //TODO: <game loop>
    public void gameLoop() {
        while(true) {

        }
    }


    public void showTable() {
        Cell[][] cells = game.getPlayer().getTable().getCells();

        System.out.print("   ");

        for (int i = 0; i < cells.length; i++) {
            System.out.print((char)(i + 65) + " ");
        }
        System.out.println();

        for (int i = 0; i < cells.length; i++) {
            if (i < 9) System.out.print("0");
            System.out.print(i + 1);

            for (int j = 0; j < cells[i].length; j++) {
                if (cells[j][i].isVisibility()) {
                    switch (cells[j][i].getState()) {
                        case EMPTY :
                            System.out.print(" .");
                            break;
                        case SHIP :
                            System.out.print(" X");
                            break;
                    }
                } else {
                    System.out.print(" *");
                }
            }
            System.out.print("\n");
        }
    }

    // TODO: <winning/losing screen>
    public void finish() {
        scanner.close();
    }
}
