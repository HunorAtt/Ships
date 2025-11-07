package Game;

import java.util.List;

/**
 * Player class is the base for players.
 * Manages their {@link Table}.
 */

public class Player {
    private final Table table;
    private boolean isAlive;

    /**
     * Player creation with {@link Table}.
     *
     * @param table The {@link Table} that the player can interact with.
     */
    public Player(Table table) {
        this.table = table;
        isAlive = true;
    }

    /**
     * Creating a {@link Ship} with the given parameters and checking if it can be placed,
     * then giving it to its table to be placed.
     *
     * @param position Starting position of the ship
     * @param direction The {@link Direction} the ship is facing
     * @param length The length of the ship
     *
     * @throws IllegalArgumentException if {@code length} is not available
     * @throws IndexOutOfBoundsException if the {@link Ship} is out of boundaries of the {@link Table}
     * @throws IllegalArgumentException if another {@link Ship} is in the way
     */
    public void placeShip(int[] position, Direction direction, int length) {
        Ship ship = new Ship(position, direction ,length);
        if (!isValidLength(length)) {
            throw new IllegalArgumentException("The ship length is not available!");
        }
        table.validatePositionShip(ship);
        table.placeShip(ship);
    }

    public void hit(int[] position) {
        table.validatePosition(position);
        table.hit(position);

        if (table.isEmpty()) {
            isAlive = false;
        }
    }

    public void hideShips() {
        table.hideShips();
    }

    /**
     * @param length the length of the ship
     * @return if the ship length is available
     */
    public boolean isValidLength(int length) {
        return table.getAvailable().contains(length);
    }

    public List<Integer> getAvailableShipLengths() {
        return table.getAvailable();
    }

    public Cell[][] getGrid() {
        return table.getGrid();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isTableSet() {
        return table.isTableSet();
    }

}
