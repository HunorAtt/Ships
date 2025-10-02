package Game;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final Cell[][] cells;           // cells[x][y]
    private List<Ship> ships;
    private List<Integer> available;


    public Table(int size) {
        this.cells = new Cell[size][size];
        available = new ArrayList<>();
        ships = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
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

    public void validatePosition(Ship ship) {
        int XPos = ship.getPosition()[0];
        int YPos = ship.getPosition()[1];
        int length = ship.getLength();
        Direction direction = ship.getDirection();


        for (int i = 0; i < length; i++) {
            int newX = XPos + i * direction.dx();
            int newY = YPos + i * direction.dy();


            if (newX < 0 || newY < 0 || newX > cells.length || newY > cells[0].length) {
                throw new IndexOutOfBoundsException("Ship is out of boundaries!\n");
            }

            if (cells[newX][newY].getState() == CellState.SHIP) {
                throw new IllegalArgumentException("There is a ship in the way!\n");
            }
        }
    }

    protected void placeShip(Ship ship) {
        int XPos = ship.getPosition()[0];
        int YPos = ship.getPosition()[1];
        int length = ship.getLength();
        Direction direction = ship.getDirection();

        ships.add(ship);
        available.remove((Integer) ship.getLength());

        for (int i = 0; i < length; i++) {
            int newX = XPos + i * direction.dx();
            int newY = YPos + i * direction.dy();
            cells[newX][newY].setState(CellState.SHIP);
        }

    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isTableSet() {
        return available.isEmpty();
    }

    public List<Integer> getAvailable() {
        return available;
    }
}
