package Game;

import java.util.List;
import java.util.Random;

import Game.GamePhase;

/**
 * The game class is responsible for keeping track of the current state of the game.
 */
public class Game {
    private final Player[] players;
    private int roundCounter;
    private int currentPlayerIndex;
    private GamePhase phase;

    public Game() {
        players = new Player[2];
        roundCounter = 0;
        currentPlayerIndex = 0;
    }

    /**
     * Creates instances of players and their tables. After the table is created,
     * it's given to the player class.
     *
     * @param size The size of tables that is given to the players
     * @throws NullPointerException if {@code table} is {@code null}
     */
    public void start(int size) {
        Table table1 = new Table(size);
        players[0] = new Player(table1);

        Table table2 = new Table(size);
        players[1] = new Player(table2);

        phase = GamePhase.SETUP;
    }

    public void shoot(int[] position) {
        if (phase != GamePhase.FIGHT) {
            throw new IllegalStateException("Cannot call this function in " + phase);
        }

        nextPlayer().hit(position);

        if (!nextPlayer().isAlive()) {
            phase = GamePhase.FINISH;
        }

        nextTurn();
    }

    public void placeShip(int[] position, Direction direction, int length) {
        currentPlayer().placeShip(position, direction ,length);
    }

    /**
     * Creating a {@link Ship} with random parameters and tries to place it. It will try until a ship fits.
     */
    public void placeShipsAutomatically() {
        Random random = new Random();
        while (!currentPlayer().isTableSet()) {
            try {
                placeShip(new int[]{random.nextInt(currentPlayer().getGrid().length), random.nextInt(currentPlayer().getGrid()[0].length)}, Direction.values()[random.nextInt(Direction.values().length)], currentPlayer().getAvailableShipLengths().get(random.nextInt(currentPlayer().getAvailableShipLengths().size())));
            } catch (Exception _) {
                //expected exceptions if the random generated ship doesn't fit.
            }
        }
    }

    /**
     * Checks if all the {@link Player}'s {@link Table} are set.
     * If current player's table is set, it switches to the other.
     *
     * @return If the game is in the table setup phase.
     */
    public boolean isSetUpPhase() {
        if (currentPlayer().isTableSet()) {
            currentPlayerIndex = currentPlayerIndex == 0 ? 1 : 0;
        }

        if (players[0].isTableSet() && players[1].isTableSet()) {
            for(Player player : players) {
                player.hideShips();
            }
            phase = GamePhase.FIGHT;
            return false;
        } else {
            return true;
        }
    }

    public boolean isFightPhase() {
        return phase == GamePhase.FIGHT;
    }

    public boolean isFinishPhase() {
        return phase == GamePhase.FINISH;
    }

    public List<Integer> getAvailableShipLengths() {
        return currentPlayer().getAvailableShipLengths();
    }

    public Cell[][] getGrid() {
        if (phase == GamePhase.SETUP) {
            return currentPlayer().getGrid();
        } else {
            return nextPlayer().getGrid();
        }
    }

    public int getCurrentPlayer() {
        return currentPlayerIndex;
    }

    public boolean isValidLength(int Length) {
        return currentPlayer().isValidLength(Length);
    }

    /**
     * Advances the game to the next turn.
     * <p>
     * This method increments the round counter, calculates the index of the next player,
     * and completes the current turn's final adjustments to transition to the next turn.
     * </p>
     */
    private void nextTurn() {
        roundCounter++;
        currentPlayerIndex = roundCounter % 2;
    }

    private Player currentPlayer() {
        return players[currentPlayerIndex];
    }

    private Player nextPlayer() {
        return players[(roundCounter + 1) % 2];
    }
}
