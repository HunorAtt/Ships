package Game;

/**
 * The game class is responsible for keeping track of the current state of the game.
 */

public class Game {
    private Player player1;
    private Player player2;

    private Player currentPlayer;

    /**
     * Creates instances of players and their tables. After the table is created,
     * it's given to the player class.
     *
     * @param size The size of tables that is given to the players
     * @throws NullPointerException if {@code table} is {@code null}
     */
    public void start(int size) {
        Table table1 = new Table(size);
        player1 = new Player(table1);


        Table table2 = new Table(size);
        player2 = new Player(table2);


    }

    public void shoot() {

    }

    public boolean areTablesSet() {
        return player1.getTable().isTableSet() && player2.getTable().isTableSet();
    }

    public Player getCurrentPlayer() {
        return CurrentPlayer;
    }
}
