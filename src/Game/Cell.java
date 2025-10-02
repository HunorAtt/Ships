package Game;

public class Cell {
    private CellState state;
    private boolean visibility;

    public Cell() {
        this.state = CellState.EMPTY;
        this.visibility = true;
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
