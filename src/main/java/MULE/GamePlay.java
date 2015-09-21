package MULE;

import java.util.Random;

/**
 * Contains the main game logic
 */
public class GamePlay {
    public static GameConfig GAMECONFIG;
    //public static Board GAMEBOARD;
    private static boolean isGameOver = false;
    private static int round;
    public static Player currentPlayer;

    public static void startGame() {
        Player[] players = GAMECONFIG.getPlayers();

        while (!isGameOver) {
            for (int i = 0; i < GAMECONFIG.getNumPlayers(); i++) {
                playRound(players[i]);
            }
            round++;
        }


    }

    public void buyLand(int x, int y) {
        Tile current = GAMECONFIG.getGAMEBOARD().getTiles()[x][y];
        Random rand = new Random();
        int price = 300 + round * rand.nextInt(101);
        if (currentPlayer.getMoney() < price) {
            return;
        } else if (current.getOwner() != null) {
            return;
        } else {
            current.setOwner(currentPlayer);
            currentPlayer.setMoney(currentPlayer.getMoney() - price);
        }
    }

    private static void playRound(Player player) {
    }
}
