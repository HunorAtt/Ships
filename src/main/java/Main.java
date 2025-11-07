import Game.Game;

/**
 * Starts the main.java.UI.
 */
public class Main {
    static private Game game = new Game();

    public static void main(String[] args) {

        new UI.ConsoleUI(game).start();

    }
}
