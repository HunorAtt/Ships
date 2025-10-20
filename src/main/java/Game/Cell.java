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

    public Cell() {
        this.state = CellState.EMPTY;
        this.visibility = false;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
