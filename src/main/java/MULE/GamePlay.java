package MULE;

import java.util.Random;

/**
 * Contains the main game logic
 */
public class GamePlay {
    public static GameConfig GAMECONFIG;
    //public static Board GAMEBOARD;
    private static boolean isGameOver = false;
    private static int round = 1;
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

    public static void buyLand(int x, int y) {
        //HARDCODED
        currentPlayer = GAMECONFIG.players[0];

        Tile current = GAMECONFIG.getGAMEBOARD().getTiles()[x][y];
        Random rand = new Random();
        int price = 300 + round * rand.nextInt(101);
        if (currentPlayer.getMoney() >= price && current.getOwner() == null) {
            current.setOwner(currentPlayer);
            currentPlayer.setMoney(currentPlayer.getMoney() - price);
        }
       // GameScreenController.UpdatePlayer(0);
    }

    private static void playTurn(Player player) {
        currentPlayer = player;
    }
}
