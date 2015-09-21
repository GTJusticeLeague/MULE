package MULE;

/**
 * Contains the main game logic
 */
public class GamePlay {
    public static GameConfig GAMECONFIG;
    //public static Board GAMEBOARD;
    private static boolean isGameOver = false;
    public static int round = 1;
    public static Player currentPlayer;

    public static void startGame() {
        Player[] players = GAMECONFIG.getPlayers();

        while (!isGameOver) {
            for (int i = 0; i < GAMECONFIG.getNumPlayers(); i++) {
                playTurn(players[i]);
            }
            round++;
        }
    }



    private static void playTurn(Player player) {
        currentPlayer = player;
    }
}
