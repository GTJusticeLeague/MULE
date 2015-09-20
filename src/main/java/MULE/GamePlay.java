package MULE;

/**
 * Contains the main game logic
 */
public class GamePlay {
    public static GameConfig GAMECONFIG;
    //public static Board GAMEBOARD;
    private static boolean isGameOver = false;
    private static int round;

    public static void startGame() {
        Player[] players = GAMECONFIG.getPlayers();

        while(!isGameOver) {
            for (int i = 0; i < GAMECONFIG.getNumPlayers(); i++) {
                playRound(players[i]);
            }
            round++;
        }


    }

    private static void playRound(Player player) {
    }
}
