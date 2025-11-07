package Game;

/**
 * Base for a ship on the {@link Player}'s {@link Table}.
 */
public class Ship {
    private static int counter = 0;
    private final int shipID;

    private boolean isDestroyed;
    private final int[] position;                 // [0] x position, [1] y position
    private final Direction direction;
    private final int length;
    private int hitPoint;

    /**
     * Constructor for a {@link Ship}. Creates it with the given attributes.
     *
     * @param position Starting position of the ship
     * @param direction The {@link Direction} the ship is facing
     * @param length The length of the ship
     */
    public Ship(int[] position, Direction direction , int length) {
        shipID = counter;
        counter++;
        this.isDestroyed = false;
        this.position = position;
        this.direction = direction;
        this.length = length;
        this.hitPoint = length;
    }

    public int getShipID() {
        return shipID;
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

    public void hit() {
        hitPoint--;

        if (hitPoint == 0) {
            isDestroyed = true;
        }
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
