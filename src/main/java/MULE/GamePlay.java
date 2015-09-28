package MULE;

import java.util.Stack;

/**
 * Contains the main game logic
 */
public class GamePlay {
    public static GameConfig GAMECONFIG;
    public static int round = 0;
    public static Player currentPlayer;
    private static Stack<Player> playerOrder = new Stack<>();

    public static void startGame() {
        nextPlayer(); // increments round number and initializes stack if nothing on stack
    }

    /**
     * End the current players turn by beginning the turn of the next player
     */
    public static void nextPlayer() {
        Player nextPlayer = playerOrder.pop();
        if (nextPlayer == null) {
            // We have gone through all players in the stack, round is over
            round++;
            initializePlayerOrder();
            nextPlayer = playerOrder.pop();
        }
        currentPlayer = nextPlayer;
        // TODO: Set up timer for player
    }

    /**
     * Sets up the stack of players in the correct order (based on score)
     */
    private static void initializePlayerOrder() {
        // TODO: Reorder this so that the players are pushed in order of score
        for (int i = 0; i < GAMECONFIG.getNumPlayers(); i++) {
            Player temp = GAMECONFIG.getPlayers()[i];
            playerOrder.push(temp);
        }
    }
}
