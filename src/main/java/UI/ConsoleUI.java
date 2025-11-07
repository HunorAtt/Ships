package UI;

import Game.Game;
import Game.Cell;
import Game.Direction;
import Game.Table;
import Game.Player;

import java.io.IOException;
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
    private final Game game;

    public ConsoleUI(Game game) {
        this.game = game;
    }

    /**
     * This method starts the {@link Game}. Asks the user for {@link Table} size.
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
        tableSetup();
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

            showTable();
            String validNumbers = game.getAvailableShipLengths().stream().map(String::valueOf).collect(Collectors.joining(", "));
            System.out.print("What length is your ship you want to add? (" + validNumbers + ") (if you want to skip this, type: random)\n");

            try {
                length = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                if (scanner.nextLine().equalsIgnoreCase("random")) {
                    game.placeShipsAutomatically();
                    break;
                }
                System.out.println("That's not a number!");
                continue;
            }
            if (game.isValidLength(length)) {
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
                                game.placeShip(position, direction ,length);
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


    /**
     * Sets up every {@link Player}'s {@link Table}.
     */
    public void tableSetup() {
        while (game.isSetUpPhase()) {
            clearConsole();
            shipPlacement();
            showTable();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        clearConsole();

        System.out.println("Your tables are set!");

        gameLoop();
    }

    //TODO: <game loop>
    public void gameLoop() {
        while(game.isFightPhase()) {
            System.out.println(game.getCurrentPlayer() + ". player's turn!");
            showTable();
            System.out.println("Where do you want to shoot? (example: A01)");

            int[] position = new int[2];
            String temp = scanner.nextLine().toUpperCase();
            if (temp.length() == 3 && Character.isLetter(temp.charAt(0)) && Character.isDigit(temp.charAt(1)) && Character.isDigit(temp.charAt(2))) {
                position[0] = temp.charAt(0) - 'A';
                position[1] = (temp.charAt(1) - '0') * 10 + (temp.charAt(2) - '1');
            }

            try {
                game.shoot(position);
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

            showTable();

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            clearConsole();
        }

        finish();
    }

    /**
     * In setup phase shows the current player's {@link Table}. In fight phase it shows the enemy's {@link Table}.
     */
    private void showTable() {
        Cell[][] grid = game.getGrid();

        System.out.print("   ");

        for (int i = 0; i < grid.length; i++) {
            System.out.print((char)(i + 65) + " ");
        }
        System.out.println();

        for (int i = 0; i < grid.length; i++) {
            if (i < 9) System.out.print("0");
            System.out.print(i + 1);

            for (int j = 0; j < grid[i].length; j++) {
                if (grid[j][i].isVisibility()) {
                    switch (grid[j][i].getState()) {
                        case EMPTY :
                            System.out.print(" .");
                            break;
                        case SHIP :
                            System.out.print(" " + grid[j][i].getShipLenght());
                            break;
                        case HIT :
                            System.out.print(" X");
                            break;
                        case MISS:
                            System.out.print(" ~");
                    }
                } else {
                    System.out.print(" *");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * WIP. For now it just print
     */
    private void clearConsole() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    // TODO: <winning/losing screen>
    public void finish() {
        System.out.println(game.getCurrentPlayer() + " player wins!");
        scanner.close();
    }
}
