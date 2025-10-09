package Game;

/**
 * The game class is responsible for keeping track of the current state of the game.
 */

public class Game {
    private Player player;

    /**
     * Creates instances of players and their tables. After the table is created
     * its given to the player class.
     *
     * @param size The size of tables that is given to the players
     * @throws NullPointerException if {@code table} is {@code null}
     */
    public void start(int size) {
        Table table = new Table(size);
        player = new Player(table);
    }

    public boolean areTablesSet() {
        return (player.getTable().isTableSet());
    }

    public Player getPlayer() {
        return player;
    }
}
