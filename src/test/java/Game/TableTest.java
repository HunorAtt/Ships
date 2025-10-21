package Game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @Nested
    class tableTests {

        @Test
        void tableCellsInitializationTest() {
            int size = 10;
            Table table = new Table(size);
            int cellCount = 0;

            for (int i = 0; i < table.getCells().length; i++) {
                for (int j = 0; j < table.getCells()[i].length; j++) {
                    if (table.getCells()[i][j].getState() != null) {
                        cellCount++;
                    }
                }
            }

            assertEquals(size * size, cellCount);
        }

        @Test
        void tableSizeTest() {
            int[] sizes = {10, 15, 20};
            for (int size : sizes) {
                Table table = new Table(size);
                assertEquals(size, table.getCells().length);
                assertEquals(size, table.getCells()[0].length);
                assertNotNull(table.getAvailable());
                assertFalse(table.getAvailable().isEmpty());
            }
        }

        @Test
        void incorrectTableSize() {
            int[] sizes = {-1, 0, 9};
            for (int size : sizes) {
                assertThrows(IllegalArgumentException.class, () -> new Table(size));
            }
        }

    }

    @Test
    @Disabled("Already tested in the PlayerTest file")
    void validatePosition() {
        //Player test uses it indirectly through ship placement
    }

    @Test
    @Disabled("Already tested in the PlayerTest file")
    void placeShip() {
        //Player test uses it indirectly through its own ship placement method
    }

    @Test
    void isTableSet() {
        Table table = new Table(10);
        Player player = new Player(table);

        assertFalse(table.isTableSet());
        player.randomShipPlacement();
        assertTrue(table.isTableSet());
    }

}