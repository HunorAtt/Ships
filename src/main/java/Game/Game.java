package Game;

import java.util.ArrayList;

/**
 * The game class is responsible for keeping track of the current state of the game.
 */

public class Game {
    private final Player[] players;
    private int roundCounter;
    private int currentPlayerIndex;
    private String phase;

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


    }

    public void shoot() {

    }

    public boolean isTableSetUpPhase() {
        if (players[currentPlayerIndex].getTable().isTableSet()) {
            currentPlayerIndex = currentPlayerIndex == 0 ? 1 : 0;
        }

        return players[0].getTable().isTableSet() && players[1].getTable().isTableSet();
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    /**
     * Advances the game to the next turn.
     * <p>
     * This method increments the round counter, calculates the index of the next player,
     * and completes the current turn's final adjustments to transition to the next turn.
     * </p>
     */
    public void nextTurn() {
        roundCounter++;
        currentPlayerIndex = roundCounter % 2;
    }
}
