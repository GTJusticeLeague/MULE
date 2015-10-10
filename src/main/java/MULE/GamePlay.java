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
    public static int turnSeconds = 0;

    /**
     * Start the regular gameplay. Should be called at the end of land selection phase
     */
    public static void startGame() {
        nextPlayer(); // increments round number and initializes queue if empty
    }

    /**
     * End the current players turn by beginning the turn of the next player
     */
    public static void nextPlayer() {
        Player nextPlayer = playerOrder.peek();
        if (nextPlayer == null) {
            // We have gone through all players in the queue, round is over
            nextRound();
        }
        currentPlayer = playerOrder.poll();
        currentPlayer.initTimer();
        currentPlayer.startTime();
    }

    /**
     * Do all actions that should occur at the start of each round
     */
    private static void nextRound() {
        round++;
        calculateProduction();
        initializePlayerOrder();
    }

    /**
     * Calculate production for all tiles in the GAMEBOARD
     */
    private static void calculateProduction() {
        Tile[][] tiles = GAMECONFIG.getGAMEBOARD().getTiles();

        // Loop through all tiles, calculate their production
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].calculateProduction();
            }
        }
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
