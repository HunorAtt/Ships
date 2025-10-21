package Game;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PlayerTest {

    @Test
    void isValidLengthTest() {
        Table table = new Table(10);
        Player player = new Player(table);

        assertFalse(player.isValidLength(1));
        assertTrue(player.isValidLength(2));
        assertTrue(player.isValidLength(3));
        assertTrue(player.isValidLength(4));
        assertTrue(player.isValidLength(5));
        assertFalse(player.isValidLength(6));
    }

    @Test
    void randomShipPlacementTest() {
        Table table = new Table(10);
        Player player = new Player(table);

        assertDoesNotThrow(player::randomShipPlacement);

        assertTrue(player.getTable().isTableSet());
    }

    @Test
    void getTableTest() {
        Table table = new Table(10);
        Player player = new Player(table);

        assertNotNull(player.getTable());
        assertEquals(table, player.getTable());

    }

    @Nested
    class PlaceShipsTests {

        @Test
        void placeShip() {
            Table table1 = new Table(10);
            Player player1 = new Player(table1);
            int cellCount = 0;

            assertDoesNotThrow(() -> player1.placeShip(new int[] {0, 0}, Direction.EAST, 5));
            assertDoesNotThrow(() -> player1.placeShip(new int[] {9, 0}, Direction.SOUTH, 4));
            assertDoesNotThrow(() -> player1.placeShip(new int[] {0, 9}, Direction.NORTH, 3));
            assertDoesNotThrow(() -> player1.placeShip(new int[] {9, 9}, Direction.WEST, 2));

            assertTrue(player1.getTable().isTableSet());

            for (int i = 0; i < table1.getCells().length; i++) {
                for (int j = 0; j < table1.getCells()[i].length; j++) {
                    if (table1.getCells()[i][j].getState() == CellState.SHIP) {
                        cellCount++;
                    }
                }
            }

            assertTrue(cellCount > 0);


            Table table2 = new Table(10);
            Player player2 = new Player(table2);
            cellCount = 0;

            assertDoesNotThrow(() -> player2.placeShip(new int[] {0, 0}, Direction.SOUTH, 5));
            assertDoesNotThrow(() -> player2.placeShip(new int[] {9, 0}, Direction.WEST, 4));
            assertDoesNotThrow(() -> player2.placeShip(new int[] {0, 9}, Direction.EAST, 3));
            assertDoesNotThrow(() -> player2.placeShip(new int[] {9, 9}, Direction.NORTH, 2));

            assertTrue(player2.getTable().isTableSet());

            for (int i = 0; i < table1.getCells().length; i++) {
                for (int j = 0; j < table1.getCells()[i].length; j++) {
                    if (table1.getCells()[i][j].getState() == CellState.SHIP) {
                        cellCount++;
                    }
                }
            }

            assertTrue(cellCount > 0);

        }

        @Test
        void placeShipInvalidLength() {
            Table table = new Table(10);
            Player player = new Player(table);


            assertDoesNotThrow(() -> player.placeShip(new int[] {0, 0}, Direction.EAST, 2));
            assertThrows(IllegalArgumentException.class, () -> player.placeShip(new int[] {0, 1}, Direction.EAST, 2));

        }

        @Test
        void placeShipIndexOutOfBoundaries() {
            Table table = new Table(10);
            Player player = new Player(table);
            //Top left corner
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {0, 0}, Direction.NORTH, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {0, 0}, Direction.WEST, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {-1, 0}, Direction.EAST, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {0, -1}, Direction.SOUTH, 2));
            //Top right corner
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {9, 0}, Direction.NORTH, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {9, 0}, Direction.EAST, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {10, 0}, Direction.WEST, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {9, -1}, Direction.SOUTH, 2));
            //Bottom left corner
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {0, 9}, Direction.SOUTH, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {0, 9}, Direction.WEST, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {-1, 9}, Direction.EAST, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {0, 10}, Direction.NORTH, 2));
            //Bottom right corner
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {9, 9}, Direction.SOUTH, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {9, 9}, Direction.EAST, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {10, 9}, Direction.WEST, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> player.placeShip(new int[] {9, 10}, Direction.NORTH, 2));

        }

        @Test
        void placeShipCollision() {
            Table table = new Table(10);
            Player player = new Player(table);

            assertDoesNotThrow(() -> player.placeShip(new int[] {0, 0}, Direction.EAST, 2));
            assertThrows(IllegalArgumentException.class, () -> player.placeShip(new int[] {0, 0}, Direction.SOUTH, 3));
            assertThrows(IllegalArgumentException.class, () -> player.placeShip(new int[] {1, 0}, Direction.SOUTH, 3));
            assertThrows(IllegalArgumentException.class, () -> player.placeShip(new int[] {1, 2}, Direction.NORTH, 3));
        }
    }
}