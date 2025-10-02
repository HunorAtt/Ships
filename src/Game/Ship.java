package Game;

public class Ship {
    private static int counter = 0;
    private int shipNumber;

    private boolean isDestroyed;
    private int[] position;                 // [0] x position, [1] y position
    private Direction direction;
    private int length;

    public Ship(int[] position, Direction direction , int length) {
        shipNumber = counter;
        counter++;
        this.isDestroyed = false;
        this.position = position;
        this.direction = direction;
        this.length = length;
    }

    public int getShipNumber() {
        return shipNumber;
    }

    public int getLength() {
        return length;
    }

    public int[] getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed() {
        isDestroyed = true;
    }
}
