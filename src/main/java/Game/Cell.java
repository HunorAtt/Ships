package Game;

/**
 * A base for a single cell on the {@link Table}.
 * It has attributes of:
 * <ul>
 *     <li>{@link CellState}</li>
 *     <li>Visibility</li>
 * </ul>
 */
public class Cell {
    private CellState state;
    private boolean visibility;
    private int shipID;
    private int shipLenght;

    public Cell() {
        this.state = CellState.EMPTY;
        this.visibility = true;
    }

    public void setShip(Ship ship) {
        this.state = CellState.SHIP;
        shipID = ship.getShipID();
        shipLenght = ship.getLength();
    }

    public void shoot() {
        if (state == CellState.EMPTY) {
            state = CellState.MISS;
        } else if (state == CellState.SHIP) {
            state = CellState.HIT;
        } else {
            throw new IllegalArgumentException("You already shot here!");
        }
    }

    public int getShipID() {
        return shipID;
    }

    public int getShipLenght() {
        return shipLenght;
    }

    public CellState getState() {
        return state;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
