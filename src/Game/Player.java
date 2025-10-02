package Game;

import java.util.Random;

public class Player {
    private final Table table;

    public Player(Table table) {
        this.table = table;
    }

    public boolean isValidLength(int length) {
        return table.getAvailable().contains(length);
    }

    public void placeShip(int[] position, Direction direction ,int length) {
        Ship ship = new Ship(position, direction ,length);
        table.validatePosition(ship);
        table.placeShip(ship);
    }

    public void randomPlacement() {
        Random random = new Random();
        while (!table.isTableSet()) {
            try {
                placeShip(new int[]{random.nextInt(table.getCells().length), random.nextInt(table.getCells()[0].length)}, Direction.values()[random.nextInt(Direction.values().length)], table.getAvailable().getLast());
            } catch (Exception e) {
                continue;
            }
        }
    }

    public Table getTable() {
        return table;
    }
}
