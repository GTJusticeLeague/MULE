package MULE;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Contains the main game logic
 */
public class GamePlay {
    public static GameConfig GAMECONFIG;
    public static int round = 0;
    public static Player currentPlayer;
    private static Queue<Player> playerOrder = new PriorityQueue<>();

    public static void startGame() {
        nextPlayer(); // increments round number and initializes stack if nothing on stack
    }

    /**
     * End the current players turn by beginning the turn of the next player
     */
    public static void nextPlayer() {
        Player nextPlayer = playerOrder.peek();
        if (nextPlayer == null) {
            // We have gone through all players in the stack, round is over
            round++;
            initializePlayerOrder();
        }
        currentPlayer = playerOrder.poll();
        currentPlayer.initTimer();
        currentPlayer.startTime();
    }

    /**
     * Sets up the stack of players in the correct order (based on score)
     */
    private static void initializePlayerOrder() {
        for (int i = 0; i < GAMECONFIG.getNumPlayers(); i++) {
            Player temp = GAMECONFIG.getPlayers()[i];
            playerOrder.add(temp);
        }
    }
}
