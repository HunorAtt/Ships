package Game;

import java.util.Random;

/**
 * Player class is the base for players.
 * This class have methods for:
 * <ul>
 *     <li>Checking if a ship length is available</li>
 *     <li>{@link Ship} placement</li>
 *     <li>Random {@link Ship} placement</li>
 * </ul>
 */

public class Player {
    private final Table table;

    /**
     * Player creation with {@link Table}.
     *
     * @param table The {@link Table} that the player can interact with.
     */
    public Player(Table table) {
        this.table = table;
    }

    /**
     * @param length the length of the ship
     * @return if the ship length is available
     */
    public boolean isValidLength(int length) {
        return table.getAvailable().contains(length);
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
    public void placeShip(int[] position, Direction direction ,int length) {
        Ship ship = new Ship(position, direction ,length);
        if (!isValidLength(length)) {
            throw new IllegalArgumentException("The ship length is not available!");
        }
        table.validatePosition(ship);
        table.placeShip(ship);
    }

    /**
     * Creating a {@link Ship} with random parameters and checking if it can be placed,
     * then giving it to its table to be placed.
     */
    public void randomShipPlacement() {
        Random random = new Random();
        while (!table.isTableSet()) {
            try {
                placeShip(new int[]{random.nextInt(table.getCells().length), random.nextInt(table.getCells()[0].length)}, Direction.values()[random.nextInt(Direction.values().length)], table.getAvailable().getLast());
            } catch (Exception _) {
            }
        }
    }

    public Table getTable() {
        return table;
    }
}
