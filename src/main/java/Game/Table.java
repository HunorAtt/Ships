package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@link Player}'s table which they can interact with.
 * It builds up of {@link Cell}s.
 */
public class Table {
    private final Cell[][] grid;           // cells[x coordinate][y coordinate]
    private Map<Integer, Ship> ships;
    private List<Integer> available;


    /**
     * Creating a table with the same length and width.
     *
     * @param size The size of length and width.
     */
    public Table(int size) {
        this.grid = new Cell[size][size];
        available = new ArrayList<>();
        ships = new HashMap<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell();
            }
        }

        if (size > 15) {
            for (int i = 0; i < 3; i++) {
                available.add(2);
            }
            for (int i = 0; i < 3; i++) {
                available.add(3);
            }
            for (int i = 0; i < 2; i++) {
                available.add(4);
            }
            available.add(5);
        } else if (size > 10) {
            for (int i = 0; i < 2; i++) {
                available.add(2);
            }
            for (int i = 0; i < 2; i++) {
                available.add(3);
            }
            available.add(4);
            available.add(5);
        } else {
            available.add(2);
            available.add(3);
            available.add(4);
            available.add(5);
        }
    }

    /**
     * Checks if the given {@link Ship} can be placed on the {@link Table}.
     *
     * @param ship The {@link Ship} to be to examined
     *
     * @throws IndexOutOfBoundsException if the {@link Ship} is out of boundaries of the {@link Table}
     * @throws IllegalArgumentException if another {@link Ship} is in the way
     */
    public void validatePositionShip(Ship ship) {
        int XPos = ship.getPosition()[0];
        int YPos = ship.getPosition()[1];
        int length = ship.getLength();
        Direction direction = ship.getDirection();


        for (int i = 0; i < length; i++) {
            int newX = XPos + i * direction.dx();
            int newY = YPos + i * direction.dy();


            if (newX < 0 || newY < 0 || newX > grid.length || newY > grid[0].length) {
                throw new IndexOutOfBoundsException("Ship is out of boundaries!\n");
            }

            if (grid[newX][newY].getState() == CellState.SHIP) {
                throw new IllegalArgumentException("There is a ship in the way!\n");
            }
        }
    }

    /**
     * Checks if provided position is on the grid.
     *
     * @param position x and y coordinates of the position
     */
    public void validatePosition(int[] position) {
        int XPos = position[0];
        int YPos = position[1];


        if (XPos < 0 || YPos < 0 || XPos > grid.length || YPos > grid[0].length) {
            throw new IndexOutOfBoundsException("This position is not part of the grid!\n");
        }
    }

    /**
     * Places a {@link Ship} on the table without checking it. (For invocation by {@link Player},
     * typically not to be used by other classes)
     *
     * @param ship The {@link Ship} to be placed
     */
    void placeShip(Ship ship) {
        int XPos = ship.getPosition()[0];
        int YPos = ship.getPosition()[1];
        int length = ship.getLength();
        Direction direction = ship.getDirection();

        ships.put(ship.getShipID(), ship);
        available.remove((Integer) ship.getLength());

        for (int i = 0; i < length; i++) {
            int newX = XPos + i * direction.dx();
            int newY = YPos + i * direction.dy();
            grid[newX][newY].setShip(ship);
        }

    }

    void hit(int[] position) {
        grid[position[0]][position[1]].shoot();
        grid[position[0]][position[1]].setVisibility(true);

        if (grid[position[0]][position[1]].getState() == CellState.HIT) {
            ships.get(grid[position[0]][position[1]].getShipID()).hit();

            if (ships.get(grid[position[0]][position[1]].getShipID()).isDestroyed()) {
                ships.remove(grid[position[0]][position[1]].getShipID());
            }
        }
    }

    public void hideShips() {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                cell.setVisibility(false);
            }
        }
    }

    public boolean isEmpty() {
        return ships.isEmpty();
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public boolean isTableSet() {
        return available.isEmpty();
    }

    public List<Integer> getAvailable() {
        return available;
    }
}
