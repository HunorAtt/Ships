package Game;

public class Game {
    private Player player;

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
